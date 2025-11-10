import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hi there and welcome to this Factorio string converter!");

        System.out.println("Would you like to decode a blueprint import string or would you to generate a blueprint import string from JSON?");

        System.out.println("generate/decode?");

        Scanner scanner = new Scanner(System.in);

        String direction = scanner.nextLine();

        //Make it a while loop as well...
        if(direction.equals("generate")) {
            // Do something...
            System.out.println("Import generation feature is still work in progress. Exiting program. ");
            return;
        } else if (direction.equals("decode")) {
            // Execute exisitng code
            System.out.println("Please provide your Factorio encoded blueprint import string for decoding:");
        }
        else {
            System.out.println("Provided wrong input, exiting program.");
            return;
        }

        //Example string:
        //"0eJylVduOgjAQ/Zd5BkOpeOE79m2zIYCN2wRL0w5GQ/j3HSTripTV6htMOZeectIWiqoR2kiFkLYgy1pZSD9bsHKv8qqf4VkLSOEoDTY0CUDlh34wfBFy6AKQaidOkLIu8ECyG2Tshfy4QfLuKwChUKIUg/XLyzlTzaEQhkxd0eKkjbA2RJMrq2uDYSEqJHZdW4LXqpcmyjBaJAGc6SFeJKS0k0aUw/qq93knEPsLsFmBpUOATwSsriQirf3jnY2JYwfx8g3n7N65SyDxFhgl42BceTP6HebaI2t2m0TdoG4woyrVhgiJwMj9N4JDY/NG7NEzsW9f/+Un/K6QWPT6DiL3uTL/mo4ycVH6FzN6RDmt4rMu5xhf6OCvSzZD6d+6K+VM69i0do9qMWduWrBnE5zz5l+n0W7p+pAoDgT/uwoDqHKC0oxnLMMmK/IqV+Vlt0dh7NC8DVuut/Gac7biSdx1P4IlbiU="

        String encodedBlueprintString = scanner.nextLine();

        System.out.println("Decoding the string the following string: \n" + encodedBlueprintString);

        System.out.println("Decoded string:");

        while(!Decoder.isValidInput(encodedBlueprintString)) {
            System.out.println("Provided import string was not of correct format. Please try again and enter a valid import string: ");
            encodedBlueprintString = scanner.nextLine();
        }


        try {
            byte[] decoded = Decoder.base64StringToByteArray(encodedBlueprintString);

            //System.out.println(Arrays.toString(decoded));

            String unformattedJson = Decoder.zlibInflate(decoded);
            System.out.println(JSONFormatter.prettyPrintJson(unformattedJson));
        }
        catch (Exception e) {
            // This block usually is reached when the input was able to pass some simple format checks and was of incorrect format
            System.out.println("Something terrible happened. Exiting program");
        }
    }
}
