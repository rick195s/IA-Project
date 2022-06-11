package statistics;


import agent.Heuristic;
import searchmethods.SearchMethod;

public abstract class Statistic {
    String fileName;

    public Statistic(String fileName) {
        this.fileName = fileName;
    }

    public abstract String getStatisticValue(SearchMethod searchMethod);

    public abstract String getStatisticHeader(SearchMethod[] searchMethods, Heuristic[] heuristics);

}
