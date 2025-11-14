import java.util.Base64;

public class Base64Encoder implements Encoder {

    //TODO: Should check whether it's a number rather than just strongly typed 0 instead.
    /**
     * Checks whether provided input starts with a "0" character.
     * This is due to the first byte of the import string being a version byte according to Factorio definition.
     * For more information see the <a href="https://wiki.factorio.com/Blueprint_string_format">Factorio wiki</a>
     * @param input the imported string used for making blueprints available
     * @return a boolean value which tells whether provided input string starts with a version byte
     */
    public static boolean startsWithZero(String input) {
        return input.charAt(0) == '0';
    }

    /**
     * Checks whether provided input has a valid format for further processing.
     * @param input the imported string used for making blueprints available
     * @return a boolean value which tells whether provided input string has a correct format,
     * so it can be processed for decoding.
     */
    @Override
    public boolean isValidInput(String input) {
        //Probably should add more conditions to be more specific
        return startsWithZero(input) && input.length() > 10;
    }

    //TODO: Add version byte
    /**
     * Encodes a byte array to a Base64 encoded import string
     * @param input a compressed byte array of a JSON format blueprint
     * @return a compressed and encoded blueprint string
     * @throws IllegalArgumentException
     */
    @Override
    public String encode(byte[] input) throws IllegalArgumentException {
        return Base64.getEncoder().encodeToString(input);
    }

    /**
     * Decodes a Factorio provided blueprint export string, with the first version byte removed.
     * For more information see the <a href="https://wiki.factorio.com/Blueprint_string_format">Factorio wiki</a>
     * @param input a compressed Base64 blueprint export string
     * @return a compressed and decoded blueprint in byte array format
     * @throws IllegalArgumentException
     */
    @Override
    public byte[] decode(String input) throws IllegalArgumentException {
        return Base64.getDecoder().decode(input.substring(1));
    }

    //How to decode Base64:

    /*
    byte[] bytes = "Hello, World!".getBytes("UTF-8");
    String encoded = Base64.getEncoder().encodeToString(bytes);
    byte[] decoded = Base64.getDecoder().decode(encoded);
    String decodedStr = new String(decoded, StandardCharsets.UTF_8);
     */
}
