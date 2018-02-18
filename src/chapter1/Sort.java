package chapter1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by simjunbo on 2018-02-11.
 */
public class Sort {
    public static void main(String[] args) {
        List<Book> list = new ArrayList<>();
        list.add(new Book(200));
        list.add(new Book(100));

        list.sort(Comparator.comparing(Book::getPrice)); // 오름차순
        list.sort(Comparator.comparing(Book::getPrice).reversed()); // 내림차순

        for(Book book : list){
            System.out.println(book.getPrice());
        }
    }
}


class Book {
    private int price;

    public Book(int price) {
        this.price = price;
    }

    public int getPrice() {
        return this.price;
    }
}