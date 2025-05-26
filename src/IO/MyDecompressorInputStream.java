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

    @Override
    public int read(byte[] byteArray) throws IOException {
        // 1. Read the first 8 bytes (rows, cols)
        for (int i = 0; i < 8; i++) {
            byteArray[i] = (byte) in.read();
        }

        int rows = 0, cols = 0;
        for (int i = 0; i < 4; i++) {
            rows |= (byteArray[i] & 0xFF) << (i * 8);
            cols |= (byteArray[4 + i] & 0xFF) << (i * 8);
        }

        int mazeSize = rows * cols;
        int mazeStartIndex = 8;
        int mazeEndIndex = mazeStartIndex + mazeSize;
        int index = mazeStartIndex;

        // 2. Decompress maze data
        while (index < mazeEndIndex) {
            int b = in.read();
            for (int bit = 0; bit < 8 && index < mazeEndIndex; bit++) {
                byteArray[index++] = (byte) ((b >> (7 - bit)) & 1);
            }
        }

        // 3. Read the last 16 bytes (positions)
        for (int i = mazeEndIndex; i < mazeEndIndex + 16; i++) {
            byteArray[i] = (byte) in.read();
        }

        return mazeEndIndex + 16;
    }

}