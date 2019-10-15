package util;

/**
 * MergeSort
 */
public class MergeSort {

    public static <T> void sort(T[] arr, int st, int ed, CompareMethod comp) {
        int mid = (st + ed) / 2;
        if (st != mid)
            sort(arr, st, mid, comp);
        if (mid + 1 != ed)
            sort(arr, mid + 1, ed, comp);
        //T[] tmp = new T[ed - st + 1];
    }

    interface CompareMethod {
        <T> boolean smaller(T a, T b);
    }
}