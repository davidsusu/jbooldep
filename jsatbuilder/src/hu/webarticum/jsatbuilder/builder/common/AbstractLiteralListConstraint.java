package hu.webarticum.jsatbuilder.builder.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.builder.core.CollapseException;
import hu.webarticum.jsatbuilder.builder.core.Definition;

public abstract class AbstractLiteralListConstraint extends AbstractConstraint {
    
    private final LiteralListManager literalListManager;
    
    public AbstractLiteralListConstraint(boolean required, Definition... definitions) {
        this(required, Arrays.asList(definitions));
    }
    
    public AbstractLiteralListConstraint(boolean required, DefinitionLiteral... literals) {
        this(required, Arrays.asList(literals));
    }
    
    public AbstractLiteralListConstraint(boolean required, Collection<?> literalsOrDefinitions) {
        super(required);
        literalListManager = new LiteralListManager(literalsOrDefinitions);
        for (Definition definition: literalListManager.getDefinitions()) {
            getDependencyManager().linkDependency(definition);
        }
    }
    
    public LiteralListManager getLiteralListManager() {
        return literalListManager;
    }

    @Override
    public List<Definition> getDependencies() {
        return literalListManager.getDefinitions();
    }

    @Override
    public void dependencyRemoved(Definition definition) throws CollapseException {
        literalListManager.dependencyRemoved(definition);
        if (literalListManager.isEmpty()) {
            remove();
        }
    }
    
}
