package hu.webarticum.jsatbuilder.solver.sat4j;

import java.util.List;

import org.sat4j.core.VecInt;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import hu.webarticum.jsatbuilder.solver.core.AbstractSolver;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public abstract class AbstractSat4jSolver extends AbstractSolver {
    
    protected final int DEFAULT_TIMEOUT = 10000;
    
    protected volatile ISolver solver = null;
    
    @Override
    public boolean run() {
        status = STATUS.RUNNING;
        buildSolver();
        status = runSolver();
        return (status == STATUS.SAT);
    }
    
    @Override
    public synchronized void close() {
        if (solver != null) {
            if (status == STATUS.RUNNING) {
                solver.expireTimeout();
                status = STATUS.ABORTED;
            }
            solver.reset();
        }
    }
    
    protected void buildSolver() {
        getSat4jSolver();
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
        List<Solver.Clause> _normalClauses = getClausesForDecision();
        for (Clause clause: _normalClauses) {
            solver.addClause(createSat4jVecInt(clause));
        }
        for (CardinalityClauseWrapper cardinalityClauseWrapper: cardinalityClauses) {
            VecInt sat4jClause = createSat4jVecInt(cardinalityClauseWrapper.clause);
            if (cardinalityClauseWrapper.isExactly()) {
                solver.addExactly(sat4jClause, cardinalityClauseWrapper.minimum);
            } else if (cardinalityClauseWrapper.isBound()) {
                solver.addAtLeast(sat4jClause, cardinalityClauseWrapper.minimum);
                solver.addAtMost(sat4jClause, cardinalityClauseWrapper.maximum);
            } else if (cardinalityClauseWrapper.isAtLeast()) {
                solver.addAtLeast(sat4jClause, cardinalityClauseWrapper.minimum);
            } else if (cardinalityClauseWrapper.isAtMost()) {
                solver.addAtMost(sat4jClause, cardinalityClauseWrapper.maximum);
            } else {
                solver.addClause(sat4jClause);
            }
        }
    }

    protected STATUS runSolver() {
        model = new Model();
        
        boolean solved;
        
        if (status == STATUS.UNSAT) {
            solved = false;
        } else {
            try {
                solved = solver.isSatisfiable();
            } catch (TimeoutException e) {
                if (status == STATUS.ABORTED) {
                    messages.add("Aborted");
                    return STATUS.ABORTED;
                } else {
                    messages.add(e.getMessage());
                    return STATUS.UNDECIDED;
                }
            }
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
        return DEFAULT_TIMEOUT;
    }

    public ISolver getSat4jSolver() {
        if (solver == null) {
            solver = createSolver();
        }
        return solver;
    }
    
    protected abstract ISolver createSolver();
    
}
