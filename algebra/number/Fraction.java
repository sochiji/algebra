package number;

import exception.FractionException;
import util.GCD;
import util.STDIN;

public final class Fraction implements Cloneable, Visible {
    public static Fraction fractionFromInput() {
        String s = STDIN.sc.next();
        return valueOf(s);
    }

    public static Fraction valueOf(String s) {
        Fraction ret;
        int len = s.length();
        for (int i = 0; i <= len - 1; i++)
            if (s.charAt(i) == '/') {
                ret = new Fraction(Integer.parseInt(s.substring(0, i)), Integer.parseInt(s.substring(i + 1)));
                return ret;
            }
        ret = new Fraction(Integer.parseInt(s));
        return ret;
    }

    private long a, b;

    public Fraction(long n) {
        a = n;
        b = 1;
    }

    public Fraction(long x, long y) {
        a = x;
        b = y;
        if (b != 0)
            exact();
    }

    public Fraction add(Fraction r) {
        long maxf = GCD.maxCommonFactor(b, r.b);
        long b_ = b / maxf;
        long rb_ = r.b / maxf;
        return new Fraction(a * rb_ + r.a * b_, b_ * rb_ * maxf);
    }

    @Override
    public Fraction clone() throws CloneNotSupportedException {
        return (Fraction) super.clone();
    }

    public Fraction div(Fraction r) throws FractionException {
        if (r.a == 0)
            throw new FractionException("Zero Division!");
        return new Fraction(a * r.b, b * r.a);

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Fraction))
            return false;
        Fraction frc = (Fraction) obj;
        if (frc.a != a || frc.b != b)
            return false;
        return true;
    }

    void exact() {
        long maxf = GCD.maxCommonFactor(a, b);
        a /= maxf;
        b /= maxf;
        if (b < 0) {
            b = -b;
            a = -a;
        }
    }

    /**
     * @return the a
     */
    public long getA() {
        return a;
    }

    /**
     * @return the b
     */
    public long getB() {
        return b;
    }

    public Fraction getInverse() throws FractionException {
        if (a == 0)
            throw new FractionException("No inverse for zero.");
        return new Fraction(b, a);
    }

    public Fraction getMinus() {
        return new Fraction(-a, b);
    }

    public Fraction mul(Fraction r) {
        return new Fraction(a * r.a, b * r.b);
    }

    public Fraction pow(int n) {
        if (a == 0) {
            if (n == 0)
                throw new FractionException();
            return new Fraction(0);
        }
        if (n == 0)
            return new Fraction(1);
        if (n == 1)
            return this;
        if (n < 0)
            return pow(-n).getInverse();
        // Optimize Needed
        Fraction ret = this;
        for (int i = 1; i <= n - 1; i++)
            ret = this.mul(ret);
        return ret;
    }

    public Fraction sub(Fraction r) {
        long maxf = GCD.maxCommonFactor(b, r.b);
        long b_ = b / maxf;
        long rb_ = r.b / maxf;
        return new Fraction(a * rb_ - r.a * b_, b_ * rb_ * maxf);
    }

    public double toDouble() {
        return (double) a / b;
    }

    @Override
    public String toString() {
        if (a % b == 0)
            return String.valueOf(a / b);
        else
            return (a + "/" + b);
    }
}