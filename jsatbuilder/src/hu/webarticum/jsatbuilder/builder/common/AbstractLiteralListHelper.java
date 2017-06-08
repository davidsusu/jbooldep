package hu.webarticum.jsatbuilder.builder.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.AbstractHelper;
import hu.webarticum.jsatbuilder.builder.core.CollapseException;
import hu.webarticum.jsatbuilder.builder.core.Definition;

public abstract class AbstractLiteralListHelper extends AbstractHelper {

    private final LiteralListManager literalListManager;
    
    public AbstractLiteralListHelper(Definition... definitions) {
        this(Arrays.asList(definitions));
    }
    
    public AbstractLiteralListHelper(DefinitionLiteral... literals) {
        this(Arrays.asList(literals));
    }
    
    public AbstractLiteralListHelper(Collection<?> literalsOrDefinitions) {
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
