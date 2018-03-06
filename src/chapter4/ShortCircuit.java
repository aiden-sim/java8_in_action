package chapter4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by simjunbo on 2018-03-05.
 */
public class ShortCircuit {
    public static void main(String[] args) {
        // 자바7 스타일
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 500, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );

        // 중간 연산
        List<String> name =
                menu.stream()
                        .filter(d -> {
                            System.out.println("filtering : " + d.getName());
                            return d.getCalories() > 500;
                        })
                        .map(d -> {
                            System.out.println("mapping : " + d);
                            return d.getName();
                        })
                        .sorted()
                        .limit(3)
                        .collect(Collectors.toList());
    }
}
