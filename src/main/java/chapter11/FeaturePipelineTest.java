package chapter11;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by simjunbo on 2018-04-09.
 */
public class FeaturePipelineTest {
    public static void main(String[] args) {
        Shop4 shop = new Shop4("Test");

        long start = System.nanoTime();
        System.out.println(shop.getPrice("BestPrice"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");

        long start2 = System.nanoTime();
        System.out.println(findPrices2("myPhone275"));
        long duration2 = (System.nanoTime() - start2) / 1_000_000;
        System.out.println("Done in " + duration2 + " msecs");

        findPricesStream("myPhone").map(f -> f.thenAccept(System.out::println));

        //CompletableFuture 종료에 반응하기
        long start3 = System.nanoTime();
        CompletableFuture[] futures = (CompletableFuture[])findPricesStream("myPhone")
                .map(f -> f.thenAccept(System.out::println))
                .toArray(size -> new CompletableFuture[size]);

        CompletableFuture.anyOf(futures).join();
        long duration3 = (System.nanoTime() - start3) / 1_000_000;
        System.out.println("Done in " + duration3 + " msecs");
    }

    public static List<Shop4> shops = Arrays.asList(
            new Shop4("BestPrice"),
            new Shop4("LetsSaveBig"),
            new Shop4("MyFavoriteShop"),
            new Shop4("BuyItAll"),
            new Shop4("ShopEasy price"));

    // 할인 서비스 사용
    public static List<String> findPrices(String product) {
        return shops.stream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDisCount)
                .collect(Collectors.toList());
    }

    private static final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    // 동기 작업과 비동기 작업 조합하기
    public static List<String> findPrices2(String product) {
        List<CompletableFuture<String>> pricefutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse)) // thenApply는 타입을 변환
                .map(future -> future.thenCompose(quote ->
                        CompletableFuture.supplyAsync(() -> Discount.applyDisCount(quote), executor)))
                .collect(Collectors.toList());

        return pricefutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public List<String> findPricesInUSD(String product) {
        List<CompletableFuture<Double>> priceFutures = new ArrayList<>();
        for (Shop4 shop : shops) {
            CompletableFuture<Double> futurePriceInUSD =
                    CompletableFuture.supplyAsync(() -> shop.getPriceToDobule(product))
                            .thenCombine(
                                    CompletableFuture.supplyAsync(
                                            () ->  ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD)),
                                    (price, rate) -> price * rate
                            );
            priceFutures.add(futurePriceInUSD);
        }

        List<String> prices = priceFutures
                .stream()
                .map(CompletableFuture::join)
                .map(price -> /*shop.getName() +*/ " price is " + price)
                .collect(Collectors.toList());
        return prices;
    }

    // Java7 방식
    public List<String> findPricesInUSDJava7(String product) {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<Double>> priceFutures = new ArrayList<>();
        for (Shop4 shop : shops) {
            final Future<Double> futureRate = executor.submit(new Callable<Double>() {
                public Double call() {
                    return ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD);
                }
            });
            Future<Double> futurePriceInUSD = executor.submit(new Callable<Double>() {
                public Double call() {
                    try {
                        double priceInEUR = shop.getPriceToDobule(product);
                        return priceInEUR * futureRate.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            });
            priceFutures.add(futurePriceInUSD);
        }
        List<String> prices = new ArrayList<>();
        for (Future<Double> priceFuture : priceFutures) {
            try {
                prices.add(/*shop.getName() +*/ " price is " + priceFuture.get());
            }
            catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return prices;
    }

    public static Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->
                        CompletableFuture.supplyAsync(
                                () -> Discount.applyDisCount(quote), executor)));
    }
}

class Discount {
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public static String applyDisCount(Quote quote) {
        return quote.getShopName() + " price is " +
                Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }

    private static double apply(double price, Code code) {
        delay();
        return (price * (100 - code.percentage) / 100);

    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Quote {
    private final String shopName;
    private final double price;
    private final Discount.Code discountCode;

    public Quote(String shopName, double price, Discount.Code code) {
        this.shopName = shopName;
        this.price = price;
        this.discountCode = code;
    }

    public static Quote parse(String s) {
        String[] split = s.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Discount.Code discountCode = Discount.Code.valueOf(split[2]);
        return new Quote(shopName, price, discountCode);
    }

    public String getShopName() {
        return shopName;
    }

    public double getPrice() {
        return price;
    }

    public Discount.Code getDiscountCode() {
        return discountCode;
    }
}
