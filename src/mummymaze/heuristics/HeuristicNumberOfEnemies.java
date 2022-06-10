package mummymaze.heuristics;

import agent.Heuristic;
import mummymaze.MummyMazeProblem;
import mummymaze.MummyMazeState;

public class HeuristicNumberOfEnemies extends Heuristic<MummyMazeProblem, MummyMazeState>{
    // nesta heuristica um estado é quanto melhor quanto menor for o número de movimentos
    // que um inimigo possa fazer
    @Override
    public double compute(MummyMazeState state){
        return state.getNumberOfEnemies();
    }
    
    @Override
    public String toString(){
        return "Number of enemies";
    }
}