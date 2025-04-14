package algorithms.MazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator{
    /**
     * This function generates a maze with the given dimensions.
     *
     * @param rows    The number of rows in the maze.
     * @param columns The number of columns in the maze.
     * @return A 2D array representing the generated maze.
     */
    public abstract Maze generate(int rows, int columns);

    /**
     * This function measures the time it takes to generate a maze with the given dimensions.
     *
     * @param rows    The number of rows in the maze.
     * @param columns The number of columns in the maze.
     * @return The time taken to generate the maze in milliseconds.
     */
    public long measureAlgorithmTimeMillis(int rows, int columns){
        long startTime = System.currentTimeMillis(); // Start measuring time
        generate(rows, columns); // Call the generate method to create the maze
        long endTime = System.currentTimeMillis(); // End measuring time
        return endTime - startTime; // Calculate the elapsed time
    }
}
