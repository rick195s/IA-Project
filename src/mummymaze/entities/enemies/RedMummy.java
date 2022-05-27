package mummymaze.entities.enemies;

import mummymaze.MummyMazeState;
import mummymaze.StateRepresentation;

public class RedMummy extends Enemy {
    public RedMummy(int line, int column) {
        super(line, column, StateRepresentation.REDMUMMY, 2);
    }


    @Override
    public void particularMove(MummyMazeState state) {

        if(state.getLineHero() < cellBeing.getLine()){
            if (canMoveUp(state.getMatrix())){
                move(-2, "line", state);
            }else {
                moveInColumn(state);
            }
        }else if (state.getLineHero() > cellBeing.getLine()){
            if (canMoveDown(state.getMatrix())){
                move(2, "line", state);
            }else {
                moveInColumn(state);
            }
        }else {
            moveInColumn(state);
        }

    }


    private void moveInColumn(MummyMazeState state){
        char[][] matrix = state.getMatrix();

        if(state.getColumnHero() < cellBeing.getColumn()){
            if (canMoveLeft(matrix)){
                move(-2, "column", state);
            }
        }else if (state.getColumnHero() > cellBeing.getColumn()) {
            if (canMoveRight(matrix)){
                move(2, "column", state);
            }
        }
    }
}
