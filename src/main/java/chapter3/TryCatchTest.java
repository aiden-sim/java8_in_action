package chapter3;

import chapter2.Apple;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by simjunbo on 2018-02-17.
 */
public class TryCatchTest {
    public static void main(String[] args) {
        Function<BufferedReader, String> f =
                (BufferedReader b) -> {
                    try {
                        return b.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };
            }
}
