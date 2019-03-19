package chapter14;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by simjunbo on 2018-04-18.
 */
public class StreamTest {
/*    public static Stream<Integer> primes(int n) {
        return Stream.iterate(2, i -> i + 1)
                .filter(MymathUtils::isPrime)
                .limit(n);
    }

    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }*/

    // 단계1: 스트림 숫자 얻기
    public static IntStream numbers() {
        return IntStream.iterate(2, n -> n + 1);
    }

    // 단계2: 머리 획득
    public static int head(IntStream numbers) {
        return numbers.findFirst().getAsInt();
    }

    // 단계3: 꼬리 필터링
    public static IntStream tail(IntStream numbers) {
        return numbers.skip(1);
    }

    // 단계4 : 재귀적으로 소수 스트림 생성
    public static IntStream primes(IntStream numbers) {
        int head = head(numbers);
        return IntStream.concat(
                IntStream.of(head),
                primes(tail(numbers).filter(n -> n % head != 0))
        );
    }

    // java.lang.IllegalStateException : stream has already benn operated upon or closed 라는 에러가 발생
}
