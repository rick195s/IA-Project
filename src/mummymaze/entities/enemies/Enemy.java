package mummymaze.entities.enemies;

import mummymaze.MummyMazeState;
import mummymaze.entities.Entity;
import mummymaze.Cell;

public abstract class Enemy extends Entity {

    // existem inimigos que mexem-se menos vezes que outros (ex: escorpiao)
    private int possibleMoves;
    protected Enemy(int line, int column, char symbol, int possibleMoves) {
        super(line, column, symbol);
        if (possibleMoves <= 0) {
            throw new IllegalArgumentException("Possible moves can't be 0 or negative");
        }

        this.possibleMoves = possibleMoves;
    }

    @Override
    public boolean isBeingDead(MummyMazeState state) {
        for (Enemy enemy : state.enemies) {
            if (this != enemy && this.cellBeing.equals(enemy.cellBeing)) {
                return true;
            }
        }
        return false;
    }


    // antes de um inimigo se mexer ele verifica se ainda está vivo
    // (ve se ainda está na lista de inimigos)
    public void move(MummyMazeState state) {
        for (int i = 0; i < possibleMoves; i++) {
            if (state.enemies.contains(this)) {
                particularMove(state);
            }
        }
    }


    // cada inimigo mexe-se de forma diferente por isso cada um é que implementa este metodo
    protected abstract void particularMove(MummyMazeState state);
}
