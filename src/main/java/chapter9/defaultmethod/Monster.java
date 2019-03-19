package chapter9.defaultmethod;

/**
 * Created by simjunbo on 2018-03-28.
 */
public class Monster implements Rotatable, Moveable, Resizable {

    public static void main(String[] args) {
        Monster m = new Monster();
        m.rotateBy(180);
        m.moveVertically(10);
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setX(int x) {

    }

    @Override
    public void setY(int y) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeigth() {
        return 0;
    }

    @Override
    public void setWidth(int width) {

    }

    @Override
    public void setHeight(int height) {

    }

    @Override
    public void setAbsoluteSize(int width, int height) {

    }

    @Override
    public void setRotationAngle(int angleInDegrees) {

    }

    @Override
    public int getRotationAngle() {
        return 0;
    }
}
