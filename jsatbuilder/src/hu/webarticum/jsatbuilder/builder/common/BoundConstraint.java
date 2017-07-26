package hu.webarticum.jsatbuilder.builder.common;

import java.util.Collection;
import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.Viability;
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

    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause clause = new Solver.Clause();
        for (Solver.Literal solverLiteral: getLiteralListManager().getSolverLiterals()) {
            clause.addLiteral(solverLiteral);
        }
        solver.addSpecial(clause, minimum, maximum);
    }

    @Override
    protected Viability createViability(List<Definition> definitions) {
        if (minimum != null) {
            return new AtLeastViability(minimum, definitions);
        } else {
            return new DefaultViability(definitions);
        }
    }

}
