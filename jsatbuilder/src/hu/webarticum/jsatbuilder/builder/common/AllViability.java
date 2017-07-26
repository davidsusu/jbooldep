package hu.webarticum.jsatbuilder.builder.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.Viability;

public class AllViability implements Viability {

    private final Set<Definition> definitions;
    
    private final int size;

    public AllViability(Definition... definitions) {
        this(Arrays.asList(definitions));
    }
    
    public AllViability(Collection<Definition> definitions) {
        this.definitions = new HashSet<Definition>(definitions);
        this.size = this.definitions.size();
    }
    
    @Override
    public void removeDefinition(Definition definition) {
        definitions.remove(definition);
    }
    
    @Override
    public boolean isViable() {
        return definitions.size() == size;
    }

}
