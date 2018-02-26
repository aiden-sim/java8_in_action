package chapter5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by simjunbo on 2018-02-26.
 */
public class FlatMapTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("test", "test2");
        Stream<Character> flatMapStream = list.stream().flatMap(x -> characterStream(x));

        flatMapStream.forEach(System.out::println);
    }

    private static Stream<Character> characterStream(String s) {
        List<Character> result = new ArrayList<>();
        for (char c : s.toCharArray()) {
            result.add(c);
        }
        return result.stream();
    }
}
