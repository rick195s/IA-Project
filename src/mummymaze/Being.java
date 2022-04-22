package mummymaze;

public class Being {
    protected int line;
    protected int column;

    public Being(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
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


    public void move(int number, String direction,MummyMazeState state) {
        char[][] matrix = state.getMatrix();

        matrix[line][column] = '.';
        // Caso a chave tenha sido ativada pelo heroi a chave passa para a quadricula imediatamente à esquerda e o heroi
        // fica na quadricula da chave (isto é feito para a chave nao desaparecer).
        // Depois do heroi sair da quadricula da chave, a chave tem de voltar à sua quadricula inicial
        if(line == state.lineKey && column-1 == state.columnKey){
            matrix[state.lineKey][state.columnKey] = ' ';
            state.columnKey++;
            matrix[state.lineKey][state.columnKey] = 'C';
        }

        if (direction.equals("column")){
            column += number;
        }else if (direction.equals("line")){
            line += number;
        }

        if(line == state.lineKey && column == state.columnKey){
            state.changeDoorState();
        }
    }
}
