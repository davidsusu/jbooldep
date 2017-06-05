package hu.webarticum.jsatbuilder.satbuilder.core;

import java.util.List;

public interface Dependant {
    
    public List<Definition> getDependencies();

    public void dependencyRemoved(Definition definition) throws CollapseException;

    public DependencyManager getDependencyManager();
    
}
