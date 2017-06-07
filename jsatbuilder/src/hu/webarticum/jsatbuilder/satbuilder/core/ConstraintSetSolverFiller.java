package hu.webarticum.jsatbuilder.satbuilder.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hu.webarticum.jsatbuilder.sat.Solver;
import hu.webarticum.jsatbuilder.satbuilder.core.Constraint;
import hu.webarticum.jsatbuilder.satbuilder.core.Definition;
import hu.webarticum.jsatbuilder.satbuilder.core.Dependant;
import hu.webarticum.jsatbuilder.satbuilder.core.SolverFiller;

public class ConstraintSetSolverFiller implements SolverFiller {
    
    List<Constraint> constraints = new ArrayList<Constraint>();
    
    public void add(Constraint constraint) {
        constraints.add(constraint);
    }
    
    @Override
    public void fillSolver(Solver solver) {
        Set<Dependant> handledDependants = new HashSet<Dependant>();
        Set<Dependant> unhandledDependants = new HashSet<Dependant>();
        
        for (Constraint constraint: constraints) {
            if (!constraint.isRemoved()) {
                unhandledDependants.add(constraint);
            }
        }
        
        while (!unhandledDependants.isEmpty()) {
            Set<Dependant> newDependants = new HashSet<Dependant>();
            
            for (Dependant dependant: unhandledDependants) {
                for (Definition definition: dependant.getDependencies()) {
                    if (
                        definition instanceof Dependant &&
                        !handledDependants.contains(definition) &&
                        !unhandledDependants.contains(definition)
                    ) {
                        newDependants.add(dependant);
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

}
