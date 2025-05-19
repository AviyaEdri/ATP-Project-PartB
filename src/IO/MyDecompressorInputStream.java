package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream {
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
     * @param byteArraay The byte array to read from the input stream.
     * @throws IOException If an I/O error occurs.
     */
    public void read(byte[] byteArraay) throws IOException{
        int index = 0; // Initialize the index to 0

        for(; index < 4 * 6; index++) { // Read the first 4 bytes
            byteArraay[index++] = (byte) in.read(); // Read a byte and store it in the array
        }

        int dataSize = byteArraay.length - index; // Calculate the size of the data
        int fullBytes = dataSize / 8; // Calculate the number of full bytes
        int remainingBits = dataSize % 8; // Calculate the number of remaining bits

        for(int i = 0; i < fullBytes; i++) { // Read the full bytes
            byte b = (byte) in.read(); // Read a byte from the input stream
            for(int bit = 0; bit < 8; bit++) {
                byteArraay[index++] = (byte) ((b >> (7 - bit)) & 1); // Store the bits in the array
            }
        }
        if(remainingBits > 0) { // Read the remaining bits
            byte b = (byte) in.read(); // Read a byte from the input stream
            for(int bit = 0; bit < remainingBits; bit++) {
                byteArraay[index++] = (byte) ((b >> (7 - bit)) & 1); // Store the bits in the array
            }
        }
    }
}
