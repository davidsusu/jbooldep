package hu.webarticum.jsatbuilder.builder.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.Viability;

public class AtLeastViability implements Viability {

    private final Set<Definition> definitions;
    
    private final int minimumSize;

    public AtLeastViability(int minimumSize, Definition... definitions) {
        this(minimumSize, Arrays.asList(definitions));
    }
    
    public AtLeastViability(int minimumSize, Collection<Definition> definitions) {
        this.definitions = new HashSet<Definition>(definitions);
        this.minimumSize = minimumSize;
    }
    
    @Override
    public void removeDefinition(Definition definition) {
        definitions.remove(definition);
    }
    
    @Override
    public boolean isViable() {
        return definitions.size() >= minimumSize;
    }

}
