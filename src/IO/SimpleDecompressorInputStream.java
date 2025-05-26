package IO;
import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream {
    private InputStream in;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in; // Initialize the input stream
    }
    /**
     * This method reads a byte array from the input stream.
     * The first 24 bytes are read as is, and the remaining bits are packed into bytes.
     *
     * @param byteArray The byte array to read from the input stream.
     * @throws IOException If an I/O error occurs.
     */
    public void read(byte[] byteArray) throws IOException {
        int index = 0; // Initialize the index to 0


        for (; index < 4 * 6; index++) {
            byteArray[index] = (byte) in.read(); // Read a byte and store it in the array
        }

        int dataIndex = index; // Calculate the size of the data
        byte current = 0; // Initialize the current byte to 0

        while (dataIndex < byteArray.length) {
            int runLength = readInt(); // Read the run length from the input stream
            for (int i = 0; i < runLength && dataIndex < byteArray.length; i++) {
                byteArray[dataIndex++] = current; // Store the current byte in the array
            }
            current = (byte) (1 - current); // Toggle the current byte
        }
    }

    /**
     * Reads an integer from the input stream.
     *
     * @return The integer read from the input stream.
     * @throws IOException If an I/O error occurs.
     */
    private int readInt() throws IOException {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result |= (in.read() & 0xFF) << (i * 8); // Read a byte and shift it to the correct position
        }
        return result;
    }

    /**
     * Closes the input stream.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void close() throws IOException {
        in.close();
    }
}
