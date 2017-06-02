package hu.webarticum.jbooldep;


/*

Evaluable
Definition
Brick
Variable :Evaluable :Definition :Brick
Composite :Brick
Constraint :Composite
Helper :Composite :Definition
Expression :Evaluable
Or: Expression
And: Expression
Not: Expression
One: Expression [Restriction: Expression]

##########

new Constraint(required, dependencies, statement)
new Helper(dependencies, statement)
helper.getVariable()

expressions / dependencies ??
?? new <? extends Expression>(...evaluables[, some restrictions])

Util.transformToCnf(expression[, variable])
--> http://fmv.jku.at/limboole/
--> https://github.com/dbasedow/aima-propositional-logic/blob/master/aima-core/src/main/java/aima/core/logic/propositional/visitors/ConvertToCNF.java

*/

public class Variable implements Brick, Definition, Evaluable {
    
}
