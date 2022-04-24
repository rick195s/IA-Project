package mummymaze;

public class Hero extends Being{
    public Hero(int line, int column) {
        super(line, column);
    }

    @Override
    public void move(int number, String direction, MummyMazeState state) {
        super.move(number, direction, state);
        state.getMatrix()[line][column] = 'H';
        updateGUI(state);
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
