import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ZlibCompressor implements Compressor {
    final int BUFFER_SIZE = 1024;

    /**
     * Compress data stored in a byte array to a decompressed byte array
     * @param input a byte array containing any uncompressed data
     * @return a compressed byte array
     * @throws IOException When invalid input was provided, an exception might occur
     */
    @Override
    public byte[] compress(byte[] input) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(input.length);

        try (outputStream) {
            byte[] buffer = new byte[BUFFER_SIZE];

            while(!deflater.finished()) {
                int count = deflater.deflate(buffer);
                outputStream.write(buffer, 0, count);
            }
        } finally {
            deflater.end();
        }
        return outputStream.toByteArray();
    }

    /***
     * Decompresses data stored in a byte array to a decompressed byte array
     * @param input a byte array containing any compressed data
     * @return a decompressed byte array
     * @throws DataFormatException When invalid input was provided, an exception might occur
     * @throws IOException When invalid input was provided, an exception might occur
     */
    @Override
    public byte[] decompress(byte[] input) throws DataFormatException, IOException {
        Inflater inflater = new Inflater();
        inflater.setInput(input);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(input.length);

        try (outputStream) {
            byte[] buffer = new byte[BUFFER_SIZE];

            while (true) {
                int count = inflater.inflate(buffer);

                if (count > 0) {
                    outputStream.write(buffer, 0, count);
                } else if (inflater.finished()) {
                    break;
                } else if (inflater.needsInput()) {
                    throw new IOException("Inflater needs more input but no more data is available. Data might be incomplete or not valid");
                } else {
                    // No progress → infinite loop safeguard
                    throw new IOException("Inflater is stuck without any progress (no count, not finished, no more input needed).");
                }
            }
        } finally {
            inflater.end();
        }

        return outputStream.toByteArray();
    }
}
