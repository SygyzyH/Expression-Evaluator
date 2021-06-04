package components.operators.trigonometric;

import components.Complex;
import components.Component;
import components.Immediate;

public class cosine extends Function {

    public cosine(Component c) {
        super(c);
    }

    @Override
    public Immediate evaluate(double value) {
        return new Immediate(Math.cos(this.leftHandSide.evaluate(value).value));
    }

    @Override
    public Complex evaluateComplex(Complex value) {
        Complex imm = this.leftHandSide.evaluateComplex(value);

        return new Complex(Math.cos(imm.real) * Math.cosh(imm.complex), - Math.sin(imm.real) * Math.sinh(imm.complex));
    }

    @Override
    public Component derive() {
        return new sine(this.leftHandSide);
    }
}
