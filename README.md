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
Start by considering the polar form of the first argument

![alt text](https://latex.codecogs.com/svg.latex?\left(a+bi\right)=re^{i\theta})

Where

![alt text](https://latex.codecogs.com/svg.latex?r=\sqrt{a^{2}+b^{2}})

![alt text](https://latex.codecogs.com/svg.latex?\theta=\arcsin\left(\frac{b}{r}\right))

We keep all arguments in the exponent, to make the following calculations cleaner, giving

![alt text](https://latex.codecogs.com/svg.latex?e^{\ln\left(r\right)i\theta})

Thus, we can derive that

![alt text](https://latex.codecogs.com/svg.latex?\left(a+bi\right)^{\left(c+di\right)})

Is equivelant to

![alt text](https://latex.codecogs.com/svg.latex?e^{\left(\ln\left(r\right)i\theta\right)\left(c+di\right)})

Using power rules. We can simplify the expression slightly to seperate real and complex components

![alt text](https://latex.codecogs.com/svg.latex?e^{\ln\left(r\right)c}e^{\ln\left(r\right)d}e^{i\theta c}e^{-\theta d})

We can assing a few repeating arguments to clean up even further, and save on calculation time

![alt text](https://latex.codecogs.com/svg.latex?g=e^{\ln\left(r\right)c-\theta d})

![alt text](https://latex.codecogs.com/svg.latex?v=fd+\theta c)

Next, using Euler's formula, we can find the real and complex components of the expression as

![alt text](https://latex.codecogs.com/svg.latex?g\left(\cos\left(v\right)+i\sin\left(v\right)\right))

Giving the final solution of form a + bi.
