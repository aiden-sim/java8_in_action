package chapter2;

/**
 * Created by simjunbo on 2018-02-12.
 */
public class RunnableCodeBlock {
    public static void main(String... args) {

        // Runnable 사용
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world");
            }
        });

        // 람다 표현식으로 사용
        Thread t2 = new Thread(() -> System.out.println("Hello world"));
    }
}
