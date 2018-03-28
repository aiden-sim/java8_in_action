package chapter9.defaultmethod;

/**
 * Created by simjunbo on 2018-03-28.
 */
public interface Resizable {
    int getWidth();

    int getHeigth();

    void setWidth(int width);

    void setHeight(int height);

    void setAbsoluteSize(int width, int height);

    default void setRelativeSize(int wFactor, int hFactor) {
        setAbsoluteSize(getWidth() / wFactor, getHeigth() / hFactor);
    }
}
