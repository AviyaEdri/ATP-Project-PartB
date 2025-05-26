package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.Objects;

public class Position implements Serializable {
    private int row; // Row index of the position
    private int column; // Column index of the position

    /**
     * Constructor for the Position class.
     *
     * @param row    The row index of the position.
     * @param column The column index of the position.
     */
    public Position(int row, int column) {
        this.row = row; // Set the row index
        this.column = column; // Set the column index
    }

    public int getRowIndex(){
        return row; // Return the row index
    }
    public int getColumnIndex(){
        return column; // Return the column index
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position that = (Position) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "{" + row + "," + column + "}";
    }

}
