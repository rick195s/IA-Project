package mummymaze.heuristics;

import agent.Heuristic;
import mummymaze.MummyMazeProblem;
import mummymaze.MummyMazeState;

public class HeuristicDistanceBetweenHeroAndEnemies extends Heuristic<MummyMazeProblem, MummyMazeState>{

    @Override
    public double compute(MummyMazeState state){
        return state.getDistanceBetweenHeroAndEnemies();
    }
    
    @Override
    public String toString(){
        return "Distancia entre heroi e inimigos";
    }
}