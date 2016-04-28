import edu.princeton.cs.algs4.TST;
import edu.princeton.cs.algs4.TrieSET;

import java.util.Set;
import java.util.TreeSet;

public class BoggleSolver {
    private TrieSET dictionary;
    //private TST<Boolean> dictionary;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        this.dictionary = new TrieSET();
        for(String s : dictionary) {
            this.dictionary.add(s);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        
        boolean[][] marked = new boolean[board.cols()][board.rows()];
        
        Set<String> res = new TreeSet<>();
        
        for(int i = 0; i < board.cols(); i++) {
            for(int j = 0; j < board.rows(); j++) {
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

            Iterable<String> wordsPrefixed = dictionary.keysWithPrefix(pathLetters);
            if(! wordsPrefixed.iterator().hasNext()) {
                return;
            }
            
            for(String w : wordsPrefixed) {
                if(w.length() >2 && w.length() == pathLetters.length()) {
                    wordsFound.add(pathLetters);
                }
            }
            
            for(int k = i - 1; k <= i + 1; k++) {
                for(int l = j - 1; l <= j + 1; l++) {
                    if(k < 0 || k >= boggleBoard.cols() || l < 0 || l >= boggleBoard.rows()) {
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
        
        /*
        0–2	0
        3–4	1
        5	2
        6	3
        7	5
        8+	11
        */
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
