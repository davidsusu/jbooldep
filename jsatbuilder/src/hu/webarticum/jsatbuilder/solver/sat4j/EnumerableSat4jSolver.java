package hu.webarticum.jsatbuilder.solver.sat4j;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;
import org.sat4j.tools.ModelIterator;

public class EnumerableSat4jSolver extends AbstractSat4jSolver {

    @Override
    public boolean run() {
        STATUS previousStatus = this.status;
        this.status = STATUS.RUNNING;
        if (previousStatus==STATUS.INITIAL) {
            buildSolver();
        }
        STATUS status = runSolver();
        if (previousStatus==STATUS.INITIAL) {
            this.status = status;
        } else {
            this.status = previousStatus;
        }
        return (status==STATUS.SAT);
    }
    
    protected ISolver createSolver() {
        return new ModelIterator(SolverFactory.newLight());
    }
    
}
