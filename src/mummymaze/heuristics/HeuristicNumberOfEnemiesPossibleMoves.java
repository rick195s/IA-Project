package mummymaze.heuristics;

import agent.Heuristic;
import mummymaze.MummyMazeProblem;
import mummymaze.MummyMazeState;

public class HeuristicNumberOfEnemiesPossibleMoves extends Heuristic<MummyMazeProblem, MummyMazeState>{
    // nesta heuristica um estado é quanto melhor quanto menor for o número de movimentos
    // que um inimigo possa fazer
    @Override
    public double compute(MummyMazeState state){
        return state.getNumberOfEnemiesPossibleMoves();
    }
    
    @Override
    public String toString(){
        return "Enemies Possible Moves";
    }
}