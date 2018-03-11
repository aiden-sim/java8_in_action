package chapter6;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class CollectorsTest {
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

        // # 리듀싱 요약

        // count
        long howManyDishes = menu.stream().collect(Collectors.counting());
        long howManyDishes2 = menu.stream().count();

        Comparator<Dish> dishCaloriesComparator =
                comparingInt(Dish::getCalories);

        // 최댓값 최솟값
        Optional<Dish> mostCalorieDish =
                menu.stream()
                        .collect(maxBy(dishCaloriesComparator));

        // 요약 연산
        // 합계
        int totalCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
        // 평균
        double avgCalories = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
        // IntSummaryStatistics (count, sum, min, average, max 다포함)
        IntSummaryStatistics menuStatistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));

        // 문자열 연결
        String shortMenu = menu.stream().map(Dish::getName).collect(Collectors.joining(", "));


        // 범용 리듀싱 요약 연산
        //int totalCalories2 = menu.stream().collect(Collectors
        //        .reducing(0, Dish::getCalories, (i, j) -> i + j));

        // => 더 단순화
        int totalCalories3 = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));

        Optional<Dish> mostCalorieDish2 = menu.stream().collect(Collectors.reducing(
                (d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));

        int totalCalories4 = menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();

        int totalCalories5 = menu.stream().mapToInt(Dish::getCalories).sum();


        // #그룹화
        Map<Dish.Type, List<Dish>> dishesByType =
                menu.stream().collect(groupingBy(Dish::getType));

        Map<CaloricLevel, List<Dish>> dishesByCaloriceLevel = menu.stream().collect(
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    } else if (dish.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    } else {
                        return CaloricLevel.FAT;
                    }
                }));

        // 다수준 그룹화
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel =
                menu.stream().collect(
                        groupingBy(Dish::getType,
                                groupingBy(dish -> {
                                    if (dish.getCalories() <= 400) {
                                        return CaloricLevel.DIET;
                                    } else if (dish.getCalories() <= 700) {
                                        return CaloricLevel.NORMAL;
                                    } else {
                                        return CaloricLevel.FAT;
                                    }
                                })));


        // 서브그룹으로 데이터 수집
        Map<Dish.Type, Long> typesCount = menu.stream()
                .collect(groupingBy(Dish::getType, Collectors.counting()));

        Map<Dish.Type, Optional<Dish>> mostCaloricByType =
                menu.stream()
                        .collect(groupingBy(Dish::getType,
                                maxBy(comparingInt(Dish::getCalories))));

        // 컬렉터 결과를 다른 형식에 적용하기
        Map<Dish.Type, Dish> mostCaloricByType2 =
                menu.stream()
                        .collect(groupingBy(Dish::getType,
                                collectingAndThen(
                                        maxBy(comparingInt(Dish::getCalories)),
                                        Optional::get)));

        // groupingBy와 함게 사용하는 다른 컬렉터
        Map<Dish.Type, Integer> totalCaloriesByType =
                menu.stream().collect(groupingBy(Dish::getType,
                        summingInt(Dish::getCalories)));

        // mapping
        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType =
                menu.stream().collect(
                        groupingBy(Dish::getType,
                                mapping(dish -> {
                                    if (dish.getCalories() <= 400) {
                                        return CaloricLevel.DIET;
                                    } else if (dish.getCalories() <= 700) {
                                        return CaloricLevel.NORMAL;
                                    } else {
                                        return CaloricLevel.FAT;
                                    }

                                }, toSet()))
                );

        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType2 =
                menu.stream().collect(
                        groupingBy(Dish::getType,
                                mapping(dish -> {
                                    if (dish.getCalories() <= 400) {
                                        return CaloricLevel.DIET;
                                    } else if (dish.getCalories() <= 700) {
                                        return CaloricLevel.NORMAL;
                                    } else {
                                        return CaloricLevel.FAT;
                                    }

                                }, toCollection(HashSet::new))));


        // #분할
        List<Dish> vegetarianDishes =
                menu.stream().collect(partitioningBy(Dish::isVegetarian)).get(true);

        List<Dish> vegetarianDishes2 =
                menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList());

        // 분할의 장점
        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType =
                menu.stream().collect(
                        partitioningBy(Dish::isVegetarian,
                                groupingBy(Dish::getType)));

        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian =
                menu.stream()
                        .collect(partitioningBy(Dish::isVegetarian,
                                collectingAndThen(
                                        maxBy(comparingInt(Dish::getCalories)),
                                        Optional::get
                                ))
                        );


        // # Collector 인터페이스
        // supplier

        List<Dish> dishes = menu.stream().collect(new ToListCollector<Dish>());


        // stream 사용
        long sum1 = menu
                .stream()
                .mapToInt(Dish::getCalories)
                .sum();

        System.out.println(sum1);

        // reduce 사용
        long sum2 = menu
                .stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);

        System.out.println(sum2);

        // collect reducing 사용
        long sum3 = menu
                .stream()
                .collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));

        System.out.println(sum3);

        // collectors summingInt
        long sum4 = menu
                .stream()
                .collect(Collectors.summingInt(Dish::getCalories));

        System.out.println(sum4);
    }
}

enum CaloricLevel {
    DIET, NORMAL, FAT
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

