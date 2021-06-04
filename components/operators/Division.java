package components.operators;

import components.Complex;
import components.Component;
import components.Immediate;

public class Division extends Operator {

    public Division() {
        super();
    }
    public Division(Component leftHandSide, Component rightHandSide) {
        super(leftHandSide, rightHandSide);
    }

    @Override
    public Immediate evaluate(double value) {
        return new Immediate(this.leftHandSide.evaluate(value).value / this.rightHandSide.evaluate(value).value);
    }

    @Override
    public Complex evaluateComplex(Complex value) {
        Complex lhs = this.leftHandSide.evaluateComplex(value), rhs = this.rightHandSide.evaluateComplex(value);
        double denominator = rhs.real * rhs.real + rhs.complex * rhs.complex;

        double real = (lhs.real * rhs.real + lhs.complex * rhs.complex) / denominator;
        double complex = (-lhs.real * rhs.complex + lhs.complex * rhs.real) / denominator;
        return new Complex(real, complex);
    }

    @Override
    public Component derive() {
        Subtraction dividend;
        Multiplication dividendLhs, dividendRhs;
        Exponentiation divisor;

        dividendLhs = new Multiplication(this.leftHandSide.derive(), this.rightHandSide);
        dividendRhs = new Multiplication(this.leftHandSide, this.rightHandSide.derive());

        dividend = new Subtraction(dividendLhs, dividendRhs);
        divisor = new Exponentiation(this.leftHandSide, new Immediate(2));

        return new Division(divisor, dividend);
    }
}
