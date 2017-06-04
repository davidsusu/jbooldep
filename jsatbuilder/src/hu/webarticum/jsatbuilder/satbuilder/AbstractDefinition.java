package hu.webarticum.jsatbuilder.satbuilder;

import java.util.LinkedList;
import java.util.List;

public class AbstractDefinition implements Definition {
    
    private boolean removed = false;
    
    private List<RemovalListener> removalListeners = new LinkedList<RemovalListener>();
    
    public void remove() throws CollapseException {
        removed = true;
        for (RemovalListener removalListener: removalListeners) {
            removalListener.definitionRemoved(this);
        }
    }

    @Override
    public void addRemovalListener(RemovalListener removalListener) {
        removalListeners.add(removalListener);
    }

    @Override
    public void removeRemovalListener(RemovalListener removalListener) {
        removalListeners.remove(removalListener);
    }

    @Override
    public boolean isRemoved() {
        return removed;
    }
    
}
