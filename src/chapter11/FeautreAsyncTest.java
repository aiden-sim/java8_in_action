package chapter11;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Stream;

/**
 * Created by simjunbo on 2018-04-09.
 */
public class FeautreAsyncTest {
    public static void main(String[] args) {
/*        findPricesStream("myPhone").map(f -> f.thenAccept(System.out::println));

        CompletableFuture[] futures = (CompletableFuture[]) findPricesStream("myPhone")
                .map(f -> f.thenAccept(System.out::println))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();*/


        // 개선
        long start = System.nanoTime();

        findPricesStream("myPhone").map(f -> f.thenAccept(System.out::println));
        CompletableFuture[] futures = (CompletableFuture[]) findPricesStream("myPhone275")
                .map(f -> f.thenAccept(
                        s -> System.out.println(s + " (done in " +
                                ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have noew responded in " +
                ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }

    public static List<Shop4> shops = Arrays.asList(
            new Shop4("BestPrice"),
            new Shop4("LetsSaveBig"),
            new Shop4("MyFavoriteShop"),
            new Shop4("BuyItAll"),
            new Shop4("ShopEasy price"));

    private static final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    public static Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->
                        CompletableFuture.supplyAsync(() -> Discount.applyDisCount(quote), executor)));
    }
}
