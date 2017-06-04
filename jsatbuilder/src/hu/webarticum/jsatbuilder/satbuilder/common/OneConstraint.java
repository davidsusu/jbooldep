package hu.webarticum.jsatbuilder.satbuilder.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hu.webarticum.jsatbuilder.sat.Solver;
import hu.webarticum.jsatbuilder.satbuilder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.satbuilder.core.CollapseException;
import hu.webarticum.jsatbuilder.satbuilder.core.Definition;
import hu.webarticum.jsatbuilder.satbuilder.core.RemovalListener;


public class OneConstraint extends AbstractConstraint {
    
    private final List<Definition> definitions;

    private final RemovalListener removalListener;
    
    public OneConstraint(Collection<Definition> definitions, boolean required) {
        super(required);
        this.definitions = new ArrayList<Definition>(definitions);
        removalListener = new RemovalListener() {
            
            @Override
            public void definitionRemoved(Definition definition) throws CollapseException {
                OneConstraint.this.definitions.remove(definition);
                definition.removeRemovalListener(removalListener);
                if (OneConstraint.this.definitions.isEmpty()) {
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
        Solver.Clause clause = new Solver.Clause();
        for (Definition definition: definitions) {
            clause.addLiteral(new Solver.Literal(definition, true));
        }
        solver.addUnique(clause);
    }

    @Override
    protected void unlinkDependencies() {
        for (Definition definition: definitions) {
            definition.removeRemovalListener(removalListener);
        }
    }

}
