package hu.webarticum.jsatbuilder.builder.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import hu.webarticum.jsatbuilder.builder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.builder.core.CollapseException;
import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class GeneralClauseSetConstraint extends AbstractConstraint {
    
    private final List<List<DefinitionLiteral>> clauses;
    
    private final Definition condition;
    
    public GeneralClauseSetConstraint(boolean required, Collection<? extends Collection<DefinitionLiteral>> clauses, Definition condition) {
        super(required);
        this.clauses = new ArrayList<List<DefinitionLiteral>>();
        for (Collection<DefinitionLiteral> clause: clauses) {
            this.clauses.add(new ArrayList<DefinitionLiteral>(clause));
        }
        this.condition = condition;
        for (Definition definition: getDependencies()) {
            getDependencyManager().linkDependency(definition);
        }
    }

    @Override
    public List<Definition> getDependencies() {
        Map<Definition, Object> dependencies = new IdentityHashMap<Definition, Object>();
        for (List<DefinitionLiteral> clause: clauses) {
            for (DefinitionLiteral literal: clause) {
                dependencies.put(literal.getDefinition(), null);
            }
        }
        if (condition != null) {
            dependencies.put(condition, null);
        }
        return new ArrayList<Definition>(dependencies.keySet());
    }

    @Override
    public void dependencyRemoved(Definition definition) throws CollapseException {
        // XXX
        remove();
    }

    @Override
    public void fillSolver(Solver solver) {
        for (List<DefinitionLiteral> clause: clauses) {
            Solver.Clause solverClause = new Solver.Clause();
            if (condition != null) {
                solverClause.addLiteral(new Solver.Literal(condition, false));
            }
            for (DefinitionLiteral literal: clause) {
                solverClause.addLiteral(literal.toSolverLiteral());
            }
            solver.add(solverClause);
        }
    }
    
}
