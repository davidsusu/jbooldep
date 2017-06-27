package hu.webarticum.jsatbuilder.builder.common;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class ConflictConstraint extends AbstractLiteralListConstraint {

    public ConflictConstraint(Definition definition1, Definition definition2) {
        this(new DefinitionLiteral(definition1, true), new DefinitionLiteral(definition2, true));
    }

    public ConflictConstraint(DefinitionLiteral literal1, DefinitionLiteral literal2) {
        this(false, literal1, literal2);
    }
    
    public ConflictConstraint(boolean required, DefinitionLiteral literal1, DefinitionLiteral literal2) {
        super(required, literal1, literal2);
    }

    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause clause = new Solver.Clause();
        for (Solver.Literal solverLiteral: getLiteralListManager().getSolverLiterals()) {
            clause.addLiteral(solverLiteral.getNegated());
        }
        solver.add(clause);
    }

}
