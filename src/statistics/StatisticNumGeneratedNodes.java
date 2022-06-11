package statistics;


import searchmethods.SearchMethod;

public class StatisticNumGeneratedNodes extends Statistic {


    public StatisticNumGeneratedNodes(String fileName) {
        super(fileName);
    }

    @Override
    public String getStatisticValue(SearchMethod searchMethod) {
        return searchMethod.getStatistics().numGeneratedNodes + "";
    }
}
