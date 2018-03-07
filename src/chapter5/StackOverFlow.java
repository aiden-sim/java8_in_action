package chapter5;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class StackOverFlow {
    public static void main(String[] args) {
        //int:     32 bit              -2 147 483 648                  +2 147 483 647
        //long:    64 bit  -9 223 372 036 854 775 808      +9 223 372 036 854 775 807

        // reduce - 바운드
        List<Integer> integerSum = Arrays.asList(2_147_483_647, 1);
        long firstSum = integerSum.stream().reduce(0, Integer::sum);
        System.out.println(firstSum);

        List<Long> longSum = Arrays.asList(2_147_483_647L, 1L);
        long secondSum = longSum.stream().reduce(0L, Long::sum);
        System.out.println(secondSum);

        // limit, skip - 바운드
        long count = IntStream.rangeClosed(0, 2_147_483_647).skip(2_147_483_649L).count();
        System.out.println(count);
    }
}
