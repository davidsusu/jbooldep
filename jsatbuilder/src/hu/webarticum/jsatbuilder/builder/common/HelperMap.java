package hu.webarticum.jsatbuilder.builder.common;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import hu.webarticum.jsatbuilder.builder.core.CollapseException;
import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.Helper;
import hu.webarticum.jsatbuilder.builder.core.RemovalListener;

public class HelperMap<T> implements Map<T, Helper> {

    private final Map<T, Helper> innerMap;
    
    private final HelperRemovalListener removalListener = new HelperRemovalListener();
    
    public HelperMap() {
        this(false);
    }

    public HelperMap(boolean navigable) {
        if (navigable) {
            innerMap = new TreeMap<T, Helper>();
        } else {
            innerMap = new HashMap<T, Helper>();
        }
    }

    public HelperMap(Comparator<T> comparator) {
        innerMap = new TreeMap<T, Helper>(comparator);
    }

    @Override
    public int size() {
        return innerMap.size();
    }

    @Override
    public boolean isEmpty() {
        return innerMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return innerMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return innerMap.containsValue(value);
    }

    @Override
    public Helper get(Object key) {
        return innerMap.get(key);
    }

    @Override
    public Helper put(T key, Helper value) {
        if (!register(key, value)) {
            return value;
        }
        
        Helper existing = innerMap.put(key, value);
        
        if (existing != null) {
            existing.removeRemovalListener(removalListener);
        }
        
        return existing;
    }

    @Override
    public Helper remove(Object key) {
        return innerMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends T, ? extends Helper> m) {
        for (Map.Entry<? extends T, ? extends Helper> entry: m.entrySet()) {
            innerMap.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        innerMap.clear();
    }

    @Override
    public Set<T> keySet() {
        return innerMap.keySet();
    }

    @Override
    public Collection<Helper> values() {
        return innerMap.values();
    }

    @Override
    public Set<Entry<T, Helper>> entrySet() {
        return innerMap.entrySet();
    }
    
    @Override
    public String toString() {
        return innerMap.toString();
    }

    @Override
    public boolean equals(Object other) {
        return innerMap.equals(other);
    }

    @Override
    public int hashCode() {
        return innerMap.hashCode();
    }

    private boolean register(T key, Helper helper) {
        if (helper.isRemoved()) {
            return false;
        }
        
        helper.addRemovalListener(removalListener);
        return true;
    }
    
    private class HelperRemovalListener implements RemovalListener {

        @Override
        public void definitionRemoved(Definition definition) throws CollapseException {
            innerMap.remove(definition);
        }
        
    }
    
}
