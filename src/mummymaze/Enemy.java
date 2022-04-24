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
        switch (state.getMatrix()[line][column]){
            case 'V':
            case 'M':
            case 'E':
                return true;
        }
        return false;
    }


    public void move(MummyMazeState state) {

        for (int i = 0; i < possibleMoves; i++) {
            int oldLine = line;
            int oldColumn = column;

            particularMove(state);

            // so se o inimigo se mexer é que verificamos se ele morreu e se sim fazemos as
            // operacoes necessarias
            if ((oldLine != line || oldColumn != column) && isBeingDead(state) ){
                state.enemies.remove(this);
                updateGUI(state);
                break;
            }

            state.getMatrix()[line][column] = this.symbol;
            updateGUI(state);

        }


    }

    // cada inimigo mexe-se de forma diferente por isso cada um é que implementa este metodo
    protected abstract void particularMove(MummyMazeState state);
}
