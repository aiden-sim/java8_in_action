package chapter9.defaultmethod;

/**
 * Created by simjunbo on 2018-03-28.
 */
public interface Rotatable {
    void setRotationAngle(int angleInDegrees);

    int getRotationAngle();

    default void rotateBy(int angleInDegrees) {
        setRotationAngle((getRotationAngle() + angleInDegrees) % 360);
    }
}
