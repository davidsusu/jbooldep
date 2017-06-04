package hu.webarticum.jsatbuilder.satbuilder;


public abstract class AbstractConstraint implements Constraint {
    
    private boolean removed;
    
    private boolean required;
    
    public AbstractConstraint(boolean required) {
        this.required = required;
    }
    
    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public void remove() throws CollapseException {
        unset();
        if (required) {
            throw new CollapseException(this);
        }
    }

    @Override
    public void unset() {
        removed = true;
        unlinkDependencies();
    }

    @Override
    public boolean isRemoved() {
        return removed;
    }
    
    abstract protected void unlinkDependencies();
    
}
