import java.util.ArrayList;
import java.util.List;

public class Trie {
    private static final int R = 26;
    private Node root;

    public static class ContainsResponse {
        private final boolean contains;
        private final boolean containsAsPrefix;
        
        public ContainsResponse(boolean contains, boolean containsAsPrefix) {
            this.contains = contains;
            this.containsAsPrefix = containsAsPrefix;
        }

        public boolean getContains() {
            return contains;
        }

        public boolean getContainsAsPrefix() {
            return containsAsPrefix;
        }
    }
    
    public boolean contains(CharSequence s) {
        return contains(root, s, 0).getContains();  
    }

    public boolean containsPrefix(CharSequence s) {
        ContainsResponse containsResponse = contains(root, s, 0);
        return containsResponse.getContainsAsPrefix() || containsResponse.getContains();
    }
    
    public ContainsResponse contains2(CharSequence s) {
        return contains(root, s, 0);
    }
            
    private ContainsResponse contains(Node n, CharSequence s, int position) {
        if(position == s.length()) {
            boolean hasChildren = false;
            for(int i = 0; i < n.next.length; i++) {
                if (n.next[i] != null) {
                    hasChildren = true;
                    break;
                }
            }
            
            return new ContainsResponse(n.isPopulated, hasChildren);
        }
        
        int index = charToIndex(s.charAt(position));
        if(n.next[index] == null) {
            return new ContainsResponse(false, false);
        } else {
            return contains(n.next[index], s, position + 1);
        }
    }

    public void put(CharSequence s) {
        root = put(root, s, 0);
    }
    
    private Node put(Node n, CharSequence s, int position) {
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
