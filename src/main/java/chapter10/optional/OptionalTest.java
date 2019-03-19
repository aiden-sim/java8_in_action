package chapter10.optional;

import java.beans.PersistenceDelegate;
import java.util.Optional;

/**
 * Created by simjunbo on 2018-04-01.
 */
public class OptionalTest {
    public static void main(String[] args) {
        // 빈 Optional
        Optional<Car> optCar = Optional.empty();

        // null이 아닌 값으로 Optional
        // Optional<Car> optCar2 = Optional.of(car);

        // null값으로 Optional 만들기
        // Optional<Car> optCar3 = Optional.ofNullable(car);
    }
}

class Person {
    private Optional<Car> car;

    public Optional<Car> getCar() {
        return car;
    }
}

class Car {
    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}

class Insurance {
    private String name;

    public String getName() {
        return name;
    }
}
