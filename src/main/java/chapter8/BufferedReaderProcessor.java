package chapter8;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by simjunbo on 2018-02-17.
 */
@FunctionalInterface
public interface BufferedReaderProcessor {
    String process(BufferedReader b) throws IOException;
}
