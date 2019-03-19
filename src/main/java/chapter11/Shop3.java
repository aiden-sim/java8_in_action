package chapter11;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop3 {

    // 동기 메서드를 비동기 메서드로 변환
    public Future<Double> getPriceAsync(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    private double calculatePrice(String product) {
        delay(); // 외부 API 호출 하는 부분을 대신 delay로 인위적 1초 지연
        return Math.random() * product.charAt(0) + product.charAt(1);
    }

    public void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
