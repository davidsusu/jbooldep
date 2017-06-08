package hu.webarticum.jsatbuilder.builder.common;

import java.util.Arrays;
import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.builder.core.CollapseException;
import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.solver.Solver;

public class CauseConstraint extends AbstractConstraint {
    
    private Definition cause;
    private Definition effect;
    
    public CauseConstraint(Definition cause, Definition effect) {
        this(cause, effect, false);
    }

    public CauseConstraint(Definition cause, Definition effect, boolean required) {
        super(required);
        getDependencyManager().linkDependency(cause);
        getDependencyManager().linkDependency(effect);
    }

    @Override
    public void fillSolver(Solver solver) {
        solver.add(new Solver.Clause(
            new Solver.Literal(cause, false),
            new Solver.Literal(effect, true)
        ));
    }
    
    @Override
    public List<Definition> getDependencies() {
        return Arrays.asList(cause, effect);
    }

    @Override
    public void dependencyRemoved(Definition definition) throws CollapseException {
        remove();
    }

}
