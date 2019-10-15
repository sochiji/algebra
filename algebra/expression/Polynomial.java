package expression;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

import number.Fraction;

public class Polynomial {
    int maxDegree = -1;
    LinkedList<Monomial> rec;
    static Comparator<Monomial> comparator = new Comparator<Monomial>() {
        @Override
        public int compare(Monomial a, Monomial b) {
            return a.deg - b.deg;
        }
    };

    public Polynomial(LinkedList<Monomial> o) {
        maxDegree = o.getLast().deg;
        rec = o;
    }

    public Polynomial(Fraction coef) {
        this(new Monomial(coef));
    }

    public Polynomial(long coef) {
        this(new Monomial(coef));
    }

    public Polynomial(Monomial o) {
        maxDegree = o.deg;
        rec = new LinkedList<Monomial>();
        rec.addLast(o);
    }

    public Polynomial(Monomial[] o) {
        int len = o.length;
        Arrays.sort(o, 0, len - 1, comparator);
        rec = new LinkedList<Monomial>();
        for (int i = 0; i <= len - 1; i++)
            rec.addLast(o[i]);
        updateMaxDegree();
    }

    void updateMaxDegree() {
        maxDegree = rec.get(rec.size() - 1).deg;
    }

    void sortAndMerge() {
        rec.sort(comparator);
        ListIterator<Monomial> p = rec.listIterator();
        Fraction coefBuf = new Fraction(0);
        int degBuf = -1;
        while (p.hasNext()) {
            Monomial x = p.next();
            if (degBuf == x.deg) {
                coefBuf = coefBuf.add(x.coef);
            } else {
                degBuf = x.deg;
                coefBuf = x.coef;
            }
        }
    }

    int[] getDegList() {
        int[] degList = new int[rec.size()];
        int index = 0;
        for (Monomial x : rec) {
            degList[index] = x.deg;
            index++;
        }
        return degList;
    }

    public Polynomial linearAdd(Polynomial o) {
        int len1 = rec.size();
        int len2 = o.rec.size();
        Monomial[] arr1 = rec.toArray(new Monomial[len1]);
        Monomial[] arr2 = o.rec.toArray(new Monomial[len2]);
        int p1 = 0;
        int p2 = 0;
        LinkedList<Monomial> ret = new LinkedList<Monomial>();
        while (p1 <= len1 && p2 <= len2) {
            int diff = comparator.compare(arr1[p1], arr2[p2]);
            if (diff == 0) {
                ret.add(arr1[p1].add(arr2[p2]));
                p1++;
                p2++;
            } else if (diff < 0) {
                ret.add(arr1[p1]);
                p1++;
            } else {
                ret.add(arr2[p2]);
                p2++;
            }
        }
        while (p1 <= len1) {
            ret.add(arr1[p1]);
            p1++;
        }
        while (p2 <= len2) {
            ret.add(arr2[p2]);
            p2++;
        }
        return new Polynomial(ret);
    }

    public Polynomial add(Polynomial o) {
        ListIterator<Monomial> p1 = rec.listIterator();
        ListIterator<Monomial> p2 = o.rec.listIterator();
        Monomial m1 = p1.next();
        Monomial m2 = p2.next();
        LinkedList<Monomial> ret = new LinkedList<Monomial>();
        boolean over1 = !p1.hasNext(), over2 = !p2.hasNext();

        while (!over1 && !over2) {
            if (m1.deg < m2.deg) {
                ret.add(m1);
                if (p1.hasNext())
                    m1 = p1.next();
                else {
                    over1 = true;
                    break;
                }
            } else if (m1.deg > m2.deg) {
                ret.add(m2);
                if (p2.hasNext())
                    m2 = p2.next();
                else {
                    over2 = true;
                    break;
                }
            } else if (m1.deg == m2.deg) {
                ret.add(m1.add(m2));
                if (p1.hasNext())
                    m1 = p1.next();
                else {
                    over1 = true;
                    break;
                }
                if (p2.hasNext())
                    m2 = p2.next();
                else {
                    over2 = true;
                    break;
                }
            }
        }
        while (!over1) {
            ret.add(p1.next());
            if (p1.hasNext())
                m1 = p1.next();
            else
                over1 = true;
        }
        while (!over2) {
            ret.add(m2);
            if (p2.hasNext())
                m2 = p2.next();
            else
                over2 = true;
        }
        return new Polynomial(ret);
    }

}
