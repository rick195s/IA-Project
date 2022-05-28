package mummymaze.entities;

import mummymaze.Cell;
import mummymaze.entities.enemies.Enemy;
import mummymaze.MummyMazeState;
import mummymaze.StateRepresentation;

import static mummymaze.StateRepresentation.HERO;

public class Hero extends Entity {
    public Hero(int line, int column) {
        super(line, column, HERO);
    }


    @Override
    public boolean isBeingDead(MummyMazeState state) {
        for (Enemy enemy : state.enemies) {
            if (this.cell.equals(enemy.cell)) {
                return true;
            }
        }

        for (Cell trap : state.traps) {
            if (this.cell.equals(trap)) {
                return true;
            }
        }

        return false;
    }


}
