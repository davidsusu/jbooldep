package hu.webarticum.jsatbuilder.builder.core;



// TODO: optional constraints...
// TODO: unnecessary --> unset() ?

public interface Constraint extends Brick, Dependant, SolverFiller {
    
    public boolean isRequired();
    
    public void remove() throws CollapseException;
    
    public void unset();
    
}
