package hu.webarticum.jsatbuilder.builder.core;


public abstract class AbstractConstraint implements Constraint {
    
    private boolean removed;
    
    private boolean required;
    
    private String label = null;

    private final DependencyLinker dependencyLinker;
    
    public AbstractConstraint() {
        this(false);
    }
    
    public AbstractConstraint(boolean required) {
        this.required = required;
        dependencyLinker = new DependencyLinker(this);
    }
    
    public AbstractConstraint setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public AbstractConstraint setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getLabel() {
        return label;
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
        dependencyLinker.unlinkDependencies();
    }

    @Override
    public boolean isRemoved() {
        return removed;
    }

    @Override
    public DependencyLinker getDependencyLinker() {
        return dependencyLinker;
    }
    
    public String getInfo() {
        return getClass().getSimpleName();
    }
    
    @Override
    public String toString() {
        return label == null ? getInfo() : label;
    }

    @Override
    public void dependencyRemoved(Definition definition) throws CollapseException {
        Viability viability = getViability();
        viability.removeDefinition(definition);
        if (!viability.isViable()) {
            remove();
        }
        
        freeDefinition(definition);
    }
    
    protected abstract void freeDefinition(Definition definition);
    
}
