public class JSONFormatter {
    public static String prettyPrintJson (String json) {
        StringBuilder pretty = new StringBuilder();
        int indent = 0;
        for(char c : json.toCharArray()) {
            switch(c) {
                case '{':
                case '[':
                    pretty.append(c).append("\n");
                    indent++;
                    pretty.append("\t".repeat(indent));
                    break;
                case'}':
                case ']':
                    pretty.append("\n");
                    indent--;
                    pretty.append("\t".repeat(indent)).append(c);
                    break;
                case ',':
                    pretty.append(c).append("\n").append("\t".repeat(indent));
                    break;
                default:
                    pretty.append(c);
            }
        }
        return pretty.toString();
    }
}
