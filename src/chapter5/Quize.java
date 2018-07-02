package chapter5;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by simjunbo on 2018-02-24.
 */
public class Quize {
    public static void main(String[] args) {
        // Quize 1
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        list.stream()
                .map(x -> x * x)
                .forEach(x -> {
                    System.out.print(x);
                    System.out.print(" ");
                });


        // Quize 2
        List<Integer> num1 = Arrays.asList(1, 2, 3);
        List<Integer> num2 = Arrays.asList(3, 4);

        List<int[]> paris =
                num1.stream()
                        .flatMap(i -> num2.stream()
                                .map(j -> new int[]{i, j}))
                        .collect(Collectors.toList());

        // Quize 3
        List<int[]> paris2 =
                num1.stream()
                        .flatMap(i -> num2.stream()
                                .filter(j -> (i + j) % 3 == 0)
                                .map(j -> new int[]{i, j}))
                        .collect(Collectors.toList());

    }

}
