import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TrieTest {
    @Test
    public void test() {
        Trie trie = new Trie();
        
        trie.put("AA");
        trie.put("AB");
        trie.put("ABC");
        trie.put("ABV");
        
        assertEquals(Arrays.asList("AA", "AB", "ABC", "ABV"), trie.content());
        
        assertFalse(trie.contains("HH"));
        assertTrue(trie.contains("AA"));
        assertTrue(trie.contains("AB"));
        assertTrue(trie.contains("ABC"));
        assertTrue(trie.contains("ABV"));
        assertFalse(trie.contains("ABG"));
        assertTrue(trie.containsPrefix("A"));
        assertFalse(trie.containsPrefix("AH"));
        assertFalse(trie.containsPrefix("AC"));
        assertFalse(trie.containsPrefix("H"));
        assertTrue(trie.containsPrefix("AA"));
        assertTrue(trie.containsPrefix("AB"));
        assertTrue(trie.containsPrefix("ABC"));
    }
    
    @Test
    public void testInvalidInput() {
        try {
            Trie trie = new Trie();
            
            trie.put("Aa");
            
            fail();
        } catch(IllegalArgumentException iae) {
            // expected
        }
    }
}
