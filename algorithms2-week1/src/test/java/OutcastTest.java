import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/*
% more outcast5.txt
horse zebra cat bear table

% more outcast8.txt
water soda bed orange_juice milk apple_juice tea coffee

% more outcast11.txt
apple pear peach banana lime lemon blueberry strawberry mango watermelon potato


% java Outcast synsets.txt hypernyms.txt outcast5.txt outcast8.txt outcast11.txt
outcast5.txt: table
outcast8.txt: bed
outcast11.txt: potato
 */
public class OutcastTest {

    @Test
    public void test() throws Exception {

        String synsets = getFullFilename("synsets.txt");
        String hypernyms = getFullFilename("hypernyms.txt");
        WordNet wordNet = new WordNet(synsets, hypernyms);

        Outcast outcast = new Outcast(wordNet);

        assertEquals("table", outcast.outcast(new String[] {"horse", "zebra", "cat", "bear", "table"}));
        assertEquals("potato", outcast.outcast(new String[] {"apple", "pear", "peach", "banana", "lime",
                "lemon", "blueberry", "strawberry", "mango", "watermelon", "potato"}));
        assertEquals("bed", outcast.outcast(new String[] {"water", "soda", "bed", "orange_juice", "milk",
                "apple_juice", "tea", "coffee"}));
    }

    private String getFullFilename(String shortName) {

        return OutcastTest.class.getResource(new File("wordnet", shortName).getPath()).getPath();
    }
}