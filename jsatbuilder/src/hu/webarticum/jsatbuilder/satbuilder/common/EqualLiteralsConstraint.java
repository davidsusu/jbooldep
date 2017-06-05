package hu.webarticum.jsatbuilder.satbuilder.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import hu.webarticum.jsatbuilder.sat.Solver;
import hu.webarticum.jsatbuilder.satbuilder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.satbuilder.core.CollapseException;
import hu.webarticum.jsatbuilder.satbuilder.core.Definition;

public class EqualLiteralsConstraint extends AbstractConstraint {
    
    private final List<DefinitionLiteral> literals;

    public EqualLiteralsConstraint(DefinitionLiteral... literals) {
        this(Arrays.asList(literals));
    }
    
    public EqualLiteralsConstraint(Collection<DefinitionLiteral> literals) {
        super(false);
        this.literals = new ArrayList<DefinitionLiteral>(literals);
    }

    @Override
    public void fillSolver(Solver solver) {
        int literalCount = literals.size();
        if (literalCount > 1) {
            for (int i = 0; i < literalCount; i++) {
                int previousIndex = i == 0 ? literalCount - 1 : i - 1;
                Solver.Literal previousLiteral = literals.get(previousIndex).toSolverLiteral();
                Solver.Literal currentLiteral = literals.get(i).toSolverLiteral();
                solver.add(new Solver.Clause(previousLiteral, currentLiteral.getNegated()));
                solver.add(new Solver.Clause(previousLiteral.getNegated(), currentLiteral));
            }
        }
    }

    @Override
    public List<Definition> getDependencies() {
        List<Definition> dependencies = new ArrayList<Definition>(literals.size());
        for (DefinitionLiteral literal: literals) {
            dependencies.add(literal.getDefinition());
        }
        return dependencies;
    }

    @Override
    public void dependencyRemoved(Definition definition) throws CollapseException {
        Iterator<DefinitionLiteral> literalIterator = literals.iterator();
        while (literalIterator.hasNext()) {
            DefinitionLiteral literal = literalIterator.next();
            if (literal.getDefinition() == definition) {
                literalIterator.remove();
                break;
            }
        }
        if (literals.isEmpty()) {
            remove();
        }
    }

}
