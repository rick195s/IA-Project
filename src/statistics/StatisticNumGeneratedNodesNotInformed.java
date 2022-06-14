package statistics;


import agent.Heuristic;
import searchmethods.InformedSearch;
import searchmethods.SearchMethod;

public class StatisticNumGeneratedNodesNotInformed extends Statistic {


    public StatisticNumGeneratedNodesNotInformed(String fileName) {
        super(fileName);
    }

    @Override
    public String getStatisticValue(SearchMethod searchMethod, boolean hasSolution) {
        if (!(searchMethod instanceof InformedSearch)) {
            if (hasSolution){
                return searchMethod.getStatistics().numGeneratedNodes + "\t";

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
            if (!(searchMethod instanceof InformedSearch)) {
                header  += searchMethod.toString() + "\t";
            }
        }

        return header;
    }


}
