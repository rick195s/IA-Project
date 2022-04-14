package mummymaze;

import agent.Agent;

import java.io.File;
import java.io.IOException;

public class MummyMazeAgent extends Agent<MummyMazeState>{
    
    protected MummyMazeState initialEnvironment;
    
    public MummyMazeAgent(MummyMazeState environemt) {
        super(environemt);
        initialEnvironment = (MummyMazeState) environemt.clone();
        heuristics.add(new HeuristicTileDistance());
        heuristics.add(new HeuristicTilesOutOfPlace());
        heuristic = heuristics.get(0);
    }
            
    public MummyMazeState resetEnvironment(){
        environment = (MummyMazeState) initialEnvironment.clone();
        return environment;
    }
                 
    public MummyMazeState readInitialStateFromFile(File file) throws IOException {
        java.util.Scanner scanner = new java.util.Scanner(file);
        //read all from file to string, and use mamymazestate.convertToMatrix to convert to matrix
        String fileString = scanner.useDelimiter("\\Z").next();
        scanner.close();
/*
        int[][] matrix = new int [3][3];
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = scanner.nextInt();
            }
            scanner.nextLine();
        }*/
        initialEnvironment = new MummyMazeState(MummyMazeState.convertToMatrix(fileString));
        resetEnvironment();
        return environment;
    }


}
