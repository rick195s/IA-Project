package mummymaze;

public class Cell {
    private int column;
    private int line;
    private char symbol;

    public Cell(int line, int column, char symbol) {
        this.column = column;
        this.line = line;
        this.symbol = symbol;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public Cell clone() {
        return new Cell(this.getLine(), this.getColumn(), this.getSymbol());
    }

    public boolean equals(Cell cell) {
        if (cell == null) {
            return false;
        }

        return this.getLine() == cell.getLine() && this.getColumn() == cell.getColumn();

    }
}
