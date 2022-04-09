package mummymaze;

import agent.Action;

public class ActionDontMove extends Action<MummyMazeState>{

    public ActionDontMove(){
        super(1);
    }

    @Override
    public void execute(MummyMazeState state){
        state.dontMove(); // manda o estado modificar-se movendo a vazia para cima
        state.setAction(this); // define esta ação como sendo a ação que deu origem ao estado
    }

    @Override
    public boolean isValid(MummyMazeState state){
        // o humano pode escolher nao se mover em qualquer situacao
        return true;

    }
}