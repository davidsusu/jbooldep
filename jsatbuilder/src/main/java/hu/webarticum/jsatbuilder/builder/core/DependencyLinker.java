package hu.webarticum.jsatbuilder.builder.core;


public class DependencyLinker {
    
    private final RemovalListener dependencyRemovalListener;
    
    private final Dependant dependant;
    
    public DependencyLinker(final Dependant dependant) {
        this.dependant = dependant;
        dependencyRemovalListener = new RemovalListener() {
            
            @Override
            public void definitionRemoved(Definition definition) throws CollapseException {
                unlinkDependency(definition);
                dependant.dependencyRemoved(definition);
            }
            
        };
    }

    public void linkDependency(Definition definition) {
        definition.addRemovalListener(dependencyRemovalListener);
    }
    
    public void unlinkDependency(Definition definition) {
        definition.removeRemovalListener(dependencyRemovalListener);
    }

    public void unlinkDependencies() {
        for (Definition definition: dependant.getDependencies()) {
            unlinkDependency(definition);
        }
    }

}
