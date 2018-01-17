package hu.webarticum.jsatbuilder.builder.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class LiteralListManager {
    
    private final List<DefinitionLiteral> literals;
    
    public <T> LiteralListManager(Collection<T> literalsOrDefinitions) {
        Object sampleItem = Util.getAnItem(literalsOrDefinitions);
        if (sampleItem instanceof Definition) {
            this.literals = new ArrayList<DefinitionLiteral>(literalsOrDefinitions.size());
            for (Object item: literalsOrDefinitions) {
                Definition definition = (Definition)item;
                this.literals.add(new DefinitionLiteral(definition, true));
            }
        } else if (sampleItem instanceof DefinitionLiteral) {
            @SuppressWarnings("unchecked")
            Collection<DefinitionLiteral> literals = (Collection<DefinitionLiteral>)literalsOrDefinitions;
            this.literals = new ArrayList<DefinitionLiteral>(literals);
        } else {
            this.literals = new ArrayList<DefinitionLiteral>();
        }
    }

    public List<DefinitionLiteral> getLiterals() {
        return new ArrayList<DefinitionLiteral>(literals);
    }

    public List<Solver.Literal> getSolverLiterals() {
        List<Solver.Literal> solverLiterals = new ArrayList<Solver.Literal>(literals.size());
        for (DefinitionLiteral literal: literals) {
            solverLiterals.add(literal.toSolverLiteral());
        }
        return solverLiterals;
    }

    public List<Definition> getDefinitions() {
        List<Definition> definitions = new ArrayList<Definition>(literals.size());
        for (DefinitionLiteral literal: literals) {
            definitions.add(literal.getDefinition());
        }
        return definitions;
    }
    
    public boolean isEmpty() {
        return literals.isEmpty();
    }
    
    public void dependencyRemoved(Definition definition) {
        Iterator<DefinitionLiteral> literalIterator = literals.iterator();
        while (literalIterator.hasNext()) {
            DefinitionLiteral literal = literalIterator.next();
            if (literal.getDefinition() == definition) {
                literalIterator.remove();
                break;
            }
        }
    }
    
}
