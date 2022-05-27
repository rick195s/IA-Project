package mummymaze.heuristics;

import agent.Heuristic;
import mummymaze.MummyMazeProblem;
import mummymaze.MummyMazeState;

public class HeuristicNumerOfWallsHero extends Heuristic<MummyMazeProblem, MummyMazeState>{

    @Override
    public double compute(MummyMazeState state){
        return state.getNumberOfWallsHero();
    }
    
    @Override
    public String toString(){
        return "Hero walls";
    }
}