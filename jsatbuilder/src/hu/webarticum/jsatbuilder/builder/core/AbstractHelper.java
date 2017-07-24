package hu.webarticum.jsatbuilder.builder.core;


public abstract class AbstractHelper extends AbstractDefinition implements Helper {
    
    private final DependencyManager dependencyManager;
    
    public AbstractHelper() {
        dependencyManager = new DependencyManager(this);
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
            dependencyManager.unlinkDependencies();
        }
    }
    
    @Override
    public DependencyManager getDependencyManager() {
        return dependencyManager;
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
