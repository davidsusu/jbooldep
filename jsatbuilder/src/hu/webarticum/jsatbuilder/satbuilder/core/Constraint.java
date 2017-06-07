package hu.webarticum.jsatbuilder.satbuilder.core;



// TODO: optional constraints...

public interface Constraint extends Brick, Dependant, SolverFiller {
    
    public boolean isRequired();
    
    public void remove() throws CollapseException;
    
    public void unset();
    
}
