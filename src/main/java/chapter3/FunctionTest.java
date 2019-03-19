package chapter3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by simjunbo on 2018-02-17.
 */
public class FunctionTest {

    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for (T s : list) {
            result.add(f.apply(s));
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> l = map(
                Arrays.asList("lamdas", "in", "action"), (String s) -> s.length()
        );

        l.stream().forEach(
                x -> System.out.println(x)
        );

        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.compose(g);
        int result = h.apply(1); // 1 + (1 * 2) = 3
    }
}
