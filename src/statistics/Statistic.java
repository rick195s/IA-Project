package statistics;

import agent.Heuristic;
import mummymaze.MummyMazeAgent;
import mummymaze.MummyMazeProblem;
import mummymaze.MummyMazeState;
import searchmethods.*;

import java.io.File;

public class Statistics {
    MummyMazeAgent agent;
    int limit = 100;
    int beamSize = 100;

    public Statistics(MummyMazeAgent agent, int limitDepthSearch, int beamSize) {
        this.agent = agent;
        this.limit = limitDepthSearch;
        this.beamSize = beamSize;

    }

    public void generateStatistics(){
        File folder = new File("Niveis");
        File[] listOfFiles = folder.listFiles();

        SearchMethod[] searchMethodsArray = agent.getSearchMethodsArray();
        createExcelFile(searchMethodsArray);

        for (File levelFile : listOfFiles) {
            if (levelFile.isFile()) {
                System.out.println("Level: " + levelFile.getName());
                addToExcelFile(""+levelFile.getName());
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
                    finishExcelLine();
                    continue;
                }
                finishExcelLine();
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
        addToExcelFile(""+searchMethod.getStatistics().numExpandedNodes);

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

    private void createExcelFile(SearchMethod[] searchMethods) {
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

        File file = new File(fileName);
        if(!file.exists()){
            utils.FileOperations.appendToTextFile(fileName, excelHeader + "\t"+"\r\n");
        }
    }

    private void addToExcelFile(String value){
        utils.FileOperations.appendToTextFile(fileName, value + "\t");
    }

    private void finishExcelLine(){
        utils.FileOperations.appendToTextFile(fileName, "\r\n");
    }
}
