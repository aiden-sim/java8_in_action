package chapter5;

import java.util.stream.IntStream;

public class RangeTest {
    public static void main(String[] args) {
        // rangeClosed
        IntStream.rangeClosed(1, 100).forEach(System.out::print);

        System.out.println("");

        // range
        IntStream.range(1, 100).forEach(System.out::print);
    }
}
