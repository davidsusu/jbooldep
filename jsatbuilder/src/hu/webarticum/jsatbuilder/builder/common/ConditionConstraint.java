package hu.webarticum.jsatbuilder.builder.common;

import java.util.Arrays;
import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.LiveManager;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class ConditionConstraint extends AbstractConstraint {
    
    private final Definition definition;
    
    private final Solver.CLAUSE_PRIORITY priority;
    
    private final LiveManager liveManager;

    public ConditionConstraint(Definition definition) {
        this(definition, null);
    }
    
    public ConditionConstraint(Definition definition, Solver.CLAUSE_PRIORITY priority) {
        super(false);
        this.definition = definition;
        this.priority = priority;
        getDependencyLinker().linkDependency(definition);
        liveManager = new DefaultLiveManager(definition);
    }

    public Solver.CLAUSE_PRIORITY getPriority() {
        return priority;
    }
    
    @Override
    public List<Definition> getDependencies() {
        return Arrays.<Definition>asList(definition);
    }

    @Override
    public LiveManager getLiveManager() {
        return liveManager;
    }

    @Override
    protected void freeDefinition(Definition definition) {
        // nothing to do
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
