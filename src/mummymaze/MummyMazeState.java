package mummymaze;

import agent.Action;
import agent.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MummyMazeState extends State implements Cloneable {

    public char[][] matrix;
    Cell cellExit;
    Cell cellDoor;
    Cell cellTrap;

    Cell cellHeroShouldBe;
    private boolean key = false;


    Hero hero;
    public LinkedList<Enemy> enemies;

    public MummyMazeState(char[][] matrix) {

        // calcular o estado final para cada nivel
        this.matrix = new char[matrix.length][matrix.length];
        enemies = new LinkedList<>();

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
                cellTrap = new Cell(i,j);
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
        //TODO
        // os inimigos têm de se mexer quando o heroi nao se mexe
        enemiesMove();
    }

    public void moveUp() {
        move(-2, "line");
    }

    public void moveRight() {
        move(2, "column");
    }

    public void moveDown() {
        move(2, "line");
    }

    public void moveLeft() {
        move(-2, "column");
    }

    // funcao usada para mover o heroi
    public void move(int number , String direction){
        hero.move(number, direction, this);
        enemiesMove();
    }

    public void enemiesMove() {
        // os inimigos têm de se mexer quando o heroi se mexe ou opta por ficar na mesma casa
        for (Enemy enemy : enemies) {
            enemy.move(this);
        }
    }
    public boolean isHeroDead() {
        return hero.isBeingDead(this);
    }

    public void changeMatrixCell(Cell cell, char symbol) {
        matrix[cell.getLine()][cell.getColumn()] = symbol;
        fireMatrixChanged(null);
    }

    public void changeDoorState() {
        key = !key;

        // se a chave estiver ativa, as portas sao abertas senao sao fechadas
        if (key){
            if (matrix[cellDoor.getLine()][cellDoor.getColumn()] == StateRepresentation.HORIZONTAL_CLOSE){
                matrix[cellDoor.getLine()][cellDoor.getColumn()] = StateRepresentation.HORIZONTAL_OPEN;
            }
            if (matrix[cellDoor.getLine()][cellDoor.getColumn()] == StateRepresentation.VERTICAL_CLOSE){
                matrix[cellDoor.getLine()][cellDoor.getColumn()] = StateRepresentation.VERTICAL_OPEN;
            }
        }else{
            if (matrix[cellDoor.getLine()][cellDoor.getColumn()] == StateRepresentation.HORIZONTAL_OPEN){
                matrix[cellDoor.getLine()][cellDoor.getColumn()] = StateRepresentation.HORIZONTAL_CLOSE;
            }
            if (matrix[cellDoor.getLine()][cellDoor.getColumn()] == StateRepresentation.VERTICAL_OPEN){
                matrix[cellDoor.getLine()][cellDoor.getColumn()] = StateRepresentation.VERTICAL_CLOSE;
            }
        }
    }


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
        return new MummyMazeState(matrix);
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
