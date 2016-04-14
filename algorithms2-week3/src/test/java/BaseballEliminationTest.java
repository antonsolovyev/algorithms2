import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Created by antonsolovyev on 4/13/16.
 */
public class BaseballEliminationTest {

    /*
    New_York    75 59 28   0 4  10  9 5
    Baltimore   71 63 28   4 0   5 11 8
    Boston      69 66 27  10 5   0  2 10
    Toronto     63 72 27   9 11  2  0 5
    Detroit     49 86 28   5  8 10  5 0
     */
    @Test
    public void testParsing() throws Exception {
        String filename = getPath("teams5a.txt");

        BaseballElimination baseballElimination = new BaseballElimination(filename);

        assertEquals(new HashSet<>(Arrays.asList("Detroit", "Baltimore", "New_York", "Toronto", "Boston")),
                baseballElimination.teams());
        assertEquals(5, baseballElimination.numberOfTeams());

        assertEquals(75, baseballElimination.wins("New_York"));
        assertEquals(59, baseballElimination.losses("New_York"));
        assertEquals(28, baseballElimination.remaining("New_York"));

        assertEquals(71, baseballElimination.wins("Baltimore"));
        assertEquals(63, baseballElimination.losses("Baltimore"));
        assertEquals(28, baseballElimination.remaining("Baltimore"));

        assertEquals(69, baseballElimination.wins("Boston"));
        assertEquals(66, baseballElimination.losses("Boston"));
        assertEquals(27, baseballElimination.remaining("Boston"));

        assertEquals(63, baseballElimination.wins("Toronto"));
        assertEquals(72, baseballElimination.losses("Toronto"));
        assertEquals(27, baseballElimination.remaining("Toronto"));

        assertEquals(49, baseballElimination.wins("Detroit"));
        assertEquals(86, baseballElimination.losses("Detroit"));
        assertEquals(28, baseballElimination.remaining("Detroit"));

        assertEquals(0, baseballElimination.against("New_York", "New_York"));
        assertEquals(0, baseballElimination.against("Baltimore", "Baltimore"));
        assertEquals(0, baseballElimination.against("Boston", "Boston"));
        assertEquals(0, baseballElimination.against("Toronto", "Toronto"));
        assertEquals(0, baseballElimination.against("Detroit", "Detroit"));

        assertEquals(4, baseballElimination.against("New_York", "Baltimore"));
        assertEquals(10, baseballElimination.against("New_York", "Boston"));
        assertEquals(9, baseballElimination.against("New_York", "Toronto"));
        assertEquals(5, baseballElimination.against("New_York", "Detroit"));
        assertEquals(5, baseballElimination.against("New_York", "Detroit"));

        assertEquals(5, baseballElimination.against("Baltimore", "Boston"));
        assertEquals(11, baseballElimination.against("Baltimore", "Toronto"));
        assertEquals(8, baseballElimination.against("Baltimore", "Detroit"));

        assertEquals(2, baseballElimination.against("Boston", "Toronto"));
        assertEquals(10, baseballElimination.against("Boston", "Detroit"));

        assertEquals(5, baseballElimination.against("Toronto", "Detroit"));
    }

//    @Test
//    public void testVertexCalcs() {
//
//        String filename = getPath("teams5a.txt");
//
//        BaseballElimination baseballElimination = new BaseballElimination(filename);
//
//        for(int j = 0; j < baseballElimination.numberOfTeams(); j++) {
//            for(int i = 0; i < baseballElimination.numberOfTeams(); i++) {
//                System.out.print(baseballElimination.getGameVertex(i, j) + " ");
//            }
//            System.out.println();
//         }
//
//        System.out.println("==");
//
//        System.out.println(baseballElimination.getTeam1FromGameVertex(23));
//        System.out.println(baseballElimination.getTeam2FromGameVertex(23));
//
//        System.out.println(baseballElimination.getTeamFromTeamVertex(6));
//    }

    private String getPath(String name) {
        return BaseballEliminationTest.class.getResource(new File("baseball", name).getPath()).getPath();
    }

    @Test
    public void testIsEliminated() {
        String filename = getPath("teams4.txt");
        BaseballElimination baseballElimination = new BaseballElimination(filename);

        /*
        Atlanta is not eliminated
        Philadelphia is eliminated by the subset R = { Atlanta New_York }
        New_York is not eliminated
        Montreal is eliminated by the subset R = { Atlanta }
         */
        assertFalse(baseballElimination.isEliminated("Atlanta"));
        assertFalse(baseballElimination.isEliminated("New_York"));
        assertTrue(baseballElimination.isEliminated("Philadelphia"));
        assertTrue(baseballElimination.isEliminated("Montreal"));
        assertEquals(new HashSet<>(Arrays.asList("Atlanta", "New_York")),
                baseballElimination.certificateOfElimination("Philadelphia"));
        assertEquals(new HashSet<>(Arrays.asList("Atlanta")),
                baseballElimination.certificateOfElimination("Montreal"));

        /*
        New_York is not eliminated
        Baltimore is not eliminated
        Boston is not eliminated
        Toronto is not eliminated
        Detroit is eliminated by the subset R = { New_York Baltimore Boston Toronto }
         */
        filename = getPath("teams5.txt");
        baseballElimination = new BaseballElimination(filename);
        assertFalse(baseballElimination.isEliminated("New_York"));
        assertFalse(baseballElimination.isEliminated("Baltimore"));
        assertFalse(baseballElimination.isEliminated("Boston"));
        assertFalse(baseballElimination.isEliminated("Toronto"));
        assertTrue(baseballElimination.isEliminated("Detroit"));
        assertEquals(new HashSet<>(Arrays.asList("New_York", "Baltimore", "Boston", "Toronto")),
                baseballElimination.certificateOfElimination("Detroit"));

        /*
        x teams4.txt: Philadelphia.
        teams4a.txt: Ghaddafi.
        x teams5.txt: Detroit.
        teams7.txt: Ireland.
        teams24.txt: Team13.
        teams32.txt: Team25, Team29.
        teams36.txt: Team21.
        teams42.txt: Team6, Team15, Team25.
        teams48.txt: Team6, Team23, Team47.
        teams54.txt: Team3, Team29, Team37, Team50.
         */
        filename = getPath("teams4a.txt");
        baseballElimination = new BaseballElimination(filename);
        assertTrue(baseballElimination.isEliminated("Ghaddafi"));

        filename = getPath("teams54.txt");
        baseballElimination = new BaseballElimination(filename);
        assertTrue(baseballElimination.isEliminated("Team3"));
        assertTrue(baseballElimination.isEliminated("Team29"));
        assertTrue(baseballElimination.isEliminated("Team37"));
        assertTrue(baseballElimination.isEliminated("Team50"));
        assertFalse(baseballElimination.isEliminated("Team1"));
        assertFalse(baseballElimination.isEliminated("Team2"));
        assertFalse(baseballElimination.isEliminated("Team13"));
    }
}