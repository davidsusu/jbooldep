package hu.webarticum.jsatbuilder.satbuilder.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hu.webarticum.jsatbuilder.sat.Solver;
import hu.webarticum.jsatbuilder.satbuilder.core.AbstractHelper;
import hu.webarticum.jsatbuilder.satbuilder.core.CollapseException;
import hu.webarticum.jsatbuilder.satbuilder.core.Definition;

public class AnyHelper extends AbstractHelper {

    
    private final List<Definition> definitions;

    public AnyHelper(Collection<Definition> definitions, boolean required) {
        this.definitions = new ArrayList<Definition>(definitions);
        for (Definition definition: definitions) {
            getDependencyManager().linkDependency(definition);
        }
    }
    
    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause backClause = new Solver.Clause();
        backClause.addLiteral(new Solver.Literal(this, true));
        
        for (Definition definition: definitions) {
            Solver.Clause clause = new Solver.Clause();
            clause.addLiteral(new Solver.Literal(definition, false));
            clause.addLiteral(new Solver.Literal(this, true));
            solver.add(clause);
            
            backClause.addLiteral(new Solver.Literal(definition, true));
        }
        
        solver.add(backClause);
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
