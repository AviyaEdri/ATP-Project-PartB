package algorithms.MazeGenerators;

import java.util.Random;
/**
 * This class generates a simple maze with the specified number of rows and columns.
 * The maze is represented as a 2D array filled with random values (0 or 1).
 */
public class SimpleMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int rows, int columns){
        Maze maze = new Maze(); // Create a new maze object
        maze.setRows(rows); // Set the number of rows
        maze.setColumns(columns); // Set the number of columns

        int[][] mazeArray = new int[rows][columns]; // Initialize the maze array

        for(int i =0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                mazeArray[i][j] = 1; // Fill the maze with walls (1)
            }
        }

        int k = 0, l =0;
        while (k < rows && l < columns){
            mazeArray[k][l] = 0; // Set the current cell to empty (0)
            if(k < rows -1)
                k++; // Move down
            if(l < columns -1) {
                mazeArray[k][l] = 0; // Set the next cell to empty (0)
                l++; // Move right
            }
        }

        Random random = new Random(); // Create a random number generator
        double chance = 0.8; // Set the chance of making a wall empty

        for (int i = 0; i < rows; i++){
            for(int j =0; j < columns; j++){
                if(mazeArray[i][j] == 1 && random.nextDouble() < chance){
                    mazeArray[i][j] = 0; // Set the cell to empty (0) with a certain chance
                }
            }
        }
        maze.setMaze(mazeArray); // Set the maze array in the maze object
        maze.setStartPosition(new Position(0,0)); // Set the starting position
        maze.setGoalPosition(new Position(rows - 1, columns - 1)); // Set the goal position

        return maze; // Return the generated maze
    }
}
