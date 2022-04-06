package mummymaze;

import agent.Action;
import agent.State;

import java.util.ArrayList;
import java.util.Arrays;

public class MummyMazeState extends State implements Cloneable {

    static final char[][] GOAL_MATRIX = {{0, 1, 2},
                                       {3, 4, 5},
                                       {6, 7, 8}};
    final int[] linesfinalMatrix = {0, 0, 0, 1, 1, 1, 2, 2, 2};
    final int[] colsfinalMatrix = {0, 1, 2, 0, 1, 2, 0, 1, 2};
    public static final int SIZE = 3;
    private final char[][] matrix;
    private int lineExit;
    private int columnExit;
    private int lineHuman;
    private int columnHuman;


    public MummyMazeState(char[][] matrix) {
        // calcular o estado final para cada nivel

        this.matrix = new char[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                if (this.matrix[i][j] == 'H') {
                    lineHuman = i;
                    columnHuman = j;
                }
                if (this.matrix[i][j] == 'S') {
                    lineExit = i;
                    columnExit = j;
                }
            }
        }

          }

    @Override
    public void executeAction(Action action) {
        action.execute(this);
        firePuzzleChanged(null);
    }

    public boolean canMoveUp() {
        return lineExit != 0;
    }

    public boolean canMoveRight() {
        return columnExit != matrix.length - 1;
    }

    public boolean canMoveDown() {
        return lineExit != matrix.length - 1;
    }

    public boolean canMoveLeft() {
        return columnExit != 0;
    }

    /*
     * In the next four methods we don't verify if the actions are valid.
     * This is done in method executeActions in class EightPuzzleProblem.
     * Doing the verification in these methods would imply that a clone of the
     * state was created whether the operation could be executed or not.
     */
    public void moveUp() {
        matrix[lineExit][columnExit] = matrix[--lineExit][columnExit];
        matrix[lineExit][columnExit] = 0;
    }

    public void moveRight() {
        matrix[lineExit][columnExit] = matrix[lineExit][++columnExit];
        matrix[lineExit][columnExit] = 0;
    }

    public void moveDown() {
        matrix[lineExit][columnExit] = matrix[++lineExit][columnExit];
        matrix[lineExit][columnExit] = 0;
    }

    public void moveLeft() {
        matrix[lineExit][columnExit] = matrix[lineExit][--columnExit];
        matrix[lineExit][columnExit] = 0;
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
                    tileDistance += Math.abs(i-linesfinalMatrix[matrix[i][j]]) + Math.abs(j-colsfinalMatrix[matrix[i][j]]);
                }
            }
        }

        // sum of manhattan distance between the piece place and the place it should be
        return tileDistance;
    }

    public boolean humanInExit(){
        int columnHumanShouldBe=0;
        int lineHumanShouldBe=0;

        if (lineExit == matrix.length - 1){
            lineHumanShouldBe = 11;

        }else if (lineExit == 0){
            lineHumanShouldBe = 1;
        }

        columnHumanShouldBe = columnExit;

        if (columnExit == matrix.length - 1){
            columnHumanShouldBe = 11;
            lineHumanShouldBe = lineExit;

        }else if (columnHumanShouldBe == 0){
            columnHumanShouldBe = 1;
            lineHumanShouldBe = lineExit;
        }


        System.out.println("Linha da Saida: "+lineExit+"\nColuna da Saida: "+columnExit);
        System.out.println("Linha onde humano devida de estar: "+lineHumanShouldBe+"\nColuna onde humano devida de estar: "+columnHumanShouldBe);
        System.out.println("Num linhas da matriz: "+matrix[0].length+"\nNum colunas da matriz: "+matrix.length);

        return matrix[lineHumanShouldBe][columnHumanShouldBe] == 'H';
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
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            buffer.append('\n');
            for (int j = 0; j < matrix.length; j++) {
                buffer.append(matrix[i][j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
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

}
