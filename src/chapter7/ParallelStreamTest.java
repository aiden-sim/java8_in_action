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
        // System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "12");
        // System.out.println(Runtime.getRuntime().availableProcessors());


        /*
        // 첫번째 예제
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
        */

        /*
        // 두번째 예제
        System.out.println("Sequential SideEffect sum " +
                measureSumPerf(ParallelStreamTest::sideEffectSum, 10_000_000) + " msecs");

        System.out.println("Parallel SideEffect sum " +
                measureSumPerf(ParallelStreamTest::sideEffectParallelSum, 10_000_000) + " msecs");
        */

        /*
        // Unordered
        System.out.println("parallelUnorderedLimit sum done in: " +
                measureSumPerf(ParallelStreamTest::parallelUnorderedLimit, 10_000_000) + " msecs");

        System.out.println("parallelLimit sum done in: " +
                measureSumPerf(ParallelStreamTest::parallelLimit, 10_000_000) + " msecs");


        // fork join
        System.out.println("fork join sum " +
                measureSumPerf(ParallelStreamTest::forkJoinSum, 4000) + " msecs");
        */

        final String SENTENCE =
                "  Nel mezzo del cammin di nostra vita " +
                        "mi ritrovai in una selva oscura" +
                        " ch la dritta via era smarrita ";
        System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");

        Stream<Character> stream = IntStream.range(0, SENTENCE.length())
                                            .mapToObj(SENTENCE::charAt);

        System.out.println("Found " + countWords(stream) + " words");
    }

    //////////////////////// iterate //////////////////////
    public static long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 1; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;
        }

        return fastest;
    }

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

    //////////////////////// unOrdered //////////////////////
    public static long parallelUnorderedLimit(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .unordered()
                .limit(1000)
                .reduce(0L, Long::sum);
    }

    // 병렬
    public static long parallelLimit(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .limit(1000)
                .reduce(0L, Long::sum);
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

    // 병렬 - 대참사
    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    //////////////////////// Spliterator ////////////////////////
    private static int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                                                    WordCounter::accumulate,
                                                    WordCounter::combin);
        return wordCounter.getCounter();
    }

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
}

class Accumulator {
    public long total = 0;
    public void add(long value) {
        total += value;
    }
}
