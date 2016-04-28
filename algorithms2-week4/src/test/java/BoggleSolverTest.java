import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.*;

public class BoggleSolverTest {

    @Test
    public void testSolver() throws Exception {

        String[] dictionary = readDictionary("dictionary-algs4.txt");
        BoggleSolver boggleSolver = new BoggleSolver(dictionary);
        
        assertEquals(33, getScore(boggleSolver, "board4x4.txt"));
        assertEquals(84, getScore(boggleSolver, "board-q.txt"));
    }

    @Test
    public void testSolver3() throws Exception {

        String[] dictionary = readDictionary("dictionary-algs4.txt");
        BoggleSolver boggleSolver = new BoggleSolver(dictionary);

        BoggleBoard boggleBoard = new BoggleBoard(getPath("board4x4.txt"));

        Set<String> res = (Set<String>) boggleSolver.getAllValidWords(boggleBoard);
        assertEquals(29, res.size());
        System.out.println("word count: " + res.size() + ", words: " + res);
    }

    @Test
    public void testExotic() throws Exception {

        String[] dictionary = readDictionary("dictionary-yawl.txt");
        BoggleSolver boggleSolver = new BoggleSolver(dictionary);
        
        for(String boardName : Arrays.asList("board-antidisestablishmentarianisms.txt",
                "board-dichlorodiphenyltrichloroethanes.txt",
                "board-pneumonoultramicroscopicsilicovolcanoconiosis.txt")) {

            BoggleBoard boggleBoard = new BoggleBoard(getPath(boardName));
            Set<String> res = (Set<String>) boggleSolver.getAllValidWords(boggleBoard);
            System.out.println("word count: " + res.size() + ", words: " + res);
        }
    }

    @Test
    public void testSolver2() throws Exception {
        String[] dictionary = readDictionary("dictionary-yawl.txt");
        BoggleSolver boggleSolver = new BoggleSolver(dictionary);

        assertEquals(0, getScore(boggleSolver, "board-points0.txt"));
        assertEquals(1, getScore(boggleSolver, "board-points1.txt"));
        assertEquals(2, getScore(boggleSolver, "board-points2.txt"));
        assertEquals(3, getScore(boggleSolver, "board-points3.txt"));
        assertEquals(4, getScore(boggleSolver, "board-points4.txt"));
        assertEquals(5, getScore(boggleSolver, "board-points5.txt"));
        assertEquals(100, getScore(boggleSolver, "board-points100.txt"));
        assertEquals(200, getScore(boggleSolver, "board-points200.txt"));
        assertEquals(300, getScore(boggleSolver, "board-points300.txt"));
    }

    @Test
    public void testSpeed() throws Exception {
        String[] dictionary = readDictionary("dictionary-yawl.txt");
        BoggleSolver boggleSolver = new BoggleSolver(dictionary);

        
        long start = System.currentTimeMillis();
        int  n = 100;
        for(int i = 0; i < n; i++) {
            System.out.println("Running board #" + i);
            
            boggleSolver.getAllValidWords(new BoggleBoard());
        }
        long end = System.currentTimeMillis();
        System.out.println("Average time per board: " + ((end - start) / n) + "ms");
    }

    private int getScore(BoggleSolver boggleSolver, String boardFileName) {
        BoggleBoard boggleBoard = new BoggleBoard(getPath(boardFileName));
        System.out.println(boggleSolver.getAllValidWords(boggleBoard));

        int score = 0;
        for(String w : boggleSolver.getAllValidWords(boggleBoard)) {
            score += boggleSolver.scoreOf(w);
        }
        return score;
    }    

    private String[] readDictionary(String fileName) throws IOException {
        return FileUtils.readLines(new File(getPath(fileName))).toArray(new String[]{});
    }

    private String getPath(String name) {
        return BoggleSolverTest.class.getResource(new File("boggle", name).getPath()).getPath();
    }

}