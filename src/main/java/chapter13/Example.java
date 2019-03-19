package chapter13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

/**
 * Created by simjunbo on 2018-04-17.
 */
public class Example {
/*
    // 명령형
    public static void Command() {
        Transaction mostExpensive = transactions.get(0);
        if (Transaction == null)
            throw new IllegalArgumentException("Empty list of transactions");

        for (Transaction t : transactions.subList(1, transactions.size())) {
            if (t.getValue() > mostExpensive.getValue()) {
                mostExpensive = t;
            }
        }
    }
*/

/*
    // 함수형
    public static void Function() {
        Optional<Transaction> mostExpensive = transactions.stream()
                .max(comparing(Transaction::getValue));
    }
    */

    public static List<List<Integer>> subsets(List<Integer> list) {
        if (list.isEmpty()) { // 입력 리스트가 비었다면 빈 리스트 자신이 서브집합
            List<List<Integer>> ans = new ArrayList<>();
            ans.add(Collections.emptyList());
            return ans;
        }

        Integer first = list.get(0);
        List<Integer> rest = list.subList(1, list.size());

        List<List<Integer>> subans = subsets(rest); // 빈 리스트가 아니면 먼저 하나의 요소를 꺼내고 나머지 요소의 모든 서브집합을 찾아서 subans로 전달한다. subans는 절반의 정답을 포함한다.
        List<List<Integer>> subans2 = insertAll(first, subans); // 정답의 나머지 절반을 포함하는 subans2는 subans의 모든 리스트에 처음 꺼낸 요소를 앞에 추가해서 만든다.
        return concat(subans, subans2);                         // subans, subans2를 연결하면 정답이 완성된다.
    }

    public static List<List<Integer>> insertAll(Integer first, List<List<Integer>> lists) {
        List<List<Integer>> result = new ArrayList<>();
        for (List<Integer> list : lists) {
            List<Integer> copyList = new ArrayList<>();
            copyList.add(first);
            copyList.addAll(list);
            result.add(copyList);
        }
        return result;
    }

    public static List<List<Integer>> concat(List<List<Integer>> a, List<List<Integer>> b) {
        List<List<Integer>> r = new ArrayList<>(a);
        r.addAll(b);
        return r;
    }

    // 반복 방식 팩토리얼
    public static int factorialIterative(int n) {
        int r = 1;
        for (int i = 0; i <= n; i++) {
            r *= i;
        }
        return r;
    }

    // 재귀 방식의 팩토리얼
    public static long factorialRecursive(long n) {
        return n == 1 ? 1 : n * factorialRecursive(n - 1);
    }

    // 스트림 팩토리얼
    public static long factorialStreams(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(1, (long a, long b) -> a * b);
    }

    // 꼬리재귀 형태
    public static long factorialTailRecursive(long n) {
        return factorialHelper(1, n);
    }

    public static long factorialHelper(long acc, long n) {
        return n == 1 ? acc : factorialHelper(acc * n, n - 1);
    }
}
