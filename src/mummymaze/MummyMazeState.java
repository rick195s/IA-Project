package mummymaze;

import agent.Action;
import agent.State;
import mummymaze.entities.Hero;
import mummymaze.entities.enemies.Enemy;
import mummymaze.entities.enemies.RedMummy;
import mummymaze.entities.enemies.Scorpion;
import mummymaze.entities.enemies.WhiteMummy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static mummymaze.StateRepresentation.*;

public class MummyMazeState extends State implements Cloneable {

    public char[][] matrix;
    Cell cellExit;
    public LinkedList<Cell> doors;

    Cell cellHeroShouldBe;

    public Cell cellKey;
    public Hero hero;
    public LinkedList<Enemy> enemies;
    public LinkedList<Cell> traps;

    public MummyMazeState(MummyMazeState state) {
        this.matrix = new char[state.matrix.length][state.matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = state.matrix[i][j];
            }
        }
        enemies = new LinkedList<>();
        for (Enemy enemy : state.enemies) {
            if (enemy instanceof WhiteMummy) {
                enemies.add(new WhiteMummy(enemy.cell.getLine(), enemy.cell.getColumn()));
            }
            if (enemy instanceof RedMummy) {
                enemies.add(new RedMummy(enemy.cell.getLine(), enemy.cell.getColumn()));
            }
            if (enemy instanceof Scorpion) {
                enemies.add(new Scorpion(enemy.cell.getLine(), enemy.cell.getColumn()));
            }
        }

        this.traps = new LinkedList<>(state.traps);
        this.doors = new LinkedList<>(state.doors);
        this.cellExit = state.cellExit.clone();

        if (state.cellKey != null) {
            this.cellKey = state.cellKey.clone();
        }

        this.cellHeroShouldBe = state.cellHeroShouldBe.clone();
        this.hero = new Hero(state.hero.cell.getLine(), state.hero.cell.getColumn());
    }

    public MummyMazeState(char[][] matrix) {

        // calcular o estado final para cada nivel
        this.matrix = new char[matrix.length][matrix.length];
        enemies = new LinkedList<>();
        traps = new LinkedList<>();
        doors = new LinkedList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                findEnteties(i, j);
            }
        }

        cellHeroShouldBe = new Cell(0, 0, HERO);
        // if the exit it's in the last line human should be in the line before that
        cellHeroShouldBe.setLine(cellExit.getLine() == matrix.length - 1 ? 11 : 1);
        cellHeroShouldBe.setColumn(cellExit.getColumn());

        if (cellExit.getColumn() == matrix.length - 1) {
            cellHeroShouldBe.setColumn(11);
            cellHeroShouldBe.setLine(cellExit.getLine());

        } else if (cellHeroShouldBe.getColumn() == 0) {
            cellHeroShouldBe.setColumn(1);
            cellHeroShouldBe.setLine(cellExit.getLine());
        }


    }

    private void findEnteties(int i, int j) {
        switch (matrix[i][j]) {
            case HERO:
                hero = new Hero(i, j);
                break;
            case EXIT:
                cellExit = new Cell(i, j, EXIT);
                break;
            case WHITEMUMMY:
                enemies.add(new WhiteMummy(i, j));
                break;
            case REDMUMMY:
                enemies.add(new RedMummy(i, j));
                break;
            case SCORPION:
                enemies.add(new Scorpion(i, j));
                break;
            case TRAP:
                traps.add(new Cell(i, j, TRAP));
                break;
            case KEY:
                cellKey = new Cell(i, j, KEY);
                break;
            case HORIZONTAL_CLOSE:
                doors.add(new Cell(i, j, HORIZONTAL_CLOSE));
                break;
            case HORIZONTAL_OPEN:
                doors.add(new Cell(i, j, HORIZONTAL_OPEN));
                break;
            case VERTICAL_CLOSE:
                doors.add(new Cell(i, j, VERTICAL_CLOSE));
                break;
            case VERTICAL_OPEN:
                doors.add(new Cell(i, j, VERTICAL_OPEN));
                break;
        }
    }

    @Override
    public void executeAction(Action action) {
        action.execute(this);
    }

    public char[][] getMatrix() {
        return matrix;
    }

    public int getLineHero() {
        return hero.cell.getLine();
    }

    public int getColumnHero() {
        return hero.cell.getColumn();
    }

    public boolean canMoveUp() {return hero.canMoveUp(this);}
    public boolean canMoveRight() {
        return hero.canMoveRight(this);
    }
    public boolean canMoveDown() {return hero.canMoveDown(this);}
    public boolean canMoveLeft() {
        return hero.canMoveLeft(this);
    }

    public void dontMove() {
        // os inimigos têm de se mexer quando o heroi nao se mexe
        enemiesMove();
    }

    public void moveUp() {
        move(-2, LINE);
    }

    public void moveRight() {
        move(2, COLUMN);
    }

    public void moveDown() {
        move(2, LINE);
    }

    public void moveLeft() {
        move(-2, COLUMN);
    }

    // funcao usada para mover o heroi
    public void move(int number, String direction) {
        hero.move(number, direction, this);
        enemiesMove();

    }

    public void enemiesMove() {
        // os inimigos têm de se mexer quando o heroi se mexe ou opta por ficar na mesma casa
        // a usar auxEnemies, porque a lista "enemies" é modificada durante o ciclo
        LinkedList<Enemy> auxEnemies = new LinkedList<>(enemies);
        for (Enemy enemy : auxEnemies) {
            enemy.move(this);
        }
    }

    public boolean isHeroDead() {
        return hero.isBeingDead(this);
    }

    public void changeMatrixCell(Cell cell, char symbol, boolean updateGUI) {
        matrix[cell.getLine()][cell.getColumn()] = symbol;
        if (updateGUI) {
            fireMatrixChanged(null);
        }
    }

    public boolean isMatrixCellEquals(int line, int column, char symbol) {
        return matrix[line][column] == symbol;
    }

    public void changeDoorState() {
        for (Cell door : doors) {
            switch (matrix[door.getLine()][door.getColumn()]) {
                case HORIZONTAL_CLOSE:
                    changeMatrixCell(door, HORIZONTAL_OPEN, false);
                    door.setSymbol(HORIZONTAL_OPEN);
                    break;
                case HORIZONTAL_OPEN:
                    changeMatrixCell(door, HORIZONTAL_CLOSE, false);
                    door.setSymbol(HORIZONTAL_CLOSE);
                    break;
                case VERTICAL_CLOSE:
                    changeMatrixCell(door, VERTICAL_OPEN, false);
                    door.setSymbol(VERTICAL_OPEN);
                    break;
                case VERTICAL_OPEN:
                    changeMatrixCell(door, VERTICAL_CLOSE, false);
                    door.setSymbol(VERTICAL_CLOSE);
                    break;
            }
        }

    }


    //region Heuristicas

    public double getNumberOfEnemyPossibleMovesRelative() {
        int numWallsNearEnemy = 0;
        for (Enemy enemy : enemies) {

            // como a mumia branca e o escorpiao primeiro tentam andar para a mesma coluna do heroi
            // nesta heuristica só nos interessa saber se esses inimigos conseguem andar para a esquerda ou direita
            if (enemy.cell.getSymbol() == WHITEMUMMY || enemy.cell.getSymbol() == SCORPION) {
                numWallsNearEnemy += enemy.canMoveRight(this) ? 1 : 0;
                numWallsNearEnemy += enemy.canMoveLeft(this) ? 1 : 0;
            }

            // como a mumia vermelha primeiro tenta andar para a mesma linha do heroi
            // nesta heuristica só nos interessa saber se o inimigo consegue andar para cima ou baixo
            if (enemy.cell.getSymbol() == REDMUMMY) {                numWallsNearEnemy += enemy.canMoveDown(this) ? 1 : 0;
                numWallsNearEnemy += enemy.canMoveUp(this) ? 1 : 0;
            }
        }
        return numWallsNearEnemy;
    }

    public double computeTileDistances() {
        return Math.abs(cellHeroShouldBe.getLine() - hero.cell.getLine()) + Math.abs(cellHeroShouldBe.getColumn() - hero.cell.getColumn());
    }

    public double getNumberOfEnemiesPossibleMoves() {
        int numEnemyPossibleMoves = 0;
        for (Enemy enemy : enemies) {
            // nesta heuristica um estado é quanto melhor quanto menor for o número de movimentos
            // que um inimigo possa fazer
            numEnemyPossibleMoves += enemy.canMoveUp(this) ? 1 : 0;
            numEnemyPossibleMoves += enemy.canMoveDown(this) ? 1 : 0;
            numEnemyPossibleMoves += enemy.canMoveLeft(this) ? 1 : 0;
            numEnemyPossibleMoves += enemy.canMoveRight(this) ? 1 : 0;

        }
        return numEnemyPossibleMoves;
    }


    public double getNumberHeroPossibleMoves() {
        int numPossibleHeroMoves = 0;
        // nesta heuristica um estado é quanto melhor quanto maior for o número de movimentos
        // possiveis do heroi
        numPossibleHeroMoves += canMoveUp() ? 1 : 0;
        numPossibleHeroMoves += canMoveDown() ? 1 : 0;
        numPossibleHeroMoves += canMoveLeft() ? 1 : 0;
        numPossibleHeroMoves += canMoveRight() ? 1 : 0;

        return -numPossibleHeroMoves;
    }

    public double getNumberOfEnemies(){
        return enemies.size();
    }


    // heuristica que vê se os inimigos têm parede na mesma
    // direção que a saida
    public double enemiesWallInDirectionOfExit(){
        double numEnemiesWithWallInDirectionOfExit = 0;

        for (Enemy enemy : enemies) {
            if (enemy.cell.getLine() == cellExit.getLine() || enemy.cell.getColumn() == cellExit.getColumn()) {
                numEnemiesWithWallInDirectionOfExit++;
            }
        }

        return numEnemiesWithWallInDirectionOfExit;
    }

    // distancia entre o heroi e inimigos
    public double getDistanceBetweenHeroAndEnemies(){
        double distance = 0;
        for (Enemy enemy : enemies) {
            distance += Math.abs(enemy.cell.getLine() - hero.cell.getLine()) + Math.abs(enemy.cell.getColumn() - hero.cell.getColumn());
        }
        return -distance;
    }

    // distancia entre inimigos
    public double getDistanceBetweenEnemies(){
        double distance = 0;

        for (int i = 0; i < enemies.size(); i++) {
            for (int j = i + 1; j < enemies.size(); j++) {
                distance += Math.abs(enemies.get(i).cell.getLine() - enemies.get(j).cell.getLine()) + Math.abs(enemies.get(i).cell.getColumn() - enemies.get(j).cell.getColumn());
            }
        }
        return distance;
    }

    //endregion

    public boolean heroInExit() {
        return cellHeroShouldBe.equals(hero.cell);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MummyMazeState)) {
            return false;
        }

        MummyMazeState o = (MummyMazeState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix);
    }

    @Override
    public int hashCode() {
        return 97 * 7 + Arrays.deepHashCode(this.matrix);
    }

    @Override
    public String toString() {
        String s = "";
        for (int k = 0; k < 13; k++) {
            s += String.valueOf(matrix[k]) + "\n";
        }


        return s;
    }

    @Override
    public Object clone() {
        return new MummyMazeState(this);
    }
    //Listeners

    private transient ArrayList<MummyMazeListener> listeners = new ArrayList<MummyMazeListener>(3);

    public synchronized void removeListener(MummyMazeListener l) {
        if (listeners != null && listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    public synchronized void addListener(MummyMazeListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void fireMatrixChanged(MummyMazeEvent pe) {
        for (MummyMazeListener listener : listeners) {
            listener.puzzleChanged(null);
        }
    }

    // transforma a string dada numa matriz facilitando os calculos das posicoes

    public static char[][] convertToMatrix(String string) {
        int i = 0, j = 0;

        char[][] matrix = new char[13][13];
        for (char t : string.toCharArray()) {
            if (t != '\n') {
                matrix[i][j] = t;
                j++;
            } else {
                j = 0;
                i++;
            }
        }
        return matrix;
    }
}
