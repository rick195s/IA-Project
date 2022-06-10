package mummymaze.heuristics;

import agent.Heuristic;
import mummymaze.MummyMazeProblem;
import mummymaze.MummyMazeState;

public class HeuristicEnemiesWallInDirectionExit extends Heuristic<MummyMazeProblem, MummyMazeState>{

    @Override
    public double compute(MummyMazeState state){
        return state.enemiesWallInDirectionOfExit();
    }
    
    @Override
    public String toString(){
        return "Numero de inimigos com parede na direção da saída";
    }
}