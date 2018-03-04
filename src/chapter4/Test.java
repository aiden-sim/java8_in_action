package chapter4;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by simjunbo on 2018-03-04.
 */
public class Test {
    public static void main(String[] args) {
        List<Dish> list = Arrays.asList(
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


        Stream<Dish> maleFemaleStream = list.stream();
        Stream<Dish> maleStream = maleFemaleStream.filter(m -> m.getType() == Dish.Type.MEAT);
        IntStream ageStream = maleStream.mapToInt(Dish::getCalories);
        OptionalDouble optionalDouble = ageStream.average();
        double ageAvg = optionalDouble.getAsDouble();
    }
}
