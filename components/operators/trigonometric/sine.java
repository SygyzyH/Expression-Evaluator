package components.operators.trigonometric;

import components.Complex;
import components.Component;
import components.Immediate;

public class sine extends Function {

    public sine(Component c) {
        super(c);
    }

    @Override
    public Immediate evaluate(double value) {
        return new Immediate(Math.sin(this.leftHandSide.evaluate(value).value));
    }

    @Override
    public Complex evaluateComplex(Complex value) {
        Complex imm = this.leftHandSide.evaluateComplex(value);

        return new Complex(Math.sin(imm.real) * Math.cosh(imm.complex), Math.cos(imm.real) * Math.sinh(imm.complex));
    }

    @Override
    public Component derive() {
        return new cosine(this.leftHandSide);
    }
}
