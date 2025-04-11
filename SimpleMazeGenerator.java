package algorithms.MazeGenerators;

import java.util.Arrays;
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
            Arrays.fill(mazeArray[i], 1); // Fill the maze with walls (1)
        }

        int x = 0, y =0;
        while (y < columns){
            mazeArray[x][y] = 0; // Set the cell to empty (0)
            y++; // Move to the next column
        }
        y = columns -1; // Reset y to the last column
        x++;

        while (x < rows){
            mazeArray[x][y] = 0; // Set the cell to empty (0)
            x++; // Move to the next row
        }


        Random random = new Random(); // Create a random number generator
        double chance = 0.5; // Set the chance of making a wall empty

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
