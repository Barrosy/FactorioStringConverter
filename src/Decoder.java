import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class Decoder {
    public static boolean startsWithZero(String input) {
        return input.charAt(0) == '0';
    }

    public static boolean isValidInput(String input) {
        //Probably should add more conditions to be more specific
        return startsWithZero(input) && input.length() > 10;
    }

    public static byte[] base64StringToByteArray(String input) throws IllegalArgumentException {
        return Base64.getDecoder().decode(input.substring(1));
    }

    /***
     * Decompresses data stored in a byte array to an actual uncompressed readable JSON string
     * @param base64DecodedInput a byte array containing a base64 decoded import string
     * @return Decompressed string containing JSON data
     * @throws DataFormatException When invalid input was provided, an exception might occur
     * @throws IOException When invalid input was provided, an exception might occur
     */
    public static String zlibInflate(byte[] base64DecodedInput) throws DataFormatException, IOException {
        Inflater inflater = new Inflater();
        inflater.setInput(base64DecodedInput);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(base64DecodedInput.length);

        byte[] buffer = new byte[1024];

        while(!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();

        return outputStream.toString(StandardCharsets.UTF_8);
    }

    /*
        How to encode and decode Base64:
        /*
        byte[] bytes = "Hello, World!".getBytes("UTF-8");
        String encoded = Base64.getEncoder().encodeToString(bytes);
        byte[] decoded = Base64.getDecoder().decode(encoded);
        String decodedStr = new String(decoded, StandardCharsets.UTF_8);


        How to deflate (or compress information):
        try {
            byte[] arrayOfBytesOfString = encodedBlueprintString.getBytes("UTF-8");

            Deflater deflater = new Deflater();
            deflater.setInput(arrayOfBytesOfString);
            deflater.finish();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(arrayOfBytesOfString.length);
            byte[] buffer = new byte[1024];
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();

            byte[] compressed = outputStream.toByteArray();

            System.out.println("Origineel: " + encodedBlueprintString.getBytes("UTF-8").length + " bytes");
            System.out.println("Gecomprimeerd: " + compressed.length + " bytes");

        }
        catch (Exception e) {
            System.out.println("Oops something went wrong");
        }*/
}
