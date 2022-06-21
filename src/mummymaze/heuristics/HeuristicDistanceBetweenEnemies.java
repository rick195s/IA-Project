package mummymaze.heuristics;

import agent.Heuristic;
import mummymaze.MummyMazeProblem;
import mummymaze.MummyMazeState;

public class HeuristicDistanceBetweenEnemies extends Heuristic<MummyMazeProblem, MummyMazeState>{

    @Override
    public double compute(MummyMazeState state){
        return state.getDistanceBetweenEnemies();
    }
    
    @Override
    public String toString(){
        return "Distance Between Enemies";
    }
}