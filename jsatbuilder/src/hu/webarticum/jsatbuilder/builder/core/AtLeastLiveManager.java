package hu.webarticum.jsatbuilder.builder.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AtLeastLiveManager implements LiveManager {

    private final Set<Definition> definitions;
    
    private final int minimumSize;

    public AtLeastLiveManager(int minimumSize, Definition... definitions) {
        this(minimumSize, Arrays.asList(definitions));
    }
    
    public AtLeastLiveManager(int minimumSize, Collection<Definition> definitions) {
        this.definitions = new HashSet<Definition>(definitions);
        this.minimumSize = minimumSize;
    }
    
    @Override
    public void removeDefinition(Definition definition) {
        definitions.remove(definition);
    }
    
    @Override
    public boolean isLive() {
        return definitions.size() >= minimumSize;
    }

}
