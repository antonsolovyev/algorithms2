import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

import java.io.*;
import java.util.*;

public class BaseballElimination {

    private static final int SOURCE_VERTEX = 0;
    private static final int TARGET_VERTEX = 1;
    private int numberOfTeams;
    private int[][] games;
    private Map<String, Team> teams;

    public BaseballElimination(String filename)  {

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(filename))))) {

            List<String> lines = new ArrayList<>();
            while(true) {
                String s = bufferedReader.readLine();
                if(s == null) {
                    break;
                }
                s = s.trim();
                lines.add(s);
            }

            numberOfTeams = Integer.parseInt(lines.get(0));
            games = new int[numberOfTeams][numberOfTeams];
            teams = new HashMap<>();

            for(int i = 1; i < lines.size(); i++) {
                String[] tokens = lines.get(i).split("\\s+");
                String name = tokens[0];
                int wins = Integer.parseInt(tokens[1]);
                int losses = Integer.parseInt(tokens[2]);
                int remaining = Integer.parseInt(tokens[3]);
                teams.put(name, new Team(i - 1, name, wins, losses, remaining));

                for(int j = 4; j < tokens.length; j++) {
                    games[i - 1][j - 4] = Integer.parseInt(tokens[j]);
                }
            }
        }
        catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }                    // create a baseball division from given filename in format specified below

    public int numberOfTeams() {
        return numberOfTeams;
    }                        // number of teams

    public Iterable<String> teams() {
        return teams.keySet();
    }                                // all teams

    public int wins(String team) {
        if(!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        return teams.get(team).getWins();
    }                      // number of wins for given team

    public int losses(String team) {
        if(!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        return teams.get(team).getLosses();
    }                    // number of losses for given team

    public int remaining(String team) {
        if(!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        return teams.get(team).getRemaning();
    }                 // number of remaining games for given team

    public int against(String team1, String team2) {
        if(!teams.containsKey(team1) || !teams.containsKey(team2)) {
            throw new IllegalArgumentException();
        }

        return games[teams.get(team1).getId()][teams.get(team2).getId()];
    }    // number of remaining games between team1 and team2

    public boolean isEliminated(String team) {
        if(!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        return certificateOfElimination(team) != null;
    }             // is given team eliminated?

    public Iterable<String> certificateOfElimination(String teamName) {

        Team team = teams.get(teamName);
        if(team == null) {
            throw new IllegalArgumentException();
        }

        int vertexCount = 2 + numberOfTeams + numberOfTeams * (numberOfTeams - 1) / 2;

        FlowNetwork flowNetwork = new FlowNetwork(vertexCount);

        int gameVertex = 2 + numberOfTeams;
        for(int j = 0; j < numberOfTeams; j++) {
            for(int i = j + 1; i < numberOfTeams; i++) {

                if (i == team.getId() || j == team.getId()) {
                    continue;
                }

                flowNetwork.addEdge(new FlowEdge(SOURCE_VERTEX, gameVertex, games[i][j]));
                flowNetwork.addEdge(new FlowEdge(gameVertex, getTeamVertex(i), Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(gameVertex, getTeamVertex(j), Double.POSITIVE_INFINITY));
                gameVertex++;
            }
        }

        for(Team t : teams.values()) {

            if(t.getId() == team.getId()) {
                continue;
            }

            int limit = team.getWins() + team.getRemaning() - t.getWins();
            if(limit < 0) {
                return new HashSet<>(Arrays.asList(t.getName()));
            }

            flowNetwork.addEdge(new FlowEdge(getTeamVertex(t.getId()), TARGET_VERTEX, limit >= 0 ? limit : 0));
        }

        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, SOURCE_VERTEX, TARGET_VERTEX);

        Set<String> res = new HashSet<>();
        for(Team t : teams.values()) {

            if(t.getId() == team.getId()) {
                continue;
            }

            if(fordFulkerson.inCut(getTeamVertex(t.getId()))) {
                res.add(t.getName());
            }
        }

        if(!res.isEmpty()) {
            return res;
        }

        return null;
    }  // subset R of teams that eliminates given team; null if not eliminated

    private int getTeamVertex(int id) {
        return 2 + id;
    }

    private static class Team {
        private final int id;
        private final String name;
        private final int wins;
        private final int losses;
        private final int remaning;

        public Team(int id, String name, int wins, int losses, int remaining) {
            this.id = id;
            this.name = name;
            this.wins = wins;
            this.losses = losses;
            this.remaning = remaining;
        }

        public String getName() {

            return name;
        }

        public int getWins() {
            return wins;
        }

        public int getLosses() {
            return losses;
        }

        public int getRemaning() {
            return remaning;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Team{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", wins=" + wins +
                    ", losses=" + losses +
                    ", remaning=" + remaning +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Team team = (Team) o;

            if (id != team.id) return false;
            if (wins != team.wins) return false;
            if (losses != team.losses) return false;
            if (remaning != team.remaning) return false;
            return name != null ? name.equals(team.name) : team.name == null;
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + wins;
            result = 31 * result + losses;
            result = 31 * result + remaning;
            return result;
        }
    }
}
