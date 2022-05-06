package mummymaze;

public class Hero extends Being{
    public Hero(int line, int column) {
        super(line, column, StateRepresentation.HERO);
    }

    @Override
    public void move(int number, String direction, MummyMazeState state) {
        super.move(number, direction, state);
        state.changeMatrixCell(this.cellBeing, this.symbol);
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
