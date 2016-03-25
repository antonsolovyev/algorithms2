import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.Arrays;

public class SAP {

    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph digraph) {

        if(digraph == null) {
            throw new NullPointerException();
        }

        this.digraph = new Digraph(digraph);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {

        return length(Arrays.asList(v), Arrays.asList(w));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {

        return ancestor(Arrays.asList(v), Arrays.asList(w));
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

        SearchResult searchResult = search(v, w);
        if(searchResult == null) {
            return -1;
        }

        return searchResult.getLength();
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {

        SearchResult searchResult = search(v, w);
        if(searchResult == null) {
            return -1;
        }

        return searchResult.getAncestor();
    }

    private SearchResult search(Iterable<Integer> v, Iterable<Integer> w) {

        if(v == null || w == null) {
            throw new NullPointerException();
        }
        for(Integer i : v) {
            if(i < 0 || i > digraph.V()) {
                throw new IndexOutOfBoundsException();
            }
        }
        for(Integer i : w) {
            if(i < 0 || i > digraph.V()) {
                throw new IndexOutOfBoundsException();
            }
        }

        BreadthFirstDirectedPaths breadthFirstDirectedPathsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths breadthFirstDirectedPathsW = new BreadthFirstDirectedPaths(digraph, w);

        SearchResult res = null;
        int minLength = Integer.MAX_VALUE;
        for(int u = 0; u < digraph.V(); u++) {
            if(breadthFirstDirectedPathsV.hasPathTo(u) && breadthFirstDirectedPathsW.hasPathTo(u)) {
                int length = breadthFirstDirectedPathsV.distTo(u) + breadthFirstDirectedPathsW.distTo(u);
                if(length < minLength) {
                    minLength = length;
                    res = new SearchResult(minLength, u);
                }
            }
        }

        return res;
    }

    private static final class SearchResult {

        private final int length;
        private final int ancestor;

        public SearchResult(int length, int ancestor) {

            this.length = length;
            this.ancestor = ancestor;
        }

        public int getLength() {
            return  length;
        }

        public int getAncestor() {
            return ancestor;
        }
    }
}