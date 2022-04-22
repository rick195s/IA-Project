package mummymaze;

public class WhiteMummy extends Being implements Enemie{
    public WhiteMummy(int line, int column) {
        super(line, column);
    }

    @Override
    public void move(char[][] matrix, int lineHero, int columnHero) {
        for (int i = 0; i < 2; i++) {
            matrix[line][column] = '.';

            if(columnHero < column){
                if (canMoveLeft(matrix)){
                    column -=2;
                }else {
                    moveInLine(matrix, lineHero);
                }
            }else if (columnHero > column){
                if (canMoveRight(matrix)){
                    column +=2;
                }else {
                    moveInLine(matrix, lineHero);
                }
            }else {
                moveInLine(matrix, lineHero);
            }

            matrix[line][column] = 'M';
        }


    }

    private void moveInLine(char[][] matrix, int lineHero) {
        if(lineHero < line){
            if (canMoveUp(matrix)){
                line-=2;
            }
        }else if (lineHero > line) {
            if (canMoveDown(matrix)){
                line+=2;
            }
        }
    }
}
