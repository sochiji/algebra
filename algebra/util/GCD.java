package util;

public class GCD {
    public static long maxCommonFactor(long a, long b) {
        if (a < 0)
            a = -a;
        if (b < 0)
            b = -b;
        if (a < b) {
            a ^= b;
            b ^= a;
            a ^= b;
        }
        return res(a, b);
    }

    private static long res(long a, long b) {
        if (b == 0)
            return a;
        else
            return res(b, a % b);
    }

    public static long minCommonMultiple(long a, long b) {
        return a * b / maxCommonFactor(a, b);
    }
}