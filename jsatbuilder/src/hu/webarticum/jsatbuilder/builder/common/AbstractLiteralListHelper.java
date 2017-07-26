package hu.webarticum.jsatbuilder.builder.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.AbstractHelper;
import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.Viability;

public abstract class AbstractLiteralListHelper extends AbstractHelper {

    private final LiteralListManager literalListManager;
    
    private final Viability viability;
    
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
            getDependencyLinker().linkDependency(definition);
        }
        viability = createViability(definitions);
    }
    
    public LiteralListManager getLiteralListManager() {
        return literalListManager;
    }

    @Override
    public List<Definition> getDependencies() {
        return literalListManager.getDefinitions();
    }
    
    @Override
    public Viability getViability() {
        return viability;
    }

    @Override
    protected void freeDefinition(Definition definition) {
        literalListManager.dependencyRemoved(definition);
    }
    
    protected Viability createViability(List<Definition> definitions) {
        return new DefaultViability(definitions);
    }
    
}
