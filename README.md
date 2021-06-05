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

## Division


## Exponentiation

