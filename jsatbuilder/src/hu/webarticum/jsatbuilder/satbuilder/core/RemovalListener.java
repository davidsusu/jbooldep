package hu.webarticum.jsatbuilder.satbuilder.core;

public interface RemovalListener {
    
    public void definitionRemoved(Definition definition) throws CollapseException;
    
}
