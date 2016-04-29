import java.util.*;

public class BoggleSolver {
    private static final int R = 26;
    
    private Node root;

    private static class Node {
        private Node[] next = new Node[R];
        boolean isPopulated;
        boolean hasChildren;
    }
    
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for(String s : dictionary) {
            put(s);
        }
    }

    private boolean contains(CharSequence s) {
        Node n = contains(root, s, 0);
        if(n == null) {
            return false;
        } else {
            return n.isPopulated;
        }
    }

    private Node contains(Node n, CharSequence s, int position) {
        if(n == null) {
            return null;
        }
        
        if(position == s.length()) {
            return n;
        }

        int index = charToIndex(s.charAt(position));
        
        return contains(n.next[index], s, position + 1);
    }

    private void put(CharSequence s) {
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
            n.hasChildren = true;
        }

        return n;
    }

    private int charToIndex(char c) {
        return c - 'A';
    }

    private char indexToChar(int index) {
        return (char) (index + 'A');
    }
    
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        
        boolean[][] marked = new boolean[board.rows()][board.cols()];
        
        Set<String> res = new TreeSet<>();
        for(int i = 0; i < board.rows(); i++) {
            for(int j = 0; j < board.cols(); j++) {
                dfs(board, i, j, marked, new StringBuilder(), res, root);
            }
        }
        
        return res;
    }
    
    private void dfs(BoggleBoard boggleBoard, int i, int j, boolean[][] marked,
                     StringBuilder pathLetters, Set<String> wordsFound, Node node) {
        boolean q = false;
        try {
            marked[i][j] = true;
            char letter = boggleBoard.getLetter(i, j);
            if(letter == 'Q') {
                q = true;
                pathLetters.append("QU");
            } else {
                pathLetters.append(letter);
            }

            node = contains(node, pathLetters, pathLetters.length() - (q ? 2 : 1));
            
            if(node == null) {
                return;
            }

            if (pathLetters.length() > 2 && node.isPopulated) {
                wordsFound.add(pathLetters.toString());
            }

            if(!node.hasChildren) {
                return;
            }
            
            for(int k = i - 1; k <= i + 1; k++) {
                for(int l = j - 1; l <= j + 1; l++) {
                    if(k < 0 || k >= boggleBoard.rows() || l < 0 || l >= boggleBoard.cols()) {
                        continue;
                    }
                    
                    if(marked[k][l]) {
                        continue;
                    }
                    
                    dfs(boggleBoard, k, l, marked, pathLetters, wordsFound, node);
                }
            }
        }
        finally {
            marked[i][j] = false;
            pathLetters.deleteCharAt(pathLetters.length() - 1);
            if(q) {
                pathLetters.deleteCharAt(pathLetters.length() - 1);
            }
        }
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if(!contains(word)) {
            return 0;
        }
        
        switch(word.length()) {
            case 0:
            case 1:
            case 2:
                return 0;
            case 3:
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 5;
            default:
                return 11;
        }
    }
}
