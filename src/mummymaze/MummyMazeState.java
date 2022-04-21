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
    private int lineHero;
    private int columnHero;
    private int lineWhiteMummy;
    private int columnWhiteMummy;
    private int lineRedMummy;
    private int columnRedMummy;
    private int lineTrap;
    private int columnTrap;
    private int lineScorpion;
    private int columnScorpion;
    private int lineKey;
    private int columnKey;
    private int lineDoor;
    private int columnDoor;
    private boolean key = false;

    int columnHeroShouldBe;
    int lineHeroShouldBe;



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

    private void findEnteties(int i, int j) {
        switch (matrix[i][j]) {
            case 'H':
                lineHero = i;
                columnHero = j;
                break;
            case 'S':
                lineExit = i;
                columnExit = j;
                break;
            case 'M':
                lineWhiteMummy = i;
                columnWhiteMummy = j;
                break;
            case 'V':
                lineRedMummy = i;
                columnRedMummy = j;
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


    public boolean canMoveUp() {
        // linha 1 é a linha minima onde o heroi pode estar
        // se na linha acima estiver uma parede '-' o heroi nao pode subir
        // se a linha duas casas acima estiver com um '.' o heroi pode subir
        // se tiver uma chave 'C' o heroi pode subir
        return lineHero != 1 && matrix[lineHero-1][columnHero] != '-' && matrix[lineHero-1][columnHero] != '='
                && (matrix[lineHero-2][columnHero] == '.' || matrix[lineHero-2][columnHero] == 'C');
    }

    public boolean canMoveRight() {
        // se tiver na ultima coluna nao pode mover para a direita
        // se tiver uma parede à direita nao pode mover para a direita
        // se tiver um '.' à direita pode mover para a direita
        // se tiver uma chave 'C' o heroi pode mover para a direita
        return columnHero != matrix.length - 2 && matrix[lineHero][columnHero+1] != '|' && matrix[lineHero][columnHero+1] != '"'
                && (matrix[lineHero][columnHero+2] == '.' || matrix[lineHero][columnHero+2] == 'C');
    }

    public boolean canMoveDown() {
        // se tiver na ultima linha nao pode mover para baixo
        // se tiver uma parede à baixo nao pode mover para baixo
        // se tiver um '.' à baixo pode mover para baixo
        // se tiver uma chave 'C' o heroi pode mover para baixo

        return lineHero != matrix.length - 2 && matrix[lineHero+1][columnHero] != '-' && matrix[lineHero+1][columnHero] != '='
                && (matrix[lineHero+2][columnHero] == '.' || matrix[lineHero+2][columnHero] == 'C');
    }

    public boolean canMoveLeft() {
        // se tiver na primeira coluna nao pode mover para a esquerda
        // se tiver uma parede à esquerda nao pode mover para a esquerda
        // se tiver um '.' à esquerda pode mover para a esquerda
        // se tiver uma chave 'C' o heroi pode mover para a esquerda
        return columnHero != 1 && matrix[lineHero][columnHero-1] != '|' && matrix[lineHero][columnHero-1] != '"'
                && (matrix[lineHero][columnHero-2] == '.' || matrix[lineHero][columnHero-2] == 'C');
    }
    /*
     * In the next four methods we don't verify if the actions are valid.
     * This is done in method executeActions in class EightPuzzleProblem.
     * Doing the verification in these methods would imply that a clone of the
     * state was created whether the operation could be executed or not.
     */

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

    public void move(int number , String direction){

        matrix[lineHero][columnHero] = '.';

        // meter a chave na posicao anterior depois do heroi passar-lhe por cima
        if(lineHero == lineKey && columnHero-1 == columnKey){
            matrix[lineKey][columnKey] = ' ';
            columnKey++;
            matrix[lineHero][columnKey] = 'C';
        }

        if (direction.equals("column")){
            columnHero += number;
        }else if (direction.equals("line")){
            lineHero += number;
        }

        if(lineHero == lineKey && columnHero == columnKey){
            changeDoorState();
        }

        matrix[lineHero][columnHero] = 'H';

    }

    private void changeDoorState() {
        columnKey--;
        matrix[lineKey][columnKey] = 'C';
        key = !key;

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


    public void dontMove() {
        //TODO
        // os inimigos têm de se mexer quando o heroi nao se mexe
        return;
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
        return matrix[lineHeroShouldBe][columnHeroShouldBe] == 'H';
    }

    public int getNumLines() {
        return matrix.length;
    }

    public int getNumColumns() {
        return matrix[0].length;
    }

    public int getTileValue(int line, int column) {
        if (!isValidPosition(line, column)) {
            throw new IndexOutOfBoundsException("Invalid position!");
        }
        return matrix[line][column];
    }

    public boolean isValidPosition(int line, int column) {
        return line >= 0 && line < matrix.length && column >= 0 && column < matrix[0].length;
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
