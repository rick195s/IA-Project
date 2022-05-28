package mummymaze.heuristics;

import agent.Heuristic;
import mummymaze.MummyMazeProblem;
import mummymaze.MummyMazeState;

public class HeuristicNumberOfHeroPossibleMoves extends Heuristic<MummyMazeProblem, MummyMazeState>{

    @Override
    public double compute(MummyMazeState state){
        return state.getNumberHeroPossibleMoves();
    }
    
    @Override
    public String toString(){
        return "Hero Possible Moves";
    }
}