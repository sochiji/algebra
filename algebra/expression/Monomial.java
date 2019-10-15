package expression;

import exception.AlgebraException;
import number.Fraction;

public class Monomial {
    Fraction coef;
    int deg;

    public Monomial(Fraction coef, int deg) {
        this.coef = coef;
        this.deg = deg;
    }

    public Monomial(long coef, int deg) {
        this(new Fraction(coef), deg);
    }

    public Monomial(long coef) {
        this(new Fraction(coef));
    }

    public Monomial(Fraction coef) {
        this(coef, 0);
    }

    public boolean isSameDeg(Monomial o) {
        return deg == o.deg;
    }

    Monomial add(Monomial o) {
        if (deg != o.deg)
            throw new AlgebraException();
        return new Monomial(coef.add(o.coef), deg);
    }

    public int getDeg() {
        return deg;
    }

    public Fraction getCoef() {
        return coef;
    }
}
