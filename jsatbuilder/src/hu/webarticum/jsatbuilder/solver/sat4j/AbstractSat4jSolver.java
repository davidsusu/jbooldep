package hu.webarticum.jsatbuilder.solver.sat4j;

import java.util.List;

import org.sat4j.core.VecInt;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import hu.webarticum.jsatbuilder.solver.core.AbstractSolver;
import hu.webarticum.jsatbuilder.solver.core.Solver;

abstract public class AbstractSat4jSolver extends AbstractSolver {
    
    protected volatile ISolver solver = null;
    
    @Override
    public boolean run() {
        status = STATUS.RUNNING;
        buildSolver();
        status = runSolver();
        return (status==STATUS.SAT);
    }
    
    @Override
    synchronized public void close() {
        if (solver!=null) {
            if (status == STATUS.RUNNING) {
                solver.expireTimeout();
                status = STATUS.ABORTED;
            }
            solver.reset();
        }
    }
    
    protected void buildSolver() {
        solver = createSolver();
        solver.setTimeout(getTimeout());
        solver.newVar(variables.size());
        
        try {
            fillSolver();
        } catch (Exception e) {
            messages.add("Exception: " + e.getLocalizedMessage());
            status = STATUS.UNSAT;
            solver.reset();
            return;
        }
    }
    
    protected void fillSolver() throws Exception {
        List<Solver.Clause> _normalClauses = getNormalClausesForDecision();
        for (Clause clause: _normalClauses) {
            solver.addClause(createSat4jVecInt(clause));
        }
        for (SpecialClauseWrapper specialClauseWrapper: specialClauseWrappers) {
            if (specialClauseWrapper.minimum != null && specialClauseWrapper.maximum != null) {
                solver.addExactly(createSat4jVecInt(specialClauseWrapper.clause), specialClauseWrapper.minimum);
            } else if (specialClauseWrapper.minimum!=null) {
                solver.addAtLeast(createSat4jVecInt(specialClauseWrapper.clause), specialClauseWrapper.minimum);
            } else if (specialClauseWrapper.maximum!=null) {
                solver.addAtMost(createSat4jVecInt(specialClauseWrapper.clause), specialClauseWrapper.maximum);
            } else {
                solver.addClause(createSat4jVecInt(specialClauseWrapper.clause));
            }
        }
    }

    protected STATUS runSolver() {
        model = new Model();
        
        boolean solved;
        try {
            solved = solver.isSatisfiable();
        } catch (TimeoutException e) {
            if (status == STATUS.ABORTED) {
                messages.add("Aborted");
            } else {
                messages.add("Timeout reached: " + getTimeout());
            }
            return STATUS.ABORTED;
        }
        
        if (status == STATUS.ABORTED) {
            messages.add("Aborted");
            return STATUS.ABORTED;
        }
        
        if (!solved) {
            messages.add("Unsolvable");
            solver.reset();
            return STATUS.UNSAT;
        }
        
        for (int index: solver.model()) {
            model.put(variables.get(Math.abs(index) - 1), index > 0);
        }
        
        return STATUS.SAT;
    }
    
    protected VecInt createSat4jVecInt(Clause clause) {
        List<Literal> literals = clause.getLiterals();
        int[] literalArray = new int[literals.size()];
        int i = 0;
        for (Literal literal: literals) {
            Object variable = literal.getVariable();
            int value = variables.indexOf(variable)+1;
            if (!literal.isPositive()) {
                value = -value;
            }
            literalArray[i] = value;
            i++;
        }
        return new VecInt(literalArray);
    }

    protected int getTimeout() {
        return 100000;
    }

    abstract protected ISolver createSolver();
    
}
