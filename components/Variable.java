package components;

public class Variable extends Component {

    @Override
    public Immediate evaluate(double value) {
        return new Immediate(value);
    }

    @Override
    public Complex evaluateComplex(Complex value) {
        return new Complex(value.real, value.complex);
    }

    @Override
    public Component derive() {
        return new Immediate(1);
    }
}
