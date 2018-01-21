package hu.webarticum.jsatbuilder.solver.sat4j;

import java.util.List;

import org.sat4j.specs.ISolver;

public class WeightedSat4jSolver extends AbstractSat4jSolver {

    @Override
    protected void fillSolver() throws Exception {
        org.sat4j.maxsat.WeightedMaxSatDecorator weightedSolver = (org.sat4j.maxsat.WeightedMaxSatDecorator)solver;
        for (Clause clause: normalClauses) {
            weightedSolver.addHardClause(createSat4jVecInt(clause));
        }
        for (SpecialClauseWrapper specialClauseWrapper: specialClauseWrappers) {
            if (specialClauseWrapper.minimum!=null && specialClauseWrapper.maximum!=null) {
                weightedSolver.addExactly(createSat4jVecInt(specialClauseWrapper.clause), specialClauseWrapper.minimum);
            } else if (specialClauseWrapper.minimum!=null) {
                weightedSolver.addAtLeast(createSat4jVecInt(specialClauseWrapper.clause), specialClauseWrapper.minimum);
            } else if (specialClauseWrapper.maximum!=null) {
                weightedSolver.addAtMost(createSat4jVecInt(specialClauseWrapper.clause), specialClauseWrapper.maximum);
            } else {
                weightedSolver.addClause(createSat4jVecInt(specialClauseWrapper.clause));
            }
        }
        List<WeightedClauseWrapper> weightedClauseWrappers = getWeightedClauseWrappers();
        for (WeightedClauseWrapper weightedClauseWrapper: weightedClauseWrappers) {
            weightedSolver.addSoftClause(weightedClauseWrapper.weight, createSat4jVecInt(weightedClauseWrapper.clause));
        }
    }
    
    @Override
    protected ISolver createSolver() {
        return new org.sat4j.maxsat.WeightedMaxSatDecorator(org.sat4j.pb.SolverFactory.newDefault());
    }
    
}
