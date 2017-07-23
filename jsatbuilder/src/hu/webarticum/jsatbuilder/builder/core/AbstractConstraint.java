package hu.webarticum.jsatbuilder.builder.core;


public abstract class AbstractConstraint implements Constraint {
    
    private boolean removed;
    
    private boolean required;
    
    private String label = "AbstractConstraint";

    private final DependencyManager dependencyManager;
    
    public AbstractConstraint() {
        this(false);
    }
    
    public AbstractConstraint(boolean required) {
        this.required = required;
        dependencyManager = new DependencyManager(this);
    }
    
    public AbstractConstraint setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public AbstractConstraint setLabel(String label) {
        this.label = label;
        return this;
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
        dependencyManager.unlinkDependencies();
    }

    @Override
    public boolean isRemoved() {
        return removed;
    }

    @Override
    public DependencyManager getDependencyManager() {
        return dependencyManager;
    }
    
    @Override
    public String toString() {
        return label;
    }

    @Override
    public void dependencyRemoved(Definition definition) throws CollapseException {
        LiveManager liveManager = getLiveManager();
        liveManager.removeDefinition(definition);
        if (!liveManager.isLive()) {
            remove();
        }
        
        freeDefinition(definition);
    }
    
    abstract protected void freeDefinition(Definition definition);
    
}
