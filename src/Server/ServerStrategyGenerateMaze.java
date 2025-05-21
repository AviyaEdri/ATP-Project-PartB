package Server;

import java.io.InputStream;
import java.io.OutputStream;
import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void applyStrategy(InputStream in, OutputStream out) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(in); // Create an ObjectInputStream to read the maze dimensions
            ObjectOutputStream outputStream = new ObjectOutputStream(out); // Create an ObjectOutputStream to write the maze

            int[] size = (int[]) inputStream.readObject(); // Read the maze dimensions from the input stream
            int rows = size[0]; // Get the number of rows
            int cols = size[1]; // Get the number of columns

            AMazeGenerator mazeGenerator = new MyMazeGenerator(); // Create a maze generator
            Maze maze = mazeGenerator.generate(rows, cols); // Generate the maze
            byte[] mazeBytes = maze.toByteArray(); // Convert the maze to a byte array
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(); // Create a byte array output stream
            MyCompressorOutputStream compressorOutputStream = new MyCompressorOutputStream(outputStream); // Create a compressor output stream

            compressorOutputStream.write(mazeBytes); // Write the compressed maze to the output stream
            compressorOutputStream.flush(); // Flush the output stream to ensure all data is written
            compressorOutputStream.close(); // Close the compressor output stream

            outputStream.writeObject(byteStream.toByteArray()); // Write the maze to the output stream
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}