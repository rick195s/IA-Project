package mummymaze;

public class Hero extends Being{
    public Hero(int line, int column) {
        super(line, column, 'H');
    }

    @Override
    public void move(int number, String direction, MummyMazeState state) {
        super.move(number, direction, state);
        if (!isBeingDead(state)){
            state.getMatrix()[line][column] = this.symbol;
            updateGUI(state);
        }

    }

    @Override
    public boolean isBeingDead(MummyMazeState state) {
        switch (state.getMatrix()[line][column]){
            case 'M':
            case 'V':
            case 'A':
            case 'E':
                return true;
        }
        return false;
    }


}
