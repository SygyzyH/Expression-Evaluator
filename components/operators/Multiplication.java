package components.operators;

import components.Complex;
import components.Component;
import components.Immediate;

public class Multiplication extends Operator {

    public Multiplication() {
        super();
    }
    public Multiplication(Component leftHandSide, Component rightHandSide) {
        super(leftHandSide, rightHandSide);
    }

    @Override
    public Immediate evaluate(double value) {
        return new Immediate(this.leftHandSide.evaluate(value).value * this.rightHandSide.evaluate(value).value);
    }

    @Override
    public Complex evaluateComplex(Complex value) {
        Complex lhs = this.leftHandSide.evaluateComplex(value), rhs = this.rightHandSide.evaluateComplex(value);
        double real = lhs.real * rhs.real - lhs.complex * rhs.complex;
        double complex = lhs.real * rhs.complex + lhs.complex * rhs.real;
        return new Complex(real, complex);
    }

    @Override
    public Component derive() {
        Multiplication lhs, rhs;

        lhs = new Multiplication(this.leftHandSide.derive(), this.rightHandSide);
        rhs = new Multiplication(this.leftHandSide, this.rightHandSide.derive());

        return new Addition(lhs, rhs);
    }
}
