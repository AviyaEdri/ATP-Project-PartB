package IO;

import algorithms.mazeGenerators.*;
import org.junit.jupiter.api.Test;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class MyDecompressorInputStreamTest {

    @Test
    public void testCompressionAndDecompression() throws IOException {
        String tempFileName = "tempMazeTest.maze";

        AMazeGenerator generator = new MyMazeGenerator();
        Maze maze = generator.generate(50, 50);

        try (OutputStream out = new MyCompressorOutputStream(new FileOutputStream(tempFileName))) {
            out.write(maze.toByteArray());
        }

        byte[] decompressedData = new byte[maze.toByteArray().length];
        try (InputStream in = new MyDecompressorInputStream(new FileInputStream(tempFileName))) {
            in.read(decompressedData);
        }

        Maze loadedMaze = new Maze(decompressedData);
        assertArrayEquals(maze.toByteArray(), loadedMaze.toByteArray(), "Original and loaded mazes should be identical");

        new File(tempFileName).delete();
    }
}
