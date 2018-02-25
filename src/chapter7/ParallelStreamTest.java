package chapter7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by simjunbo on 2018-02-25.
 */
public class ParallelStreamTest {
    public static void main(String[] args) {
/*
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "12");
        System.out.println(Runtime.getRuntime().availableProcessors());

        System.out.println("Iterative sum done in: " +
                measureSumPerf(ParallelStreamTest::iterativeSum, 10_000_000) + " msecs");

        System.out.println("Sequential sum done in: " +
                measureSumPerf(ParallelStreamTest::sequentialSum, 10_000_000) + " msecs");

        System.out.println("Parallel sum done in: " +
                measureSumPerf(ParallelStreamTest::parallerSum, 10_000_000) + " msecs");


        System.out.println("Sequential Ranged sum done in  " +
                measureSumPerf(ParallelStreamTest::rangedSum, 10_000_000) + " msecs");

        System.out.println("Parallel Ranged sum done in  " +
                measureSumPerf(ParallelStreamTest::parallelRangedSum, 10_000_000) + " msecs");


        System.out.println("Sequential SideEffect sum " +
                measureSumPerf(ParallelStreamTest::sideEffectSum, 10_000_000) + " msecs");
        System.out.println("Parallel SideEffect sum " +
                measureSumPerf(ParallelStreamTest::sideEffectParallelSum, 10_000_000) + " msecs");

        System.out.println("fork join sum " +
                measureSumPerf(ParallelStreamTest::forkJoinSum, 100000) + " msecs");
        */
        final String SENTENCE =
                "  Nel mezzo del cammin di nostra vita " +
                        "mi ritrovai in una selva oscura" +
                        " ch la dritta via era smarrita ";
        //System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");

        Stream<Character> stream = IntStream.range(0, SENTENCE.length())
                                            .mapToObj(SENTENCE::charAt);

    }

    //////////////////////// Spliterator ////////////////////////
    public static int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) {
                    counter++;
                }
                lastSpace = false;
            }
        }
        return counter;
    }


    //////////////////////// forkJoin ////////////////////////
    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

    //////////////////////// sideEffect //////////////////////
    // 순차
    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    // 병렬
    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    //////////////////////// rangeClosed //////////////////////
    // 순차
    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
    }

    // 병렬
    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    //////////////////////// iterate //////////////////////
    // 반복
    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 1L; i <= n; i++) {
            result += i;
        }
        return result;
    }

    // 순차
    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .reduce(0L, Long::sum);
    }

    // 병렬
    public static long parallerSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()                 // 병렬 스트림으로 변환
                .reduce(0L, Long::sum);
    }

    public static long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;
        }

        return fastest;
    }
}

class Accumulator {
    public long total = 0;

    public void add(long value) {
        total += value;
    }
}
