package components;

public class Immediate extends Component {

    public double value;

    public Immediate(double value) {
        this.value = value;
    }

    @Override
    public Immediate evaluate(double value) {
        return this;
    }

    @Override
    public Complex evaluateComplex(Complex value) {
        return new Complex(this.value, 0);
    }

    @Override
    public Component derive() {
        return new Immediate(0);
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                "value=" + value +
                '}';
    }
}
