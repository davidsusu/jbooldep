package hu.webarticum.jsatbuilder.builder.core;


public class CollapseException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    
    private final Constraint constraint;
    
    public CollapseException(Constraint constraint) {
        this.constraint = constraint;
    }
    
    public Constraint getConstraint() {
        return constraint;
    }
    
}
