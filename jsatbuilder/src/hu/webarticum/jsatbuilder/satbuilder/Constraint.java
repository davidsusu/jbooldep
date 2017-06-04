package hu.webarticum.jsatbuilder.satbuilder;


public interface Constraint extends Brick, SolverFiller {
    
    public boolean isRequired();
    
    public void remove() throws CollapseException;
    
    public void unset();
    
}
