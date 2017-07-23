package hu.webarticum.jsatbuilder.builder.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AllLiveManager implements LiveManager {

    private final Set<Definition> definitions;
    
    private final int size;

    public AllLiveManager(Definition... definitions) {
        this(Arrays.asList(definitions));
    }
    
    public AllLiveManager(Collection<Definition> definitions) {
        this.definitions = new HashSet<Definition>(definitions);
        this.size = this.definitions.size();
    }
    
    @Override
    public void removeDefinition(Definition definition) {
        definitions.remove(definition);
    }
    
    @Override
    public boolean isLive() {
        return definitions.size() == size;
    }

}
