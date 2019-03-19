package chapter9.defaultmethod;

/**
 * Created by simjunbo on 2018-03-28.
 */

interface A {
    default void hello() {
        System.out.println("Hello form A");
    }
}

// 2) 서브 인터페이스가 이긴다.
interface B {
    default void hello() {
        System.out.println("Hello from B");
    }
}

// 1) 클래스나 슈퍼클래스에 정의한 메서드 디폴트 메서드보다 우선권을 갖는다.
class C {
    public void hello() {
        System.out.println("Hello from C");
    }
}

public class MultipleImplement extends C implements A, B {
    public static void main(String[] args) {
        // 디폴트 메서드 우선순위가 결정되지 않았다면 여러 인터페이스를 상속받는 클래스가 명시적으로 디폴트 메서드를 오버라이드 하고 호출한다.
        new MultipleImplement().hello();
    }
}
