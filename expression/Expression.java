package expression;

import components.*;
import components.operators.*;
import expression.parser.ComponentToken;
import expression.parser.Parser;
import expression.parser.Token;

import java.util.*;

public class Expression {

    private Component expressionTree;

    private static final int MAX_ITERATIONS = 1000;

    private static final double EPSILON = 10e-14;
    private static final double TOLERANCE = 10e-7;

    public Expression(String expressionString) {
        this.expressionTree = Parser.parseString(expressionString);
    }
    public Expression(Component expressionTree) {
        this.expressionTree = expressionTree;
    }

    public void derive() {
        this.expressionTree = this.expressionTree.derive();
    }

    public Expression getDerivative() {
        return new Expression(this.expressionTree.derive());
    }

    public double eval(double value) {
        return this.expressionTree.evaluate(value).value;
    }

    public Complex evalComplex(Complex value) {
        return this.expressionTree.evaluateComplex(value);
    }

    public Complex evalComplex(double real, double complex) {
        return this.expressionTree.evaluateComplex(new Complex(real, complex));
    }

    public double solve(int maxIterations, double epsilon, double tolerance) {
        double x0 = 1;

        Expression thisPrime = this.getDerivative();

        for (int i = 0; i < maxIterations; i++) {
            double y = this.eval(x0);
            double yPrime = thisPrime.eval(x0);

            if (Math.abs(yPrime) < epsilon)
                break;

            double x1 = x0 - y / yPrime;

            if (Math.abs(x1 - x0) <= tolerance)
                break;

            x0 = x1;
        }

        return x0;
    }

    public double solve() {
        return solve(MAX_ITERATIONS, EPSILON, TOLERANCE);
    }

}

