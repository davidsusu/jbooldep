package hu.webarticum.jsatbuilder.builder.common;

import java.util.Collection;
import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.Viability;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class ConflictGroupConstraint extends AbstractLiteralListConstraint {

    public ConflictGroupConstraint(Definition... definitions) {
        super(false, definitions);
    }
    
    public ConflictGroupConstraint(boolean required, DefinitionLiteral... literals) {
        super(false, literals);
    }
    
    public ConflictGroupConstraint(boolean required, Collection<?> literalsOrDefinitions) {
        super(false, literalsOrDefinitions);
    }
    
    @Override
    public void fillSolver(Solver solver) {
        for (Solver.Literal solverLiteral1: getLiteralListManager().getSolverLiterals()) {
            for (Solver.Literal solverLiteral2: getLiteralListManager().getSolverLiterals()) {
                if (!solverLiteral1.equals(solverLiteral2)) {
                    Solver.Clause clause = new Solver.Clause();
                    clause.addLiteral(solverLiteral1.getNegated());
                    clause.addLiteral(solverLiteral2.getNegated());
                    solver.add(clause);
                }
            }
        }
    }
    
    @Override
    protected Viability createViability(List<Definition> definitions) {
        return new AllViability(definitions);
    }

}
