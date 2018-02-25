package chapter5;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * Created by simjunbo on 2018-02-24.
 */
public class Example {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Combridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Combridge");
        Trader brian = new Trader("Brian", "Combridge");

        List<Transcatino> transactions = Arrays.asList(
                new Transcatino(brian, 2011, 300),
                new Transcatino(raoul, 2012, 1000),
                new Transcatino(raoul, 2011, 400),
                new Transcatino(mario, 2012, 710),
                new Transcatino(mario, 2012, 700),
                new Transcatino(alan, 2012, 950)
        );

        // 문제1
        // 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.
        List<Integer> values = transactions.stream()
                .filter(year -> year.getYear() == 2011)
                .map(Transcatino::getValue)
                .sorted()
                .collect(Collectors.toList());

        // 정답
        List<Transcatino> tr2011 = transactions.stream()
                .filter(year -> year.getYear() == 2011)
                .sorted(comparing(Transcatino::getValue))
                .collect(Collectors.toList());


        // 문제2
        // 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.
        List<String> cityList = transactions.stream()
                .map(city -> city.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());

        // 정답
        List<String> cities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());

        // toSet 방식
        Set<String> cities2 = transactions.stream()
                .map(city -> city.getTrader().getCity())
                .collect(Collectors.toSet());


        // 문제3
        // 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
        List<String> names = transactions.stream()
                .filter(city -> city.getTrader().getCity().equals("Combridge"))
                .map(name -> name.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        // 정답
        List<Trader> traders = transactions.stream()
                .map(Transcatino::getTrader)
                .filter(trader -> trader.getCity().equals("Combridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(Collectors.toList());


        // 문제4
        // 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.
        List<String> alphabets = transactions.stream()
                .map(name -> name.getTrader().getName().split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        // 정답
        String traderStr = transactions.stream()
                .map(transactino -> transactino.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);


        // 문제5
        // 밀라노(Milano) 에 거래자가 있는가?
        boolean isMilano = transactions.stream()
                .map(city -> city.getTrader().getCity())
                .anyMatch(city -> city.equals("Milan"));

        // 정답
        boolean milanBased = transactions.stream()
                .anyMatch(transcatino -> transcatino.getTrader().getCity().equals("Milan"));


        // 문제6
        // 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.
        List<Integer> values2 = transactions.stream()
                .filter(city -> city.getTrader().getCity().equals("Combridge"))
                .map(Transcatino::getValue)
                .collect(Collectors.toList());

        // 정답
        transactions.stream()
                .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                .map(Transcatino::getValue)
                .forEach(System.out::println);


        // 문제7
        // 전체 트랜잭션 중 최댓값은 얼마인가?
        int max = transactions.stream()
                .map(Transcatino::getValue)
                .reduce(0, Integer::max);

        // 정답
        Optional<Integer> hgihestValue = transactions.stream()
                .map(Transcatino::getValue)
                .reduce(Integer::max);

        // 문제8
        // 전체 트랜잭션 중 최소값은 얼마인가?
        Optional<Integer> min = transactions.stream()
                .map(Transcatino::getValue)
                .reduce(Integer::min);

        // 정답
        Optional<Transcatino> smallestTransaction = transactions.stream()
                .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);


    }
}

class Trader {
    private final String name;
    private final String city;

    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String toString() {
        return "Trader:" + this.name + " in " + this.city;
    }
}

class Transcatino {
    private final Trader trader;
    private final int year;
    private final int value;

    public Transcatino(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return trader;
    }

    public int getYear() {
        return year;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return "{" + this.trader + ", " +
                "year: " + this.year + ", " +
                "value:" + this.value + "}";
    }
}