package mummymaze.entities;

import mummymaze.Cell;
import mummymaze.entities.enemies.Enemy;
import mummymaze.MummyMazeState;
import java.util.LinkedList;

import static mummymaze.StateRepresentation.*;

public abstract class Entity {
    public Cell cell;
    private Cell onTopOf;

    public Entity(int line, int column, char symbol) {
        cell = new Cell(line, column, symbol);
        onTopOf = new Cell(0,0,EMPTY);
    }

    public boolean canMoveUp(MummyMazeState state) {
        // so pode ir para cima se nao estiver na primeira linha jogavel
        // se na linha acima estiver uma parede '-' o heroi nao pode subir
        // se tiver uma porta em cima nao pode mover para cima
        return  cell.getLine() > 2 &&
                !state.isMatrixCellEquals(cell.getLine()-1, cell.getColumn(), HORIZONTAL_WALL) &&
                !state.isMatrixCellEquals(cell.getLine()-1, cell.getColumn(), HORIZONTAL_CLOSE);
    }
    public boolean canMoveRight(MummyMazeState state) {
        // so pode ir para a direita se nao estiver na última coluna jogavel
        // se tiver uma parede à direita nao pode mover para a direita
        // se tiver uma porta à diretia nao pode mover para a direita
        return  cell.getColumn() < state.matrix.length - 2 &&
                !state.isMatrixCellEquals(cell.getLine(), cell.getColumn()+1, VERTICAL_WALL) &&
                !state.isMatrixCellEquals(cell.getLine(), cell.getColumn()+1, VERTICAL_CLOSE);
    }
    public boolean canMoveDown(MummyMazeState state) {
        // so pode ir para baixo se nao estiver na última linha jogavel
        // se tiver na ultima linha nao pode mover para baixo
        // se tiver uma porta abaxio nao pode mover para baixo
        return  cell.getLine() <  state.matrix.length - 2 &&
                !state.isMatrixCellEquals(cell.getLine()+1, cell.getColumn(), HORIZONTAL_WALL) &&
                !state.isMatrixCellEquals(cell.getLine()+1, cell.getColumn(), HORIZONTAL_CLOSE);
    }

    public boolean canMoveLeft(MummyMazeState state) {
        // so pode ir para a esquerda se nao estiver na primeira coluna jogavel
        // se tiver uma parede à esquerda nao pode mover para a esquerda
        // se tiver uma porta à esquerda nao pode mover para a esquerda
        return  cell.getColumn() > 2 &&
                !state.isMatrixCellEquals(cell.getLine(), cell.getColumn()-1, VERTICAL_WALL) &&
                !state.isMatrixCellEquals(cell.getLine(), cell.getColumn()-1, VERTICAL_CLOSE);
    }


    public void move(int number, String direction, MummyMazeState state) {
        char[][] matrix = state.getMatrix();
        Cell oldCell = cell.clone();

        // repor o elemento que estava na posicao onde a entidade está
        state.changeMatrixCell(oldCell, onTopOf.getSymbol(),  false);

        if (direction.equals(COLUMN)){
            cell.setColumn(cell.getColumn() + number);
        }else if (direction.equals(LINE)){
            cell.setLine(cell.getLine() + number);
        }

        // se um "inimigo" depois de se mexer ficar em cima de um outro "inimigo"
        // o "inimigo" que se mexeu mata o outro
        boolean onTopOfEntity = false;
        LinkedList<Enemy> auxEnemies = new LinkedList<> (state.enemies);

        for (Enemy enemy : auxEnemies) {
            if (this != enemy && this != state.hero && cell.equals(enemy.cell)){
               onTopOfEntity = true;
               state.enemies.remove(enemy);
            }
        }

        // guardar elemento que está na posição para onde a entidade vai
        // só guardamos o elemento se esse elemento nao for uma entidade
        if (!onTopOfEntity) {
            onTopOf = new Cell(cell.getLine(), cell.getColumn(), matrix[cell.getLine()][cell.getColumn()]);
            if(onTopOf.equals(state.cellKey)){
                state.changeDoorState();
            }
        }


        if (!oldCell.equals(cell)){

            state.changeMatrixCell(cell, cell.getSymbol(),  true);
        }
    }


    // cada "ser" é que sabe quais sao os "seres" que os podem matar
    public abstract boolean isBeingDead(MummyMazeState state);
}
