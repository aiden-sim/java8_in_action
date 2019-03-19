package chapter16;

import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.LongToIntFunction;
import java.util.stream.Collectors;

/**
 * Created by simjunbo on 2018-04-22.
 */
public class Test {
    public static void main(String[] args) {

        // 배열 초기화
        Double[] a1 = new Double[]{1.2, 3.4, 5.9};
        // 축약
        Double[] a2 = {1.2, 3.4, 5.9};

        // 컬렉션 Map
        Map<String, Integer> map = new HashMap<>();
        map.put("raoul", 23);
        map.put("mario", 40);
        map.put("alan", 53);

        // 자바9 immutable Collection 만드는 방법
        // List notEmptyList = List.of("wdkang","yhlee", "shlee", "kjhwang")
        // notEmptyList ==> [wdkang, yhlee, shlee, kjhwang]

        // 이건 좀 구린듯
        // Map notEmptyMap = Map.of("key1", "val1", "key2", "val2", "key3", "val3")
        // notEmptyMap ==> {key3=val3, key1=val1, key2=val2}

        /*
        static <E> List<E> of(E e1, E e2, E e3)
        static <E> Set<E>  of(E e1, E e2, E e3)
        static <K,V> Map<K,V> of(K k1, V v1, K k2, V v2, K k3, V v3)
        */


        // 제네릭

        // 공변성
        List<Integer> integers = new ArrayList<>();
        integers.add(1);

        List<? extends Number> numbers = integers;
        System.out.println(numbers);

        // 반 공변성
        List<Number> numbers2 = new ArrayList<>();
        numbers2.add(10);

        List<? super Integer> integers2 = numbers2;
        System.out.println(integers2);


        // 지역 변수 형식 추론
        Map<String, List<String>> myMap = new HashMap<String, List<String>>();
        // 좀더 줄이자 (형식 추론)
        Map<String, List<String>> myMap2 = new HashMap<>();

        // 앞으로의 버전에서는
        // var myMap3 = new HashMap<String, List<String>>();

        Function<Integer, Boolean> p = (Integer x) -> true;
        // 좀더 줄이자 (형식 추론)
        Function<Integer, Boolean> p2 = x -> true;


        // 제네릭이 함수 형식에 제공하는 문법적 유연성
        Function<Integer, Integer> square = x -> x * x;


        // 값 형식
        double d1 = 3.14;
        double d2 = d1;
        Double o1 = d1;
        Double o2 = d2;
        Double ox = o1;
        System.out.println(d1 == d2 ? "yes" : "no");        // yes
        System.out.println(o1 == o2 ? "yes" : "no");        // no
        System.out.println(o1 == ox ? "yes" : "no");        // yes
    }
}



