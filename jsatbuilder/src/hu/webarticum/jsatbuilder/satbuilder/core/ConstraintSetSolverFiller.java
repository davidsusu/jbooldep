package hu.webarticum.jsatbuilder.satbuilder.core;

import java.util.ArrayList;
import java.util.List;

import hu.webarticum.jsatbuilder.sat.Solver;

public class ConstraintSetSolverFiller implements SolverFiller {
    
    private List<Constraint> constraints = new ArrayList<Constraint>();
    
    public void add(Constraint constraint) {
        constraints.add(constraint);
    }

    @Override
    public void fillSolver(Solver solver) {
        
        // TODO
        
    }
    
}
