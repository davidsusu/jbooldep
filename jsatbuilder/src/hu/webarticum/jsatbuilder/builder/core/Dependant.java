package hu.webarticum.jsatbuilder.builder.core;

import java.util.List;

public interface Dependant {
    
    public List<Definition> getDependencies();

    public void dependencyRemoved(Definition definition) throws CollapseException;

    public DependencyManager getDependencyManager();
    
    public LiveManager getLiveManager();
    
}
