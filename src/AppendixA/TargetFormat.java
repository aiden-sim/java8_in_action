package AppendixA;

import java.util.Collections;
import java.util.List;

/**
 * Created by simjunbo on 2018-05-10.
 */

public class TargetFormat {
    public static void main(String[] args) {
        List<Car> cars = Collections.<Car>emptyList();

        // Java8 이전까지 해당 코드는 사용할 수 없었음 형식 추론을 할 수 없었기 때문에
        cleanCars(Collections.emptyList());
    }

    static void cleanCars(List<Car> cars) {

    }
}

class Car{

}