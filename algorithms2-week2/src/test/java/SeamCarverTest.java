import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.ThreeSum;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.util.Arrays;

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
                assertEquals(expectedEnergy[i][j], seamCarver.energy(i, j), 0.01);
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

    @Test
    public void testFindVerticalSeam() throws Exception {
        Picture picture = new Picture(getPath("6x5.png"));
        SeamCarver seamCarver = new SeamCarver(picture);
        int[] seam = seamCarver.findVerticalSeam();
        assertTrue(Arrays.equals(new int[]{3, 4, 3, 2, 1}, seam));

        picture = new Picture(getPath("10x12.png"));
        seamCarver = new SeamCarver(picture);
        seam = seamCarver.findVerticalSeam();
        assertTrue(Arrays.equals(new int[]{5, 6, 7, 8, 7, 7, 6, 7, 6, 5, 6, 5}, seam));

        picture = new Picture(getPath("7x3.png"));
        seamCarver = new SeamCarver(picture);
        seam = seamCarver.findVerticalSeam();
        assertTrue(Arrays.equals(new int[]{2, 3, 2}, seam));

        picture = new Picture(getPath("5x6.png"));
        seamCarver = new SeamCarver(picture);
        seam = seamCarver.findVerticalSeam();
        assertTrue(Arrays.equals(new int[]{1, 2, 2, 3, 2, 1}, seam));
    }

    @Test
    public void testHorizontalSeam() throws Exception {
        Picture picture = new Picture(getPath("6x5.png"));
        SeamCarver seamCarver = new SeamCarver(picture);
        int[] seam = seamCarver.findHorizontalSeam();
        assertTrue(Arrays.equals(new int[]{1, 2, 1, 2, 1, 0}, seam));

        picture = new Picture(getPath("10x12.png"));
        seamCarver = new SeamCarver(picture);
        seam = seamCarver.findHorizontalSeam();
        assertTrue(Arrays.equals(new int[]{8, 9, 10, 10, 10, 9, 10, 10, 9, 8}, seam));

        picture = new Picture(getPath("7x3.png"));
        seamCarver = new SeamCarver(picture);
        seam = seamCarver.findHorizontalSeam();
        assertTrue(Arrays.equals(new int[]{0, 1, 1, 1, 1, 1, 0}, seam));

        picture = new Picture(getPath("5x6.png"));
        seamCarver = new SeamCarver(picture);
        seam = seamCarver.findHorizontalSeam();
        assertTrue(Arrays.equals(new int[]{2, 3, 2, 3, 2}, seam));
    }

    @Ignore
    @Test
    public void testVisual() throws Exception {
        Picture picture = new Picture(getPath("HJocean.png"));
        SeamCarver seamCarver = new SeamCarver(picture);

        System.out.println(picture.width());

        picture.show();
        Thread.sleep(10000);

        for(int i = 0; i < 256; i++) {
            System.out.println("removing #" + i);
            seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        }

        seamCarver.picture().show();
        Thread.sleep(10000);
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