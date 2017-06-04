package hu.webarticum.jsatbuilder.satbuilder;

public interface RemovalListener {
    
    public void definitionRemoved(Definition definitions) throws CollapseException;
    
}
