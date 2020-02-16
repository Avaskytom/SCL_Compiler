
    public enum LexemeType {

        keyword, // Word, no quotes, numbers, more than length 1 (import, function, is, define, type, display)
        string_literal, // String in quotes ("scl.h", "Welcome to the world of SCL", "Value of x: ")
        identifier, // Variables defined with values
        assignment_op, // "="
        real_constant,
        Relational_operator, // Equality (==, !=, <, <=, >, >=)
        integer_constant, // Ints
        signed_integer_constant,
        signed_real_constant,
        comment // Comments following forward slashes
    }
