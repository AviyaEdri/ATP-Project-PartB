package Server;


import java.io.*;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    @Override
    public void serverStrategy(InputStream in, OutputStream out) {
        try{
            ObjectInputStream input = new ObjectInputStream(in);
            ObjectOutputStream output = new ObjectOutputStream(out);

            Maze maze = (Maze) input.readObject(); // Read the maze from the input stream
            String tempDirectory = System.getProperty("java.io.tmpdir"); // Get the temporary directory
            File mazeSol = new File(tempDirectory + "mazeSolution"); // Create a file for the maze solution
            mazeSol.mkdir(); // Create the directory if it doesn't exist
            String mazeHash = Integer.toString(maze.hashCode()); // Get the hash code of the maze
            File mazeFile = new File(mazeSol, mazeHash); // Create a file for the maze
            Solution sol;
            if(mazeFile.exists()){
                // If the maze file exists, read the solution from it
                try (ObjectInputStream mazeFileInput = new ObjectInputStream(new FileInputStream(mazeFile))) {
                    sol = (Solution) mazeFileInput.readObject();
                }
            } else {
                // If the maze file does not exist, solve the maze and save the solution to the file
                ISearchable searchableMaze = new SearchableMaze(maze);
                ISearchingAlgorithm searchAlgorithm = new BestFirstSearch();
                sol = searchAlgorithm.solve(searchableMaze);
                try (ObjectOutputStream mazeFileOutput = new ObjectOutputStream(new FileOutputStream(mazeFile))) {
                    mazeFileOutput.writeObject(sol);
                }
            }
            output.writeObject(sol); // Write the solution to the output stream

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}