package hu.webarticum.jsatbuilder.builder.common;

import java.util.Arrays;
import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.Viability;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class CauseConstraint extends AbstractConstraint {
    
    private Definition cause;
    private Definition effect;
    
    private final Viability viability;
    
    public CauseConstraint(Definition cause, Definition effect) {
        super(false);
        getDependencyLinker().linkDependency(cause);
        getDependencyLinker().linkDependency(effect);
        viability = new AllViability(cause, effect);
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
    public Viability getViability() {
        return viability;
    }

    @Override
    protected void freeDefinition(Definition definition) {
        // nothing to do
    }
    
}
