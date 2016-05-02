import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
    private String string;
    private Integer[] indexes;
    
    public CircularSuffixArray(String string) {
        this.string = string;
        
        indexes = new Integer[string.length()];
        for(int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }

        Arrays.sort(indexes, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                String s1 = new StringBuilder(string.substring(i1)).append(string.substring(0, i1)).toString();
                String s2 = new StringBuilder(string.substring(i2)).append(string.substring(0, i2)).toString();
                return s1.compareTo(s2);
            }
        });
    } // circular suffix array of s
    
    public int length() {
        return string.length();
    }                  // length of s
    
    public int index(int i) {
        return indexes[i];
    }              // returns index of ith sorted suffix
}
