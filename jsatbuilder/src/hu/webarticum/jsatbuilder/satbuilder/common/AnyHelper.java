package hu.webarticum.jsatbuilder.satbuilder.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hu.webarticum.jsatbuilder.sat.Solver;
import hu.webarticum.jsatbuilder.satbuilder.core.AbstractHelper;
import hu.webarticum.jsatbuilder.satbuilder.core.CollapseException;
import hu.webarticum.jsatbuilder.satbuilder.core.Definition;
import hu.webarticum.jsatbuilder.satbuilder.core.RemovalListener;

public class AnyHelper extends AbstractHelper {

    
    private final List<Definition> definitions;

    private final RemovalListener removalListener;
    
    public AnyHelper(Collection<Definition> definitions, boolean required) {
        this.definitions = new ArrayList<Definition>(definitions);
        removalListener = new RemovalListener() {
            
            @Override
            public void definitionRemoved(Definition definition) throws CollapseException {
                AnyHelper.this.definitions.remove(definition);
                definition.removeRemovalListener(removalListener);
                if (AnyHelper.this.definitions.isEmpty()) {
                    remove();
                }
            }
            
        };
        for (Definition definition: definitions) {
            definition.addRemovalListener(removalListener);
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
    protected void unlinkDependencies() {
        for (Definition definition: definitions) {
            definition.removeRemovalListener(removalListener);
        }
    }
    
}
