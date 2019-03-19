package chapter10.nulls;

import java.util.Optional;

/**
 * Created by simjunbo on 2018-04-01.
 */
public class NullPointerExceptionTest {
    public static void main(String[] args) {

    }

    // 문제점1) 체크 안함.
    public static String getCarInsuranceName(Person person) {
        return person.getCar().getInsurance().getName();
    }

    // 문제점2) 깊은 의심
    // if 블록을 추가하면 코드 들여쓰기 수준이 증가한다.
    // 코드의 구조가 엉망이 되고 가독성도 떨어진다.
    public static String getCarInsuranceName2(Person person) {
        if (person != null) {
            Car car = person.getCar();
            if (car != null) {
                Insurance insurance = car.getInsurance();
                if (insurance != null) {
                    return insurance.getName();
                }
            }
        }
        return "Unknown";
    }

    // 문제점3) 너무 많은 출구
    // 출구 때문에 유지보수가 어려워진다.
    // 문자열을 반복하면서 오타 등의 실수가 생길 수 있다.
    public static String getCarInsuranceName3(Person person) {
        if (person == null) {
            return "Unknown";
        }

        Car car = person.getCar();
        if (car == null) {
            return "Unknown";
        }

        Insurance insurance = car.getInsurance();
        if (insurance == null) {
            return "Unknown";
        }

        return insurance.getName();
    }


}

class Person {
    private Car car;

    public Car getCar() {
        return car;
    }
}

class Car {
    private Insurance insurance;

    public Insurance getInsurance() {
        return insurance;
    }
}

class Insurance {
    private String name;

    public String getName() {
        return name;
    }
}