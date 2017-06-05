package hu.webarticum.jsatbuilder.satbuilder.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hu.webarticum.jsatbuilder.sat.Solver;
import hu.webarticum.jsatbuilder.satbuilder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.satbuilder.core.CollapseException;
import hu.webarticum.jsatbuilder.satbuilder.core.Definition;

public class OneConstraint extends AbstractConstraint {
    
    private final List<Definition> definitions;

    public OneConstraint(Collection<Definition> definitions, boolean required) {
        super(required);
        this.definitions = new ArrayList<Definition>(definitions);
        for (Definition definition: definitions) {
            getDependencyManager().linkDependency(definition);
        }
    }
    
    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause clause = new Solver.Clause();
        for (Definition definition: definitions) {
            clause.addLiteral(new Solver.Literal(definition, true));
        }
        solver.addUnique(clause);
    }

    @Override
    public List<Definition> getDependencies() {
        return new ArrayList<Definition>(definitions);
    }

    @Override
    public void dependencyRemoved(Definition definition) throws CollapseException {
        definitions.remove(definition);
        if (definitions.isEmpty()) {
            remove();
        }
    }

}
