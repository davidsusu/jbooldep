package hu.webarticum.jsatbuilder.satbuilder.core;


public abstract class AbstractHelper extends AbstractDefinition implements Helper {
    
    private final DependencyManager dependencyManager;
    
    public AbstractHelper() {
        dependencyManager = new DependencyManager(this);
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
    
}
