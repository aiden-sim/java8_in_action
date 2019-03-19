package chapter8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by simjunbo on 2018-03-26.
 */
public class Refectoring {
    public static void main(String[] args) throws IOException {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 500, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );

        // 1) 익명 클래스를 람다 표현식으로 리팩토링
        // 익명 클래스 이용
        Runnable r1 = new Runnable() {
            public void run() {
                System.out.println("Hello");
            }
        };

        // 람다 이용
        Runnable r2 = () -> System.out.println("Hello");


        // Task를 구현하는 익명 클래스
        doSomething(new Task() {
            @Override
            public void execute() {
                System.out.println("Danger danger!!");
            }
        });

        // 위코드를 람다로 변경
        // doSomething(() -> System.out.println("Danger danger!!")); // Runnable인지 doSomething 인지 모호함

        doSomething((Task) () -> System.out.println("Danger danger!!"));


        // 2) 람다 표현식을 메서드 레퍼런스로 리팩토링 하기
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel =
                menu.stream()
                        .collect(
                                Collectors.groupingBy(dish -> {
                                    if (dish.getCalories() <= 400)
                                        return CaloricLevel.DIET;
                                    else if (dish.getCalories() <= 700)
                                        return CaloricLevel.NORMAL;
                                    else
                                        return CaloricLevel.FAT;
                                })
                        );

        // 메서드 레퍼런스 방식으로 변경
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel2 =
                menu.stream().collect(Collectors.groupingBy(Dish::getCaloricLevel));


        // 저수준 리듀싱을 Collectors API로 바꾸기
        int totalCalories = menu.stream().map(Dish::getCalories)
                .reduce(0, (c1, c2) -> c1 + c2);

        int totalCalories2 = menu.stream()
                .collect(Collectors.summingInt(Dish::getCalories));


        // 3) 명령형 데이터 처리를 스트림으로 리팩토링 하기
        List<String> dishNames = new ArrayList<>();
        for (Dish dish : menu) {
            if (dish.getCalories() > 300) {
                dishNames.add(dish.getName());
            }
        }

        // 스트림 API로 변환
        menu.parallelStream()
                .filter(d -> d.getCalories() > 300)
                .map(Dish::getName)
                .collect(Collectors.toList());


        // 조건부 연기 실행
/*        if(logger.isLoggable(Log.FINER)) {
            logger.finer("Problem: " + generateDiagnostic());
        }

        ==>

        logger.log(Level.FINER, "Problem: " + generateDiagnostic());*/


        // 실행 어라운드
        String oneLine = processFile((BufferedReader b) -> b.readLine()); // 람다 전달
        String towLines = processFile((BufferedReader b) -> b.readLine() + b.readLine()); // 다른 람다 전달

    }

    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("java8incation/chap8/data.txt"))) {
            return p.process(br);
        }
    }

    public static void doSomething(Runnable r) {
        r.run();
    }

    public static void doSomething(Task r) {
        r.execute();
    }
}

enum CaloricLevel {
    DIET, NORMAL, FAT
}

class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Dish.Type type;

    public Dish(String name, boolean vegetarian, int calories, Dish.Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Dish.Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Dish) {
            if (this.name.equals(((Dish) o).name))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public enum Type {
        MEAT, FISH, OTHER
    }

    public CaloricLevel getCaloricLevel() {
        if (this.getCalories() <= 400)
            return CaloricLevel.DIET;
        else if (this.getCalories() <= 700)
            return CaloricLevel.NORMAL;
        else
            return CaloricLevel.FAT;
    }
}

