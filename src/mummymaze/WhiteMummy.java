package mummymaze;

public class WhiteMummy extends Being implements Enemie{
    public WhiteMummy(int line, int column) {
        super(line, column);
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

    @Override
    public void move(MummyMazeState state) {
        char[][] matrix = state.getMatrix();

        for (int i = 0; i < 2; i++) {
            int oldLine = line;
            int oldColumn = column;

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

            if ((oldLine != line || oldColumn != column) && isBeingDead(state) ){
                    state.enemies.remove(this);
                    updateGUI(state);
                    break;
            }

            matrix[line][column] = 'M';
            updateGUI(state);
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
