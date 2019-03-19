package chapter3;

import java.util.Arrays;
import java.util.List;

/**
 * Created by simjunbo on 2018-02-17.
 */
public class MethodReference {
    public static void main(String[] args) {
        List<String> str = Arrays.asList("a", "b", "A", "B");

        str.sort((s1, s2) -> s1.compareToIgnoreCase(s2)); // 람다 표현식
        str.sort(String::compareToIgnoreCase); // 메서드 레퍼런스
    }
}
