package hu.webarticum.jsatbuilder.builder.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hu.webarticum.jsatbuilder.builder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.Viability;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class GeneralClauseSetConstraint extends AbstractConstraint {
    
    private final List<List<DefinitionLiteral>> clauses;
    
    private final Definition condition;
    
    private final Viability viability;

    public GeneralClauseSetConstraint(DefinitionLiteral[][] clauses) {
        this(true, clauses, null);
    }
    
    public GeneralClauseSetConstraint(boolean required, DefinitionLiteral[][] clauses) {
        this(required, clauses, null);
    }
    
    public GeneralClauseSetConstraint(boolean required, DefinitionLiteral[][] clauses, Definition condition) {
        super(required);
        this.clauses = new ArrayList<List<DefinitionLiteral>>();
        for (DefinitionLiteral[] clause: clauses) {
            this.clauses.add(new ArrayList<DefinitionLiteral>(Arrays.asList(clause)));
        }
        this.condition = condition;
        linkDependencies();
        viability = createViability();
    }

    public GeneralClauseSetConstraint(Collection<? extends Collection<DefinitionLiteral>> clauses) {
        this(true, clauses, null);
    }
    
    public GeneralClauseSetConstraint(boolean required, Collection<? extends Collection<DefinitionLiteral>> clauses) {
        this(required, clauses, null);
    }
    
    public GeneralClauseSetConstraint(boolean required, Collection<? extends Collection<DefinitionLiteral>> clauses, Definition condition) {
        super(required);
        this.clauses = new ArrayList<List<DefinitionLiteral>>();
        for (Collection<DefinitionLiteral> clause: clauses) {
            this.clauses.add(new ArrayList<DefinitionLiteral>(clause));
        }
        this.condition = condition;
        linkDependencies();
        viability = createViability();
    }
    
    private void linkDependencies() {
        for (Definition definition: getDependencies()) {
            getDependencyLinker().linkDependency(definition);
        }
    }

    @Override
    public List<Definition> getDependencies() {
        Map<Definition, Object> dependencies = new IdentityHashMap<Definition, Object>();
        for (List<DefinitionLiteral> clause: clauses) {
            for (DefinitionLiteral literal: clause) {
                dependencies.put(literal.getDefinition(), null);
            }
        }
        if (condition != null) {
            dependencies.put(condition, null);
        }
        return new ArrayList<Definition>(dependencies.keySet());
    }

    @Override
    public Viability getViability() {
        return viability;
    }

    @Override
    protected void freeDefinition(Definition definition) {
        Iterator<List<DefinitionLiteral>> outerIterator = clauses.iterator();
        while (outerIterator.hasNext()) {
            List<DefinitionLiteral> clause = outerIterator.next();
            Iterator<DefinitionLiteral> innerIterator = clause.iterator();
            while (innerIterator.hasNext()) {
                DefinitionLiteral literal = innerIterator.next();
                if (literal.getDefinition() == definition) {
                    innerIterator.remove();
                }
            }
            if (clause.isEmpty()) {
                outerIterator.remove();
            }
        }
    }
    
    @Override
    public void fillSolver(Solver solver) {
        for (List<DefinitionLiteral> clause: clauses) {
            Solver.Clause solverClause = new Solver.Clause();
            if (condition != null) {
                solverClause.addLiteral(new Solver.Literal(condition, false));
            }
            for (DefinitionLiteral literal: clause) {
                solverClause.addLiteral(literal.toSolverLiteral());
            }
            solver.add(solverClause);
        }
    }
    
    protected Viability createViability() {
        List<Set<Definition>> groups = new ArrayList<Set<Definition>>();
        for (List<DefinitionLiteral> clause: clauses) {
            Set<Definition> definitions = new HashSet<Definition>();
            for (DefinitionLiteral literal: clause) {
                definitions.add(literal.getDefinition());
            }
            groups.add(definitions);
        }
        return new GroupedViability(groups);
    }
    
}
