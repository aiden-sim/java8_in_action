package chapter3;

/**
 * Created by simjunbo on 2018-02-17.
 */
public class CapturingLambda {
    public static void main(String[] args) {
        int portNumber = 1337;
        int finalPortNumber = portNumber;
        Runnable r = () -> System.out.println(finalPortNumber);

        portNumber = 123123;

        r.run();
    }
}
