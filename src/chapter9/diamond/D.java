package chapter9.diamond;

/**
 * Created by simjunbo on 2018-03-28.
 */
public class D implements B, C {
    public static void main(String[] args) {
        new D().hello();
    }
}
