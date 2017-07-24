package hu.webarticum.jsatbuilder.builder.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.LiveManager;

public abstract class AbstractLiteralListConstraint extends AbstractConstraint {
    
    private final LiteralListManager literalListManager;

    private final LiveManager liveManager;
    
    public AbstractLiteralListConstraint(boolean required, Definition... definitions) {
        this(required, Arrays.asList(definitions));
    }
    
    public AbstractLiteralListConstraint(boolean required, DefinitionLiteral... literals) {
        this(required, Arrays.asList(literals));
    }
    
    public AbstractLiteralListConstraint(boolean required, Collection<?> literalsOrDefinitions) {
        super(required);
        literalListManager = new LiteralListManager(literalsOrDefinitions);
        List<Definition> definitions = literalListManager.getDefinitions();
        for (Definition definition: definitions) {
            getDependencyManager().linkDependency(definition);
        }
        liveManager = createLiveManager(definitions);
    }
    
    public LiteralListManager getLiteralListManager() {
        return literalListManager;
    }

    @Override
    public List<Definition> getDependencies() {
        return literalListManager.getDefinitions();
    }

    @Override
    public LiveManager getLiveManager() {
        return liveManager;
    }

    @Override
    protected void freeDefinition(Definition definition) {
        literalListManager.dependencyRemoved(definition);
    }

    protected LiveManager createLiveManager(List<Definition> definitions) {
        return new DefaultLiveManager(definitions);
    }
    
}
