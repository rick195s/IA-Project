package mummymaze;

import agent.Action;
import agent.State;

import java.util.ArrayList;
import java.util.Arrays;

public class MummyMazeState extends State implements Cloneable {
    static final char[][] GOAL_MATRIX = {{0, 1, 2},
                                       {3, 4, 5},
                                       {6, 7, 8}};

    public static final int SIZE = 3;
    private final char[][] matrix;
    private int lineExit;
    private int columnExit;
    private int lineTrap;
    private int columnTrap;
    private int lineScorpion=-1;
    private int columnScorpion=-1;
    public int lineKey;
    public int columnKey;
    private int lineDoor;
    private int columnDoor;
    private boolean key = false;

    int columnHeroShouldBe;
    int lineHeroShouldBe;

    Hero hero;
    WhiteMummy whiteMummy;
    RedMummy redMummy;

    private ArrayList<Enemie> enemies;

    public MummyMazeState(char[][] matrix) {

        // calcular o estado final para cada nivel
        this.matrix = new char[matrix.length][matrix.length];
        enemies = new ArrayList<>();

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

    private void findEnteties(int i, int j) {
        switch (matrix[i][j]) {
            case 'H':
                hero = new Hero(i, j);
                break;
            case 'S':
                lineExit = i;
                columnExit = j;
                break;
            case 'M':
                whiteMummy = new WhiteMummy(i,j);
                enemies.add(whiteMummy);
                break;
            case 'V':
                redMummy = new RedMummy(i,j);
                enemies.add(redMummy);
                break;
            case 'A':
                lineTrap = i;
                columnTrap = j;
                break;
            case 'E':
                lineScorpion = i;
                columnScorpion = j;
                break;
            case 'C':
                lineKey = i;
                columnKey = j;
                break;
            case '=':
            case '_':
            case '"':
            case ')':
                lineDoor = i;
                columnDoor = j;
                break;
        }
    }

    @Override
    public void executeAction(Action action) {
        action.execute(this);
        firePuzzleChanged(null);
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
    }

    public void enemiesMove() {
        // TODO
        // os inimigos têm de se mexer quando o heroi se mexe ou opta por ficar na mesma casa
        for (Enemie enemy : enemies) {
            enemy.move(this);
        }
    }

    // se na posicao do heroi estiver algum inimigo, o heroi morre
    public boolean isHeroDead() {
        switch (matrix[hero.line][hero.column]){
            case 'M':
            case 'V':
            case 'A':
            case 'E':
                return true;
        }
        return false;
    }

    public void changeDoorState() {
        columnKey--;
        matrix[lineKey][columnKey] = 'C';
        key = !key;

        // se a chave estiver ativa, as portas sao abertas senao sao fechadas
        if (key){
            if (matrix[lineDoor][columnDoor] == '=') {
                matrix[lineDoor][columnDoor] = '_';
            }
            if (matrix[lineDoor][columnDoor] == '"') {
                matrix[lineDoor][columnDoor] = ')';
            }
        }else{
            if (matrix[lineDoor][columnDoor] == '_') {
                matrix[lineDoor][columnDoor] = '=';
            }
            if (matrix[lineDoor][columnDoor] == ')') {
                matrix[lineDoor][columnDoor] = '"';
            }
        }
    }


    public double computeTilesOutOfPlace(MummyMazeState finalState) {

        //TODO
        double numTilesOutOfPLace=0;

        for (int i = 0; i< this.matrix.length; i++){
            for (int j = 0; j< this.matrix.length; j++){
                if (this.matrix[i][j] != finalState.matrix[i][j]){
                    numTilesOutOfPLace++;
                }
            }
        }

        return numTilesOutOfPLace;
    }

    public double computeTileDistances(MummyMazeState finalState) {

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
        return matrix[lineHeroShouldBe][columnHeroShouldBe] == 'H' ;
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

    public void firePuzzleChanged(MummyMazeEvent pe) {
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
