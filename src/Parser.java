/*
Herve Ingende
Jacob Dangler
Erwin Joubert

CS4308: Concept of Programming Languages
Section W01
2nd Project Deliverable
March 21st, 2020
*/

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Statement> statements; // Stores all statements as a list
    private List<Lexeme> lexemes;       // Lexeme list, output of parser
    private Lexeme currentLexeme;       // Lexeme that is being processed
    private int index, rowIndex;        // Keep track of current lexeme and current row

    private Statement statement = new Statement();

    Parser(List<Lexeme> lexemes) {
        // Initialize all variables
        this.lexemes = lexemes;
        this.statements = new ArrayList<>();
        this.index = -1;
        this.currentLexeme = null;
        this.rowIndex = -1;

        // Skip any comments at the top of the file
        nextLexeme();

        // Run parser
        if(implementations()) {
            storeLexeme();
        }

        // Display all statements
        System.out.println("Statements:");
        for(Statement s : statements) {
            System.out.println(s);
        }
    }

    // <implementations> ::= implementations <func_list>
    // returns true if no errors
    private boolean implementations() {
        if(currentLexeme.getLexeme().equals(TerminalType.implementations.toString())) {
            storeAndNext();
            return funcList();
        } else
            throw new ParserError(currentLexeme, TerminalType.implementations);
    }

    // <func_list> ::= <func_list> <func_def> | <func_def>
    private boolean funcList() {
        return funcDef();
    }

    // <func_def> ::= function IDENTIFIER is <var_list> begin <statement> endfun IDENTIFIER
    private boolean funcDef() {
        if(currentLexeme.getLexeme().equals(TerminalType.function.toString())) {
            storeAndNext();

            // Store function name
            storeAndNext();

            if(currentLexeme.getLexeme().equals(TerminalType.is.toString())) {
                storeAndNext();

                if(varList() && currentLexeme.getLexeme().equals(TerminalType.begin.toString())) {
                    storeAndNext();

                    if(statement()) {
                        storeAndNext();
                    }

                }else {
                    throw new ParserError(currentLexeme, TerminalType.begin);
                }
            } else {
                throw new ParserError(currentLexeme, TerminalType.is);
            }
        } else {
            return true;
        }

        return funcDef();
    }

    // <var_list> ::= <var_list> <variable> | <variable>
    private boolean varList() {
        if(currentLexeme.getLexeme().equals(TerminalType.variables.toString())) {
            storeAndNext();
            return variable();
        }

        return true;
    }

    // <variable> ::= define IDENTIFIER of type <data_type>
    private boolean variable() {
        if(currentLexeme.getLexeme().equals(TerminalType.begin.toString())) {
            return true;
        }

        if(currentLexeme.getLexeme().equals(TerminalType.define.toString())) {
            // Store 'define'
            storeAndNext();

            // Store identifier
            storeAndNext();

            if(currentLexeme.getLexeme().equals(TerminalType.of.toString())) {
                storeAndNext();

                if(currentLexeme.getLexeme().equals(TerminalType.type.toString())) {
                    storeAndNext();

                    if(!dataType()) {
                        return false;
                    }
                } else {
                    throw new ParserError(currentLexeme, TerminalType.type);
                }
            } else {
                throw new ParserError(currentLexeme, TerminalType.of);
            }
        } else {
            throw new ParserError(currentLexeme, TerminalType.define);
        }

        return variable();
    }

    // <statement> ::= <statement> <action> | <statement>
    private boolean statement() {
        if(currentLexeme.getLexeme().equals(TerminalType.endfun.toString())) {
            storeAndNext();
            return true;
        }

        return action();
    }

    // <action> ::= <set_action> | <display_action> | <input_action> | <return_action>
    private boolean action() {
        String currentAction = currentLexeme.getLexeme();

        switch (currentAction) {
            case "set" -> {
                storeAndNext();
                setAction();
            }
            case "display" -> {
                storeAndNext();
                displayAction();
            }
            case "input" -> {
                storeAndNext();
                inputAction();
            }
            case "return" -> {
                storeAndNext();
                returnAction();
            }
            default -> {
                throw new ParserError(currentLexeme, "set, display, input, or return");
            }
        }

        return statement();
    }

    // <set_action>	::= set IDENTIFIER = <expr>
    private boolean setAction() {
        if(currentLexeme.getLexemeType() == LexemeType.identifier) {
            storeAndNext();

            if(currentLexeme.getLexemeType() == LexemeType.assignment_op) {
                storeAndNext();
                return term();
            } else
                throw new ParserError(currentLexeme, LexemeType.assignment_op);
        } else {
            throw new ParserError(currentLexeme, LexemeType.identifier);
        }
    }

    // <display_action> ::= display IDENTIFIER | display STRING_LITERAL, IDENTIFIER
    private boolean displayAction() {
        if(currentLexeme.getLexemeType() == LexemeType.string_literal) {
            storeAndNext();

            if (currentLexeme.getLexemeType() == LexemeType.comma) {
                storeAndNext();

                if(currentLexeme.getLexemeType() == LexemeType.identifier) {
                    storeAndNext();
                }
            }

            return true;
        } else if(currentLexeme.getLexemeType() == LexemeType.identifier) {
            storeAndNext();
        } else {
            throw new ParserError(currentLexeme, "string_literal or identifier");
        }

        return false;
    }

    // <input_action> ::= input STRING_LITERAL, IDENTIFIER
    private boolean inputAction() {
        if(currentLexeme.getLexemeType() == LexemeType.string_literal) {
            storeAndNext();

            if (currentLexeme.getLexemeType() == LexemeType.comma) {
                storeAndNext();

                if(currentLexeme.getLexemeType() == LexemeType.identifier) {
                    storeAndNext();
                    return true;
                } else {
                    throw new ParserError(currentLexeme, LexemeType.identifier);
                }
            } else {
                throw new ParserError(currentLexeme, LexemeType.comma);
            }
        } else {
            throw new ParserError(currentLexeme, LexemeType.string_literal);
        }
    }

    // <return_action> ::= return <expr>
    private boolean returnAction() {
        return expr();
    }

    // <expr> ::= term
    private boolean expr() {
        return term();
    }

    // <term> ::= IDENTIFIER | REAL_CONSTANT | INTEGER_CONSTANT
    private boolean term() {
        switch (currentLexeme.getLexemeType()) {
            case signed_integer_constant:
            case signed_real_constant:
            case real_constant:
            case integer_constant:
            case identifier:
                storeAndNext();
                return true;
            default:
                throw new ParserError(currentLexeme, "signed_integer_constantL, signed_real_constant, real_constant, integer_constant, or identifier");
        }
    }

    // <data_type> ::= integer | double | real
    // Data types declared in variables
    // i.e. define t of type double
    private boolean dataType() {
        switch (currentLexeme.getLexeme()) {
            case "double":
            case "integer":
            case "real":
                storeAndNext();
                return true;
            default:
                throw new ParserError(currentLexeme, "double, integer, or real");
        }
    }

    // Returns all statements
    List<Statement> getStatements() {
        return statements;
    }

    // Store current lexeme and go to next one
    private void storeAndNext() {
        storeLexeme();
        nextLexeme();
    }

    // Store lexeme into a statement
    private void storeLexeme() {
        // Check if new line since SCL has statements broken up into lines
        if (rowIndex != currentLexeme.getRowNum()) {
            rowIndex = currentLexeme.getRowNum();

            // Don't add empty statement
            if(statement.toString().length() > 0) {
                statements.add(statement);
            }

            statement = new Statement();
        }

        statement.add(currentLexeme);
    }

    // Go to next lexeme and skip comments
    private void nextLexeme() {
        advance();
        skipComments();
    }

    // Don't process comments and skip to next lexeme
    private void skipComments() {
        while(currentLexeme.getLexemeType() == LexemeType.comment) {
            this.rowIndex = currentLexeme.getRowNum();
            advance();
        }
    }

    // Go to the next lexeme and accumulate index
    private void advance() {
        currentLexeme = lexemes.get(++index);
    }
}
