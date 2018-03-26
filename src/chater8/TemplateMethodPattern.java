package chater8;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Created by simjunbo on 2018-03-26.
 */
/*
템플릿 메서드패턴
 */
public class TemplateMethodPattern {
    public static void main(String[] args) {
        Database.addCustomerInfo(1337, new Customer(1337, "심준보"));
        new TemplateMethodPattern().processCustomer(1337, (Customer c) -> System.out.println("Hello " + c.getName()));
    }

    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }
}

// 템플릿 일반
abstract class OnlineBanking {
    public void processCustomer(int id) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy(c);
    }

    abstract void makeCustomerHappy(Customer c);
}

class Database {
    public static HashMap<Integer, Customer> map = new HashMap<Integer, Customer>();

    public static void addCustomerInfo(int id, Customer customer) {
        if (!map.containsKey(id)) {
            map.put(id, customer);
        }
    }

    public static Customer getCustomerWithId(int id) {
        if (map.containsKey(id)) {
            return map.get(id);
        } else {
            return null;
        }
    }
}

class Customer {
    private int id;
    private String name;

    Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}