import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class WordNetTest {

    @Test
    public void testCtor() throws Exception {

        // NPE on nulls
        try {
            WordNet wordNet = new WordNet(null, null);
            fail();
        }
        catch(NullPointerException npe) {
        }

        //
        String synsets = getFullFilename("synsets8.txt");
        String hypernyms = getFullFilename("hypernyms8ManyAncestors.txt");
        WordNet wordNet = new WordNet(synsets, hypernyms);
        assertTrue(wordNet.isNoun("a"));
        assertTrue(wordNet.isNoun("b"));
        assertTrue(wordNet.isNoun("c"));
        assertTrue(wordNet.isNoun("d"));
        assertTrue(wordNet.isNoun("e"));
        assertTrue(wordNet.isNoun("f"));
        assertTrue(wordNet.isNoun("g"));
        assertTrue(wordNet.isNoun("h"));

        synsets = getFullFilename("synsets.txt");
        hypernyms = getFullFilename("hypernyms.txt");
        wordNet = new WordNet(synsets, hypernyms);
        int count = 0;
        for(String noun : wordNet.nouns()) {
            count++;
        }
        assertEquals(119188, count);
    }

    private String getFullFilename(String shortName) {

        return WordNetTest.class.getResource(new File("wordnet", shortName).getPath()).getPath();
    }

    @Test
    public void testSap() throws Exception {

        // test args
        String synsets = getFullFilename("synsets.txt");
        String hypernyms = getFullFilename("hypernyms.txt");
        WordNet wordNet = new WordNet(synsets, hypernyms);

        try {
            wordNet.sap("hui", "qwerty");
            fail();
        }
        catch(IllegalArgumentException iae) {
        }

        try {
            wordNet.sap(null, null);
            fail();
        }
        catch(NullPointerException iae) {
        }

        try {
            wordNet.distance(null, null);
            fail();
        }
        catch(NullPointerException iae) {
        }

        try {
            wordNet.isNoun(null);
            fail();
        }
        catch(NullPointerException iae) {
        }

        // Some tests
        assertEquals("great_ape pongid", wordNet.sap("chimpanzee", "gorilla"));
        assertEquals(2, wordNet.distance("chimpanzee", "gorilla"));
        assertEquals("primate", wordNet.sap("chimpanzee", "human"));
        assertEquals("big_cat cat", wordNet.sap("tiger", "cheetah"));
        assertEquals("big_cat cat", wordNet.sap("cat", "cheetah"));
        assertEquals("adult grownup", wordNet.sap("man", "woman"));
    }

    @Test
    public void testInvalids() {

        try {

            String synsets = getFullFilename("synsets3.txt");
            String hypernyms = getFullFilename("hypernyms3InvalidCycle.txt");
            WordNet wordNet = new WordNet(synsets, hypernyms);

            fail();
        } catch(IllegalArgumentException iae) {
        }

        try {

            String synsets = getFullFilename("synsets3.txt");
            String hypernyms = getFullFilename("hypernyms3InvalidTwoRoots.txt");
            WordNet wordNet = new WordNet(synsets, hypernyms);

            fail();
        } catch(IllegalArgumentException iae) {
        }

        try {

            String synsets = getFullFilename("synsets6.txt");
            String hypernyms = getFullFilename("hypernyms6InvalidCycle.txt");
            WordNet wordNet = new WordNet(synsets, hypernyms);

            fail();
        } catch(IllegalArgumentException iae) {
        }

        try {

            String synsets = getFullFilename("synsets6.txt");
            String hypernyms = getFullFilename("hypernyms6InvalidTwoRoots.txt");
            WordNet wordNet = new WordNet(synsets, hypernyms);

            fail();
        } catch(IllegalArgumentException iae) {
        }

        try {

            String synsets = getFullFilename("synsets6.txt");
            String hypernyms = getFullFilename("hypernyms6InvalidCycle+Path.txt");
            WordNet wordNet = new WordNet(synsets, hypernyms);

            fail();
        } catch(IllegalArgumentException iae) {
        }
    }
}