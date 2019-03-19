package chapter4;

/**
 * Created by simjunbo on 2018-02-22.
 */
@FunctionalInterface
public interface FunctionalnterfaceTest {
    boolean test(); // 하나의 추상메소드

    boolean equals(Object obj); // Object 객체의 메소드 제외

    String toString();          // Object 객체의 메소드 제외

    static void test2() {
        System.out.print("test");
    }
}
