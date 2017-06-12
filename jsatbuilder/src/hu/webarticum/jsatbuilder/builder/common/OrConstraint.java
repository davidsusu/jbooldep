package hu.webarticum.jsatbuilder.builder.common;

import java.util.Collection;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class OrConstraint extends AbstractLiteralListConstraint {

    public OrConstraint(Definition... definitions) {
        super(true, definitions);
    }
    
    public OrConstraint(DefinitionLiteral... literals) {
        super(true, literals);
    }
    
    public OrConstraint(Collection<?> literalsOrDefinitions) {
        super(true, literalsOrDefinitions);
    }

    public OrConstraint(boolean required, Definition... definitions) {
        super(required, definitions);
    }
    
    public OrConstraint(boolean required, DefinitionLiteral... literals) {
        super(required, literals);
    }
    
    public OrConstraint(boolean required, Collection<?> literalsOrDefinitions) {
        super(required, literalsOrDefinitions);
    }

    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause clause = new Solver.Clause();
        for (Solver.Literal solverLiteral: getLiteralListManager().getSolverLiterals()) {
            clause.addLiteral(solverLiteral);
        }
        solver.add(clause);
    }

}
