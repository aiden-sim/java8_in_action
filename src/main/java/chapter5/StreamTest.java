package chapter5;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by simjunbo on 2018-02-24.
 */
public class StreamTest {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 500, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );

        // 외부 반복
        List<Dish> vegetarianDishes = new ArrayList<>();
        for (Dish d : menu) {
            if (d.isVegetarian()) {
                vegetarianDishes.add(d);
            }
        }

        // 내부 반복
        List<Dish> vegetarianDishes2 = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toList());

        // distinct
        /*
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);
        */

        // distinct2
/*        menu.stream()
                .filter(Dish::isVegetarian)
                .distinct()
                .forEach(System.out::println);*/

        // filtering
        menu.stream()
                .filter(d -> d.getCalories() > 300)
                .limit(3)
                .collect(Collectors.toList());

        // skip
/*        menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .forEach(System.out::println);*/

        // map
        List<String> dishNames = menu.stream()
                .map(Dish::getName)
                .collect(Collectors.toList());

        // 요리 명의 길이
        List<Integer> dishNameLengths = menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(Collectors.toList());

        // 스트림 평면화
        List<String> strList = Arrays.asList("Hello", "World");

        // 첫번째
        List<String[]> temp = strList.stream()
                .map(x -> x.split(""))  // ["H", "e", "l", "l" "o"]["W", "o", "r", "l", "d"]
                .distinct()
                .collect(Collectors.toList());

        for (String[] sArray : temp) {
            System.out.println("=======");
            for (String str : sArray) {
                System.out.print(str);
                System.out.println();
            }
        }

        // 두번째
        List<Stream<String>> temp2 = strList.stream()
                .map(x -> x.split("")) // ["H", "e", "l", "l" "o"]["W", "o", "r", "l", "d"]
                .map(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        for (Stream<String> stream : temp2) {
            System.out.println("=======");
            for (String str : stream.collect(Collectors.toList())) {
                System.out.print(str);
                System.out.println();
            }
        }

        // flatMap
        List<String> temp3 = strList.stream()
                .map(x -> x.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

/*        for (String s : temp3) {
            System.out.print(s + "-");
        }*/
        System.out.println("=======");

        // 검색과 매칭
/*        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("the menu is (some what) vegetarian friendly!!");
        }*/

        boolean isHealthy = menu.stream()
                .allMatch(d -> d.getCalories() < 100);

        boolean isHealthy2 = menu.stream()
                .noneMatch(d -> d.getCalories() >= 1000);

        // 요소 검색
/*        menu.parallelStream()
                .map(Dish::getName)
                .forEach(System.out::println);*/


        // 요리의 개수
        int count = menu.stream()
                .map(d -> 1)
                .reduce(0, (a, b) -> a + b);

        long count2 = menu.parallelStream().distinct().count();

        List<Integer> list = Arrays.asList(2147483647, 1);
        long result = list.stream().reduce(0, Integer::sum);

        // 숫자형 스트림
        int calories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);

        int calories2 = menu.stream()
                .mapToInt(Dish::getCalories)    // IntStream 반환
                .sum();

        // 객체 스트림으로 복원
        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> stream = intStream.boxed();

        //기본값: OptionalInt
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();

        int max = maxCalories.orElse(1);
    }
}


class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Dish.Type type;

    public Dish(String name, boolean vegetarian, int calories, Dish.Type type) {
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

    public Dish.Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Dish) {
            if (this.name.equals(((Dish) o).name))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public enum Type {
        MEAT, FISH, OTHER
    }
}