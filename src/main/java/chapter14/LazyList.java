package chapter14;

import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Created by simjunbo on 2018-04-18.
 */
public class LazyList<T> implements MyList<T> {
    final T head;
    final Supplier<MyList<T>> tail;

    public LazyList(T head, Supplier<MyList<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public T head() {
        return head;
    }

    @Override
    public MyList<T> tail() {
        return tail.get();
    }

    public boolean isEmpty() {
        return false;
    }

    // 무한히 게으른 리스트
    public static LazyList<Integer> from(int n) {
        return new LazyList<Integer>(n, () -> from(n + 1));
    }
}
