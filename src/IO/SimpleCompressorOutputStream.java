package IO;
import java.io.OutputStream;
import java.io.IOException;

public class SimpleCompressorOutputStream {
    private OutputStream out;

    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out; // Initialize the output stream
    }
    /**
     * This method writes a byte array to the output stream.
     * The first 24 bytes are written as is, and the remaining bits are packed into bytes.
     *
     * @param byteArray The byte array to write to the output stream.
     * @throws IOException If an I/O error occurs.
     */
    public void write(byte[] byteArray) throws IOException {
        int headerSize = 4 * 6; // Calculate the size of the header

        // Write the header bytes directly to the output stream
        for (int i = 0; i < headerSize; i++) {
            out.write(byteArray[i]);
        }

        byte current = byteArray[headerSize]; // Initialize the current byte to the first data byte
        int count = 1; // Initialize the count of consecutive bytes

        for (int i = headerSize + 1; i < byteArray.length; i++) {
            if (byteArray[i] == current) {
                count++; // Increment the count if the byte is the same as the current byte
            } else {
                writeInt(count); // Write the count of consecutive bytes
                current = byteArray[i]; // Update the current byte
                count = 1; // Reset the count
            }
        }
        writeInt(count); // Write the count of the last sequence
        out.flush(); // Flush the output stream to ensure all data is written
    }
    /**
     * Writes an integer to the output stream in big-endian format.
     *
     * @param value The integer to write.
     * @throws IOException If an I/O error occurs.
     */
    private void writeInt(int value) throws IOException {
        for (int i = 0; i < 4; i++) {
            out.write((value >> (i * 8)) & 0xFF);
        }
    }
    /**
     * Closes the output stream.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void close() throws IOException {
        out.close();
    }
}