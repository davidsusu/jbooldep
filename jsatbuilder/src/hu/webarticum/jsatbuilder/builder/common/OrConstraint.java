package hu.webarticum.jsatbuilder.builder.common;

import java.util.Collection;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class OrConstraint extends AbstractLiteralListConstraint {

    public OrConstraint(Definition... definitions) {
        super(false, definitions);
    }
    
    public OrConstraint(DefinitionLiteral... literals) {
        super(false, literals);
    }
    
    public OrConstraint(Collection<?> literalsOrDefinitions) {
        super(false, literalsOrDefinitions);
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
