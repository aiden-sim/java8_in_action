package chapter3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by simjunbo on 2018-02-17.
 */


public class Sorting {
    public static void main(String... args) {
        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red")));

        // 객체 인수로 전달
        inventory.sort(new AppleComparator());

        // 익명 클래스
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple a1, Apple a2) {
                return a1.getWeight().compareTo(a2.getWeight());
            }
        });

        // 람다
        inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));

        // Comparator의 comparing을 이용하여 더 간소화
        inventory.sort(Comparator.comparing((a) -> a.getWeight()));

        // 메서드 레퍼런스
        inventory.sort(Comparator.comparing(Apple::getWeight));

        // 역정렬
        inventory.sort(Comparator.comparing(Apple::getWeight).reversed());

        // 두번째 정렬
        inventory.sort(Comparator.comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
    }

    static class AppleComparator implements Comparator<Apple> {
        public int compare(Apple a1, Apple a2) {
            return a1.getWeight().compareTo(a2.getWeight());
        }
    }

    public static class Apple {
        private Integer weight = 0;
        private String color = "";

        public Apple(Integer weight, String color) {
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
