package expression.parser;

import components.Component;
import components.operators.Operator;

import java.util.*;

public class Parser {

    private static final boolean DEBUG = true;

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

        List<Token> tokens = new ArrayList<>();

        for (int i = 0; i < expressionString.length(); i++) {
            char c = expressionString.charAt(i);

            // immediates
            if (Character.isDigit(c)) {
                Token lastToken = getLastToken(tokens);
                if (lastToken != null) switch (lastToken.type) {
                    case CLOSE_PARENTHESES:
                    case VARIABLE:
                    case COMPLEX:
                        tokens.add(new Token(ComponentToken.MULTIPLICATION));
                }

                // capture next immediate
                StringBuilder num = new StringBuilder();
                for (; i < expressionString.length() && (Character.isDigit(expressionString.charAt(i)) || expressionString.charAt(i) == '.'); i++) num.append(expressionString.charAt(i));
                tokens.add(new Token(ComponentToken.IMMEDIATE, Double.parseDouble(num.toString())));
                i--;
                continue;
            }

            switch (c) {
            // operators
                case '+':
                    tokens.add(new Token(ComponentToken.ADDITION));
                    break;
                case '-': {
                    Token lastToken = getLastToken(tokens);
                    if (lastToken == null) {
                        tokens.add(new Token(ComponentToken.IMMEDIATE, -1));
                        tokens.add(new Token(ComponentToken.MULTIPLICATION));
                    } else switch (lastToken.type) {
                        case OPEN_PARENTHESES:
                        case EQUALITY:
                        case ADDITION:
                        case SUBTRACTION:
                        case MULTIPLICATION:
                        case DIVISION:
                        case EXPONENTIATION:
                            tokens.add(new Token(ComponentToken.IMMEDIATE, -1));
                            tokens.add(new Token(ComponentToken.MULTIPLICATION));
                            break;
                        default:
                            // if there is no negation, it must be subtraction
                            tokens.add(new Token(ComponentToken.SUBTRACTION));
                            break;
                    }
                    break;
                }
                case '*':
                    tokens.add(new Token(ComponentToken.MULTIPLICATION));
                    break;
                case '/':
                    tokens.add(new Token(ComponentToken.DIVISION));
                    break;
                case '^':
                    tokens.add(new Token(ComponentToken.EXPONENTIATION));
                    break;
                case '=':
                    tokens.add(new Token(ComponentToken.EQUALITY));
                    break;
                case ')':
                    tokens.add(new Token(ComponentToken.CLOSE_PARENTHESES));
                    break;
                case '(':
                case 'x':
                case 'i':
                case 'e': {
                    Token lastToken = getLastToken(tokens);
                    if (lastToken == null) switch (c) {
                        case '(':
                            tokens.add(new Token(ComponentToken.OPEN_PARENTHESES));
                            break;
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
                    else switch (lastToken.type) {
                        case CLOSE_PARENTHESES:
                        case IMMEDIATE:
                        case COMPLEX:
                        case VARIABLE:
                            tokens.add(new Token(ComponentToken.MULTIPLICATION));
                        default:
                            switch (c) {
                                case '(':
                                    tokens.add(new Token(ComponentToken.OPEN_PARENTHESES));
                                    break;
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
                            break;
                    }
                    break;
                }
            }
        }

        if (DEBUG) {
            System.out.println("==DEBUG: Lexer==");
            System.out.println("Tokens being parsed: ");
            for (Token token : tokens) System.out.println(token);
            System.out.println();
        }

        return tokens.toArray(new Token[0]);
    }

    private static Token getLastToken(List<Token> tokens) {
        if (tokens.size() > 0)
            return tokens.get(tokens.size() - 1);
        return null;
    }
}
