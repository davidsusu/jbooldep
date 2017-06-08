package hu.webarticum.jsatbuilder.builder.common;

import java.util.Collection;
import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.solver.Solver;

public class EqualConstraint extends AbstractLiteralListConstraint {
    
    public EqualConstraint(Definition... definitions) {
        super(false, definitions);
    }
    
    public EqualConstraint(DefinitionLiteral... literals) {
        super(false, literals);
    }
    
    public EqualConstraint(Collection<?> literalsOrDefinitions) {
        super(false, literalsOrDefinitions);
    }

    @Override
    public void fillSolver(Solver solver) {
        List<Solver.Literal> solverLiterals = getLiteralListManager().getSolverLiterals();
        int literalCount = solverLiterals.size();
        if (literalCount > 1) {
            for (int i = 0; i < literalCount; i++) {
                int previousIndex = i == 0 ? literalCount - 1 : i - 1;
                Solver.Literal previousLiteral = solverLiterals.get(previousIndex);
                Solver.Literal currentLiteral = solverLiterals.get(i);
                solver.add(new Solver.Clause(previousLiteral, currentLiteral.getNegated()));
                solver.add(new Solver.Clause(previousLiteral.getNegated(), currentLiteral));
            }
        }
    }

}
