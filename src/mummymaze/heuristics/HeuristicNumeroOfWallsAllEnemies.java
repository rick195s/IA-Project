package mummymaze.heuristics;

import agent.Heuristic;
import mummymaze.MummyMazeProblem;
import mummymaze.MummyMazeState;

public class HeuristicNumeroOfWallsAllEnemies extends Heuristic<MummyMazeProblem, MummyMazeState>{

    @Override
    public double compute(MummyMazeState state){
        return state.getNumberOfWallAllEnemies();
    }
    
    @Override
    public String toString(){
        return "All Enemy wall";
    }
}