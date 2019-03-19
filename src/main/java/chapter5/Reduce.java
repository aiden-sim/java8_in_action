package chapter5;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Created by simjunbo on 2018-02-24.
 */
public class Reduce {
    public static void main(String[] args) {
        // 이전방식
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = 0;
        for (int x : numbers) {
            sum += x;
        }

        // 람다
        int sum2 = numbers.stream()
                .reduce(0, (a, b) -> a + b);

        // 메서드 레퍼런스
        int sum3 = numbers.stream()
                .reduce(0, Integer::sum);

        // 초기값 없을경우
        Optional<Integer> sum4 = numbers.stream()
                .reduce((a, b) -> (a + b));
    }
}
