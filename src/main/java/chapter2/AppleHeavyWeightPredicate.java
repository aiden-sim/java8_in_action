package chapter2;

/**
 * Created by simjunbo on 2018-02-11.
 */
public class AppleHeavyWeightPredicate implements  ApplePredicate {
    @Override
    public boolean test(FilteringApples.Apple apple) {
        return apple.getWeight() > 150;
    }
}
