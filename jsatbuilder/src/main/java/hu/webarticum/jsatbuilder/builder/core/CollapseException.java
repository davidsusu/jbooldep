
package hu.webarticum.jsatbuilder.builder.core;


public class CollapseException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    
    private final Constraint constraint;

    public CollapseException() {
        this(null, "Constraint set collapsed");
    }
    
    public CollapseException(Constraint constraint) {
        this(constraint, "Constraint collapsed: " + constraint);
    }

    public CollapseException(String message) {
        this(null, message);
    }
    
    public CollapseException(Constraint constraint, String message) {
        super(message);
        this.constraint = constraint;
    }
    
    public Constraint getConstraint() {
        return constraint;
    }
    
}
