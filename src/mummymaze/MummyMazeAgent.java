package mummymaze;

import agent.Agent;
import mummymaze.heuristics.*;

import java.io.File;
import java.io.IOException;

public class MummyMazeAgent extends Agent<MummyMazeState>{
    
    protected MummyMazeState initialEnvironment;
    
    public MummyMazeAgent(MummyMazeState environemt) {
        super(environemt);
        initialEnvironment = (MummyMazeState) environemt.clone();
        heuristics.add(new HeuristicTileDistance());
        heuristics.add(new HeuristicNumberOfEnemyPossibleMovesRelative());
        heuristics.add(new HeuristicNumberOfEnemiesPossibleMoves());
        heuristics.add(new HeuristicNumberOfHeroPossibleMoves());
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
        initialEnvironment = new MummyMazeState(MummyMazeState.convertToMatrix(fileString));
        resetEnvironment();
        return environment;
    }


}
