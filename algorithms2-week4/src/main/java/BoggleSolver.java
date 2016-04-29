import edu.princeton.cs.algs4.TST;

import java.util.*;

public class BoggleSolver {
    private TST<Boolean> dictionary;
    private Map<String, Boolean> prefixes;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        this.dictionary = new TST<>();
        for(String s : dictionary) {
            this.dictionary.put(s, true);
        }
        prefixes = new HashMap<>();
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        
        boolean[][] marked = new boolean[board.rows()][board.cols()];
        
        Set<String> res = new TreeSet<>();
        for(int i = 0; i < board.rows(); i++) {
            for(int j = 0; j < board.cols(); j++) {
                dfs(board, i, j, marked, "", res);
            }
        }
        
        return res;
    }
    
    private void dfs(BoggleBoard boggleBoard, int i, int j, boolean[][] marked,
                     String pathLetters, Set<String> wordsFound) {
        try {
            marked[i][j] = true;
            char letter = boggleBoard.getLetter(i, j);
            pathLetters = pathLetters + (letter == 'Q' ? "QU" : letter);

            Boolean prefixExists = prefixes.get(pathLetters);
            if (prefixExists == null) {
                Iterable<String> wordsPrefixed = dictionary.keysWithPrefix(pathLetters);
                if (!wordsPrefixed.iterator().hasNext()) {
                    prefixExists = false;
                } else {
                    prefixExists = true;
                }
                prefixes.put(pathLetters, prefixExists);
            }
            
            if(!prefixExists) {
                return;
            }
            
            if (pathLetters.length() > 2 && dictionary.contains(pathLetters)) {
                wordsFound.add(pathLetters);
            }
            
            for(int k = i - 1; k <= i + 1; k++) {
                for(int l = j - 1; l <= j + 1; l++) {
                    if(k < 0 || k >= boggleBoard.rows() || l < 0 || l >= boggleBoard.cols()) {
                        continue;
                    }
                    
                    if(marked[k][l]) {
                        continue;
                    }
                    
                    dfs(boggleBoard, k, l, marked, pathLetters, wordsFound);
                }
            }
        }
        finally {
            marked[i][j] = false;
        }
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if(!dictionary.contains(word)) {
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
