package mummymaze.heuristics;

import agent.Heuristic;
import mummymaze.MummyMazeProblem;
import mummymaze.MummyMazeState;

public class HeuristicNumeroOfWalls extends Heuristic<MummyMazeProblem, MummyMazeState>{

    @Override
    public double compute(MummyMazeState state){
        return state.getNumberOfWallsNearEnemy();
    }
    
    @Override
    public String toString(){
        return "Enemy wall";
    }
}