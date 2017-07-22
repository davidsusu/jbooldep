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

    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause clause = new Solver.Clause();
        for (Solver.Literal solverLiteral: getLiteralListManager().getSolverLiterals()) {
            clause.addLiteral(solverLiteral);
        }
        solver.add(clause);
    }

}
