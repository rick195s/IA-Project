package mummymaze;

public class Hero extends Being{
    public Hero(int line, int column) {
        super(line, column, StateRepresentation.HERO);
    }

    @Override
    public void move(int number, String direction, MummyMazeState state) {
        super.move(number, direction, state);
        state.changeMatrixCell(this.cellBeing, this.symbol);
        updateGUI(state);
    }

    @Override
    public boolean isBeingDead(MummyMazeState state) {
        for (Enemy enemy : state.enemies) {
            if (this.cellBeing.equals(enemy.cellBeing)) {
                return true;
            }
        }

        if (state.cellTrap != null && state.cellTrap.equals(this.cellBeing)) {
            return true;
        }

        return false;
    }


}
