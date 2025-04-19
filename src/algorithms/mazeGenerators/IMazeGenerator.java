package algorithms.mazeGenerators;

public interface IMazeGenerator {
    /**
     * This function generates a maze with the given dimensions.
     *
     * @param rows    The number of rows in the maze.
     * @param columns The number of columns in the maze.
     * @return A 2D array representing the generated maze.
     */
    public Maze generate(int rows, int columns);

    /**
     * This function measures the time it takes to generate a maze with the given dimensions.
     *
     * @param rows    The number of rows in thet maze.
     * @param columns The number of columns in the maze.
     * @return The time taken to generate the maze in milliseconds.
     */
    long measureAlgorithmTimeMillis(int rows, int columns);


    /**
     *This function ensures that there is at least one legal path in the maze.
     *
     * @param maze    The maze.
     * @return a boolean value indicating whether such a route exists or not.
     */
    boolean possiblePath(Maze maze);
}
