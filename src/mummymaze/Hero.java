package mummymaze;

public class Hero extends Being{
    public Hero(int line, int column) {
        super(line, column);
    }

    @Override
    public void move(int number, String direction, MummyMazeState state) {
        super.move(number, direction, state);
        char[][] matrix = state.getMatrix();
        if (!state.isHeroDead()){
            matrix[line][column] = 'H';
            state.enemiesMove();
        }
    }


}
