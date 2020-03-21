/*
Herve Ingende
Jacob Dangler
Erwin Joubert

CS4308: Concept of Programming Languages
Section W01
2nd Project Deliverable
March 21st, 2020
*/

import java.util.List;
import java.util.ArrayList;

public class Statement {
    // Statements are a list of lexemes
    private List<Lexeme> lexemeList;

    Statement() {
        // Create a new lexeme list when statement is created
        this.lexemeList = new ArrayList<>();
    }

    // add a lexeme to the statement
    void add(Lexeme lexeme) {
        lexemeList.add(lexeme);
    }

    // returns lexeme list
    List<Lexeme> getLexemeList() {
        return lexemeList;
    }

    @Override
    // Turns the list into a string
    public String toString() {
        String statementString = "";

        // append each lexeme to a string
        for (Lexeme l : lexemeList) {
            statementString += l.getLexeme() + " ";
        }

        // remove trailing whitespace
        return statementString.trim();
    }
}
