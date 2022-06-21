package statistics;


import agent.Heuristic;
import mummymaze.MummyMazeAgent;
import searchmethods.InformedSearch;
import searchmethods.SearchMethod;

public class StatisticSolutionCostPerAlgo extends Statistic {


    public StatisticSolutionCostPerAlgo(String fileName) {
        super(fileName);
    }

    @Override
    public String getStatisticValue(SearchMethod searchMethod, MummyMazeAgent agent) {
                return agent.getSolution().getCost() + "\t";

    }

    @Override
    public String getStatisticHeader(SearchMethod[] searchMethods, Heuristic[] heuristics) {
        String header = "";

        for (SearchMethod searchMethod : searchMethods) {
            if ((searchMethod instanceof InformedSearch)) {
                for (Heuristic heuristic : heuristics) {
                    header  += searchMethod.toString() +", " + heuristic.toString() + "\t";
                }
            }else{
                header  += searchMethod.toString() + "\t";

            }
        }

        return header;
    }


}
