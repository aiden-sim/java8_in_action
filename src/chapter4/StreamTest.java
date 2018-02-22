package chapter4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by simjunbo on 2018-02-22.
 */
public class StreamTest {
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

        List<Dish> lowCaloricDishes = new ArrayList<>();
        for (Dish d : menu) {
            if (d.getCalories() < 400) {
                lowCaloricDishes.add(d);
            }
        }

        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish d1, Dish d2) {
                return Integer.compare(d1.getCalories(), d2.getCalories());
            }
        });

        // 최종적으로 lowCaloricDishes는 중간아 역할만 한다.
        List<String> lowCaloricDishesName = new ArrayList<>();
        for (Dish d : lowCaloricDishes) {
            lowCaloricDishesName.add(d.getName());
        }

        // 자바8 스타일
        List<String> lowCaloricDishesName2 = menu.stream()
                .filter(d -> d.getCalories() < 400)                 // 필터
                .sorted(Comparator.comparing(Dish::getCalories))    // 정렬
                .map(Dish::getName)                                 // 특정 형태로 변환
                .collect(Collectors.toList());                      // 출력 형태


        // 한번만 탐색 가능
        List<String> title = Arrays.asList("Java8", "In", "Action");
        Stream<String> s = title.stream();
        s.forEach(System.out::println);
        //s.forEach(System.out::println);


        // for-each
        List<String> names = new ArrayList<>();
        for(Dish d: menu) {
            names.add(d.getName());
        }

        // iterator
        List<String> names2 = new ArrayList<>();
        Iterator<Dish> iterator = menu.iterator();
        while(iterator.hasNext()) {
            Dish d = iterator.next();
            names2.add(d.getName());
        }

        // stream
        List<String> names3 = menu.stream().map(Dish::getName).collect(Collectors.toList());


        // 중간 연산
        List<String> name =
                menu.stream()
                    .filter(d -> {
                        System.out.println("filtering : " + d.getName());
                        return d.getCalories() > 500;
                    })
                    .map(d ->{
                        System.out.println("mapping : " + d);
                        return d.getName();
                    })
                    .limit(3)
                    .collect(Collectors.toList());
    }
}


class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }

    public enum Type {
        MEAT, FISH, OTHER
    }
}