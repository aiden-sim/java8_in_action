package chapter11;

import java.util.Random;

public class Shop5 {
    public Random random = new Random();
    private String name;

    Shop5() {

    }

    Shop5(String name) {
        this.name = name;
    }

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    private double calculatePrice(String product) {
        randomDelay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public void randomDelay() {
        int delay = 500 + random.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
