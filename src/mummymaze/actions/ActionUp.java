package mummymaze;

import agent.Action;

public class ActionUp extends Action<MummyMazeState>{

    public ActionUp(){
        super(1);
    }

    @Override
    public void execute(MummyMazeState state){
        state.moveUp(); // manda o estado modificar-se movendo a vazia para cima
        state.setAction(this); // define esta ação como sendo a ação que deu origem ao estado
    }

    @Override
    public boolean isValid(MummyMazeState state){
        return state.canMoveUp(); // pergunta ao estado se a peça vazia pode mover para baixo

    }
}