package hu.webarticum.jsatbuilder.solver.sat4j;

import org.sat4j.core.VecInt;
import org.sat4j.specs.ISolver;

public class WeightedSat4jSolver extends AbstractSat4jSolver {

    @Override
    protected void fillSolver() throws Exception {
        org.sat4j.maxsat.WeightedMaxSatDecorator weightedSolver = (org.sat4j.maxsat.WeightedMaxSatDecorator)solver;
        for (Clause clause: normalClauses) {
            weightedSolver.addHardClause(createSat4jVecInt(clause));
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
        for (WeightedClauseWrapper weightedClauseWrapper: optionalClauses) {
            weightedSolver.addSoftClause(weightedClauseWrapper.weight, createSat4jVecInt(weightedClauseWrapper.clause));
        }
        for (WeightedClauseWrapper weightedClauseWrapper: importantOptionalClauses) {
            weightedSolver.addSoftClause(weightedClauseWrapper.weight, createSat4jVecInt(weightedClauseWrapper.clause));
        }
    }
    
    @Override
    protected ISolver createSolver() {
        return new org.sat4j.maxsat.WeightedMaxSatDecorator(org.sat4j.pb.SolverFactory.newDefault());
    }
    
}
