package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * This class generates a maze using the Prim's algorithm.
 * The maze is represented as a 2D array filled with walls (1) and empty cells (0).
 */
public class MyMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int rows, int columns) {
        int col = columns * 2 + 1;
        int row = rows * 2 + 1;
        int[][] maze = new int[row][col]; // Initialize the maze array

        for (int i = 0; i < row; i++) {
            Arrays.fill(maze[i], 1); // Fill the maze with walls (1)
        }
        Random random = new Random(); // Create a random number generator
        int startRow = random.nextInt(rows) * 2 + 1; // Random starting row
        int startCol = random.nextInt(columns) * 2 + 1; // Random starting column
        maze[startRow][startCol] = 0; // Set the starting cell to empty (0)

        List<int[]> walls = new ArrayList<>(); // List to store walls
        addWalls(maze, startRow, startCol, walls); // Add walls around the starting cell

        while (!walls.isEmpty()) {
            int[] wall = walls.remove(random.nextInt(walls.size())); // Randomly select a wall
            int wallRow = wall[0]; // Wall row
            int wallCol = wall[1]; // Wall column

            int[][] neighbors = getNeighbors(wallRow, wallCol, maze); // Get neighbors of the wall
            if (neighbors == null) continue; // No neighbors, skip

            int[] cell1 = neighbors[0]; // First neighbor
            int[] cell2 = neighbors[1]; // Second neighbor

            if (maze[cell1[0]][cell1[1]] == 0 && maze[cell2[0]][cell2[1]] == 1) {
                maze[cell2[0]][cell2[1]] = 0; // Set the second neighbor to empty (0)
                maze[(wallRow + cell2[0]) / 2][(wallCol + cell2[1]) / 2] = 0; // Set the wall to empty (0)
                addWalls(maze, cell2[0], cell2[1], walls); // Add walls around the new cell
            } else if (maze[cell1[0]][cell1[1]] == 1 && maze[cell2[0]][cell2[1]] == 0) {
                maze[cell1[0]][cell1[1]] = 0; // Set the first neighbor to empty (0)
                maze[(wallRow + cell1[0]) / 2][(wallCol + cell1[1]) / 2] = 0; // Set the wall to empty (0)
                addWalls(maze, cell1[0], cell1[1], walls); // Add walls around the new cell
            }
        }

        // Choose random internal cells for start and goal
        Position innerStart = getRandomOpenCell(maze, random);
        Position innerGoal = getRandomOpenCell(maze, random);
        while (innerGoal.equals(innerStart)) {
            innerGoal = getRandomOpenCell(maze, random);
        }

        // Open entrance/exit on edge near inner cells
        Position start = openEdgeNear(innerStart, maze);
        Position goal = openEdgeNear(innerGoal, maze);

        Maze Mymaze = new Maze();
        Mymaze.setRows(row); // Set the number of rows
        Mymaze.setColumns(col); // Set the number of columns
        Mymaze.setMaze(maze); // Set the maze array
        Mymaze.setStartPosition(start);
        Mymaze.setGoalPosition(goal);

        return Mymaze; // Return the generated maze
    }

    private void addWalls(int[][] maze, int row, int col, List<int[]> walls) {
        int[][] directions = {
                {-2, 0}, {2, 0}, {0, -2}, {0, 2}
        };
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow > 0 && newRow < maze.length && newCol > 0 && newCol < maze[0].length) {
                int wallRow = row + dir[0] / 2;
                int wallCol = col + dir[1] / 2;
                if (maze[newRow][newCol] == 1) {
                    walls.add(new int[]{wallRow, wallCol});
                }
            }
        }
    }
    /**
     * Get the neighbors of a wall cell.
     *
     * @param row the row index of the wall
     * @param col the column index of the wall
     * @param maze the maze array
     * @return an array of neighbor positions
     */
    private int[][] getNeighbors(int row, int col, int[][] maze) {
        if (row % 2 == 1 && col % 2 == 0) {
            return new int[][]{{row, col - 1}, {row, col + 1}};
        } else if (row % 2 == 0 && col % 2 == 1) {
            return new int[][]{{row - 1, col}, {row + 1, col}};
        }
        return null;
    }
    /**
     * Get a random open cell in the maze.
     *
     * @param maze the maze array
     * @param rand the random number generator
     * @return a Position object representing the random open cell
     */
    private Position getRandomOpenCell(int[][] maze, Random rand) {
        int row, col;
        do {
            row = 2 * rand.nextInt((maze.length - 1) / 2) + 1;
            col = 2 * rand.nextInt((maze[0].length - 1) / 2) + 1;
        } while (maze[row][col] != 0);
        return new Position(row, col);
    }
    /**
     * Open an edge near the given cell in the maze.
     *
     * @param cell the cell position
     * @param maze the maze array
     * @return a Position object representing the opened edge
     */
    private Position openEdgeNear(Position cell, int[][] maze) {
        int row = cell.getRowIndex();
        int col = cell.getColumnIndex();

        if (row == 1) {
            maze[0][col] = 0;
            return new Position(0, col);
        } else if (row == maze.length - 2) {
            maze[maze.length - 1][col] = 0;
            return new Position(maze.length - 1, col);
        } else if (col == 1) {
            maze[row][0] = 0;
            return new Position(row, 0);
        } else if (col == maze[0].length - 2) {
            maze[row][maze[0].length - 1] = 0;
            return new Position(row, maze[0].length - 1);
        }

        // fallback
        maze[row][0] = 0;
        return new Position(row, 0);
    }
}
