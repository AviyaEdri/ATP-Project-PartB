package algorithms.search;

import algorithms.mazeGenerators.Position;

/**
 * This class represents a state in a maze,
 * holding the row and column of the current position.
 */
public class MazeState extends AState {
    private int row;
    private int col;
    private Position position;

    public MazeState(int row, int col) {
        // The state string represents the position, e.g., "[row,col]"
        super("[" + row + "," + col + "]");
        this.row = row;
        this.col = col;
        this.position = new Position(row,col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "MazeState: (" + row + "," + col + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        MazeState other = (MazeState) obj;
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    public Position getPosition() {
        return this.position;
    }

}
