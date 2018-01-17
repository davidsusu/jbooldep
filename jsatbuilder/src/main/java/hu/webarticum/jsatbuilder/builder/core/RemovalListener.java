package hu.webarticum.jsatbuilder.builder.core;

public interface RemovalListener {
    
    public void definitionRemoved(Definition definition) throws CollapseException;
    
}
