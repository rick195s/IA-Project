package statistics;


import agent.Heuristic;
import mummymaze.MummyMazeAgent;
import searchmethods.SearchMethod;

public abstract class Statistic {
    String fileName;

    public Statistic(String fileName) {
        this.fileName = fileName;
    }

    public abstract String getStatisticValue(SearchMethod searchMethod, MummyMazeAgent agent);

    public abstract String getStatisticHeader(SearchMethod[] searchMethods, Heuristic[] heuristics);

}
