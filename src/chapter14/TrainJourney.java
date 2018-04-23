package chapter14;

/**
 * Created by simjunbo on 2018-04-18.
 */
public class TrainJourney {
    public int price;
    public TrainJourney onward;

    public TrainJourney(int p, TrainJourney t) {
        price = p;
        onward = t;
    }

    // 명령형
    public TrainJourney link(TrainJourney a, TrainJourney b) {
        if(a == null)
            return b;

        TrainJourney t = a;
        while(t.onward != null) {
            t = t.onward;
        }
        t.onward = b;
        return a;
    }

    // 함수형
    TrainJourney append(TrainJourney a, TrainJourney b) {
        return a == null ? b : new TrainJourney(a.price, append(a.onward, b));
    }
}
