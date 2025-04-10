package algorithms.MazeGenerators;

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
        Maze Mymaze = new Maze(); // Create a new maze object
        Mymaze.setRows(row); // Set the number of rows
        Mymaze.setColumns(col); // Set the number of columns
        Mymaze.setMaze(maze); // Set the maze array
        Mymaze.setStartPosition(new Position(startRow, startCol)); // Set the starting position
        Mymaze.setGoalPosition(new Position(row - 1, col - 1)); // Set the goal position
        return Mymaze; // Return the generated maze
    }


    /**
     * This function adds walls around the current cell to the list of walls.
     * @param maze
     * @param row
     * @param col
     * @param walls
     */
    private void addWalls(int[][] maze, int row, int col, List<int[]> walls) {
        int[][] directions = {
            {-2, 0}, // Up
            {2, 0},  // Down
            {0, -2}, // Left
            {0, 2}   // Right
        };

        for(int[] direction : directions){
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if(newRow > 0 && newRow < maze.length &&  newCol > 0 && newCol <maze[0].length){
                int wallRow = row + direction[0] / 2; // Wall row
                int wallCol = col + direction[1] / 2; // Wall column
                if(maze[newRow][newCol] == 1){
                    walls.add(new int[]{wallRow, wallCol}); // Add wall to the list
                }
            }
        }
    }

    /**
     * This function gets the neighbors of the current cell in the maze.
     * @param row
     * @param col
     * @param maze
     * @return
     */
    private int[][] getNeighbors(int row, int col, int[][] maze){
        if(row % 2 == 1 && col % 2 == 0){
            return new int[][]{
                    {row, col - 1}, // Left
                    {row, col + 1}, // Right
            };
        } else if(row % 2 == 0 && col % 2 == 1){
            return new int[][]{
                    {row - 1, col}, // Up
                    {row + 1, col}, // Down
            };
        }
        return null; // No neighbors
    }
}
