import java.util.ArrayList;
import java.util.List;

public class Trie {
    private static final int R = 26;
    private Node root;
    
    public boolean contains(String s) {
        return contains(root, s, 0, false);  
    }
    
    private boolean contains(Node n, String s, int position, boolean prefixSearch) {
        if(position == s.length()) {
            if(!prefixSearch) {
                return n.isPopulated;
            } else {
                for(int i = 0; i < n.next.length; i++) {
                    if(n.next[i] != null) {
                        return true;
                    }
                }
                return n.isPopulated;
            }
        }
        
        int index = charToIndex(s.charAt(position));
        if(n.next[index] == null) {
            return false;
        } else {
            return contains(n.next[index], s, position + 1, prefixSearch);
        }
    }
    
    public void put(String s) {
        root = put(root, s, 0);
    }
    
    public boolean containsPrefix(String s) {
        return contains(root, s, 0, true);
    }
    
    private Node put(Node n, String s, int position) {
        if(n == null) {
            n = new Node();
        }
        
        if(position == s.length()) {
            n.isPopulated = true;    
        } else {
            int index = charToIndex(s.charAt(position));
            n.next[index] = put(n.next[index], s, position + 1);
        }
        
        return n;
    }
    
    private static class Node {
        private Node[] next = new Node[R];
        boolean isPopulated;
    }
    
    public List<String> content() {
        List<String> res = new ArrayList<>();
        
        collect(root, new StringBuilder(), res);
        
        return res;
    }
    
    private void collect(Node n, StringBuilder prefix, List<String> results) {
        if(n == null) {
            return;
        }
        
        if(n.isPopulated) {
            results.add(prefix.toString());
        }
        
        for(int i = 0; i < R; i++) {
            prefix.append(indexToChar(i));
            collect(n.next[i], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
    
    private int charToIndex(char c) {
        if(c < 'A' || c > 'Z') {
            throw new IllegalArgumentException();
        }
        
        return c - 'A';
    }
    
    private char indexToChar(int index) {
        return (char) (index + 'A');
    }
}
