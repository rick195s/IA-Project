package mummymaze;

public abstract class Enemy extends Being {

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


    // antes de se mexer um inimigo vê se ainda está vivo,
    // e se sim, mexe-se, se não, não se mexe e sai da lista de inimigos
    public void move(MummyMazeState state) {
        for (int i = 0; i < possibleMoves; i++) {
            if (!isBeingDead(state)) {
                particularMove(state);
            }else {
                state.enemies.remove(this);
            }
        }
    }


    // cada inimigo mexe-se de forma diferente por isso cada um é que implementa este metodo
    protected abstract void particularMove(MummyMazeState state);
}
