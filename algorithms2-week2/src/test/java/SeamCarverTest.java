import edu.princeton.cs.algs4.Picture;
import org.junit.Test;

import java.awt.*;
import java.io.File;

import static org.junit.Assert.*;

public class SeamCarverTest {
    @Test
    public void testEnegrgy() throws Exception {

        //
        Picture picture = new Picture(getPath("1x1.png"));
        SeamCarver seamCarver = new SeamCarver(picture);
        assertEquals(1, seamCarver.width());
        assertEquals(1, seamCarver.height());
        assertEquals(1000.0, seamCarver.energy(0, 0), 0.0);

        //
        double[][] expectedEnergy = new double[][]
        {
                /*
                1000.00  1000.00  1000.00  1000.00* 1000.00  1000.00
                1000.00   237.35   151.02   234.09   107.89* 1000.00
                1000.00   138.69   228.10   133.07*  211.51  1000.00
                1000.00   153.88   174.01*  284.01   194.50  1000.00
                1000.00  1000.00* 1000.00  1000.00  1000.00  1000.00
                 */
                {1000.00, 1000.00, 1000.00, 1000.00, 1000.00},
                {1000.00, 237.35, 138.69, 153.88, 1000.00},
                {1000.00, 151.02, 228.10, 174.01, 1000.00},
                {1000.00, 234.09, 133.07, 284.01, 1000.00},
                {1000.00, 107.89, 211.51, 194.50, 1000.00},
                {1000.00, 1000.00, 1000.00, 1000.00, 1000.00}
        };
        picture = new Picture(getPath("6x5.png"));
        seamCarver = new SeamCarver(picture);
        for(int i = 0; i < picture.width(); i++) {
            for(int j = 0; j < picture.height(); j++) {
                assertEquals(expectedEnergy[i][j], seamCarver.energy(i, j), 0.0);
            }
        }
    }

    @Test
    public void testRemoveHorizontalSeam() {

        Picture picture = new Picture(getPath("6x5.png"));

        int[] seam = new int[] {0, 1, 2, 3, 4, 4};

        SeamCarver seamCarver = new SeamCarver(picture);

        seamCarver.removeHorizontalSeam(seam);

        assertEquals(picture.height() - 1, seamCarver.height());
        assertEquals(picture.width(), seamCarver.width());
        assertEquals(new Color(224, 191, 182), seamCarver.picture().get(0, 0));
        assertEquals(new Color(117, 189, 149), seamCarver.picture().get(0, 1));
        assertEquals(new Color(163, 222, 132), seamCarver.picture().get(0, 2));
        assertEquals(new Color(211, 120, 173), seamCarver.picture().get(0, 3));

//        printPic(picture);
//        System.out.println("==");
//        printPic(seamCarver.picture());
    }

    @Test
    public void testRemoveVerticalSeam() throws Exception {
        Picture picture = new Picture(getPath("6x5.png"));

        int[] seam = new int[] {0, 1, 2, 3, 4};

        SeamCarver seamCarver = new SeamCarver(picture);

        seamCarver.removeVerticalSeam(seam);

        assertEquals(picture.height(), seamCarver.height());
        assertEquals(picture.width() - 1, seamCarver.width());
        assertEquals(new Color(63, 118, 247), seamCarver.picture().get(0, 0));
        assertEquals(new Color(224, 191, 182), seamCarver.picture().get(0, 1));
        assertEquals(new Color(117, 189, 149), seamCarver.picture().get(0, 2));
        assertEquals(new Color(163, 222, 132), seamCarver.picture().get(0, 3));
        assertEquals(new Color(211, 120, 173), seamCarver.picture().get(0, 4));

//        printPic(picture);
//        System.out.println("==");
//        printPic(seamCarver.picture());
//        picture.show();
//        Thread.sleep(10000);
    }

    private String getPath(String name) {
        return SeamCarverTest.class.getResource(new File("seamCarving", name).getPath()).getPath();
    }

    private void printPic(Picture picture) {
        for(int j = 0; j < picture.height(); j++) {
            for(int i = 0; i < picture.width(); i++) {
                System.out.print("(" + picture.get(i, j).getRed() + ", " + picture.get(i, j).getGreen() + ", " + picture.get(i, j).getBlue() + "), ");
            }
            System.out.println();
        }
    }
}