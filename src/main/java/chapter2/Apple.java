package chapter2;

import java.util.Comparator;
import java.util.List;

/**
 * Created by simjunbo on 2018-02-17.
 */
public class Apple {
    private int weight = 0;
    private String color = "";

    public Apple() {

    }

    public Apple(int weight) {

    }

    public Apple(String color,int weight) {
    }

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

    public static void main(String[] args) {
        Comparator<Apple> byWeight = new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        };

        Comparator<Apple> byWeight2 =
                (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());


    }
}
