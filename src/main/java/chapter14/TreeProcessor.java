package chapter14;

/**
 * Created by simjunbo on 2018-04-18.
 */
public class TreeProcessor {
    public static int lookup(String k, int defaultval, Tree t) {
        if (t == null)
            return defaultval;

        if (k.equals(t.key))
            return t.val;

        return lookup(k, defaultval, k.compareTo(t.key) < 0 ? t.left : t.right);
    }

    // 첫번째 방법
    public static void update(String k, int newval, Tree t) {
        if (t == null) {
            // 새로운 노드를 추가해야 함
        } else if (k.equals(t.key)) {
            t.val = newval;
        } else {
            update(k, newval, k.compareTo(t.key) < 0 ? t.left : t.right);
        }
    }

    // 두번째 방법
    public static Tree update2(String k, int newval, Tree t) {
        if (t == null) {
            t = new Tree(k, newval, null, null);
        } else if (k.equals(t.key)) {
            t.val = newval;
        } else if (k.compareTo(t.key) < 0) {
            t.left = update2(k, newval, t.left);
        } else {
            t.right = update2(k, newval, t.right);
        }
        return t;
    }

    public static Tree fupdate(String k, int newval, Tree t) {
        return (t == null) ?
                new Tree(k, newval, null, null) :
                k.equals(t.key) ?
                        new Tree(k, newval, t.left, t.right) :
                        k.compareTo(t.key) < 0 ?
                                new Tree(t.key, t.val, fupdate(k, newval, t.left), t.right) :
                                new Tree(t.key, t.val, t.left, fupdate(k, newval, t.right));
    }
}


class Tree {
    protected String key;
    protected int val;
    protected Tree left, right;

    public Tree(String k, int v, Tree l, Tree r) {
        key = k;
        val = v;
        left = l;
        right = r;
    }
}