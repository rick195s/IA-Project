package mummymaze.entities.enemies;

import mummymaze.MummyMazeState;

import static mummymaze.StateRepresentation.*;

public class RedMummy extends Enemy {
    public RedMummy(int line, int column) {
        super(line, column, REDMUMMY, 2);
    }


    @Override
    public void particularMove(MummyMazeState state) {

        if(state.getLineHero() < cell.getLine()){
            if (canMoveUp(state)){
                move(-2, LINE, state);
            }else {
                moveInColumn(state);
            }
        }else if (state.getLineHero() > cell.getLine()){
            if (canMoveDown(state)){
                move(2, LINE, state);
            }else {
                moveInColumn(state);
            }
        }else {
            moveInColumn(state);
        }

    }


    private void moveInColumn(MummyMazeState state){
        if(state.getColumnHero() < cell.getColumn()){
            if (canMoveLeft(state)){
                move(-2, COLUMN, state);
            }
        }else if (state.getColumnHero() > cell.getColumn()) {
            if (canMoveRight(state)){
                move(2, COLUMN, state);
            }
        }
    }
}
