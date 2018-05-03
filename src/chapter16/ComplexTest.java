package chapter16;

/**
 * Created by simjunbo on 2018-05-04.
 */
public class ComplexTest {
    public static void main(String[] args) {
        Complex a = new Complex(1.5, 1.4);
        Complex b = new Complex(1.5, 1.4);

        Complex c = Complex.add(a, b);
    }

}

class Complex {
    public final double re;
    public final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public static Complex add(Complex a, Complex b) {
        return new Complex(a.re + b.re, a.im + b.im);
    }
}
