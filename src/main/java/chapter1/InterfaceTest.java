package chapter1;

/**
 * Created by simjunbo on 2018-02-11.
 */
public class InterfaceTest implements A {

    @Override
    public void test() {
        A.super.print();
    }
}

