package chapter3;

import chapter2.Apple;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by simjunbo on 2018-02-17.
 */
public class ConstructorMethod {
    /*
    static Map<String, Function<Integer, Fruit>> map = new HashMap<>();

    static {
        map.put("apple", Apple::new);
        map.put("orange", Orange::new);
    }

    public static Fruit giveMeFruit(String fruit, Integer weight) {
        return map.get(fruit.toLowerCase())
                .apply(weight);
    }
*/
    public static void main(String[] args) {
        // Supplier
        Supplier<Apple> c1 = () -> new Apple();
        Apple a1 = c1.get();

        Supplier<Apple> c2 = Apple::new;
        Apple a2 = c2.get();

        // Function
        Function<Integer, Apple> f1 = (weight) -> new Apple(weight);
        Apple b1 = f1.apply(110);

        Function<Integer, Apple> f2 = Apple::new;
        Apple b2 = f2.apply(110);

        // 메서드 전달 방식 - 생성자 한개
        List<Integer> weights = Arrays.asList(7, 3, 4, 10);
        List<Apple> apples = map(weights, Apple::new);

        // BiFuntion
        BiFunction<String, Integer, Apple> g1 =
                (color, weight) -> new Apple(color, weight);
        Apple c11 = g1.apply("green", 110);

        BiFunction<String, Integer, Apple> g2 = Apple::new;
        Apple c22 = g2.apply("green", 110);

    }

    public static List<Apple> map(List<Integer> list, Function<Integer, Apple> f) {
        List<Apple> result = new ArrayList<>();
        for(Integer e : list) {
            result.add(f.apply(e));
        }
        return result;
    }
}
