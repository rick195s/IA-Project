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

public class MummyMazeState extends State implements Cloneable {

    public char[][] matrix;
    Cell cellExit;
    Cell cellDoor;

    Cell cellHeroShouldBe;
    private boolean key = false;

    Cell cellKey;
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
                enemies.add(new WhiteMummy(enemy.cellBeing.getLine(), enemy.cellBeing.getColumn()));
            }
            if (enemy instanceof RedMummy) {
                enemies.add(new RedMummy(enemy.cellBeing.getLine(), enemy.cellBeing.getColumn()));
            }
            if (enemy instanceof Scorpion) {
                enemies.add(new Scorpion(enemy.cellBeing.getLine(), enemy.cellBeing.getColumn()));
            }
        }

        this.traps = new LinkedList<>(state.traps);
        this.cellExit = state.cellExit.clone();

        if (state.cellDoor != null)
            this.cellDoor = state.cellDoor.clone();

        if (state.cellKey != null){
            this.cellKey = state.cellKey.clone();
            this.key = state.key;
        }

        this.cellHeroShouldBe = state.cellHeroShouldBe.clone();
        this.hero = new Hero(state.hero.cellBeing.getLine(), state.hero.cellBeing.getColumn());
    }

    public MummyMazeState(char[][] matrix) {

        // calcular o estado final para cada nivel
        this.matrix = new char[matrix.length][matrix.length];
        enemies = new LinkedList<>();
        traps = new LinkedList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                findEnteties( i, j);
            }
        }

        cellHeroShouldBe = new Cell(0, 0);
        // if the exit it's in the last line human should be in the line before that
        cellHeroShouldBe.setLine(cellExit.getLine() == matrix.length - 1 ? 11 : 1);
        cellHeroShouldBe.setColumn(cellExit.getColumn());

        if (cellExit.getColumn() == matrix.length - 1){
            cellHeroShouldBe.setColumn(11);
            cellHeroShouldBe.setLine(cellExit.getLine());

        }else if (cellHeroShouldBe.getColumn() == 0){
            cellHeroShouldBe.setColumn(1);
            cellHeroShouldBe.setLine(cellExit.getLine());
        }


    }

    private void findEnteties(int i, int j) {
        switch (matrix[i][j]) {
            case StateRepresentation.HERO:
                hero = new Hero(i, j);
                break;
            case StateRepresentation.EXIT:
                cellExit = new Cell(i, j);
                break;
            case StateRepresentation.WHITEMUMMY:
                enemies.add(new WhiteMummy(i,j));
                break;
            case StateRepresentation.REDMUMMY:
                enemies.add(new RedMummy(i,j));
                break;
            case StateRepresentation.SCORPION:
                enemies.add(new Scorpion(i,j));
                break;
            case StateRepresentation.TRAP:
                traps.add(new Cell(i,j));
                break;
            case StateRepresentation.KEY:
                cellKey = new Cell(i,j);
                break;
            case StateRepresentation.HORIZONTAL_CLOSE:
            case StateRepresentation.HORIZONTAL_OPEN:
            case StateRepresentation.VERTICAL_CLOSE:
            case StateRepresentation.VERTICAL_OPEN:
                cellDoor = new Cell(i, j);
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
        return hero.cellBeing.getLine();
    }

    public int getColumnHero() {
        return hero.cellBeing.getColumn();
    }

    public boolean canMoveUp() {
        return hero.canMoveUp(matrix);
    }
    public boolean canMoveRight() {
        return hero.canMoveRight(matrix);
    }
    public boolean canMoveDown() {
        return hero.canMoveDown(matrix);
    }
    public boolean canMoveLeft() {
        return hero.canMoveLeft(matrix);
    }

    public void dontMove() {
        // os inimigos têm de se mexer quando o heroi nao se mexe
        enemiesMove();
    }

    public void moveUp() {
        move(-2, StateRepresentation.LINE);
    }

    public void moveRight() {
        move(2, StateRepresentation.COLUMN);
    }

    public void moveDown() {
        move(2, StateRepresentation.LINE);
    }

    public void moveLeft() {
        move(-2, StateRepresentation.COLUMN);
    }

    // funcao usada para mover o heroi
    public void move(int number , String direction){
        hero.move(number, direction, this);
        for (Cell trap : traps) {
            changeMatrixCell(trap, StateRepresentation.TRAP, false);
        }
        if(cellKey != null){
            changeMatrixCell(cellKey, StateRepresentation.KEY, false);
        }
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
        if (updateGUI){
            fireMatrixChanged(null);
        }
    }

    public boolean isMatrixCellEquals(int line, int column, char symbol) {
        return matrix[line][column] == symbol;
    }

    public void changeDoorState() {
        key = !key;

        // se a chave estiver ativa, as portas sao abertas senao sao fechadas
        if (key) {
            if (isMatrixCellEquals(cellDoor.getLine(), cellDoor.getColumn(),  StateRepresentation.HORIZONTAL_CLOSE)) {
                changeMatrixCell(cellDoor, StateRepresentation.HORIZONTAL_OPEN, false);
                return;
            }
            if (isMatrixCellEquals(cellDoor.getLine(), cellDoor.getColumn(),  StateRepresentation.VERTICAL_CLOSE)) {
                changeMatrixCell(cellDoor, StateRepresentation.VERTICAL_OPEN, false);
                return;
            }
        }

        if (isMatrixCellEquals(cellDoor.getLine(), cellDoor.getColumn(),  StateRepresentation.HORIZONTAL_OPEN)){
            changeMatrixCell(cellDoor, StateRepresentation.HORIZONTAL_CLOSE, false);
            return;
        }
        if (isMatrixCellEquals(cellDoor.getLine(), cellDoor.getColumn(),  StateRepresentation.VERTICAL_OPEN)){
            changeMatrixCell(cellDoor, StateRepresentation.VERTICAL_CLOSE, false);
            return;
        }
    }


    //region Heuristicas
    public double computeTilesOutOfPlace() {

        //TODO
        double numTilesOutOfPLace=0;

        for (int i = 0; i< this.matrix.length; i++){
            for (int j = 0; j< this.matrix.length; j++){
                if (this.matrix[i][j] != matrix[i][j]){
                    numTilesOutOfPLace++;
                }
            }
        }

        return numTilesOutOfPLace;
    }

    public double getNumberOfWallsNearEnemy(){
        int numWallsNearEnemy = 0;
        for (Enemy enemy : enemies){
            if (enemy.symbol == StateRepresentation.WHITEMUMMY || enemy.symbol == StateRepresentation.SCORPION){
                if (matrix[enemy.cellBeing.getLine()][enemy.cellBeing.getColumn()-1] == StateRepresentation.HORIZONTAL_WALL ||
                        matrix[enemy.cellBeing.getLine()][enemy.cellBeing.getColumn()+1] == StateRepresentation.HORIZONTAL_WALL ) {
                    numWallsNearEnemy++;
                }
            }
            if (enemy.symbol == StateRepresentation.REDMUMMY){
                if (matrix[enemy.cellBeing.getLine()-1][enemy.cellBeing.getColumn()] == StateRepresentation.VERTICAL_WALL ||
                        matrix[enemy.cellBeing.getLine()+1][enemy.cellBeing.getColumn()] == StateRepresentation.VERTICAL_WALL ) {
                    numWallsNearEnemy++;
                }
            }
        }
        return numWallsNearEnemy;
    }

    public double computeTileDistances() {

        //TODO
        int tileDistance = 0;

        for (int i=0;i<this.matrix.length;i++){
            for (int j=0;j<this.matrix.length;j++){

                // nao contabilizar a distancia da peça vazia
                if (matrix[i][j] != 0){
                    //tileDistance += Math.abs(i-linesfinalMatrix[matrix[i][j]]) + Math.abs(j-colsfinalMatrix[matrix[i][j]]);
                }
            }
        }

        // sum of manhattan distance between the piece place and the place it should be
        return tileDistance;
    }

    public double getNumberOfWallAllEnemies(){
        int numWallsNearEnemy = 0;
        for (Enemy enemy : enemies){
            if ((matrix[enemy.cellBeing.getLine()][enemy.cellBeing.getColumn()-1] == StateRepresentation.HORIZONTAL_WALL ||
                matrix[enemy.cellBeing.getLine()][enemy.cellBeing.getColumn()+1] == StateRepresentation.HORIZONTAL_WALL) ||
                (matrix[enemy.cellBeing.getLine()-1][enemy.cellBeing.getColumn()] == StateRepresentation.VERTICAL_WALL ||
                matrix[enemy.cellBeing.getLine()+1][enemy.cellBeing.getColumn()] == StateRepresentation.VERTICAL_WALL )) {

                numWallsNearEnemy++;
            }
        }
        return numWallsNearEnemy;
    }


    public double getNumberOfWallsHero(){
        int numWallsNearHero = 0;
            if ((matrix[hero.cellBeing.getLine()][hero.cellBeing.getColumn()-1] == StateRepresentation.HORIZONTAL_WALL ||
                matrix[hero.cellBeing.getLine()][hero.cellBeing.getColumn()+1] == StateRepresentation.HORIZONTAL_WALL) ||
               (matrix[hero.cellBeing.getLine()-1][hero.cellBeing.getColumn()] == StateRepresentation.VERTICAL_WALL ||
                matrix[hero.cellBeing.getLine()+1][hero.cellBeing.getColumn()] == StateRepresentation.VERTICAL_WALL )) {
                numWallsNearHero++;
            }
        return numWallsNearHero;
    }

    //endregion

    public boolean heroInExit(){
        return cellHeroShouldBe.equals(hero.cellBeing);
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
        String s="";
        for (int k = 0; k < 13; k++) {
            s+=String.valueOf(matrix[k])+"\n";
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

    public static char[][] convertToMatrix(String string){
        int i=0, j=0;

        char[][] matrix = new char[13][13];
        for (char t :  string.toCharArray()){
            if(t!='\n') {
                matrix[i][j] = t;
                j++;
            }else{
                j=0;
                i++;
            }
        }
        return matrix;
    }
}
