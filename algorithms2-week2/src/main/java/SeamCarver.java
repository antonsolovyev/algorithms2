import edu.princeton.cs.algs4.Picture;

import java.util.Arrays;

public class SeamCarver {
    private Picture picture;
    private double[][] energy;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);

        initEnergy();
    }

    public void initEnergy() {
        this.energy = new double[picture.width()][picture.height()];
        for(int i = 0; i < picture.width(); i++) {
            for(int j = 0; j < picture.height(); j++) {
                energy[i][j] = computeEnergy(i, j);
            }
        }
    }

    public Picture picture() {
        return picture;
    }                          // current picture

    public int width() {
        return picture.width();
    }                           // width of current picture

    public int height() {
        return picture.height();
    }                          // height of current picture

    public double energy(int x, int y) {
        if(x < 0 || x >= picture.width() || y < 0 || y >= picture.height()) {
            throw new IllegalArgumentException();
        }

        return energy[x][y];
    }               // energy of pixel at column x and row y

    private double computeEnergy(int i, int j) {
        if(i <= 0 || i >= picture.width() - 1 || j <= 0 || j >= picture.height() - 1) {
            return 1000.0;
        }

        double res = 0.0;
        for(int p : Arrays.asList(
                picture.get(i - 1, j).getRed() - picture.get(i + 1, j).getRed(),
                picture.get(i - 1, j).getGreen() - picture.get(i + 1, j).getGreen(),
                picture.get(i - 1, j).getBlue() - picture.get(i + 1, j).getBlue(),
                picture.get(i, j - 1).getRed() - picture.get(i, j + 1).getRed(),
                picture.get(i, j - 1).getGreen() - picture.get(i, j + 1).getGreen(),
                picture.get(i, j - 1).getBlue() - picture.get(i, j + 1).getBlue()
                )) {
            res += p * p;
        }

        return Math.round(Math.sqrt(res) * 100.0) / 100.0; // round to 2 decimal places
    }

    public int[] findHorizontalSeam() {
        return null;
    }              // sequence of indices for horizontal seam

    public int[] findVerticalSeam() {
        return null;
    }                // sequence of indices for vertical seam

    public void removeHorizontalSeam(int[] seam) {
        validateSeam(seam, picture.width(), picture.height());

        Picture pictureNew = new Picture(picture.width(), picture.height() - 1);
        for(int i = 0; i < pictureNew.width(); i++) {
            for(int j = 0; j < pictureNew.height(); j++) {
                int src = j;
                if(j >= seam[i]) {
                    src = j + 1;
                }
                pictureNew.set(i, j, picture.get(i, src));
            }
        }

        picture = pictureNew;

        initEnergy();
    }  // remove horizontal seam from current picture

    public void removeVerticalSeam(int[] seam) {
        validateSeam(seam, picture.height(), picture.width());

        Picture pictureNew = new Picture(picture.width() - 1, picture.height());
        for(int i = 0; i < pictureNew.width(); i++) {
            for(int j = 0; j < pictureNew.height(); j++) {
                int src = i;
                if(i >= seam[j]) {
                    src = i + 1;
                }
                pictureNew.set(i, j, picture.get(src, j));
            }
        }

        picture = pictureNew;

        initEnergy();
    }    // remove vertical seam from current picture

    private void validateSeam(int[] seam, int length, int range) {
        if(seam == null) {
            throw new NullPointerException();
        }

        if(seam.length != length) {
            throw new IllegalArgumentException();
        }

        int last = seam[0];
        for(int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= range || Math.abs(seam[i] - last) > 1) {
                throw new IllegalArgumentException();
            }
            last = seam[i];
        }
    }
}