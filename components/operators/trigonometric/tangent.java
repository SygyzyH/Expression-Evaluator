package components.operators.trigonometric;

import components.Complex;
import components.Component;
import components.Immediate;
import components.operators.Division;
import components.operators.Exponentiation;

public class tangent extends Function {

    public tangent(Component c) {
        super(c);
    }

    @Override
    public Immediate evaluate(double value) {
        return new Immediate(Math.tan(this.leftHandSide.evaluate(value).value));
    }

    @Override
    public Complex evaluateComplex(Complex value) {
        Complex imm = this.leftHandSide.evaluateComplex(value);
        double denominator = 1 + Math.tan(imm.real) * Math.tan(imm.real) * Math.tanh(imm.complex) * Math.tanh(imm.complex);

        double real = Math.tan(imm.real) - Math.tan(imm.real) * Math.tanh(imm.complex) * Math.tanh(imm.complex);
        double complex = Math.tanh(imm.complex) + Math.tan(imm.real) * Math.tan(imm.real) * Math.tanh(imm.complex);

        return new Complex(real / denominator, complex / denominator);
    }

    @Override
    public Component derive() {
        Exponentiation denominator = new Exponentiation(new cosine(this.leftHandSide), new Immediate(2));

        return new Division(new Immediate(1), denominator);
    }
}
