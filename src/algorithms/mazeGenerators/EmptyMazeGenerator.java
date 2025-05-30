package algorithms.mazeGenerators;

/**
 * This class generates an empty maze with the specified number of rows and columns.
 * The maze is represented as a 2D array filled with zeros.
 */
public class EmptyMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int rows, int columns) {
        Maze maze =  new Maze(rows,columns); // Create a new maze object with the specified dimensions

        for (int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                maze.getMaze()[i][j] = 0; // Fill the maze with zeros (empty cells)
            }
        }
        return maze; // Return the generated maze
    }

    @Override
    public boolean possiblePath(Maze maze) {
        return true; // empty maze - For sure there is a path
    }
}
