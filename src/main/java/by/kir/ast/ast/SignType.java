package by.kir.ast.ast;

public enum SignType {
    PLUS("+"),
    MINUS("-"),
    STAR("*"),
    SLASH("/"),
    LEFT_BRACE("("),
    RIGHT_BRACE(")");

    private String raw;

    SignType(String raw) {
        this.raw = raw;
    }

    public static SignType parseSrc(String in) {
        for (SignType signType : SignType.values()) {
            if (signType.raw.equals(in)) {
                return signType;
            }
        }
        throw new IllegalArgumentException();
    }
}
