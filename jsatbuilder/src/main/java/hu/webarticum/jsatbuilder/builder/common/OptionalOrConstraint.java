package hu.webarticum.jsatbuilder.builder.common;

import java.util.Collection;

import hu.webarticum.jsatbuilder.solver.core.Solver;

public class OptionalOrConstraint extends AbstractLiteralListConstraint {

    private final boolean important;
    
    private final int weight;
    
    public OptionalOrConstraint(Collection<?> literalsOrDefinitions, int weight) {
        this(literalsOrDefinitions, false, weight);
    }

    public OptionalOrConstraint(Collection<?> literalsOrDefinitions, int weight, boolean important) {
        this(literalsOrDefinitions, important, weight);
    }

    protected OptionalOrConstraint(Collection<?> literalsOrDefinitions, boolean important, int weight) {
        super(true, literalsOrDefinitions);
        this.important = important;
        this.weight = weight;
    }
    
    public boolean isImportant() {
        return important;
    }
    
    public int getWeight() {
        return weight;
    }
    
    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause clause = new Solver.Clause();
        for (Solver.Literal solverLiteral: getLiteralListManager().getSolverLiterals()) {
            clause.addLiteral(solverLiteral);
        }
        if (important) {
            solver.addImportantOptional(clause, weight);
        } else {
            solver.addOptional(clause, weight);
        }
    }

    @Override
    public String getInfo() {
        return getClass().getSimpleName() + "(" + weight + ", " + important + ", [" + getLiteralListString() + "])";
    }
    
}
