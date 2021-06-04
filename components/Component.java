package components;

public abstract class Component {

    public abstract Immediate evaluate(double value);
    public abstract Complex evaluateComplex(Complex value);
    public abstract Component derive();

    //public abstract boolean equals(Object o);


    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
