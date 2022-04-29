package mummymaze;

import agent.Action;
import agent.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MummyMazeState extends State implements Cloneable {

    public static char[][] matrix;
    private int lineExit;
    private int columnExit;
    private int lineDoor;
    private int columnDoor;
    private boolean key = false;

    int columnHeroShouldBe;
    int lineHeroShouldBe;

    Hero hero;
    WhiteMummy whiteMummy;
    RedMummy redMummy;
    Scorpion scoprion;

    public LinkedList<Enemy> enemies = new LinkedList<>();

    public MummyMazeState(char[][] matrix) {

        // calcular o estado final para cada nivel
        this.matrix = new char[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                findEnteties( i, j);
            }
        }

        // if the exit it's in the last line human should be in the line before that
        lineHeroShouldBe = (lineExit == matrix.length - 1 ? 11 : 1);
        columnHeroShouldBe = columnExit;

        if (columnExit == matrix.length - 1){
            columnHeroShouldBe = 11;
            lineHeroShouldBe = lineExit;

        }else if (columnHeroShouldBe == 0){
            columnHeroShouldBe = 1;
            lineHeroShouldBe = lineExit;
        }


    }

    public MummyMazeState(MummyMazeState state) {

        lineDoor = state.lineDoor;
        columnDoor = state.columnDoor;
        lineExit = state.lineExit;
        columnExit = state.columnExit;
        lineHeroShouldBe = state.lineHeroShouldBe;
        columnHeroShouldBe = state.columnHeroShouldBe;
        key = state.key;

        hero = new Hero(state.hero.line, state.hero.column);

        System.out.println("hero: " + hero.line + " " + hero.column);

        for (Enemy enemy : enemies) {
            switch (enemy.symbol) {
                case StateRepresentation.WHITEMUMMY:
                    whiteMummy = new WhiteMummy(enemy.line, enemy.column);
                    enemies.add(whiteMummy);
                    break;
                case StateRepresentation.REDMUMMY:
                    redMummy = new RedMummy(enemy.line, enemy.column);
                    enemies.add(redMummy);
                    break;
                case StateRepresentation.SCORPION:
                    scoprion = new Scorpion(enemy.line, enemy.column);
                    enemies.add(scoprion);
                    break;

            }
        }

    }

    private void findEnteties(int i, int j) {
        switch (matrix[i][j]) {
            case StateRepresentation.HERO:
                hero = new Hero(i, j);
                MummyMazeState.matrix[i][j] = StateRepresentation.EMPTY;
                break;
            case StateRepresentation.EXIT:
                lineExit = i;
                columnExit = j;
                break;
            case StateRepresentation.WHITEMUMMY:
                whiteMummy = new WhiteMummy(i,j);
                enemies.add(whiteMummy);
                MummyMazeState.matrix[i][j] = StateRepresentation.EMPTY;
                break;
            case StateRepresentation.REDMUMMY:
                redMummy = new RedMummy(i,j);
                enemies.add(redMummy);
                MummyMazeState.matrix[i][j] = StateRepresentation.EMPTY;
                break;
            case StateRepresentation.SCORPION:
                scoprion = new Scorpion(i,j);
                enemies.add(scoprion);
                MummyMazeState.matrix[i][j] = StateRepresentation.EMPTY;
                break;
            case StateRepresentation.HORIZONTAL_CLOSE:
            case StateRepresentation.HORIZONTAL_OPEN:
            case StateRepresentation.VERTICAL_CLOSE:
            case StateRepresentation.VERTICAL_OPEN:
                lineDoor = i;
                columnDoor = j;
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
        return hero.line;
    }

    public int getColumnHero() {
        return hero.column;
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
       // enemiesMove();
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
        //enemiesMove();
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


    public void changeDoorState() {
        key = !key;

        // se a chave estiver ativa, as portas sao abertas senao sao fechadas
        if (key){
            if (matrix[lineDoor][columnDoor] == StateRepresentation.HORIZONTAL_CLOSE){
                matrix[lineDoor][columnDoor] = StateRepresentation.HORIZONTAL_OPEN;
            }
            if (matrix[lineDoor][columnDoor] == StateRepresentation.VERTICAL_CLOSE){
                matrix[lineDoor][columnDoor] = StateRepresentation.VERTICAL_OPEN;
            }
        }else{
            if (matrix[lineDoor][columnDoor] == StateRepresentation.HORIZONTAL_OPEN){
                matrix[lineDoor][columnDoor] = StateRepresentation.HORIZONTAL_CLOSE;
            }
            if (matrix[lineDoor][columnDoor] == StateRepresentation.VERTICAL_OPEN){
                matrix[lineDoor][columnDoor] = StateRepresentation.VERTICAL_CLOSE;
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
                if (matrix[enemy.line][enemy.column-1] == StateRepresentation.HORIZONTAL_WALL ||
                    matrix[enemy.line][enemy.column+1] == StateRepresentation.HORIZONTAL_WALL ) {
                    numWallsNearEnemy++;
                }
            }
            if (enemy.symbol == StateRepresentation.REDMUMMY){
                if (matrix[enemy.line-1][enemy.column] == StateRepresentation.VERTICAL_WALL ||
                    matrix[enemy.line+1][enemy.column] == StateRepresentation.VERTICAL_WALL ) {
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
        return MummyMazeState.matrix[lineHeroShouldBe][columnHeroShouldBe] == StateRepresentation.HERO;
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
        return 97 * 7 + Arrays.deepHashCode(matrix);
    }

    @Override
    public String toString() {
        String s="";

        for (Enemy enemy : enemies) {
            matrix[enemy.line][enemy.column] = enemy.symbol;
        }
        matrix[hero.line][hero.column] = StateRepresentation.HERO;
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

        char matrix[][] = new char[13][13];
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
