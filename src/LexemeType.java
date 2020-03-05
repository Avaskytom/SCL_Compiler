/*
Herve Ingende
Jacob Dangler
Erwin Joubert

CS4308: Concept of Programming Languages
Section W01
1st Project Deliverable
March 3rd, 2020
*/

public enum LexemeType {
    token,                  // Word, no quotes, numbers, more than length 1 (import, function, is, define, type, display)
    keyword,                // Reserved keywords
    string_literal,         // String in quotes ("scl.h", "Welcome to the world of SCL", "Value of x: ")
    identifier,             // Variables defined with values
    assignment_op,          // "="
    real_constant,
    relational_operator,    // Equality (==, !=, <, <=, >, >=)
    integer_constant,       // Ints
    signed_integer_constant,
    signed_real_constant,
    comment                 // Comments following forward slashes
}
