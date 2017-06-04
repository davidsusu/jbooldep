package hu.webarticum.jsatbuilder.satbuilder.core;


public abstract class AbstractHelper extends AbstractDefinition implements Helper {
    
    @Override
    public void remove() throws CollapseException {
        try {
            super.remove();
        } catch (CollapseException e) {
            throw e;
        } finally {
            unlinkDependencies();
        }
    }
    
    protected abstract void unlinkDependencies();
    
}
