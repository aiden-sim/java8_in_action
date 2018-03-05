package chapter5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Tmon {
    public String name;
    public int age;


    Tmon(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

public class FlatMapTest {
    public static void main(String[] args) {
        List<Tmon> list = Arrays.asList(new Tmon("심준보", 32), new Tmon("조우현", 32), new Tmon("조우현", 32), new Tmon("송두리", 32));

        // map 으로 반환
        List<String> names = list.stream()
                .map(Tmon::getName)                                 // Stream<String> => 심준보, 조우현, 조우현, 송두리
                .collect(Collectors.toList());

        // stream 형태로 반환
        String[] array = names.toArray(new String[names.size()]);
        Stream<String> streamOfName = Arrays.stream(array);         // Stream<String> => 심준보, 조우현, 조우현, 송두리

        // distinct
        List<String> str = streamOfName
                .distinct().collect(Collectors.toList());

        for (String s : str) {
            System.out.println(s);                                  // Stream<String> => 심준보, 조우현, 송두리
        }

        // split
        List<String> strList = Arrays.asList("Hello", "World");
        List<String[]> strArrayList = strList.stream()
                .map((word -> word.split("")))                  // Stream<String[]> => ["H","e","l","l","o"], ["W","o","r","l","d"]
                .collect(Collectors.toList());

        // Arrays::stream
        List<Stream<String>> streamList = strList.stream()
                .map(word -> word.split(""))                    // Stream<String[]> => ["H","e","l","l","o"], ["W","o","r","l","d"]
                .map(wordArray -> Arrays.stream(wordArray))     // Stream<Stream<String>>  => ?
                .distinct()
                .collect(Collectors.toList());                  // List<Stream<String>>

        // flatMap
        List<String> streamList2 = strList.stream()
                .map(word -> word.split(""))                    // Stream<String[]> => ["H","e","l","l","o"], ["W","o","r","l","d"]
                .flatMap(wordArray -> Arrays.stream(wordArray)) // Stream<Stream<String>>  => Stream<String>
                .distinct()
                .collect(Collectors.toList());


        List<String> a1 = new ArrayList<>();
        a1.add("test1");
        a1.add("test2");

        List<String> a2 = new ArrayList<>();
        a2.add("test2");
        a2.add("test3");

        // list 콘텐츠
        a1.addAll(a2);
    }

}
