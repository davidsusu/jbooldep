package hu.webarticum.jsatbuilder.builder.common;

import java.util.Collection;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class OptionalOrConstraint extends AbstractLiteralListConstraint {
    
    private final Solver.CLAUSE_PRIORITY priority;
    
    public OptionalOrConstraint(Solver.CLAUSE_PRIORITY priority, Definition... definitions) {
        super(true, definitions);
        this.priority = priority;
    }
    
    public OptionalOrConstraint(Solver.CLAUSE_PRIORITY priority, DefinitionLiteral... literals) {
        super(true, literals);
        this.priority = priority;
    }
    
    public OptionalOrConstraint(Solver.CLAUSE_PRIORITY priority, Collection<?> literalsOrDefinitions) {
        super(true, literalsOrDefinitions);
        this.priority = priority;
    }

    public Solver.CLAUSE_PRIORITY getPriority() {
        return priority;
    }
    
    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause clause = new Solver.Clause();
        for (Solver.Literal solverLiteral: getLiteralListManager().getSolverLiterals()) {
            clause.addLiteral(solverLiteral);
        }
        solver.addOptional(clause, priority);
    }

}
