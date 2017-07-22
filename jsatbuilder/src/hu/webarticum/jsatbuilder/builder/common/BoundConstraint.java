package hu.webarticum.jsatbuilder.builder.common;

import java.util.Collection;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class BoundConstraint extends AbstractLiteralListConstraint {

    private final Integer minimum;
    private final Integer maximum;
    
    public BoundConstraint(int number, Definition... definitions) {
        super(true, definitions);
        this.minimum = this.maximum = number;
    }
    
    public BoundConstraint(int number, DefinitionLiteral... literals) {
        super(true, literals);
        this.minimum = this.maximum = number;
    }
    
    public BoundConstraint(int number, Collection<?> literalsOrDefinitions) {
        super(true, literalsOrDefinitions);
        this.minimum = this.maximum = number;
    }

    public BoundConstraint(Integer minimum, Integer maximum, Definition... definitions) {
        super(true, definitions);
        this.minimum = minimum;
        this.maximum = maximum;
    }
    
    public BoundConstraint(Integer minimum, Integer maximum, DefinitionLiteral... literals) {
        super(true, literals);
        this.minimum = minimum;
        this.maximum = maximum;
    }
    
    public BoundConstraint(Integer minimum, Integer maximum, Collection<?> literalsOrDefinitions) {
        super(true, literalsOrDefinitions);
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public BoundConstraint(boolean required, Integer minimum, Integer maximum, Definition... definitions) {
        super(required, definitions);
        this.minimum = minimum;
        this.maximum = maximum;
    }
    
    public BoundConstraint(boolean required, Integer minimum, Integer maximum, DefinitionLiteral... literals) {
        super(required, literals);
        this.minimum = minimum;
        this.maximum = maximum;
    }
    
    public BoundConstraint(boolean required, Integer minimum, Integer maximum, Collection<?> literalsOrDefinitions) {
        super(required, literalsOrDefinitions);
        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause clause = new Solver.Clause();
        for (Solver.Literal solverLiteral: getLiteralListManager().getSolverLiterals()) {
            clause.addLiteral(solverLiteral);
        }
        solver.addSpecial(clause, minimum, maximum);
    }

}
