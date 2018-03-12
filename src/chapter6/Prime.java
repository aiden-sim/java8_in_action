package chapter6;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by simjunbo on 2018-03-12.
 */
public class Prime {
    public static Prime newInstance() {
        return new Prime();
    }

    public static void main(String[] args) {
        Map<Boolean, List<Integer>> map = Prime.newInstance().partitionPrimes(1_000_000);

        List<Integer> list = map.get(true);
        list.stream().forEach(System.out::println);
    }

    public Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(Collectors.partitioningBy(candidate -> isPrime(candidate)));
    }

    public boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        System.out.println(candidate + ":" + candidateRoot);
        return IntStream.range(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }
}
