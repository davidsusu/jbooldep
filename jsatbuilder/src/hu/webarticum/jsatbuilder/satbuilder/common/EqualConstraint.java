package hu.webarticum.jsatbuilder.satbuilder.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import hu.webarticum.jsatbuilder.sat.Solver;
import hu.webarticum.jsatbuilder.satbuilder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.satbuilder.core.CollapseException;
import hu.webarticum.jsatbuilder.satbuilder.core.Definition;

public class EqualConstraint extends AbstractConstraint {
    
    private final List<Definition> definitions;

    public EqualConstraint(Definition... definitions) {
        this(Arrays.asList(definitions));
    }
    
    public EqualConstraint(Collection<Definition> definitions) {
        super(false);
        this.definitions = new ArrayList<Definition>(definitions);
        for (Definition definition: definitions) {
            getDependencyManager().linkDependency(definition);
        }
    }

    @Override
    public void fillSolver(Solver solver) {
        int literalCount = definitions.size();
        if (literalCount > 1) {
            for (int i = 0; i < literalCount; i++) {
                int previousIndex = i == 0 ? literalCount - 1 : i - 1;
                Solver.Literal previousLiteral = new Solver.Literal(definitions.get(previousIndex), true);
                Solver.Literal currentLiteral = new Solver.Literal(definitions.get(i), true);
                solver.add(new Solver.Clause(previousLiteral, currentLiteral.getNegated()));
                solver.add(new Solver.Clause(previousLiteral.getNegated(), currentLiteral));
            }
        }
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
