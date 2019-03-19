package chapter11;

public class Shop {
    private String name;

    public Shop(String name) {
        this.name = name;
    }

    // 동기 메서드
    public double getPrice(String product) {
        return calculatePrice(product);
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

    public String getName() {
        return name;
    }
}
