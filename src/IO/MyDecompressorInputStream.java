package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in; // Initialize the input stream
    }
    public int read() throws IOException {
        return in.read(); // Read a byte from the input stream
    }
    /**
     * This method reads a byte array from the input stream.
     * The first 24 bytes are read as is, and the remaining bits are packed into bytes.
     *
     * @param byteArray byte array to read from the input stream.
     * @throws IOException If an I/O error occurs.
     */

    public int read(byte[] byteArray) throws IOException{
        for (int i = 0; i < 24; i++) {
            byteArray[i] = (byte) in.read(); // Read a byte and store it in the array
        }

        int rows = 0, cols = 0; // Initialize rows and cols to 0
        for (int i = 0; i < 4; i++) {
            rows |= (byteArray[i] & 0xFF) << (i * 8); // Read the number of rows
            cols |= (byteArray[4 + i] & 0xFF) << (i * 8); // Read the number of columns
        }

        int mazeSize = rows * cols; // Calculate the size of the maze
        int mazeStart = 8; // Start index for maze data
        int mazeEnd = mazeStart + mazeSize; // End index for maze data

        int index = mazeStart; // Start index for maze data
        int fullBytes = mazeSize / 8; // Number of full bytes in the maze data
        int remainingBits = mazeSize % 8; // Number of remaining bits in the last byte

        for (int i = 0; i < fullBytes; i++) {
            int b = in.read();
            for (int bit = 0; bit < 8; bit++) {
                byteArray[index++] = (byte) ((b >> (7 - bit)) & 1); // Read the packed byte
            }
        }

        if (remainingBits > 0) {
            int b = in.read();
            for (int bit = 0; bit < remainingBits; bit++) {
                byteArray[index++] = (byte) ((b >> (7 - bit)) & 1); // Read the remaining bits
            }
        }

        for (int i = mazeEnd; i < byteArray.length; i++) {
            int b = in.read();
            if (b == -1) {
                break; // End of stream
            }
            byteArray[i] = (byte) b; // Read the remaining bytes
        }
        return byteArray.length; // Return the number of bytes read
    }
}



