package hu.webarticum.jsatbuilder.builder.common;

import java.util.Collection;

public final class Util {
    
    private Util () {
    }
    
    public static Object getAnItem(Collection<?> collection) {
        if (collection.isEmpty()) {
            return null;
        } else {
            return collection.iterator().next();
        }
    }
    
}
