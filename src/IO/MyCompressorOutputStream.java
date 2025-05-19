package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public void MyCompressorOutputStream(OutputStream out1) {
        this.out = out1;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }
    /**
     * This method writes a byte array to the output stream.
     * The first 24 bytes are written as is, and the remaining bits are packed into bytes.
     *
     * @param byteArray The byte array to write to the output stream.
     * @throws IOException If an I/O error occurs.
     */
    public void write(Byte[] byteArray) throws IOException {
        int size = 4 *6; // Calculate the size of the byte array

        for(int i =0; i < size; i++) {
            out.write(byteArray[i]); // Write each byte to the output stream
        }
        int dataSize = byteArray.length - size; // Calculate the size of the data
        int fullBytes = dataSize / 8; // Calculate the number of full bytes
        int remainingBits = dataSize % 8; // Calculate the number of remaining bits

        for(int i = 0; i< fullBytes; i++){
            byte b = 0; // Initialize the byte to 0
            for(int bit = 0; bit <0; bit ++){
                b |= (byteArray[size + i * 8 + bit] & 1) << (7- bit); // Set the bit in the byte
            }
            out.write(b); // Write the byte to the output stream
        }
        if(remainingBits > 0){
            byte b = 0; // Initialize the byte to 0
            for(int bit = 0; bit < remainingBits; bit++){
                b |= (byteArray[size + fullBytes * 8 + bit] & 1) << (7 - bit); // Set the bit in the byte
            }
            out.write(b); // Write the byte to the output stream
        }
    }

}


