package hu.webarticum.jsatbuilder.builder.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import hu.webarticum.jsatbuilder.builder.core.Constraint;
import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.Dependant;
import hu.webarticum.jsatbuilder.builder.core.SolverFiller;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class ConstraintSetSolverFiller implements SolverFiller {
    
    private final boolean linked;
    
    private List<Constraint> constraints = new ArrayList<Constraint>();
    
    public ConstraintSetSolverFiller() {
        this(false);
    }
    
    public ConstraintSetSolverFiller(boolean linked) {
        this.linked = linked;
    }
    
    public void add(Constraint constraint) {
        constraints.add(constraint);
    }
    
    public List<Constraint> getConstraints() {
        return new ArrayList<Constraint>(constraints);
    }
    
    @Override
    public void fillSolver(Solver solver) {
        Set<Dependant> handledDependants = createDependantSet();
        Set<Dependant> unhandledDependants = createDependantSet();
        
        for (Constraint constraint: constraints) {
            if (!constraint.isRemoved()) {
                unhandledDependants.add(constraint);
            }
        }
        
        while (!unhandledDependants.isEmpty()) {
            Set<Dependant> newDependants = createDependantSet();
            
            for (Dependant dependant: unhandledDependants) {
                for (Definition definition: dependant.getDependencies()) {
                    if (
                        definition instanceof Dependant &&
                        !handledDependants.contains(definition) &&
                        !unhandledDependants.contains(definition)
                    ) {
                        newDependants.add((Dependant)definition);
                    }
                }
            }
            
            handledDependants.addAll(unhandledDependants);
            
            unhandledDependants = newDependants;
        }
        
        for (Dependant dependant: handledDependants) {
            if (dependant instanceof SolverFiller) {
                ((SolverFiller)dependant).fillSolver(solver);
            }
        }
    }
    
    private Set<Dependant> createDependantSet() {
        return linked ? new LinkedHashSet<Dependant>() : new HashSet<Dependant>();
    }

}
