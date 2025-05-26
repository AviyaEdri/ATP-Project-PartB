package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.*;

public class SearchableMaze implements ISearchable {
    private Maze maze;
    private Map<Position, MazeState> allStates = new HashMap<>();

    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        return getOrCreateState(maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex());
    }

    @Override
    public AState getGoalState() {
        return getOrCreateState(maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex());
    }

    @Override
    public List<AState> getAllPossibleStates(AState state) {
        ArrayList<AState> neighbors = new ArrayList<>();
        int row = ((MazeState) state).getRow();
        int col = ((MazeState) state).getCol();

        addNeighbor(row - 1, col, neighbors);
        addNeighbor(row + 1, col, neighbors);
        addNeighbor(row, col - 1, neighbors);
        addNeighbor(row, col + 1, neighbors);

        return neighbors;
    }

    private void addNeighbor(int row, int col, ArrayList<AState> neighbors) {
        if (isInBounds(row, col) && maze.getMaze()[row][col] == 0) {
            neighbors.add(getOrCreateState(row, col));
        }
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < maze.getRows() && col >= 0 && col < maze.getColumns();
    }

    private MazeState getOrCreateState(int row, int col) {
        Position pos = new Position(row, col);
        return allStates.computeIfAbsent(pos, p -> new MazeState(row, col));
    }
}
