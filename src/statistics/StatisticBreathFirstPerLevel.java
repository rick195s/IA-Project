package statistics;

import agent.Heuristic;
import mummymaze.MummyMazeAgent;
import searchmethods.BreadthFirstSearch;
import searchmethods.InformedSearch;
import searchmethods.SearchMethod;

public class StatisticBreathFirstPerLevel extends Statistic {

    public StatisticBreathFirstPerLevel(String fileName) {
        super(fileName);
    }

    @Override
    public String getStatisticValue(SearchMethod searchMethod, MummyMazeAgent agent) {
        if ((searchMethod instanceof BreadthFirstSearch)) {
            if (agent.hasSolution()){
                return agent.getSearchReport() + "\t";

            }else{
                return "\t";
            }
        }

        return "";
    }

    @Override
    public String getStatisticHeader(SearchMethod[] searchMethods, Heuristic[] heuristics) {
            return  "Breadth First Search\t";
    }
}
