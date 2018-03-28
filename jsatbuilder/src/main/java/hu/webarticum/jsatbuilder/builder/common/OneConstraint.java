package hu.webarticum.jsatbuilder.builder.common;

import java.util.Collection;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class OneConstraint extends AbstractLiteralListConstraint {

    public OneConstraint(Definition... definitions) {
        super(true, definitions);
    }
    
    public OneConstraint(DefinitionLiteral... literals) {
        super(true, literals);
    }
    
    public OneConstraint(Collection<?> literalsOrDefinitions) {
        super(true, literalsOrDefinitions);
    }

    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause clause = new Solver.Clause();
        for (Solver.Literal solverLiteral: getLiteralListManager().getSolverLiterals()) {
            clause.addLiteral(solverLiteral);
        }
        solver.addCardinality(clause, 1, 1);
    }

}
