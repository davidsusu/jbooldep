package hu.webarticum.jsatbuilder.builder.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DefaultLiveManager implements LiveManager {

    private final Set<Definition> definitions;

    public DefaultLiveManager(Definition... definitions) {
        this(Arrays.asList(definitions));
    }
    
    public DefaultLiveManager(Collection<Definition> definitions) {
        this.definitions = new HashSet<Definition>(definitions);
    }
    
    @Override
    public void removeDefinition(Definition definition) {
        definitions.remove(definition);
    }
    
    @Override
    public boolean isLive() {
        return !definitions.isEmpty();
    }

}
