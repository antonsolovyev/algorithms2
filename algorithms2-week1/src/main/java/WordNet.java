import edu.princeton.cs.algs4.*;

import java.util.*;

public class WordNet {

    private Map<String, List<Integer>> nounToSynsetId;
    private Map<Integer, String[]> synsetIdToWords;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        this.nounToSynsetId = new HashMap<>();
        this.synsetIdToWords = new HashMap<>();

        In inSynsets = new In(synsets);
        while(true) {
            String line = inSynsets.readLine();
            if(line == null) {
                break;
            }

            String[] tokens = line.split(",");
            Integer id = Integer.parseInt(tokens[0]);
            String[] words = tokens[1].split("\\s+");

            for(String word : words) {
                List<Integer> wordList = nounToSynsetId.get(word);
                if(wordList == null) {
                    wordList = new ArrayList<>();
                    nounToSynsetId.put(word, wordList);
                }
                wordList.add(id);
            }

            synsetIdToWords.put(id, words);
        }
        inSynsets.close();

        Digraph digraph = new Digraph(synsetIdToWords.size());
        In inHypernyms = new In(hypernyms);
        while(true) {
            String line = inHypernyms.readLine();
            if(line == null) {
                break;
            }

            String[] tokens = line.split(",");
            Integer id = Integer.parseInt(tokens[0]);
            for(int  i = 1; i < tokens.length; i++) {
                digraph.addEdge(id, Integer.parseInt(tokens[i]));
            }
        }
        inHypernyms.close();

        validateDigraph(digraph);

        sap = new SAP(digraph);
    }

    private void validateDigraph(Digraph digraph) {

        Digraph reversed = digraph.reverse();
        Topological topological = new Topological(reversed);

        if(!topological.hasOrder()) {
            throw new IllegalArgumentException();
        }

        DepthFirstDirectedPaths depthFirstDirectedPaths =
                new DepthFirstDirectedPaths(reversed, topological.order().iterator().next());
        for(int i = 0; i < reversed.V(); i++) {
            if(!depthFirstDirectedPaths.hasPathTo(i)) {
                throw new IllegalArgumentException();
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {

        return nounToSynsetId.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {

        if(word == null) {
            throw new NullPointerException();
        }

        return nounToSynsetId.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {

        if(nounA == null || nounB == null) {
            throw new NullPointerException();
        }
        if(!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        return sap.length(nounToSynsetId.get(nounA), nounToSynsetId.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {

        if(nounA == null || nounB == null) {
            throw new NullPointerException();
        }
        if(!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        int id = sap.ancestor(nounToSynsetId.get(nounA), nounToSynsetId.get(nounB));
        if(id == -1) {
            return null;
        }

        return String.join(" ", synsetIdToWords.get(id));
    }
}
