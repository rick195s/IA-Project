package mummymaze;

public class Being {
    protected int line;
    protected int column;
    private boolean moved;
    private char onTopOf;

    public Being(int line, int column) {
        this.line = line;
        this.column = column;
        moved = false;
        onTopOf = '.';
    }

    public boolean canMoveUp(char[][] matrix) {
        // so pode ir para cima se nao estiver na primeira linha jogavel
        // se na linha acima estiver uma parede '-' o heroi nao pode subir
        // se tiver uma porta em cima nao pode mover para cima
        return  line > 2 && matrix[line-1][column] != '-' && matrix[line-1][column] != '=';
    }
    public boolean canMoveRight(char[][] matrix) {
        // so pode ir para a direita se nao estiver na última coluna jogavel
        // se tiver uma parede à direita nao pode mover para a direita
        // se tiver uma porta à diretia nao pode mover para a direita
        return column < matrix.length - 2 && matrix[line][column+1] != '|' && matrix[line][column+1] != '"';
    }
    public boolean canMoveDown(char[][] matrix) {
        // so pode ir para baixo se nao estiver na última linha jogavel
        // se tiver na ultima linha nao pode mover para baixo
        // se tiver uma porta abaxio nao pode mover para baixo
        return line < matrix.length - 2 && matrix[line+1][column] != '-' && matrix[line+1][column] != '=';
    }

    public boolean canMoveLeft(char[][] matrix) {
        // so pode ir para a esquerda se nao estiver na primeira coluna jogavel
        // se tiver uma parede à esquerda nao pode mover para a esquerda
        // se tiver uma porta à esquerda nao pode mover para a esquerda
        return column > 2 && matrix[line][column-1] != '|' && matrix[line][column-1] != '"';
    }


    public void move(int number, String direction, MummyMazeState state) {
        char[][] matrix = state.getMatrix();

        // se o "ser" quando se mexeu ficou em cima de algum elemento diferente de '.'
        // o elemento é reposto onde estava
        matrix[line][column] = onTopOf != '.' ? onTopOf : '.';

        if (direction.equals("column")){
            column += number;
        }else if (direction.equals("line")){
            line += number;
        }

        // se o "ser" depois de se mexer ficou em cima do heroi ou de uma quadricula normal
        // o elemento não é reposto
        if (matrix[line][column] != '.' && matrix[line][column] != 'H') {
            onTopOf = matrix[line][column];
        }else {
            onTopOf = '.';
        }

        if(onTopOf == 'C'){
            state.changeDoorState();
        }

        moved = true;

    }

    public void updateGUI(MummyMazeState state){
        if (moved) {
            state.firePuzzleChanged(null);
            moved = false;
        }
    }
}
