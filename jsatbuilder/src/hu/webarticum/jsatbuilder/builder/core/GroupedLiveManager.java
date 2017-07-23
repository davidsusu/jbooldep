package hu.webarticum.jsatbuilder.builder.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupedLiveManager implements LiveManager {
    
    private final List<Set<Definition>> groups;

    public GroupedLiveManager(Definition[]... groups) {
        this.groups = new ArrayList<Set<Definition>>();
        for (Definition[] group: groups) {
            this.groups.add(new HashSet<Definition>(Arrays.asList(group)));
        }
    }
    
    public GroupedLiveManager(Collection<? extends Collection<Definition>> groups) {
        this.groups = new ArrayList<Set<Definition>>();
        for (Collection<Definition> group: groups) {
            this.groups.add(new HashSet<Definition>(group));
        }
    }
    
    @Override
    public void removeDefinition(Definition definition) {
        for (Set<Definition> group: groups) {
            group.remove(definition);
        }
    }

    @Override
    public boolean isLive() {
        for (Set<Definition> group: groups) {
            if (group.isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
