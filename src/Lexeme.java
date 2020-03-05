/*
Herve Ingende
Jacob Dangler
Erwin Joubert

CS4308: Concept of Programming Languages
Section W01
1st Project Deliverable
March 3rd, 2020
*/

// Implementation of the Lexeme class
public class Lexeme {
    private LexemeType lexType;
    private String lexeme;
    private int lineNum;
    private int colNum;

    /*
     * Constructor
     * Each Lexeme holds its type (keyword, string_literal, etc), Its word value, and location in  the input file
     */
    public Lexeme(LexemeType lexType, String lexeme, int lineNum, int colNum) {
        // Make sure parameters are valid and set values
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

    // Getter methods
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
