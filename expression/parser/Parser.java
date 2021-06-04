package expression.parser;

import components.Component;
import components.operators.Operator;

import java.util.*;

public class Parser {
    private static final char EOF = '$';
    private static final char negationSign = '-';
    private static final char decimalSeparator = '.';

    private static final String validVariables = "xie";
    private static final String parentheses = "()";
    private static final String validNumbers = "0123456789";
    private static final String validOperators = "+-*/^()=";
    private static final String validOperands = validNumbers + decimalSeparator;
    private static final String validCharactersAfterOperand = validVariables + validNumbers + parentheses + EOF;
    private static final String validCharactersAfterVariable = validVariables + validOperators + parentheses + EOF;
    private static final String validCharacters = validNumbers + validOperators + validVariables + decimalSeparator + EOF;
    private static final String validCharactersAtExpressionStart = negationSign + validOperands + validVariables + '(' + EOF;
    private static final String validCharactersAfterStatementSeparator = validNumbers + validVariables + negationSign + '(' + EOF;

    private static final boolean DEBUG = false;

    public static Component parseString(String expressionString) {
        Token[] tokens = tokenizeString(expressionString);
        tokens = tokensToPolishNotation(tokens);

        Stack<Component> output = new Stack<>();

        for (Token token : tokens)
            if (token.type.getIsOperator()) {
                Component rhs = output.pop(), lhs = output.pop();
                output.push(((Operator) Token.tokenToComponent(token)).setLeftRight(lhs, rhs));
            } else {
                output.push(Token.tokenToComponent(token));
            }

        if (output.size() != 1)
            System.err.println("String parsing unk error");

        return output.pop();
    }

    public static Token[] tokensToPolishNotation(Token[] tokens) {
        Stack<Token> operatorStack = new Stack<>();
        Queue<Token> output = new LinkedList<>();

        for (Token token : tokens) {
            if (token.type == ComponentToken.EQUALITY)
                token = new Token(ComponentToken.SUBTRACTION);
            if (token.type.getIsOperator()) {
                if (token.type == ComponentToken.CLOSE_PARENTHESES) {

                    while (operatorStack.peek().type != ComponentToken.OPEN_PARENTHESES)
                        output.add(operatorStack.pop());
                    operatorStack.pop();

                } else if (token.type == ComponentToken.OPEN_PARENTHESES || operatorStack.isEmpty() || token.type.getPriority() > operatorStack.peek().type.getPriority()) {

                    operatorStack.push(token);

                } else {

                    while (!operatorStack.isEmpty() && token.type.getPriority() < operatorStack.peek().type.getPriority())
                        output.add(operatorStack.pop());
                    operatorStack.push(token);

                }
            } else {

                output.add(token);

            }
        }

        while (!operatorStack.isEmpty())
            output.add(operatorStack.pop());

        if (DEBUG) {
            System.out.println("==DEBUG: Polish Notation Conversion==");
            System.out.println("Polish tokens: ");
            for (Token token : output.toArray(new Token[0])) System.out.println(token);
            System.out.println();
        }

        return output.toArray(new Token[0]);
    }

    public static Token[] tokenizeString(String expressionString) {
        expressionString = expressionString.replace(" ", "");
        expressionString = expressionString.replace(",", "");
        expressionString = expressionString.toLowerCase();

        if (expressionString.indexOf(EOF) == -1)
            expressionString += EOF;

        // oop this asap
        double numberBuffer = 0;
        int numberBufferExp = 0;
        boolean numberBufferAvailable = false;

        int parenthesisCount = 0;
        boolean equalitySeen = false;

        List<Token> tokens = new ArrayList<>();

        char prev = 0;

        for (int i = 0; i < expressionString.length(); i++) {
            char ch = expressionString.charAt(i);
            if (ch == EOF) break;
            char peek = expressionString.charAt(i+1);

            Token lastToken = null;
            if (tokens.size() > 0)
                lastToken = tokens.get(tokens.size() - 1);

            if (validCharacters.indexOf(ch) == -1)
                System.err.println("Invalid character at position " + i + ": " + ch);

            if (i == 0 && validCharactersAtExpressionStart.indexOf(ch) == -1)
                System.err.println("Unexpected operator at begging of expression");

            // tokenize literal immediates
            if (validOperands.indexOf(ch) != -1) {

                if (validOperands.indexOf(peek) == -1)
                    numberBufferAvailable = true;

                if (ch == decimalSeparator) {
                    numberBufferAvailable = false;

                    numberBufferExp = -1;

                    if (validCharactersAfterOperand.indexOf(peek) == -1)
                        System.err.println("Unexpected token after decimal point at position " + i);
                } else {
                    if (numberBufferExp >= 0) {

                        numberBuffer = numberBuffer * Math.pow(10, numberBufferExp) + Character.getNumericValue(ch);

                        numberBufferExp++;
                    } else {

                        numberBuffer += Character.getNumericValue(ch) * Math.pow(10, numberBufferExp);

                        numberBufferExp--;
                    }
                }
            }

            // tokenize variables
            if (validVariables.indexOf(ch) != -1) {
                if (validCharactersAfterVariable.indexOf(peek) == -1)
                    System.err.println("Unexpected token after variable at position " + i);

                double prefix = 1;

                if (lastToken != null) {
                    switch (lastToken.type) {
                        case IMMEDIATE:
                            prefix = lastToken.val;

                            if (prefix == 0 || prefix == 1)
                                tokens.remove(lastToken);
                            break;
                        case VARIABLE:
                        case COMPLEX:
                        case CLOSE_PARENTHESES:
                            tokens.add(new Token(ComponentToken.MULTIPLICATION));
                            break;
                    }
                }

                if (prefix != 0) {
                    if (prefix != 1)
                        tokens.add(new Token(ComponentToken.MULTIPLICATION));

                    switch (ch) {
                        case 'x':
                            tokens.add(new Token(ComponentToken.VARIABLE));
                            break;
                        case 'i':
                            tokens.add(new Token(ComponentToken.COMPLEX));
                            break;
                        case 'e':
                            tokens.add(new Token(ComponentToken.IMMEDIATE, 2.718281828459045));
                            break;
                    }
                }
            }

            // tokenize any fully parsed components
            if (numberBufferAvailable) {

                tokens.add(new Token(ComponentToken.IMMEDIATE, numberBuffer));

                numberBuffer = 0;
                numberBufferExp = 0;
                numberBufferAvailable = false;
            }

            // tokenize operators
            if (validOperators.indexOf(ch) != -1) {
                if (validCharactersAfterOperand.indexOf(peek) == -1)
                    System.err.println("Unexpected token after operator at position: " + i);
                if ((peek == EOF || peek == '=') && parentheses.indexOf(ch) == -1)
                    System.err.println("Hanging operator at position: " + i);

                switch (ch) {
                    case '+':
                        tokens.add(new Token(ComponentToken.ADDITION));
                        break;
                    case '-':
                        if (lastToken == null) {
                            tokens.add(new Token(ComponentToken.IMMEDIATE, -1));
                            tokens.add(new Token(ComponentToken.MULTIPLICATION));
                        } else if (lastToken.type == ComponentToken.EQUALITY || peek == '(') {
                            tokens.add(new Token(ComponentToken.IMMEDIATE, -1));
                            tokens.add(new Token(ComponentToken.MULTIPLICATION));
                        } else
                            tokens.add(new Token(ComponentToken.SUBTRACTION));
                        break;
                    case '*':
                        tokens.add(new Token(ComponentToken.MULTIPLICATION));
                        break;
                    case '/':
                        tokens.add(new Token(ComponentToken.DIVISION));
                        break;
                    case '^':
                        tokens.add(new Token(ComponentToken.EXPONENTIATION));
                        break;
                    case '(':
                        if (validCharactersAfterStatementSeparator.indexOf(peek) == -1)
                            System.err.println("Unexpected token after parentheses at position: " + i);

                        if (lastToken != null)
                            if (lastToken.type != ComponentToken.EQUALITY && lastToken.type != ComponentToken.NEGATE && lastToken.type != ComponentToken.OPEN_PARENTHESES && !lastToken.type.getIsOperator())
                                tokens.add(new Token(ComponentToken.MULTIPLICATION));

                        parenthesisCount++;

                        tokens.add(new Token(ComponentToken.OPEN_PARENTHESES));
                        break;
                    case ')':
                        if (validCharactersAfterStatementSeparator.indexOf(peek) == -1)
                            System.err.println("Unexpected token after parentheses at position: " + i);

                        parenthesisCount--;

                        tokens.add(new Token(ComponentToken.CLOSE_PARENTHESES));
                        break;
                    case '=':
                        if (validCharactersAfterStatementSeparator.indexOf(peek) == -1)
                            System.err.println("Unexpected token after equality sign at position: " + i);

                        if (!equalitySeen)
                            equalitySeen = true;
                        else
                            System.err.println("Too many equality signs at position: " + i);

                        tokens.add(new Token(ComponentToken.EQUALITY));
                        break;
                }
            }

        }

        if (parenthesisCount > 0)
            System.err.println("Unclosed parentheses");
        if (parenthesisCount < 0)
            System.err.println("Too many closed parentheses");

        if (DEBUG) {
            System.out.println("==DEBUG: Lexer==");
            System.out.println("Tokens being parsed: ");
            for (Token token : tokens) System.out.println(token);
            System.out.println();
        }

        return tokens.toArray(new Token[0]);
    }
}
