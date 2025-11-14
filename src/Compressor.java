import java.io.IOException;
import java.util.zip.DataFormatException;

public interface Compressor {
    byte[] compress(byte[] input) throws IOException;
    byte[] decompress(byte[] input) throws DataFormatException, IOException;
}
