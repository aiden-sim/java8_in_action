package chapter3;

import java.security.PrivilegedAction;
import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;

/**
 * Created by simjunbo on 2018-02-17.
 */
public class TargetType {
    public static void main(String[] args) {
        Callable<Integer> c = () -> 42;
        PrivilegedAction<Integer> p = () -> 42;
    }
}
