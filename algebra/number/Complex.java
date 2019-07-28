package number;

import exception.ComplexException;

/**
 * Complex
 */
public class Complex implements Cloneable, Visible {
    private Fraction a;// RealPart
    private Fraction b;// UnrealPart

    public Complex(Fraction a, Fraction b) {
        this.a = a;
        this.b = b;
    }

    public Complex(Fraction f) {
        this.a = f;
        this.b = new Fraction(0);
    }

    public Complex(long n) {
        a = new Fraction(n);
        b = new Fraction(0);
    }

    public Complex(long a, long b) {
        this.a = new Fraction(a);
        this.b = new Fraction(b);
    }

    public Complex add(Complex c) {
        return new Complex(a.add(c.a), b.add(c.b));
    }

    public Complex sub(Complex c) {
        return new Complex(a.sub(c.a), b.sub(c.b));
    }

    public Complex mul(Complex c) {
        return new Complex(a.mul(c.a).sub(b.mul(c.b)), a.add(c.b).add(b.mul(c.a)));
    }

    public Complex div(Complex c) {
        if (c.a.getA() == 0)
            throw new ComplexException();
        Fraction commonDiv = c.a.pow(2).add(c.b.pow(2));
        Fraction realPart = a.mul(c.a).add(b.mul(c.b)).div(commonDiv);
        Fraction unrealPart = b.mul(c.a).sub(a.mul(c.b)).div(commonDiv);
        return new Complex(realPart, unrealPart);
    }
}