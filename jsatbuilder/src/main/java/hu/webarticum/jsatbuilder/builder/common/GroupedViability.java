package hu.webarticum.jsatbuilder.builder.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.Viability;

public class GroupedViability implements Viability {
    
    private final List<Set<Definition>> groups;

    public GroupedViability(Definition[]... groups) {
        this.groups = new ArrayList<Set<Definition>>();
        for (Definition[] group: groups) {
            this.groups.add(new HashSet<Definition>(Arrays.asList(group)));
        }
    }
    
    public GroupedViability(Collection<? extends Collection<Definition>> groups) {
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
    public boolean isViable() {
        for (Set<Definition> group: groups) {
            if (group.isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
