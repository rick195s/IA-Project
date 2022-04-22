package mummymaze;

public class WhiteMummy extends Being implements Enemie{
    public WhiteMummy(int line, int column) {
        super(line, column);
    }

    @Override
    public void move(MummyMazeState state) {
        char[][] matrix = state.getMatrix();

        for (int i = 0; i < 2; i++) {

            if(state.getColumnHero() < column){
                if (canMoveLeft(matrix)){
                    move(-2, "column", state);
                }else {
                    moveInLine(state);
                }
            }else if (state.getColumnHero() > column){
                if (canMoveRight(matrix)){
                    move(2, "column", state);
                }else {
                    moveInLine(state);
                }
            }else {
                moveInLine(state);
            }

            matrix[line][column] = 'M';
        }


    }

    private void moveInLine(MummyMazeState state){
        char[][] matrix = state.getMatrix();

        if(state.getLineHero() < line){
            if (canMoveUp(matrix)){
                move(-2, "line", state);
            }
        }else if (state.getLineHero() > line) {
            if (canMoveDown(matrix)){
                move(2, "line", state);
            }
        }
    }
}
