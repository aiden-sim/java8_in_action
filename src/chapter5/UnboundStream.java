package chapter5;

import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by simjunbo on 2018-03-08.
 */
public class UnboundStream {
    public static void main(String[] args) {
        // iterate
        LongStream.iterate(0, n -> n + 9_223_372_036_854_775_807L)
                .sorted()
                .forEach(System.out::println);

        // generate
        Stream.generate(Math::random)
                .forEach(System.out::println);
    }
}