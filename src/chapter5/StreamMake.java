package chapter5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by simjunbo on 2018-02-25.
 */
public class StreamMake {
    public static void main(String[] args) {
        // 값으로 스트림 만들기
        Stream<String> stream = Stream.of("Java 8 ", "Lambdas ", "In ", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5);
        stream2.map(Integer::intValue).forEach(System.out::println);

        // 배열로 스트림 만들기
        int[] numbers = {2, 3, 5, 7, 11, 13};
        int sum = Arrays.stream(numbers).sum();

        String[] str = {"test", "test2", "test3"};
        List<String> list = Arrays.stream(str).collect(Collectors.toList());

        // 파일로 스트림 만들기
        long uniqueWords = 0;
        try (Stream<String> lines =
                     Files.lines(Paths.get("data.txt"), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();

        } catch (IOException e) {

        }

        // 함수로 무한 스트림 만들기
        // iterate
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);

        // generate
        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);

        // Quize 피보나치
        Stream.iterate(new int[]{0, 1},
                t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .forEach(t -> System.out.print("(" + t[0] + "," + t[1] + ")"));

        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current = 1;

            @Override
            public int getAsInt() {
                int oldPrevious = this.previous;
                int nexValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nexValue;
                return oldPrevious;
            }
        };

        IntStream.generate(fib).limit(10).forEach(System.out::println);

        /*
        IntStream ones = IntStream.generate(() -> 1);

        IntStream two = IntStream.generate(new IntSupplier() {
            public int getAsInt() {
                return 2;
            }
        }).limit(10);
        */

    }
}
