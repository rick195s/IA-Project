package searchmethods;

public class Statistics {
    public int numExpandedNodes;
    public int numGeneratedNodes = 1; //due to the initial node
    public int maxFrontierSize;
    public double duration;

    public double getDuration() {
        return duration;
    }

    public double getDurationInSeconds() {
        return duration / (long) 1000;
    }

    public void reset(){
        numExpandedNodes = 0;
        numGeneratedNodes = 1;
        maxFrontierSize = 0;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
