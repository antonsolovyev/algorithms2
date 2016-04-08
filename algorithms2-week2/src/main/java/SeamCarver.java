import edu.princeton.cs.algs4.Picture;

import java.util.Arrays;

public class SeamCarver {
    private Picture picture;
    private double[][] energy;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);

        initEnergy();
    }

    private void initEnergy() {
        this.energy = new double[picture.width()][picture.height()];
        for(int i = 0; i < picture.width(); i++) {
            for(int j = 0; j < picture.height(); j++) {
                energy[i][j] = computeEnergy(i, j);
            }
        }
    }

    public Picture picture() {
        return new Picture(picture);
    }                          // current picture

    public int width() {
        return picture.width();
    }                           // width of current picture

    public int height() {
        return picture.height();
    }                          // height of current picture

    public double energy(int x, int y) {
        if(x < 0 || x >= picture.width() || y < 0 || y >= picture.height()) {
            throw new IndexOutOfBoundsException();
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

//        return Math.round(Math.sqrt(res) * 100.0) / 100.0; // round to 2 decimal places
        return Math.sqrt(res);
    }

    public int[] findHorizontalSeam() {
        double[][] distTo = new double[picture.width()][picture.height()];
        for(int i = 0; i < picture.width(); i++) {
            for(int j = 0; j < picture.height(); j++) {
                if(i == 0) {
                    distTo[i][j] = energy(i, j);
                }
                else {
                    distTo[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }

        int[][] edgeTo = new int[picture.width()][picture.height()];

        for(int i = 0; i < picture.width(); i++) {
            for(int j = 0; j < picture.height(); j++) {
                for(int k = -1; k < 2; k++) {
                    int iAdj = i + 1;
                    int jAdj = j + k;

                    if(iAdj < 0 || iAdj >= picture.width()) {
                        continue;
                    }

                    if(jAdj < 0 || jAdj >= picture.height()) {
                        continue;
                    }

                    double newDistTo = distTo[i][j] + energy(iAdj, jAdj);
                    if(newDistTo < distTo[iAdj][jAdj]) {
                        distTo[iAdj][jAdj] = newDistTo;
                        edgeTo[iAdj][jAdj] = j;
                    }
                }
            }
        }

        double minDistTo = Double.POSITIVE_INFINITY;
        int minJ = 0;
        for(int j = 0; j < picture.height(); j++) {
            if(distTo[picture.width() - 1][j] < minDistTo) {
                minDistTo = distTo[picture.width() - 1][j];
                minJ = j;
            }
        }

        int[] res = new int[picture.width()];

        int j = minJ;
        for(int i = picture.width() - 1; i >= 0; i--) {
            res[i] = j;
            j = edgeTo[i][j];
        }

        return res;
    }              // sequence of indices for horizontal seam

    public int[] findVerticalSeam() {
        double[][] distTo = new double[picture.width()][picture.height()];
        for(int j = 0; j < picture.height(); j++) {
            for(int i = 0; i < picture.width(); i++) {
                if(j == 0) {
                    distTo[i][j] = energy(i, j);
                }
                else {
                    distTo[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }

        int[][] edgeTo = new int[picture.width()][picture.height()];

        for(int j = 0; j < picture.height(); j++) {
            for(int i = 0; i < picture.width(); i++) {
                for(int k = -1; k < 2; k++) {
                    int jAdj = j + 1;
                    int iAdj = i + k;

                    if(jAdj < 0 || jAdj >= picture.height()) {
                        continue;
                    }

                    if(iAdj < 0 || iAdj >= picture.width()) {
                        continue;
                    }

                    double newDistTo = distTo[i][j] + energy(iAdj, jAdj);
                    if(newDistTo < distTo[iAdj][jAdj]) {
                        distTo[iAdj][jAdj] = newDistTo;
                        edgeTo[iAdj][jAdj] = i;
                    }
                }
            }
        }

        double minDistTo = Double.POSITIVE_INFINITY;
        int minI = 0;
        for(int i = 0; i < picture.width(); i++) {
            if(distTo[i][picture.height() - 1] < minDistTo) {
                minDistTo = distTo[i][picture.height() - 1];
                minI = i;
            }
        }

        int[] res = new int[picture.height()];

        int i = minI;
        for(int j = picture.height() - 1; j >= 0; j--) {
            res[j] = i;
            i = edgeTo[i][j];
        }

        return res;
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