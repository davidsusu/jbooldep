package hu.webarticum.jsatbuilder.solver.sat4j;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;

public class DefaultSat4jSolver extends AbstractSat4jSolver {

    @Override
    protected ISolver createSolver() {
        return SolverFactory.newDefault();
        /*
        int numberOfProcessors = Runtime.getRuntime().availableProcessors();
        if (numberOfProcessors < 2) {
            return SolverFactory.newSAT();
        } else if (numberOfProcessors == 2) {
            return SolverFactory.newSATUNSAT();
        } else {
            return SolverFactory.newParallel();
        }*/
    }
    
}
