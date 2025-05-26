package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    public void write(byte[] byteArray) throws IOException {
        // Write the first 8 bytes: rows and columns (4 bytes each)
        for (int i = 0; i < 8; i++) {
            out.write(byteArray[i]);
        }

        // Extract maze size from rows and cols
        int rows = 0, cols = 0;
        for (int i = 0; i < 4; i++) {
            rows |= (byteArray[i] & 0xFF) << (i * 8);
            cols |= (byteArray[4 + i] & 0xFF) << (i * 8);
        }

        // Validate rows and cols
        if (rows <= 0 || cols <= 0 || rows > 10000 || cols > 10000) {
            throw new IllegalArgumentException("Invalid maze dimensions: rows=" + rows + ", cols=" + cols);
        }

        int mazeSize = rows * cols;
        int mazeStartIndex = 8;
        int mazeEndIndex = mazeStartIndex + mazeSize;

        // Compress maze data
        int index = mazeStartIndex;
        while (index < mazeEndIndex) {
            int packed = 0;
            for (int bit = 0; bit < 8 && index < mazeEndIndex; bit++) {
                packed |= (byteArray[index++] & 1) << (7 - bit);
            }
            out.write(packed);
        }

        // Write the last 16 bytes (startRow, startCol, goalRow, goalCol)
        for (int i = mazeEndIndex; i < mazeEndIndex + 16; i++) {
            out.write(byteArray[i]);
        }

        out.flush();
    }
}

