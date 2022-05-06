package mummymaze;

public class Hero extends Being{
    public Hero(int line, int column) {
        super(line, column, StateRepresentation.HERO);
    }

    @Override
    public void move(int number, String direction, MummyMazeState state) {
        super.move(number, direction, state);
        state.getMatrix()[line][column] = this.symbol;
        updateGUI(state);
    }

    @Override
    public boolean isBeingDead(MummyMazeState state) {
        switch (state.getMatrix()[line][column]){
            case StateRepresentation.WHITEMUMMY:
            case StateRepresentation.REDMUMMY:
            case StateRepresentation.TRAP:
            case StateRepresentation.SCORPION:
                return true;
        }
        return false;
    }


}
