package chapter16;

/**
 * Created by simjunbo on 2018-05-03.
 */
public class Clone {
    public static void main(String[] args) throws CloneNotSupportedException {
        Origin origin = new Origin("id", "pw", new SubOrigin("test"));

        Origin origin2 = origin.clone();

        if (origin.getSubOrigin() == origin2.getSubOrigin()) {
            System.out.println("같다");
        } else {
            System.out.println("다르다");
        }
    }
}

class Origin implements Cloneable {

    private String id;

    private String pw;

    private SubOrigin sub;

    public SubOrigin getSubOrigin() {
        return sub;
    }

    public Origin(String id, String pw, SubOrigin sub) {
        this.id = id;
        this.pw = pw;
        this.sub = sub;
    }

    public Origin clone() throws CloneNotSupportedException {
        Origin clonedObj = (Origin) super.clone();
        clonedObj.sub = this.sub.clone();
        return clonedObj;
    }
}

class SubOrigin implements Cloneable {
    private String test;

    public SubOrigin(String test) {
        this.test = test;
    }

    public SubOrigin clone() throws CloneNotSupportedException {
        return (SubOrigin) super.clone();
    }
}