package chapter9.diamond;

/**
 * Created by simjunbo on 2018-03-28.
 */
public interface A {
    default void hello() {
        System.out.println("Hello from A");
    }
}
