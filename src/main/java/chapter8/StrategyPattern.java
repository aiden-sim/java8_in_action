package chapter8;

/**
 * Created by simjunbo on 2018-03-26.
 */
/*
전략패턴
 */
public class StrategyPattern {
    private final ValidationStrategy strategy;

    public StrategyPattern(ValidationStrategy v) {
        this.strategy = v;
    }

    public boolean validate(String s) {
        return strategy.execute(s);
    }

    public static void main(String[] args) {
        StrategyPattern numericStrategyPattern = new StrategyPattern(new IsNumeric());
        boolean b1 = numericStrategyPattern.validate("aaaa");

        StrategyPattern lowerCaseStrategyPattern = new StrategyPattern(new IsAllLowerCase());
        boolean b2 = lowerCaseStrategyPattern.validate("bbbb");

        // 람다로 변환
        StrategyPattern numericStrategyPattern2 = new StrategyPattern((String s) -> s.matches("a-z+"));
        boolean b3 = numericStrategyPattern2.validate("aaaa");

        StrategyPattern lowerCaseStrategyPattern2 = new StrategyPattern((String s) -> s.matches("\\d+"));
        boolean b4 = lowerCaseStrategyPattern2.validate("bbbb");
    }
}

class IsAllLowerCase implements ValidationStrategy {

    @Override
    public boolean execute(String s) {
        return s.matches("[a-z]+");
    }
}

class IsNumeric implements ValidationStrategy {
    @Override
    public boolean execute(String s) {
        return s.matches("\\d+");
    }
}

interface ValidationStrategy {
    boolean execute(String s);
}