package expression.parser;

public enum ComponentToken {
    IMMEDIATE(false),
    VARIABLE(false),
    COMPLEX(false),
    EQUALITY(false),
    NEGATE(false),
    OPEN_PARENTHESES(true),
    CLOSE_PARENTHESES(true),
    ADDITION(true, 1),
    SUBTRACTION(true, 1),
    DIVISION(true, 2),
    MULTIPLICATION(true,2),
    EXPONENTIATION(true, 3);

    private final boolean isOperator;
    private final int priority;

    ComponentToken(boolean isOperator) {
        this.isOperator = isOperator;
        this.priority = 0;
    }
    ComponentToken(boolean isOperator, int priority) {
        this.isOperator = isOperator;
        this.priority = priority;
    }

    public boolean getIsOperator() {
        return this.isOperator;
    }
    public int getPriority() {
        return this.priority;
    }
}
