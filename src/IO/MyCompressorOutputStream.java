//package IO;
//
//import java.io.IOException;
//import java.io.OutputStream;
//
//public class MyCompressorOutputStream extends OutputStream {
//    private OutputStream out;
//
//    public MyCompressorOutputStream(OutputStream out) {
//        this.out = out;
//    }
//
//    @Override
//    public void write(int b) throws IOException {
//        out.write(b);
//    }
//    /**
//     * This method writes a byte array to the output stream.
//     * The first 24 bytes are written as is, and the remaining bits are packed into bytes.
//     *
//     * @param byteArray The byte array to write to the output stream.
//     * @throws IOException If an I/O error occurs.
//     */
//    public void write(byte[] byteArray) throws IOException {
//        // rows and cols (4 bytes each)
//        for (int i = 0; i < 8; i++) {
//            out.write(byteArray[i]);
//        }
//
//        // calculate size of maze (rows * columns)
//        int rows = 0;
//        int cols = 0;
//        for (int i = 0; i < 4; i++) {
//            rows |= (byteArray[i] & 0xFF) << (i * 8);
//            cols |= (byteArray[4 + i] & 0xFF) << (i * 8);
//        }
//
//        int mazeSize = rows * cols; // Calculate the size of the maze
//        int mazeStart = 8; // Start index for maze data
//        //int mazeEnd = mazeStart + mazeSize; // End index for maze data
//
//        int fullBytes = mazeSize / 8; // Number of full bytes in the maze data
//        int remainingBits = mazeSize % 8; // Number of remaining bits in the last byte
//
//        for (int i = 0; i < fullBytes; i++) {
//            byte b = 0;
//            for (int bit = 0; bit < 8; bit++) {
//                b |= (byteArray[mazeStart + i * 8 + bit] & 1) << (7 - bit);
//            }
//            out.write(b); // Write the packed byte
//        }
//
//        if (remainingBits > 0) {
//            byte b = 0;
//            for (int bit = 0; bit < remainingBits; bit++) {
//                b |= (byteArray[mazeStart + fullBytes * 8 + bit] & 1) << (7 - bit);
//            }
//            out.write(b); // Write the last byte with remaining bits
//        }
//
//        for (int i = mazeStart + mazeSize; i < byteArray.length; i++) {
//            out.write(byteArray[i]); // Write the start and goal positions
//        }
//        out.flush(); // Ensure all data is written to the output stream
//    }
//}
//
//
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
        // Write the first 8 bytes (rows and columns) as is
        for (int i = 0; i < 8; i++) {
            out.write(byteArray[i]);
        }

        int rows = 0, cols = 0;
        for (int i = 0; i < 4; i++) {
            rows |= (byteArray[i] & 0xFF) << (i * 8);
            cols |= (byteArray[4 + i] & 0xFF) << (i * 8);
        }

        int mazeSize = rows * cols;
        int mazeStartIndex = 8;
        int index = mazeStartIndex;

        // Compress the maze bits (1 bit per cell)
        while (index < mazeStartIndex + mazeSize) {
            int packed = 0;
            for (int bit = 0; bit < 8 && index < mazeStartIndex + mazeSize; bit++) {
                packed |= (byteArray[index++] & 1) << (7 - bit);
            }
            out.write(packed);
        }

        // Write the last 16 bytes (4 ints: startRow, startCol, goalRow, goalCol) as is
        for (int i = mazeStartIndex + mazeSize; i < byteArray.length; i++) {
            out.write(byteArray[i]);
        }

        out.flush();
    }
}
