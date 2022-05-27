package mummymaze.entities;

import mummymaze.Cell;
import mummymaze.entities.enemies.Enemy;
import mummymaze.MummyMazeState;
import mummymaze.StateRepresentation;

import java.util.LinkedList;

public abstract class Entity {
    public Cell cellBeing;
    public char symbol;
    private char onTopOf;

    public Entity(int line, int column, char symbol) {
        cellBeing = new Cell(line, column);
        this.symbol = symbol;
        onTopOf = '.';
    }

    public boolean canMoveUp(char[][] matrix) {
        // so pode ir para cima se nao estiver na primeira linha jogavel
        // se na linha acima estiver uma parede '-' o heroi nao pode subir
        // se tiver uma porta em cima nao pode mover para cima
        return  cellBeing.getLine() > 2 && matrix[cellBeing.getLine()-1][cellBeing.getColumn()] != '-'
                && matrix[cellBeing.getLine()-1][cellBeing.getColumn()] != '=';
    }
    public boolean canMoveRight(char[][] matrix) {
        // so pode ir para a direita se nao estiver na última coluna jogavel
        // se tiver uma parede à direita nao pode mover para a direita
        // se tiver uma porta à diretia nao pode mover para a direita
        return cellBeing.getColumn() < matrix.length - 2 && matrix[cellBeing.getLine()][cellBeing.getColumn()+1] != '|'
                && matrix[cellBeing.getLine()][cellBeing.getColumn()+1] != '"';
    }
    public boolean canMoveDown(char[][] matrix) {
        // so pode ir para baixo se nao estiver na última linha jogavel
        // se tiver na ultima linha nao pode mover para baixo
        // se tiver uma porta abaxio nao pode mover para baixo
        return cellBeing.getLine() < matrix.length - 2 && matrix[cellBeing.getLine()+1][cellBeing.getColumn()] != '-'
                && matrix[cellBeing.getLine()+1][cellBeing.getColumn()] != '=';
    }

    public boolean canMoveLeft(char[][] matrix) {
        // so pode ir para a esquerda se nao estiver na primeira coluna jogavel
        // se tiver uma parede à esquerda nao pode mover para a esquerda
        // se tiver uma porta à esquerda nao pode mover para a esquerda
        return cellBeing.getColumn() > 2 && matrix[cellBeing.getLine()][cellBeing.getColumn()-1] != '|'
                && matrix[cellBeing.getLine()][cellBeing.getColumn()-1] != '"';
    }


    public void move(int number, String direction, MummyMazeState state) {
        char[][] matrix = state.getMatrix();
        Cell oldCell = cellBeing.clone();

        // se o "ser" quando se mexeu ficou em cima de algum elemento diferente de '.'
        // o elemento é reposto onde estava
        matrix[cellBeing.getLine()][cellBeing.getColumn()] = onTopOf;

        if (direction.equals(StateRepresentation.COLUMN)){
            cellBeing.setColumn(cellBeing.getColumn() + number);
        }else if (direction.equals(StateRepresentation.LINE)){
            cellBeing.setLine(cellBeing.getLine() + number);
        }

        // se um "inimigo" depois de se mexer ficar em cima de um outro "inimigo"
        // o "inimigo" que se mexeu mata o outro
        boolean onTopOfBeing = false;
        LinkedList<Enemy> auxEnemies = new LinkedList<> (state.enemies);

        for (Enemy enemy : auxEnemies) {
            if (this != enemy && this != state.hero && cellBeing.equals(enemy.cellBeing)){
               onTopOfBeing = true;
               state.enemies.remove(enemy);
            }
        }

        if (!onTopOfBeing) {
            onTopOf = matrix[cellBeing.getLine()][cellBeing.getColumn()];
        }

        if(onTopOf == StateRepresentation.KEY){
            state.changeDoorState();
        }

        if (!oldCell.equals(cellBeing)){
            state.changeMatrixCell(this.cellBeing, this.symbol, true);
        }
    }


    // cada "ser" é que sabe quais sao os "seres" que os podem matar
    public abstract boolean isBeingDead(MummyMazeState state);
}
