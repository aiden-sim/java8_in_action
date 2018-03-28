package chapter9;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by simjunbo on 2018-03-28.
 */
public class DefaultMethod {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(3, 5, 1, 2, 6);
        numbers.sort(Comparator.naturalOrder());
    }
}
