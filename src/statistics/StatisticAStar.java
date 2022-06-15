package statistics;

import agent.Heuristic;
import mummymaze.MummyMazeAgent;
import searchmethods.AStarSearch;
import searchmethods.BreadthFirstSearch;
import searchmethods.SearchMethod;

public class StatisticAStar extends Statistic {

    public StatisticAStar(String fileName) {
        super(fileName);
    }

    @Override
    public String getStatisticValue(SearchMethod searchMethod, MummyMazeAgent agent) {
        if ((searchMethod instanceof AStarSearch)) {
            if (agent.hasSolution()){
                String s = agent.getSolution().getCost() + ":";
                s+=searchMethod.getStatistics().numGeneratedNodes ;
                s+="-admissivel:" + agent.getHeuristic().isAdmissivel();
                s+= '\t';
                return s;

            }else{
                return "\t";
            }
        }

        return "";
    }

    @Override
    public String getStatisticHeader(SearchMethod[] searchMethods, Heuristic[] heuristics) {
        String header = "";
        for (SearchMethod searchMethod : searchMethods) {
            if (searchMethod instanceof AStarSearch) {
                for (Heuristic heuristic : heuristics) {
                    header += "A*, "+ heuristic.toString() +"\t";

                }
            }
        }
        return header;
    }
}
