package mummymaze.entities.enemies;

import mummymaze.MummyMazeState;
import static mummymaze.StateRepresentation.*;

public class WhiteMummy extends Enemy {
    public WhiteMummy(int line, int column) {
        super(line, column, WHITEMUMMY, 2);
    }

    @Override
    protected void particularMove(MummyMazeState state) {
        if(state.getColumnHero() < cell.getColumn()){
            if (canMoveLeft(state)){
                move(-2, COLUMN, state);
            }else {
                moveInLine(state);
            }
        }else if (state.getColumnHero() > cell.getColumn()){
            if (canMoveRight(state)){
                move(2, COLUMN, state);
            }else {
                moveInLine(state);
            }
        }else {
            moveInLine(state);
        }

    }

    private void moveInLine(MummyMazeState state){
        if(state.getLineHero() < cell.getLine()){
            if (canMoveUp(state)){
                move(-2, LINE, state);
            }
        }else if (state.getLineHero() > cell.getLine()) {
            if (canMoveDown(state)){
                move(2, LINE, state);
            }
        }
    }
}
