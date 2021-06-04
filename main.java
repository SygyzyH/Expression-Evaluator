import expression.Expression;

public class main {
    public static void main(String[] args) {
        Expression e = new Expression("2x^2+ix^2");
        e.derive();
        System.out.println(e.evalComplex(2, 1));
    }
}
