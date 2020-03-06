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
import java.util.*;

public class SCLScanner {
    private List<Lexeme> lexemes;
    private List<String> identifiers = new ArrayList<>();
    private String prevLex = "";

    // Keywords for the subset of SCL
    private Set<String> keywords = new HashSet<>(Arrays.asList("array", "begin", "define", "display", "endfun",
            "endif", "endwhile", "forward", "function", "global", "if", "implementations", "import", "input",
            "integer", "is", "return", "set", "symbol", "then", "type", "while"));

    // All lexemes will be categorized and listed
    public SCLScanner(String fileName) throws FileNotFoundException {
        if (fileName == null)
            throw new IllegalArgumentException("Null string argument");

        lexemes = new ArrayList<Lexeme>();
        int lineNum = 1;
        File f = new File(fileName);
        java.util.Scanner input = new java.util.Scanner(f);

        // For ever line of the file
        while (input.hasNext()) {
            // Get the line and remove leading and trailing whitespace
            String line = input.nextLine().trim();
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

        // Loop through every word
        while (index < line.length()) {
            String lexeme = getLexeme(line, index);

            // Add lexeme to lexemes array and move on to next word
            LexemeType lexType = getLexemeType(lexeme, lineNum, index + 1);
            lexemes.add(new Lexeme(lexType, lexeme, lineNum, index + 1));
            index += lexeme.length();
            System.out.printf("Line: %-3d Col: %-3d\tSymbol: %-25s Lexeme: %s%n", lineNum, index, lexType, lexeme);
            index = skipWhiteSpace(line, index);

        }
    }

    // Method that returns lexeme type
    private LexemeType getLexemeType(String lexeme, int lineNum, int colNum) {
        assert lexeme != null;
        LexemeType lt = LexemeType.token;

        if (lexeme.charAt(0) == '"' && (lexeme.charAt(lexeme.length() - 1) == '"' || lexeme.charAt(lexeme.length() - 2) == '"')) {
            // Check for string literal
            lt = LexemeType.string_literal;
        } else if (prevLex.equals("define") || identifiers.contains(lexeme)) {
            // Check for identifier
            lt = LexemeType.identifier;
            identifiers.add(lexeme);
        } else if (lexeme.equals("=")) {
            // Check for assignment operator
            lt = LexemeType.assignment_op;
        } else {
            boolean digit = Character.isDigit(lexeme.charAt(lexeme.length() - 1));

            if (Character.isDigit(lexeme.charAt(0)) && digit) {
                // Check if lexeme is a number
                if (Double.parseDouble(lexeme) % 1 == 0) {
                    // Check if  integer
                    lt = LexemeType.integer_constant;
                } else {
                    // Check if real
                    lt = LexemeType.real_constant;
                }
            } else if ((lexeme.charAt(0) == '+' || lexeme.charAt(0) == '-') && digit) {
                // Handle signed numbers
                if (Double.parseDouble(lexeme) % 1 == 0) {
                    // Check if  integer
                    lt = LexemeType.signed_integer_constant;
                } else {
                    // Check if real
                    lt = LexemeType.signed_real_constant;
                }
            } else if (lexeme.equals("==") || lexeme.equals("!=") || lexeme.equals("<") || lexeme.equals("<=")
                    || lexeme.equals(">") || lexeme.equals(">=")) {
                // Check equality symbols
                lt = LexemeType.relational_operator;
            } else if (lexeme.length() >= 2 && (lexeme.substring(0, Math.min(lexeme.length(), 2)).equals("//"))) {
                // Check for comments
                lt = LexemeType.comment;
            } else if (keywords.contains(lexeme)) {
                // Check if keyword
                lt = LexemeType.keyword;
            }
        }

        prevLex = lexeme;
        return lt;
    }

    // Returns index of next word in a sentence
    private int skipWhiteSpace(String line, int index) {
        assert line != null;
        assert index >= 0;
        int current = index;

        // Go from current index to an index where current character isn't whitespace
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
