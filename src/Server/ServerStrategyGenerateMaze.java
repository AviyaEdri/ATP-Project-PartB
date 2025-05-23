package Server;

import java.io.InputStream;
import java.io.OutputStream;
import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import java.io.*;
import Server.Configuration;



public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void applyStrategy(InputStream in, OutputStream out) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(in); // Create an ObjectInputStream to read the maze dimensions
            ObjectOutputStream outputStream = new ObjectOutputStream(out); // Create an ObjectOutputStream to write the maze

            int[] size = (int[]) inputStream.readObject(); // Read the maze dimensions from the input stream
            int rows = size[0]; // Get the number of rows
            int cols = size[1]; // Get the number of columns

            String generatorName = Configuration.getInstance().getMazeGeneratingAlgorithm();
            AMazeGenerator generator;

            switch (generatorName) {
                case "MyMazeGenerator":
                    generator = new MyMazeGenerator();
                    break;
                case "SimpleMazeGenerator":
                    generator = new SimpleMazeGenerator();
                    break;
                case "EmptyMazeGenerator":
                    generator = new EmptyMazeGenerator();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown maze generator: " + generatorName);
            }

            Maze maze = generator.generate(rows, cols); // Generate the maze
            byte[] mazeBytes = maze.toByteArray(); // Convert the maze to a byte array
            System.out.println("Original size: " + mazeBytes.length);

            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(); // Create a byte array output stream
            MyCompressorOutputStream compressorOutputStream = new MyCompressorOutputStream(byteStream); // Create a compressor output stream

            compressorOutputStream.write(mazeBytes); // Write the compressed maze to the output stream
            compressorOutputStream.close(); // Close the compressor output stream

            byte[] compressedMaze = byteStream.toByteArray(); // Get the compressed maze as a byte array
            outputStream.writeObject(compressedMaze); // Write the compressed maze to the output stream
            outputStream.flush(); // Flush the output stream
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}