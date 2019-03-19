package AppendixB;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by simjunbo on 2018-05-10.
 */
public class CollectionTest {
    public static void main(String[] args) {
        // 맵
        Map<String, Integer> carInventory = new HashMap<>();
        Integer count = 0;
        if (carInventory.containsKey("Aston Martin")) {
            count = carInventory.get("Aston Martin");
        }

        // 다음과 같이 변환 가능
        Integer count2 = carInventory.getOrDefault("Aston Martin", 0);
        System.out.println(count2);

        // 리스트
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.replaceAll(x -> x * 2);
        System.out.println(numbers);

        int[] ones = new int[10];
        Arrays.fill(ones, 1);
        Arrays.parallelPrefix(ones, (a, b) -> a + b);
    }

    Map<String, String> cache = new HashMap<>();

    public String getData(String url) {
        String data = cache.get(url);
        if (data == null) {
            data = getData(url);
            cache.put(url, data);
        }
        return data;
    }

    // computeIfAbsent를 이용하여 간결하게
    public String getData2(String url) {
        return cache.computeIfAbsent(url, this::getData);
    }
}
