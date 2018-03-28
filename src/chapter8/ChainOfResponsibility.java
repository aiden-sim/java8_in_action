package chapter8;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Created by simjunbo on 2018-03-26.
 */
/*
의무 체인
 */
public class ChainOfResponsibility {
    public static void main(String[] args) {
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();

        p1.setSuccessor(p2);

        String result = p1.handle("Aren't labdas really sexy?!!");
        System.out.println(result);
        //From Raoul, Mario and Alan:Aren't lambda really sexy?!!

        // 람다
        UnaryOperator<String> headerProcessing =
                (String text) -> "From Raoul, Mario And Alan:" + text;

        UnaryOperator<String> spellCheckerProcessing =
                (String text) -> text.replaceAll("labda", "lambda");

        Function<String, String> pipeline =
                headerProcessing.andThen(spellCheckerProcessing);

        String result2 = pipeline.apply(" Aren't labdas really sexy?!!");
        System.out.println(result2);
    }
}


abstract class ProcessingObject<T> {
    protected ProcessingObject<T> successor;

    public void setSuccessor(ProcessingObject<T> successor) {
        this.successor = successor;
    }

    public T handle(T input) {
        T r = handleWork(input);
        if (successor != null) {
            return successor.handle(r);
        }
        return r;
    }

    abstract protected T handleWork(T input); // 템플릿 메서드
}

// ConcreateProcessingObject
class HeaderTextProcessing extends ProcessingObject<String> {
    @Override
    protected String handleWork(String text) {
        return "From Raoul, Mario and Alan: " + text;
    }
}

// ConcreateProcessingObject
class SpellCheckerProcessing extends ProcessingObject<String> {
    @Override
    protected String handleWork(String text) {
        return text.replaceAll("labda", "lambda");
    }
}