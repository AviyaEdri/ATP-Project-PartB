package algorithms.MazeGenerators;

public class Position {
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

    /**
     * Returns a string representation of the position in the format "{row,column}".
     *
     * @return A string representation of the position.
     */
    @Override
    public String toString() {
        return "{" + row + "," + column + "}"; // Format the position as "{row,column}"
    }
}
