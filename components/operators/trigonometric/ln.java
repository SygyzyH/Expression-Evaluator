package components.operators.trigonometric;

import components.Complex;
import components.Component;
import components.Immediate;
import components.Variable;
import components.operators.Division;

public class ln extends Function {

    /*
    TODO: this implementation of having absolute logs is necessary for deriving exponents. However, this
    TODO: implementation doesn't take into account second derivatives of the function.

    This cant be solved by adding a abs() function since it still cannot be derived linerly. Either non-liner
    derivatives would have to be added, forking the expression tree to multiple possible solutions, or some other
    implementation of absolutes would have to be made.
    */
    private boolean abs;

    public ln(Component c) {
        super(c);
    }
    public ln(Component c, boolean abs) {
        super(c);
        this.abs = abs;
    }

    @Override
    public Immediate evaluate(double value) {
        if (abs)
            return new Immediate(Math.log(Math.abs(this.leftHandSide.evaluate(value).value)));
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
