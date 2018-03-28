package chapter8;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simjunbo on 2018-03-26.
 */
/*
옵저버 패턴
 */
public class ObserverPattern implements Subject {
    // composition
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void notifiyObservers(String tweet) {
        observers.forEach(o -> o.notifiy(tweet));
    }

    public static void main(String[] args) {
        ObserverPattern f = new ObserverPattern();
        f.registerObserver(new NYTimes());
        f.registerObserver(new Guarding());
        f.registerObserver(new LeMOnde());
        f.notifiyObservers("The queen said her favourite book is Java 9 in Action!");

        // 람다화
        f.registerObserver((String tweet) -> {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in NY! " + tweet);
            }
        });

        f.registerObserver((String tweet) -> {
            if (tweet != null && tweet.contains("queen")) {
                System.out.println("Yet another news in London... " + tweet);
            }
        });
    }
}

class NYTimes implements Observer {
    @Override
    public void notifiy(String tweet) {
        if (tweet != null && tweet.contains("money")) {
            System.out.println("Breaking news in NY! " + tweet);
        }
    }
}

class Guarding implements Observer {
    @Override
    public void notifiy(String tweet) {
        if (tweet != null && tweet.contains("queen")) {
            System.out.println("Yet another news in London... " + tweet);
        }
    }
}

class LeMOnde implements Observer {
    @Override
    public void notifiy(String tweet) {
        if (tweet != null && tweet.contains("wine")) {
            System.out.println(("Today cheese, wine and news! " + tweet));
        }
    }
}

// 옵저버
interface Observer {
    void notifiy(String tweet);
}

// 주제
interface Subject {
    void registerObserver(Observer o);

    void notifiyObservers(String tweet);
}
