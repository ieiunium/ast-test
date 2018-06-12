package by.kir.ast;

public class CalcEngine {

    public Integer eval(String in) {

        final String withOutSpace = in.replace(" ", "");
        for (int i = 0; i < withOutSpace.length(); i++) {
            final char value = withOutSpace.charAt(i);
            if (!Character.isDigit(value) && true
                    ) {
                throw new RuntimeException("Char " + value + " not allowed");
            }
            if (i != 0) {

            }
        }

        return null;
    }

    public static boolean isSign(char value) {

        return isPlus(value) ||
                isMinus(value) ||
                isSlash(value) ||
                isStar(value) ||
                isLeftBrace(value) ||
                isRightBrace(value);
    }

    private static boolean isRightBrace(char value) {
        return value == ')';
    }

    private static boolean isLeftBrace(char value) {
        return value == '(';
    }

    private static boolean isPlus(char value) {
        return value == '+';
    }

    private static boolean isMinus(char value) {
        return value == '-';
    }

    private static boolean isStar(char value) {
        return value == '*';
    }

    private static boolean isSlash(char value) {
        return value == '/';
    }
}
