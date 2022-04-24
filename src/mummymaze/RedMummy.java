package mummymaze;

public class RedMummy extends Being implements Enemie{
    public RedMummy(int line, int column) {
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
        int oldLine = line;
        int oldColumn = column;

        for (int i = 0; i < 2; i++) {

            if(state.getLineHero() < line){
                if (canMoveUp(matrix)){
                    move(-2, "line", state);
                }else {
                    moveInColumn(state);
                }
            }else if (state.getLineHero() > line){
                if (canMoveDown(matrix)){
                    move(2, "line", state);
                }else {
                    moveInColumn(state);
                }
            }else {
                moveInColumn(state);
            }


            if ((oldLine != line || oldColumn != column) && isBeingDead(state) ){
                state.enemies.remove(this);
                updateGUI(state);
                break;
            }
            matrix[line][column] = 'V';
            updateGUI(state);

        }


    }

    private void moveInColumn(MummyMazeState state){
        char[][] matrix = state.getMatrix();

        if(state.getColumnHero() < column){
            if (canMoveLeft(matrix)){
                move(-2, "column", state);
            }
        }else if (state.getColumnHero() > column) {
            if (canMoveRight(matrix)){
                move(2, "column", state);
            }
        }
    }
}
