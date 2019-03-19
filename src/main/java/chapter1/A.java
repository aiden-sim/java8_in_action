package chapter1;

/**
 * Created by simjunbo on 2018-02-19.
 */
public interface A {

    void test();

    default void print() {
        System.out.print("A");
    }
}