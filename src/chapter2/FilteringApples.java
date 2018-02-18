package chapter2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by simjunbo on 2018-02-11.
 */
public class FilteringApples {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(120, "red"));

        // 파라미터화
        filterApplesByColor(inventory, "green");
        filterApplesByColor(inventory, "red");

        // 플래그 추가
        filterApples(inventory, "green", 0, true);
        filterApples(inventory, "", 150, false);

        // 추상화 추가
        abstractFilterApples(inventory, new AppleGreenColorPredicate());
        abstractFilterApples(inventory, new AppleHeavyWeightPredicate());

        // 응용
        prettyPrintApple(inventory, new AppleCustomizing());

        // 익명 클래스
        abstractFilterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "red".equals(apple.getColor());
            }
        });

        // 람다
        abstractFilterApples(inventory, (Apple apple) -> "red".equals(apple.getColor()));

        // 리스트 형식으로 추상화
        filter(inventory, (Apple apple) -> "red".equals(apple.getColor()));
        filter(inventory,  apple -> "red".equals(apple.getColor()));



        // 자바8 Predicate 방식
        List<Apple> greeanApples = filterApples(inventory, FilteringApples::isGreeApple);
        List<Apple> heavyApples = filterApples(inventory, FilteringApples::isHeavyApple);

        // 자바8 람다 방식
        filterApples(inventory, (Apple a) -> "green".equals(a.getColor()));
        filterApples(inventory, (Apple a) -> a.getWeight() > 150);
        filterApples(inventory, (Apple a) -> a.getWeight() < 80 || "brown".equals(a.getColor()));

        // 다음과 같은 내장된 라이브러리를 추가하지 못한것은 병렬성 때문이다.
        // Collections.filter(inventory, (Apple a) -> a.getWeight() > 150);

        // 순차 처리
        inventory.stream().filter((Apple a) -> a.getWeight() > 150)
                .collect(Collectors.toList());

        // 병렬 처리
        inventory.parallelStream().filter((Apple a) -> a.getWeight() > 150)
                .collect(Collectors.toList());

        // 정렬 처리
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple a1, Apple a2) {
                return a1.getWeight().compareTo(a2.getWeight());
            }
        });

        // 메서드 레퍼런스
        inventory.sort(Comparator.comparing(Apple::getWeight));
    }

    // 자바8 이전의 방식
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    // 색깔
    public static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getColor().equals(color)) {
                result.add(apple);
            }
        }
        return result;
    }

    // 무게
    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    // 플래그 추가
    public static List<Apple> filterApples(List<Apple> inventory, String color, int weight, boolean flag) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (flag && apple.getColor().equals(color) ||
                    (!flag && apple.getWeight() > weight)) {
                result.add(apple);
            }
        }
        return result;
    }

    // 추상조건 필터
    public static List<Apple> abstractFilterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    // 리스트 형식의 추상화
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T e : list) {
            if (p.test(e)) {
                result.add(e);
            }
        }
        return result;
    }

    public static List<Apple> filterHeavyApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > 150) {
                result.add(apple);
            }
        }
        return result;
    }

    // 자바8 Predicate 방식
    public static List<Apple> filterApples(List<Apple> inventroy, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventroy) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    // 응용 유연한 prettyPrintApple
    public static void prettyPrintApple(List<Apple> inventory, ApplePredicate2 p2) {
        for (Apple apple : inventory) {
            String output = p2.test(apple);
            System.out.println(output);
        }
    }

    public static boolean isGreeApple(Apple apple) {
        return "green".equals(apple.getColor());
    }

    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }

    public static class Apple {
        private int weight = 0;
        private String color = "";

        public Apple(int weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String toString() {
            return "Apple{" +
                    "color='" + color + '\'' +
                    ", weight=" + weight +
                    '}';
        }
    }
}
