package hu.webarticum.jsatbuilder.builder.common;

import java.util.Arrays;
import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.builder.core.CollapseException;
import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.solver.Solver;

public class ConditionConstraint extends AbstractConstraint {
    
    private final Definition definition;
    
    private final Solver.CLAUSE_PRIORITY priority;

    public ConditionConstraint(Definition definition) {
        this(definition, null);
    }
    
    public ConditionConstraint(Definition definition, Solver.CLAUSE_PRIORITY priority) {
        super(false);
        this.definition = definition;
        this.priority = priority;
        getDependencyManager().linkDependency(definition);
    }

    @Override
    public List<Definition> getDependencies() {
        return Arrays.<Definition>asList(definition);
    }

    @Override
    public void dependencyRemoved(Definition definition) throws CollapseException {
        remove();
    }

    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause clause = new Solver.Clause(new Solver.Literal(definition, true));
        if (priority == null) {
            solver.add(clause);
        } else {
            solver.addOptional(clause, priority);
        }
    }
    
}
