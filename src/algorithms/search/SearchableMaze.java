package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchableMaze implements ISearchable {
    private Maze maze;
    private Map<Position, MazeState> allStates = new HashMap<>();


    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    private MazeState getOrCreateState(int row, int col) {
        Position pos = new Position(row, col);
        if (!allStates.containsKey(pos)) {
            allStates.put(pos, new MazeState(row, col));
        }
        return allStates.get(pos);
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
        ArrayList<AState> neighbors = new ArrayList<>(); // Create a list to hold the neighbors
        Position currentPos = new Position(((MazeState) state).getRow(), ((MazeState) state).getCol()); // Get the current position
        int row = currentPos.getRowIndex(); // Get the current position
        int col = currentPos.getColumnIndex(); // Get the current position

        // Check the four possible directions (up, down, left, right)
        addNeighbor(row - 1, col, neighbors); // Up
        addNeighbor(row + 1, col, neighbors); // Down
        addNeighbor(row, col - 1, neighbors); // Left
        addNeighbor(row, col + 1, neighbors); // Right

        // Check the four diagonal directions
        checkDiagonal(row - 1, col - 1, row, col, neighbors); // Up-Left
        checkDiagonal(row - 1, col + 1, row, col, neighbors); // Up-Right
        checkDiagonal(row + 1, col - 1, row, col, neighbors); // Down-Left
        checkDiagonal(row + 1, col + 1, row, col, neighbors); // Down-Right

        return neighbors; // Return the list of neighbors
    }

    private void addNeighbor(int row, int col, ArrayList<AState> neighbors) {
        if (isInBounds(row, col) && maze.getMaze()[row][col] == 0) {
            neighbors.add(getOrCreateState(row, col)); // Add the neighbor state if it's within bounds and not a wall
        }
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < maze.getRows() && col >= 0 && col < maze.getColumns();
    }


    private void checkDiagonal(int row, int col, int fromRow, int fromCol, ArrayList<AState> list) {
        if (!isInBounds(row, col) || !isInBounds(fromRow, col) || !isInBounds(row, fromCol))
            return;

        if (maze.getMaze()[row][col] == 0 &&
                maze.getMaze()[fromRow][col] == 0 &&
                maze.getMaze()[row][fromCol] == 0) {

            int newRow = (row + fromRow) / 2;
            int newCol = (col + fromCol) / 2;

            if (isInBounds(newRow, newCol) && maze.getMaze()[newRow][newCol] == 0) {
                list.add(new MazeState(newRow, newCol));
            }
        }
    }

}
//package algorithms.search;
//
//import algorithms.mazeGenerators.Maze;
//import algorithms.mazeGenerators.Position;
//
//import java.util.*;
//
//public class SearchableMaze implements ISearchable {
//    private Maze maze;
//    private Map<Position, MazeState> allStates = new HashMap<>();
//
//    public SearchableMaze(Maze maze) {
//        this.maze = maze;
//    }
//
//    @Override
//    public AState getStartState() {
//        return getOrCreateState(maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex());
//    }
//
//    @Override
//    public AState getGoalState() {
//        return getOrCreateState(maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex());
//    }
//
//    @Override
//    public List<AState> getAllPossibleStates(AState state) {
//        ArrayList<AState> neighbors = new ArrayList<>();
//        int row = ((MazeState) state).getRow();
//        int col = ((MazeState) state).getCol();
//
//        // Check four directions
//        addNeighbor(row - 1, col, neighbors); // Up
//        addNeighbor(row + 1, col, neighbors); // Down
//        addNeighbor(row, col - 1, neighbors); // Left
//        addNeighbor(row, col + 1, neighbors); // Right
//
//        // Check diagonals
//        checkDiagonal(row - 1, col - 1, row, col, neighbors); // Up-Left
//        checkDiagonal(row - 1, col + 1, row, col, neighbors); // Up-Right
//        checkDiagonal(row + 1, col - 1, row, col, neighbors); // Down-Left
//        checkDiagonal(row + 1, col + 1, row, col, neighbors); // Down-Right
//
//        return neighbors;
//    }
//
//    private void addNeighbor(int row, int col, ArrayList<AState> neighbors) {
//        if (isInBounds(row, col) && maze.getMaze()[row][col] == 0) {
//            neighbors.add(getOrCreateState(row, col));
//        }
//    }
//
//    private boolean isInBounds(int row, int col) {
//        return row >= 0 && row < maze.getRows() && col >= 0 && col < maze.getColumns();
//    }
//
//    private void checkDiagonal(int row, int col, int fromRow, int fromCol, ArrayList<AState> list) {
//        if (!isInBounds(row, col) || !isInBounds(fromRow, col) || !isInBounds(row, fromCol))
//            return;
//
//        if (maze.getMaze()[row][col] == 0 &&
//                maze.getMaze()[fromRow][col] == 0 &&
//                maze.getMaze()[row][fromCol] == 0) {
//
//            int newRow = (row + fromRow) / 2;
//            int newCol = (col + fromCol) / 2;
//
//            if (isInBounds(newRow, newCol) && maze.getMaze()[newRow][newCol] == 0) {
//                list.add(getOrCreateState(newRow, newCol));
//            }
//        }
//    }
//
//    private MazeState getOrCreateState(int row, int col) {
//        Position pos = new Position(row, col);
//        return allStates.computeIfAbsent(pos, p -> new MazeState(row, col));
//    }
//}
