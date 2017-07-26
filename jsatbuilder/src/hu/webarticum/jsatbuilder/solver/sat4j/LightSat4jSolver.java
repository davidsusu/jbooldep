package hu.webarticum.jsatbuilder.solver.sat4j;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;

public class LightSat4jSolver extends AbstractSat4jSolver {

    @Override
    protected ISolver createSolver() {
        return SolverFactory.newLight();
    }
    
}
