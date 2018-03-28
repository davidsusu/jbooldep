package hu.webarticum.jsatbuilder.builder.common;

import java.util.Arrays;
import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.Viability;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class ConditionConstraint extends AbstractConstraint {
    
    private final Definition definition;

    private final boolean optional;
    
    private final boolean important;
    
    private final int weight;
    
    private final Viability viability;

    public ConditionConstraint(Definition definition) {
        this(definition, false, false, 0);
    }

    public ConditionConstraint(Definition definition, int weight) {
        this(definition, true, false, weight);
    }

    public ConditionConstraint(Definition definition, int weight, boolean important) {
        this(definition, true, important, weight);
    }

    protected ConditionConstraint(Definition definition, boolean optional, boolean important, int weight) {
        super(false);
        this.definition = definition;
        this.optional = optional;
        this.important = important;
        this.weight = weight;
        getDependencyLinker().linkDependency(definition);
        viability = new DefaultViability(definition);
    }

    public boolean isOptional() {
        return optional;
    }

    public boolean isImportant() {
        return important;
    }
    
    public int getWeight() {
        return weight;
    }
    
    @Override
    public List<Definition> getDependencies() {
        return Arrays.<Definition>asList(definition);
    }

    @Override
    public Viability getViability() {
        return viability;
    }

    @Override
    protected void freeDefinition(Definition definition) {
        // nothing to do
    }
    
    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause clause = new Solver.Clause(new Solver.Literal(definition, true));
        if (!optional) {
            solver.add(clause);
        } else if (important) {
            solver.addImportantOptional(clause, weight);
        } else {
            solver.addOptional(clause, weight);
        }
    }

    @Override
    public String getInfo() {
        return
            getClass().getSimpleName() + "(" +
            definition + ", " +
            optional + ", " +
            important + ", " +
            weight + ")"
        ;
    }
    
}
