package hu.webarticum.jsatbuilder.builder.core;


public abstract class AbstractHelper extends AbstractDefinition implements Helper {
    
    private final DependencyLinker dependencyLinker;
    
    public AbstractHelper() {
        dependencyLinker = new DependencyLinker(this);
    }

    @Override
    public AbstractHelper setLabel(String label) {
        return (AbstractHelper)super.setLabel(label);
    }
    
    @Override
    public void remove() throws CollapseException {
        try {
            super.remove();
        } catch (CollapseException e) {
            throw e;
        } finally {
            dependencyLinker.unlinkDependencies();
        }
    }
    
    @Override
    public DependencyLinker getDependencyLinker() {
        return dependencyLinker;
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
    
    abstract protected void freeDefinition(Definition definition);
    
}
