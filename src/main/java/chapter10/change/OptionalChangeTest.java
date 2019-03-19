package chapter10.change;

import java.util.Optional;

/**
 * Created by simjunbo on 2018-04-01.
 */
public class OptionalChangeTest {
    public static void main(String[] args) {
    }

    public void getCarInsuranceName(Person person) {
        Optional<Person> optPerson = Optional.of(person);
/*        Optional<String> name = optPerson.map(Person::getCar) // 컴파일 오류
                .map(Car::getInsurance)
                .map(Insurance::getName);*/

        String name2 = optPerson.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }


    // 문자열을 정수 Optional로 변환
    public static Optional<Integer> stringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
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
