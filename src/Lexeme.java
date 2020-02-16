public class Lexeme {
    private LexemeType lexType;
    private String lexeme;
    private int lineNum;
    private int colNum;

    public Lexeme(LexemeType lexType, String lexeme, int lineNum, int colNum) {
        if (lexType == null)
            throw new IllegalArgumentException("Null lexeme type argument");

        this.lexType = lexType;

        if (lexeme == null)
            throw new IllegalArgumentException("Null string argument");

        this.lexeme = lexeme;

        if (lineNum <= 0)
            throw new IllegalArgumentException("Invalid row number argument");

        this.lineNum = lineNum;

        if (colNum <= 0)
            throw new IllegalArgumentException("Invalid column number argument");

        this.colNum = colNum;
    }

    public LexemeType getLexemeType() {
        return lexType;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getRowNum() {
        return lineNum;
    }

    public int getColNum() {
        return colNum;
    }
}
