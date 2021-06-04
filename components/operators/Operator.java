package components.operators;

import components.Component;

public abstract class Operator extends Component {

    protected Component leftHandSide, rightHandSide;

    public Operator() {
        this.leftHandSide = null;
        this.rightHandSide = null;
    }
    public Operator(Component leftHandSide, Component rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    public Operator setLeftRight(Component left, Component right) {
        this.leftHandSide = left;
        this.rightHandSide = right;
        return this;
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                "leftHandSide=" + leftHandSide +
                ", rightHandSide=" + rightHandSide +
                '}';
    }
}
