import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    final static Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Hi there and welcome to this Factorio string converter!\n" +
                "Would you like to to generate a blueprint import string from a JSON formatted input " +
                "or would you like to decode and decompress a blueprint import string to JSON?\n" +
                "Please provide the numbers 1 or 2 accordingly:\n" +
                "1. Generate a compressed Base64 import string from JSON\n" +
                "2. Decompress a compressed Base64 import string to JSON");

        String direction = validateDirection(SCANNER.nextLine());

        runDirection(direction);
    }

    /**
     * Recursively directing based on valid or invalid input.
     * @param direction provided direction to either encode, decode, compress or decompress.
     */
    public static void runDirection(String direction) {

        switch (Integer.parseInt(direction)) {
            case 1:
                compressAndEncode();
                return;
            case 2:
                decodeAndDecompress();
                return;
        }

        System.out.println("No such option is available. Please try again.");

        direction = validateDirection(SCANNER.nextLine());

        runDirection(direction);
    }

    /**
     * Prompt for correct input when incorrect input was provided.
     * @param direction provided direction to either encode, decode, compress or decompress.
     * @return provided direction to either encode, decode, compress or decompress.
     */
    public static String validateDirection(String direction) {
        final String DIRECTION_INSTRUCTION = "Provided wrong input, please enter a correct number (e.g. '1' or '2'...)";
        try{
            final int MIN_INT = 1;
            final int MAXIMUM_DIGITS = 1;
            while(Integer.parseInt(direction) < MIN_INT || direction.length() > MAXIMUM_DIGITS) {
                System.out.println(DIRECTION_INSTRUCTION);
                direction = SCANNER.nextLine();
            }
            return direction;
        }
        catch (NumberFormatException exception) {
            System.out.println(DIRECTION_INSTRUCTION);
            direction = SCANNER.nextLine();
            return validateDirection(direction);
        }
    }

    /**
     * Compresses and encodes a JSON format blueprint to a compressed Base64 encoded import string.
     */
    public static void compressAndEncode() {
        System.out.println("Please provide your Factorio JSON blueprint for compression and encoding to a Base64 import string:");

        //Example string: {"blueprint":{"icons":[{"signal":{"type":"virtual","name":"signal-3"},"index":1},{"signal":{"type":"virtual","name":"signal-1"},"index":2},{"signal":{"type":"virtual","name":"signal-T"},"index":3}],"entities":[{"entity_number":1,"name":"express-transport-belt","position":{"x":-0.5,"y":-2.5},"direction":6},{"entity_number":2,"name":"express-transport-belt","position":{"x":-1.5,"y":-2.5},"direction":4},{"entity_number":3,"name":"express-splitter","position":{"x":-0.5,"y":-1},"direction":2},{"entity_number":4,"name":"express-transport-belt","position":{"x":-1.5,"y":-1.5},"direction":2},{"entity_number":5,"name":"express-transport-belt","position":{"x":1.5,"y":-2.5}},{"entity_number":6,"name":"express-transport-belt","position":{"x":0.5,"y":-2.5},"direction":6},{"entity_number":7,"name":"express-splitter","position":{"x":1,"y":-1.5},"output_priority":"right"},{"entity_number":8,"name":"express-transport-belt","position":{"x":-1.5,"y":-0.5},"direction":2},{"entity_number":9,"name":"express-transport-belt","position":{"x":-0.5,"y":0.5},"direction":6},{"entity_number":10,"name":"express-transport-belt","position":{"x":-1.5,"y":0.5}},{"entity_number":11,"name":"express-transport-belt","position":{"x":1.5,"y":-0.5}},{"entity_number":12,"name":"express-transport-belt","position":{"x":0.5,"y":-0.5}},{"entity_number":13,"name":"express-transport-belt","position":{"x":1.5,"y":0.5}},{"entity_number":14,"name":"express-transport-belt","position":{"x":-0.5,"y":1.5}},{"entity_number":15,"name":"express-transport-belt","position":{"x":-0.5,"y":2.5}},{"entity_number":16,"name":"express-splitter","position":{"x":1,"y":1.5}},{"entity_number":17,"name":"express-transport-belt","position":{"x":1.5,"y":2.5}},{"entity_number":18,"name":"express-transport-belt","position":{"x":0.5,"y":2.5}}],"item":"blueprint","label":"3_1_tu_balancer","version":281479273316352}}

        String uncompressedBlueprintString = SCANNER.nextLine();

        try {

            //TODO: Validate user input

            //TODO: Maybe need to remove spaces in JSON formatting, not sure yet if this will cause anything

            final byte[] UNCOMPRESSED_DATA = uncompressedBlueprintString.getBytes(StandardCharsets.ISO_8859_1);

            final Compressor COMPRESSOR = new ZlibCompressor();

            final byte[] COMPRESSED_DECODED_DATA = COMPRESSOR.compress(UNCOMPRESSED_DATA);

            final Encoder Encoder = new Base64Encoder();

            final String IMPORT_STRING = Encoder.encode(COMPRESSED_DECODED_DATA);

            System.out.println(IMPORT_STRING);
        }
        catch (Exception e) {
            // This block usually is reached when the input was able to pass some simple format checks and was of incorrect format
            System.out.println("Something terrible happened. Exiting program");
        }
    }

    /**
     * Decodes and decompresses a compressed Base64 export string to a JSON format blueprint.
     */
    public static void decodeAndDecompress() {
        System.out.println("Please provide your Factorio encoded blueprint import string for decoding:");

        //Example string:
        //"0eJylVduOgjAQ/Zd5BkOpeOE79m2zIYCN2wRL0w5GQ/j3HSTripTV6htMOZeectIWiqoR2kiFkLYgy1pZSD9bsHKv8qqf4VkLSOEoDTY0CUDlh34wfBFy6AKQaidOkLIu8ECyG2Tshfy4QfLuKwChUKIUg/XLyzlTzaEQhkxd0eKkjbA2RJMrq2uDYSEqJHZdW4LXqpcmyjBaJAGc6SFeJKS0k0aUw/qq93knEPsLsFmBpUOATwSsriQirf3jnY2JYwfx8g3n7N65SyDxFhgl42BceTP6HebaI2t2m0TdoG4woyrVhgiJwMj9N4JDY/NG7NEzsW9f/+Un/K6QWPT6DiL3uTL/mo4ycVH6FzN6RDmt4rMu5xhf6OCvSzZD6d+6K+VM69i0do9qMWduWrBnE5zz5l+n0W7p+pAoDgT/uwoDqHKC0oxnLMMmK/IqV+Vlt0dh7NC8DVuut/Gac7biSdx1P4IlbiU="

        String encodedBlueprintString = SCANNER.nextLine();

        System.out.println("Decoding the string the following string: \n" + encodedBlueprintString);

        final Encoder Encoder = new Base64Encoder();

        while(!Encoder.isValidInput(encodedBlueprintString)) {
            if(encodedBlueprintString.length() < 10)
                System.out.println("Provided import string '" + encodedBlueprintString + "' was not of correct format. Please try again and enter a valid import string: ");
            else
                System.out.println("Provided import string was not of correct format.\n" +
                        "See provided input for reference: \n" +
                        encodedBlueprintString + "\n" +
                        "Please try again and enter a valid import string: ");

            encodedBlueprintString = SCANNER.nextLine();
        }

        try {
            final byte[] COMPRESSED_DECODED_DATA = Encoder.decode(encodedBlueprintString);

            //System.out.println(Arrays.toString(decoded));

            final Compressor COMPRESSOR = new ZlibCompressor();

            final String UNFORMATTED_JSON = new String(COMPRESSOR.decompress(COMPRESSED_DECODED_DATA), StandardCharsets.ISO_8859_1);

            System.out.println("Decoded string: \n" +
                    "Unformatted:\n" + UNFORMATTED_JSON + "\n" +
                    "Formatted: \n" + JSONFormatter.prettyPrintJson(UNFORMATTED_JSON));
        }
        catch (Exception e) {
            // This block usually is reached when the input was able to pass some simple format checks and was of incorrect format
            System.out.println("Something terrible happened. Exiting program");
        }
    }
}
