package components.operators;

import components.Complex;
import components.Component;
import components.Immediate;

public class Subtraction extends Operator {

    public Subtraction() {
        super();
    }
    public Subtraction(Component leftHandSide, Component rightHandSide) {
        super(leftHandSide, rightHandSide);
    }

    @Override
    public Immediate evaluate(double value) {
        return new Immediate(this.leftHandSide.evaluate(value).value - this.rightHandSide.evaluate(value).value);
    }

    @Override
    public Complex evaluateComplex(Complex value) {
        Complex lhs = this.leftHandSide.evaluateComplex(value), rhs = this.rightHandSide.evaluateComplex(value);
        double real = lhs.real - rhs.real, complex = lhs.complex - rhs.complex;
        return new Complex(real, complex);
    }

    @Override
    public Component derive() {
        return new Subtraction(this.leftHandSide.derive(), this.rightHandSide.derive());
    }
}
