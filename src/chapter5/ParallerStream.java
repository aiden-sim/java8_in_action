package chapter5;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by simjunbo on 2018-02-24.
 */
public class ParallerStream {
    public static void main(String[] args) {
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

        // 첫번째 요소
        System.out.println("첫번째 요소");
        Optional menu1 = menu.stream()
                .map(Dish::getName)
                .findFirst();
        System.out.println(menu1.get());


        Optional menu2 = menu.parallelStream()
                .map(Dish::getName)
                .findAny();
        System.out.println(menu2.get());

        // 전체
        System.out.println("전체 데이터");
        menu.stream()
                .map(Dish::getName)
                .forEach(System.out::println);

        System.out.println("=====================");
        menu.parallelStream()
                .map(Dish::getName)
                .forEach(System.out::println);

    }
}
