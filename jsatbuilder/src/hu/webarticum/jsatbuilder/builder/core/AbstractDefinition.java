package hu.webarticum.jsatbuilder.builder.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AbstractDefinition implements Definition {

    private String label = "";
    
    private boolean removed = false;
    
    private boolean iteratingRemovalListeners = false;
    
    private List<RemovalListener> removalListenersToRemove = null;
    
    private Collection<RemovalListener> removalListeners = new ArrayList<RemovalListener>(3);

    public AbstractDefinition setLabel(String label) {
        this.label = label;
        return this;
    }
    
    public void remove() throws CollapseException {
        removed = true;
        iteratingRemovalListeners = true;
        removalListenersToRemove = new ArrayList<RemovalListener>(3);
        for (RemovalListener removalListener: removalListeners) {
            removalListener.definitionRemoved(this);
        }
        iteratingRemovalListeners = false;
        removalListeners.removeAll(removalListenersToRemove);
        removalListenersToRemove = null;
    }
    
    @Override
    public void addRemovalListener(RemovalListener removalListener) {
        removalListeners.add(removalListener);
    }

    @Override
    public void removeRemovalListener(RemovalListener removalListener) {
        if (iteratingRemovalListeners) {
            removalListenersToRemove.add(removalListener);
        } else {
            removalListeners.remove(removalListener);
        }
    }

    @Override
    public boolean isRemoved() {
        return removed;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + label + ")";
    }
    
}
