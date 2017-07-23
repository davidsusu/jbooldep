package hu.webarticum.jsatbuilder.builder.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.AbstractHelper;
import hu.webarticum.jsatbuilder.builder.core.DefaultLiveManager;
import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.LiveManager;

public abstract class AbstractLiteralListHelper extends AbstractHelper {

    private final LiteralListManager literalListManager;
    
    private final LiveManager liveManager;
    
    public AbstractLiteralListHelper(Definition... definitions) {
        this(Arrays.asList(definitions));
    }
    
    public AbstractLiteralListHelper(DefinitionLiteral... literals) {
        this(Arrays.asList(literals));
    }
    
    public AbstractLiteralListHelper(Collection<?> literalsOrDefinitions) {
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
