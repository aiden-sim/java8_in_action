package AppendixD;

import java.util.function.Function;

/**
 * Created by simjunbo on 2018-05-10.
 */
public class InnerClass {
    Function<Object, String> f = new Function<Object, String>() {
        @Override
        public String apply(Object obj) {
            return obj.toString();
        }
    };
}
