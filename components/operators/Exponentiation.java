package components.operators;

import components.Complex;
import components.Component;
import components.Immediate;
import components.operators.trigonometric.ln;

public class Exponentiation extends Operator {

    public Exponentiation() {
        super();
    }
    public Exponentiation(Component base, Component exponent) {
        super(base, exponent);
    }

    @Override
    public Immediate evaluate(double value) {
        return new Immediate(Math.pow(this.leftHandSide.evaluate(value).value, this.rightHandSide.evaluate(value).value));
    }

    @Override
    public Complex evaluateComplex(Complex value) {
        Complex lhs = this.leftHandSide.evaluateComplex(value), rhs = this.rightHandSide.evaluateComplex(value);
        double c = rhs.real, d = rhs.complex;

        double theta = lhs.getAngle(), f = Math.log(lhs.getLength()), g = Math.exp(f * c - d * theta);
        double v = f * d + theta * c;

        return new Complex(g * Math.cos(v), g * Math.sin(v));
    }

    @Override
    public Component derive() {
        Component lhsTg = this.leftHandSide.derive(), rhsTg = this.rightHandSide.derive();
        Component insideLhs = new Multiplication(rhsTg, new ln(this.leftHandSide));
        Component insideRhs = new Division(new Multiplication(lhsTg, this.rightHandSide), this.leftHandSide);

        return new Multiplication(this, new Addition(insideLhs, insideRhs));
    }
}
