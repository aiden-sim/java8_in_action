package AppendixA;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/**
 * 중복 에러
 */
/*
@interface Author {
    String name();
}

@Author(name = "Raoul")
@Author(name = "Mario")
@Author(name = "Alan")
class Booke {
}
*/

// 어노테이션 반복
public class Annotation1 {
    public static void main(String[] args) {
        Author[] authors = Book.class.getAnnotationsByType(Author.class);
        Arrays.asList(authors).forEach(a -> {
            System.out.println(a.name());
        });
    }
}

@Repeatable(Authors.class)
@interface Author {
    String name();
}

@Retention(RetentionPolicy.RUNTIME)
        // 꼭 붙여 주어야 한다.
@interface Authors {
    Author[] value();
}

@Author(name = "Raoul")
@Author(name = "Mario")
@Author(name = "Alan")
class Book {
}