package components.operators.trigonometric;

import components.Complex;
import components.Component;
import components.Immediate;
import components.Variable;
import components.operators.Division;

public class ln extends Function {

    public ln(Component c) {
        super(c);
    }

    @Override
    public Immediate evaluate(double value) {
        return new Immediate(Math.log(this.leftHandSide.evaluate(value).value));
    }

    @Override
    public Complex evaluateComplex(Complex value) {
        return new Complex(Math.log(value.getLength()), value.getAngle());
    }

    @Override
    public Component derive() {
        return new Division(new Immediate(1), new Variable());
    }
}
