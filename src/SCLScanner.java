/*
Herve Ingende
Jacob Dangler
Erwin Joubert

CS4308: Concept of Programming Languages
Section W01
1st Project Deliverable
March 3rd, 2020
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SCLScanner {
    private List<Lexeme> lexemes;
    private List<String> identifiers = new ArrayList<>();
    private String prevLex = "";

    // All lexemes will be categorized and listed
    public SCLScanner(String fileName) throws FileNotFoundException {
        if (fileName == null)
            throw new IllegalArgumentException("Null string argument");

        lexemes = new ArrayList<Lexeme>();
        int lineNum = 1;
        File f = new File(fileName);
        java.util.Scanner input = new java.util.Scanner(f);

        while (input.hasNext()) {
            String line = input.nextLine().trim();

            // check for multi-line comments
            if (line.length() >= 2 && (line.charAt(0) == '/' && line.charAt(1) == '*') && (line.charAt(line.length() - 2) != '*' && line.charAt(line.length() - 1) != '/') || line.equals("description")) {
                // Handle multi-line comment symbol
                processLine(line, lineNum, 0);

                // Ignore content of multi-line comments
                while (true) {
                    lineNum++;
                    line = input.nextLine().trim();
                    System.out.printf("Line: %-14dComment%n", lineNum);

                    if (line.length() >= 2 && line.charAt(line.length() - 2) == '*' && line.charAt(line.length() - 1) == '/') {
                        break;
                    }
                }
            }

            processLine(line, lineNum, 0);
            lineNum++;
        }

        input.close();
        System.out.println("\nEnd of input file");
    }

    // Per line, list all the lexemes and columns, then move on to the next line
    private void processLine(String line, int lineNum, int index) {
        assert line != null;
        assert lineNum > 0;
        assert index >= 0;
        index = skipWhiteSpace(line, index);

        while (index < line.length()) {
            String lexeme = getLexeme(line, index);

            LexemeType lexType = getLexemeType(lexeme, lineNum, index + 1);
            lexemes.add(new Lexeme(lexType, lexeme, lineNum, index + 1));
            index += lexeme.length();
            System.out.printf("Line: %-3d Col: %-3d\tSymbol: %-25s Lexeme: %s%n", lineNum, index, lexType, lexeme);
            index = skipWhiteSpace(line, index);

        }
    }

    private LexemeType getLexemeType(String lexeme, int lineNum, int colNum) {
        assert lexeme != null;
        LexemeType lt = LexemeType.keyword;

        if (lexeme.charAt(0) == '"' && (lexeme.charAt(lexeme.length() - 1) == '"' || lexeme.charAt(lexeme.length() - 2) == '"')) {
            lt = LexemeType.string_literal;
        } else if (prevLex.equals("define") || identifiers.contains(lexeme)) {
            lt = LexemeType.identifier;
            identifiers.add(lexeme);
        } else if (lexeme.equals("=")) {
            lt = LexemeType.assignment_op;
        } else {
            if (Character.isDigit(lexeme.charAt(0)) && Character.isDigit(lexeme.charAt(lexeme.length() - 1))) {
                if (Double.parseDouble(lexeme) % 1 == 0) {
                    lt = LexemeType.integer_constant;
                } else {
                    lt = LexemeType.real_constant;
                }
            } else if ((lexeme.charAt(0) == '+' || lexeme.charAt(0) == '-') && Character.isDigit(lexeme.charAt(lexeme.length() - 1))) {
                if (Double.parseDouble(lexeme) % 1 == 0) {
                    lt = LexemeType.signed_integer_constant;
                } else {
                    lt = LexemeType.signed_real_constant;
                }
            } else if (lexeme.equals("==") || lexeme.equals("!=") || lexeme.equals("<") || lexeme.equals("<=")
                    || lexeme.equals(">") || lexeme.equals(">=")) {
                lt = LexemeType.relational_operator;
            } else if (lexeme.length() >= 2 && (lexeme.substring(0, Math.min(lexeme.length(), 2)).equals("//")
                    || (lexeme.length() >= 2 && lexeme.substring(0, 2).equals("/*") || lexeme.substring(lexeme.length() - 2).equals("*/")))) {
                lt = LexemeType.comment;
            }
        }

        prevLex = lexeme;
        return lt;
    }

    private int skipWhiteSpace(String line, int index) {
        assert line != null;
        assert index >= 0;
        int current = index;

        while (current < line.length() && Character.isWhitespace(line.charAt(current)))
            current++;

        return current;
    }


    private String getLexeme(String line, int index) {
        assert line != null;
        assert index >= 0;
        assert !Character.isWhitespace(line.charAt(index));
        int location = index + 1;

        // This if statement is meant to take in string literals in quotes
        if (line.charAt(index) == '"') {
            while (line.charAt(location) != '"') {
                location++;
            }
        }

        // Check for comments
        if (line.charAt(index) == '/') {
            while (location < line.length()) {
                location++;
            }
        }

        while (location < line.length() && !Character.isWhitespace(line.charAt(location))) {
            location++;
        }

        return line.substring(index, location);
    }

}
