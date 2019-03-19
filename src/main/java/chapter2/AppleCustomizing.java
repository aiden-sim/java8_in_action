package chapter2;

/**
 * Created by simjunbo on 2018-02-11.
 */
public class AppleCustomizing implements ApplePredicate2 {
    @Override
    public String test(FilteringApples.Apple apple) {
        return apple.getColor();
    }
}
