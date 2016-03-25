import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class SAPTest {

    @Test
    public void test() throws Exception {

        Digraph digraph = readDigraph("digraph1.txt");

        SAP sap = new SAP(digraph);

        assertEquals(1, sap.ancestor(3, 11));
        assertEquals(4, sap.length(3, 11));

        assertEquals(5, sap.ancestor(9, 12));
        assertEquals(3, sap.length(9, 12));

        assertEquals(0, sap.ancestor(7, 2));
        assertEquals(4, sap.length(7, 2));

        assertEquals(-1, sap.ancestor(1, 6));
        assertEquals(-1, sap.length(1, 6));

        try {
            sap.ancestor(1, 112);
            fail();
        }
        catch (IndexOutOfBoundsException ioobe) {
        }

        try {
            sap.length(1, 112);
            fail();
        }
        catch (IndexOutOfBoundsException ioobe) {
        }
    }

    @Test
    public void test2() throws Exception {

        String[] filenames = new String[]{"digraph2.txt", "digraph3.txt", "digraph4.txt", "digraph5.txt",
                "digraph6.txt", "digraph9.txt", "digraph-ambiguous-ancestor.txt", "digraph-wordnet.txt"};

        for(String filename : filenames) {
            Digraph digraph = readDigraph(filename);
            SAP sap = new SAP(digraph);
            System.out.println("=> " + sap.ancestor(2, 3));
        }
    }

    private Digraph readDigraph(String filename) {

        String path = SAPTest.class.getResource(new File("wordnet", filename).getPath()).getPath();
        In in = new In(path);
        return new Digraph(in);
    }
}