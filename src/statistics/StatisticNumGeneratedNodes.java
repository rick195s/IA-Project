package statistics;


import agent.Heuristic;
import searchmethods.InformedSearch;
import searchmethods.SearchMethod;

public class StatisticNumGeneratedNodes extends Statistic {


    public StatisticNumGeneratedNodes(String fileName) {
        super(fileName);
    }

    @Override
    public String getStatisticValue(SearchMethod searchMethod) {
        if (!(searchMethod instanceof InformedSearch)) {
            return searchMethod.getStatistics().numGeneratedNodes + "\t";
        }

        return "";
    }

    @Override
    public String getStatisticHeader(SearchMethod[] searchMethods, Heuristic[] heuristics) {
        String header = "";

        for (SearchMethod searchMethod : searchMethods) {
            if (searchMethod instanceof InformedSearch) {
                for (Heuristic heuristic : heuristics) {
                }
            } else {
                header  += searchMethod.toString() + "\t";
            }
        }

        return header;
    }


}
