import org.junit.Test;

import static org.junit.Assert.*;

public class CircularSuffixArrayTest {

    /*
     i       Original Suffixes           Sorted Suffixes         index[i]
    --    -----------------------     -----------------------    --------
     0    A B R A C A D A B R A !     ! A B R A C A D A B R A    11
     1    B R A C A D A B R A ! A     A ! A B R A C A D A B R    10
     2    R A C A D A B R A ! A B     A B R A ! A B R A C A D    7
     3    A C A D A B R A ! A B R     A B R A C A D A B R A !    0
     4    C A D A B R A ! A B R A     A C A D A B R A ! A B R    3
     5    A D A B R A ! A B R A C     A D A B R A ! A B R A C    5
     6    D A B R A ! A B R A C A     B R A ! A B R A C A D A    8
     7    A B R A ! A B R A C A D     B R A C A D A B R A ! A    1
     8    B R A ! A B R A C A D A     C A D A B R A ! A B R A    4
     9    R A ! A B R A C A D A B     D A B R A ! A B R A C A    6
    10    A ! A B R A C A D A B R     R A ! A B R A C A D A B    9
    11    ! A B R A C A D A B R A     R A C A D A B R A ! A B    2

     */
    @Test
    public void testIndex() throws Exception {
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");
        
        assertEquals(11, circularSuffixArray.index(0));
        assertEquals(10, circularSuffixArray.index(1));
        assertEquals(7, circularSuffixArray.index(2));
        assertEquals(0, circularSuffixArray.index(3));
        assertEquals(3, circularSuffixArray.index(4));
        assertEquals(5, circularSuffixArray.index(5));
        assertEquals(8, circularSuffixArray.index(6));
        assertEquals(1, circularSuffixArray.index(7));
        assertEquals(4, circularSuffixArray.index(8));
        assertEquals(6, circularSuffixArray.index(9));
        assertEquals(9, circularSuffixArray.index(10));
        assertEquals(2, circularSuffixArray.index(11));
    }

    @Test
    public void testNull() throws Exception {
        try {
            CircularSuffixArray circularSuffixArray = new CircularSuffixArray(null);
            fail();
        }
        catch(NullPointerException npe) {
            // good
        }
    }

    @Test
    public void testOOB() throws Exception {
        try {
            CircularSuffixArray circularSuffixArray = new CircularSuffixArray("AAA");
            circularSuffixArray.index(0);
            circularSuffixArray.index(1);
            circularSuffixArray.index(2);
            circularSuffixArray.index(3);
            fail();
        }
        catch(ArrayIndexOutOfBoundsException aioobe) {
            // good
        }
    }
}