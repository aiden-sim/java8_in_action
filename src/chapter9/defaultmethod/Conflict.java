package chapter9.defaultmethod;

/**
 * Created by simjunbo on 2018-03-28.
 */

interface AA {
    default void hello() {
        System.out.println("Hello from A");
    }
}

interface BB {
    default void hello() {
        System.out.println("Hello from A");
    }
}

public class Conflict implements BB, AA {
    public void hello() {
        BB.super.hello();
    }
}


