package components;

public class Complex extends Component {

    public double real, complex;

    public Complex(double real, double complex) {
        this.real = real;
        this.complex = complex;
    }

    public double getLength() {
        return Math.sqrt(this.real * this.real + this.complex * this.complex);
    }

    public double getAngle() {
        return Math.asin(this.complex / this.getLength());
    }

    @Override
    public Immediate evaluate(double value) {
        System.err.println("Evaluating complex number will be treat as only it's real component.");
        return new Immediate(this.real);
    }

    @Override
    public Complex evaluateComplex(Complex value) {
        return this;
    }

    @Override
    public Component derive() {
        return new Immediate(0);
    }

    @Override
    public String toString() {
        return "Complex{" +
                "real=" + real +
                ", complex=" + complex +
                '}';
    }
}
