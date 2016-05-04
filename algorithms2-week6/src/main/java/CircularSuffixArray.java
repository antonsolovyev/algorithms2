import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
    private String string;
    private int[] indexes;
    
    public CircularSuffixArray(String string) {
        this.string = string;
        
        indexes = new int[string.length()];
        for(int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }

        sort(indexes);
    } // circular suffix array of s
    
    public int length() {
        return string.length();
    }                  // length of s
    
    public int index(int i) {
        return indexes[i];
    }              // returns index of ith sorted suffix

    private void sort(int[] a) {
        sort(a, 0, a.length - 1, 0);
    }

    private void sort(int[] a, int lo, int hi, int d) {
        if(hi <= lo) {
            return;
        }
        
        int lt = lo, gt = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(a[i], d);
            if(t < v) {
                exch(a, lt++, i++);
            } else if (t > v) {
                exch(a, i, gt--);
            } else {
                i++;
            }
        }

        sort(a, lo, lt - 1, d);
        if (v >= 0) {
            sort(a, lt, gt, d + 1);
        }
        sort(a, gt + 1, hi, d);
    }

    private int charAt(int i, int d) {
        if (d == string.length()) {
            return -1;
        }
        return string.charAt((d + i) % string.length());
    }
    
    private void exch(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
