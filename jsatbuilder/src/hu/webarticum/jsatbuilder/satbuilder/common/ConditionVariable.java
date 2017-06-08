package hu.webarticum.jsatbuilder.satbuilder.common;

import java.util.Arrays;
import java.util.List;

import hu.webarticum.jsatbuilder.sat.Solver;
import hu.webarticum.jsatbuilder.satbuilder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.satbuilder.core.CollapseException;
import hu.webarticum.jsatbuilder.satbuilder.core.Constraint;
import hu.webarticum.jsatbuilder.satbuilder.core.Definition;
import hu.webarticum.jsatbuilder.satbuilder.core.Variable;


public class ConditionVariable extends Variable {
    
    private final InnerConstraint constraint;
    
    public ConditionVariable() {
        this(false);
    }

    public ConditionVariable(boolean conditionRequired) {
        this(conditionRequired, conditionRequired ? null : Solver.CLAUSE_PRIORITY.MEDIUM);
    }

    public ConditionVariable(Solver.CLAUSE_PRIORITY priority) {
        this(false, priority);
    }

    public ConditionVariable(boolean conditionRequired, Solver.CLAUSE_PRIORITY priority) {
        this.constraint = new InnerConstraint(conditionRequired, priority);
    }
    
    public Constraint getConstraint() {
        return constraint;
    }
    
    private class InnerConstraint extends AbstractConstraint {

        final Solver.CLAUSE_PRIORITY priority;
        
        public InnerConstraint(boolean required, Solver.CLAUSE_PRIORITY priority) {
            super(required);
            this.priority = priority;
            getDependencyManager().linkDependency(ConditionVariable.this);
        }

        @Override
        public List<Definition> getDependencies() {
            return Arrays.<Definition>asList(ConditionVariable.this);
        }

        @Override
        public void dependencyRemoved(Definition definition) throws CollapseException {
            remove();
        }

        @Override
        public void fillSolver(Solver solver) {
            Solver.Clause clause = new Solver.Clause(new Solver.Literal(ConditionVariable.this, true));
            if (priority == null) {
                solver.add(clause);
            } else {
                solver.addOptional(clause, priority);
            }
        }
        
    }
    
}
