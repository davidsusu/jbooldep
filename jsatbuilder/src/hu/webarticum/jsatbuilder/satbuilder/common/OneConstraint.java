package hu.webarticum.jsatbuilder.satbuilder.common;

import java.util.Collection;

import hu.webarticum.jsatbuilder.sat.Solver;
import hu.webarticum.jsatbuilder.satbuilder.core.Definition;

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
