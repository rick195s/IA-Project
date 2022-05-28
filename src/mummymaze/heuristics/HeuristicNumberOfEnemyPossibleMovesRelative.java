package mummymaze.heuristics;

import agent.Heuristic;
import mummymaze.MummyMazeProblem;
import mummymaze.MummyMazeState;

public class HeuristicNumberOfEnemyPossibleMovesRelative extends Heuristic<MummyMazeProblem, MummyMazeState>{

    @Override
    public double compute(MummyMazeState state){
        return state.getNumberOfEnemyPossibleMovesRelative();
    }
    
    @Override
    public String toString(){
        return "Possible Moves Relative To Enemy Typegit ";
    }
}