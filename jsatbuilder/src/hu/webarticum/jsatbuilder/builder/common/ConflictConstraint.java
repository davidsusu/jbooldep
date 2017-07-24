package hu.webarticum.jsatbuilder.builder.common;

import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.LiveManager;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class ConflictConstraint extends AbstractLiteralListConstraint {

    public ConflictConstraint(Definition definition1, Definition definition2) {
        this(new DefinitionLiteral(definition1, true), new DefinitionLiteral(definition2, true));
    }

    public ConflictConstraint(DefinitionLiteral literal1, DefinitionLiteral literal2) {
        super(false, literal1, literal2);
    }

    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause clause = new Solver.Clause();
        for (Solver.Literal solverLiteral: getLiteralListManager().getSolverLiterals()) {
            clause.addLiteral(solverLiteral.getNegated());
        }
        solver.add(clause);
    }
    
    @Override
    protected LiveManager createLiveManager(List<Definition> definitions) {
        return new AllLiveManager(definitions);
    }

}
