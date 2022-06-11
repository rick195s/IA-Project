package statistics;


import searchmethods.SearchMethod;

public abstract class Statistic {
    String fileName;

    public Statistic(String fileName) {
        this.fileName = fileName;
    }

    public abstract String getStatisticValue(SearchMethod searchMethod);

}
