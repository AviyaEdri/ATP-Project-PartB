package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState {
    private int row;
    private int col;
    private Position position;

    public MazeState(int row, int col) {
        super("[" + row + "," + col + "]");
        this.row = row;
        this.col = col;
        this.position = new Position(row, col);
    }

    public int getRow() { return row; }

    public int getCol() { return col; }

    public Position getPosition() { return position; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MazeState other = (MazeState) obj;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    @Override
    public String toString() {
        return "MazeState: (" + row + "," + col + ")";
    }
}
