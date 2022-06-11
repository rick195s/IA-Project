package statistics;

import agent.Heuristic;
import mummymaze.MummyMazeAgent;
import mummymaze.MummyMazeProblem;
import mummymaze.MummyMazeState;
import searchmethods.*;

import java.io.File;
import java.util.LinkedList;

public class StatisticsGenerator {
    MummyMazeAgent agent;
    int limit = 100;
    int beamSize = 100;
    String folder = "statistics/";
    LinkedList<Statistic> statisticsList = new LinkedList<>();

    public StatisticsGenerator(MummyMazeAgent agent, int limitDepthSearch, int beamSize) {
        this.agent = agent;
        this.limit = limitDepthSearch;
        this.beamSize = beamSize;

        File folder = new File(this.folder);
        if (!folder.exists()) {
            folder.mkdir();
        }

    }

    public void addStatistics(Statistic statistic) {
        statisticsList.add(statistic);
    }

    public void generateStatistics(){
        File folder = new File("Niveis");
        File[] listOfFiles = folder.listFiles();

        SearchMethod[] searchMethodsArray = agent.getSearchMethodsArray();
        createFile(searchMethodsArray);

        for (File levelFile : listOfFiles) {
            if (levelFile.isFile()) {
                System.out.println("Level: " + levelFile.getName());
                addToFile(""+levelFile.getName());
                try {
                    agent.readInitialStateFromFile(levelFile);
                    for (SearchMethod searchMethod : searchMethodsArray) {

                        if (searchMethod instanceof InformedSearch) {
                            for (Heuristic heuristic : agent.getHeuristicsArray()) {
                                System.out.println("Heuristic: " + heuristic.toString());
                                agent.setHeuristic(heuristic);
                                run(searchMethod);
                            }
                        }else{
                            run(searchMethod);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    finishFileLine();
                    continue;
                }
                finishFileLine();
            }
        }
    }

    private void run(SearchMethod searchMethod) {
        System.out.println("Generating statistics for " + searchMethod.toString() +":");
        prepareAlghoritms(searchMethod);
        agent.setSearchMethod(searchMethod);
        MummyMazeProblem problem = new MummyMazeProblem((MummyMazeState) agent.getEnvironment().clone());
        agent.solveProblem(problem);
        System.out.println(agent.getSearchReport());
        addStatisticValueToFile(searchMethod);

    }

    private void prepareAlghoritms(SearchMethod searchMethod) {
        if (searchMethod instanceof DepthLimitedSearch) {
            ((DepthLimitedSearch) searchMethod).setLimit(limit);
        } else if (searchMethod instanceof BeamSearch) {
            ((BeamSearch) searchMethod).setBeamSize(beamSize);
        } else if (searchMethod instanceof IterativeDeepeningSearch) {
            ((IterativeDeepeningSearch) searchMethod).getStatistics().setDuration(1000);
        }
    }

    private void createFile(SearchMethod[] searchMethods) {
        String excelHeader = "\t";
        for (SearchMethod searchMethod : searchMethods) {
            if (searchMethod instanceof InformedSearch) {
                for (Heuristic heuristic : agent.getHeuristicsArray()) {
                    excelHeader += searchMethod.toString()+", "+ heuristic.toString() + "\t";
                }
            } else {
                excelHeader += searchMethod.toString() + "\t";
            }
        }

        for (Statistic statistic : statisticsList) {
            File file = new File(statistic.fileName);
            if(!file.exists()){

                utils.FileOperations.appendToTextFile(folder + statistic.fileName, excelHeader + "\t"+"\r\n");
            }
        }

    }

    private void addStatisticValueToFile(SearchMethod searchMethod){
        if (agent.hasSolution()){
            for (Statistic statistic : statisticsList) {
                utils.FileOperations.appendToTextFile(folder + statistic.fileName, searchMethod.toString() + statistic.getStatisticValue(searchMethod) + "\t");
            }
        }

    }

    private void addToFile(String string) {
        for (Statistic statistic : statisticsList) {
            utils.FileOperations.appendToTextFile(folder + statistic.fileName, string+ "\t");
        }
    }

    private void finishFileLine(){
        for (Statistic statistic : statisticsList) {
            utils.FileOperations.appendToTextFile(folder + statistic.fileName, "\r\n");
        }
    }
}
