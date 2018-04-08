package chapter11;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by simjunbo on 2018-04-08.
 */
public class FeatureTest {
    public static void main(String[] args) {
        // 비동기 API 형태
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Double> future = executor.submit(new Callable<Double>() {
            public Double call() {
                return doSomeLongComputation();
            }
        });
        doSomethingElse();
        try {
            Double result = future.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException ee) {

        } catch (InterruptedException ie) {

        } catch (TimeoutException te) {

        }

        // 비동기 API 사용
        Shop2 shop = new Shop2();
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime + " msecs");
        doSomethingElse();
        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f\n", price);
        } catch (Exception e) {

        }

        // 비블록 코드 만들기
        long start2 = System.nanoTime();
        System.out.println(findPrices4("myPhone275"));
        long duration2 = (System.nanoTime() - start2) / 1_000_000;
        System.out.println("Done in " + duration2 + " msecs");
    }

    public static Double doSomeLongComputation() {
        System.out.println("test");
        return 0.0;
    }

    public static void doSomethingElse() {
        System.out.println("test2");
    }

    public static List<Shop> shops = Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy price"));

    // 순차 Stream 이용
    public static List<String> findPrices(String product) {
        return shops.stream()   // 병렬 스트림으로 상점에서 가격 정보를 병렬로 요청한다.
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    // 병렬 Stream 이용
    public static List<String> findPrices2(String product) {
        return shops.parallelStream()   // 병렬 스트림으로 상점에서 가격 정보를 병렬로 요청한다.
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    // CompletableFuture 이용
    public static List<String> findPrices3(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product), executor))
                        .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
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

    // ForkJoin 스레드 갯수 조절해서
    public static List<String> findPrices4(String product) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        try {
            return forkJoinPool.submit(() ->
                    shops.parallelStream()   // 병렬 스트림으로 상점에서 가격 정보를 병렬로 요청한다.
                            .map(x -> String.format("%s price is %.2f", x.getName(), x.getPrice(product)))
                            .collect(Collectors.toList())
            ).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }
    }
}
