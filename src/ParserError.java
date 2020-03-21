/*
Herve Ingende
Jacob Dangler
Erwin Joubert

CS4308: Concept of Programming Languages
Section W01
2nd Project Deliverable
March 21st, 2020
*/

public class ParserError extends Error {
    public ParserError(Lexeme lexeme, LexemeType expected){
        super("Error at Line " + lexeme.getRowNum() + ": Expected "
                + expected.toString() + " Got " + lexeme.getLexeme());
    }
    public ParserError(Lexeme lexeme, String message){
        super("Error at Line " + lexeme.getRowNum() + ": Expected \""
                + message + "\"");
    }
    public ParserError(Lexeme lexeme, TerminalType expected){
        super("Error at Line " + lexeme.getRowNum() + ": Expected "
                + expected.toString() + " Got " + lexeme.getLexeme());
    }
}
