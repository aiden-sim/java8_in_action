package chapter16;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // 자바8 immutable Collection 만드는 방법
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

        // 제네릭 무공변성


    }
}
