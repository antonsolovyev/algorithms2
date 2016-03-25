public class Outcast {

    private WordNet wordNet;

    public Outcast(WordNet wordnet) {

        this.wordNet = wordnet;
    }         // constructor takes a WordNet object

    public String outcast(String[] nouns) {

        if(nouns == null) {
            throw new NullPointerException();
        }
        if(nouns.length < 1) {
            throw new IllegalArgumentException();
        }

        String outcast = null;
        int maxDistance = 0;
        for(String noun : nouns) {

            int distance = 0;
            for(String other : nouns) {
                distance += wordNet.distance(noun, other);
            }

            if(distance > maxDistance) {
                maxDistance = distance;
                outcast = noun;
            }
        }

        return outcast;
    }   // given an array of WordNet nouns, return an outcast
}
