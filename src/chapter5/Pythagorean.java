package chapter5;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by simjunbo on 2018-02-24.
 */
public class Pythagorean {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 4, 5};

        Stream<int[]> pythagoreanTriples =
                IntStream.rangeClosed(1, 100).boxed()
                        .flatMap(a -> {
                                    System.out.println("a : " + a);

                                    return IntStream.rangeClosed(a, 100)
                                            .filter(b -> {
                                                System.out.println("b : " + b);
                                                return (Math.sqrt(a * a + b * b) % 1 == 0);
                                            })
                                            .mapToObj(b ->
                                                    new int[]{a, b, (int) Math.sqrt(a * a + b * b)});
                                }
                        );

        pythagoreanTriples.limit(5).forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

    }
}
