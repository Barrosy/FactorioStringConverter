public interface Encoder {
    boolean isValidInput(String input);
    String encode(byte[] input);
    byte[] decode(String input);
}
