# Expression-Evaluator
A Java based expression evaluator.

## Parser / Lexer
The expression is translated from a string to a expression tree using a parser, with the standart procedure of doing such a transformation:
- First, the string is parsed: converted into tokens.
- Secondly, the tokens are rearranged to postfix / polish notation.
- Lastly, the postfix is used to construct the expression tree.

The first stage is where most of the problems lie. Some errors still get triggered accidentally or not at all. 

# Proofs
I belive some of the methods I used to evaluate the complex components of the expressions deserves some proof, incase theres a better way to do it (or if im doing it wrong)

## Multiplication
Start with the basic multipliaction expression we have to deal with.

![alt text](https://latex.codecogs.com/svg.latex?\left(a%2Bbi\right)\cdot\left(c%2Bdi\right))

Than, we open the parentheses via multiplication to get

![alt text](https://latex.codecogs.com/svg.latex?ac+adi+bic-bd)

Reform the expression to form a + bi

![alt text](https://latex.codecogs.com/svg.latex?ac-bd\+i\left(ad+bc\right))

And thats how we get the new real - complex number pair.

## Division
To devide two complex numbers, we first need to multiply by the conjugate. The conjugate would be the denominator except its complex part is flipped. Thus we get

![alt text](https://latex.codecogs.com/svg.latex?\frac{\left(a+bi\right)\left(c-di\right)}{\left(c+di\right)\left(c-di\right)})

As the solution to the division. Next, we can open the parentheses via multiplication to get

![alt text](https://latex.codecogs.com/svg.latex?\frac{ac-adi+cbi+bd}{c^{2}-cdi+cdi+d^{2}})

We can seperate the terms to real and complex of form a + bi to get

![alt text](https://latex.codecogs.com/svg.latex?\frac{ac+bd}{c^{2}+d^{2}}+\frac{-ad+cb}{c^{2}+d^{2}}i)

We can calculate the denominator only once, and use it twice in the final calculation, to save some time.

## Exponentiation

