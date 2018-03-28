package chapter8;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by simjunbo on 2018-03-26.
 */
public class FactoryPattern {
    final static Map<String, Supplier<Product>> map = new HashMap<>();
    Map<String, TriFunction<Integer, Integer, String, Product>> map2 = new HashMap<>();

    static {
        map.put("loan", Loan::new);
        map.put("loan", Stock::new);
        map.put("loan", Bond::new);
    }

    public static Product createProduct(String name) {
        Supplier<Product> p = map.get(map);
        if (p != null)
            return p.get();
        throw new IllegalArgumentException("No such product " + name);
    }

    public static void main(String[] args) {
        Product p = ProductFactory.createProduct("loan");
        p.print();

        //람다
        Supplier<Product> loanSupplier = Loan::new;
        Loan loan = (Loan) loanSupplier.get();
    }
}

class ProductFactory {
    public static Product createProduct(String name) {
        switch (name) {
            case "loan":
                return new Loan();
            case "stock":
                return new Stock();
            case "bond":
                return new Bond();
            default:
                throw new RuntimeException("No such product " + name);
        }
    }
}

abstract class Product {
    abstract void print();
}

class Loan extends Product {
    @Override
    void print() {
        System.out.println("Loan");
    }
}

class Stock extends Product {
    @Override
    void print() {
        System.out.println("Stock");
    }
}

class Bond extends Product {
    @Override
    void print() {
        System.out.println("Bond");
    }
}

interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}