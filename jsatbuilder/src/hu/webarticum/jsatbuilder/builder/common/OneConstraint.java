package hu.webarticum.jsatbuilder.builder.common;

import java.util.Collection;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class OneConstraint extends AbstractLiteralListConstraint {

    public OneConstraint(Definition... definitions) {
        super(false, definitions);
    }
    
    public OneConstraint(DefinitionLiteral... literals) {
        super(false, literals);
    }
    
    public OneConstraint(Collection<?> literalsOrDefinitions) {
        super(false, literalsOrDefinitions);
    }

    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause clause = new Solver.Clause();
        for (Solver.Literal solverLiteral: getLiteralListManager().getSolverLiterals()) {
            clause.addLiteral(solverLiteral);
        }
        solver.addUnique(clause);
    }

}
