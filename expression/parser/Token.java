package expression.parser;

import components.*;
import components.operators.*;

public class Token {
    public ComponentToken type;
    public double val;

    public Token(ComponentToken type) {
        this.type = type;
        this.val = Double.NaN;
    }

    public Token(ComponentToken type, double val) {
        this.type = type;
        this.val = val;
    }

    public static Component tokenToComponent(Token token) {
        switch (token.type) {
            case VARIABLE:
                return new Variable();
            case IMMEDIATE:
                return new Immediate(token.val);
            case COMPLEX:
                return new Complex(0, 1);
            case ADDITION:
                return new Addition();
            case SUBTRACTION:
                return new Subtraction();
            case MULTIPLICATION:
                return new Multiplication();
            case DIVISION:
                return new Division();
            case EXPONENTIATION:
                return new Exponentiation();
        }
        assert (false);
        return new Subtraction();
    }

    @Override
    public String toString() {
        if (!Double.isNaN(this.val))
            return "expression.parser.Token{" + "type=" + type + ", val=" + val + '}';
        else
            return "expression.parser.Token{" + "type=" + type + '}';
    }
}
