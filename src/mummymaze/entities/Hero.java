package mummymaze.entities;

import mummymaze.Cell;
import mummymaze.entities.enemies.Enemy;
import mummymaze.MummyMazeState;
import mummymaze.StateRepresentation;

public class Hero extends Entity {
    public Hero(int line, int column) {
        super(line, column, StateRepresentation.HERO);
    }


    @Override
    public boolean isBeingDead(MummyMazeState state) {
        for (Enemy enemy : state.enemies) {
            if (this.cellBeing.equals(enemy.cellBeing)) {
                return true;
            }
        }

        for (Cell trap : state.traps) {
            if (this.cellBeing.equals(trap)) {
                return true;
            }
        }

        return false;
    }


}
