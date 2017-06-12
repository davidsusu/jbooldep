package hu.webarticum.jsatbuilder.builder.core;


public class Variable extends AbstractDefinition {
    
    final private String name;
    
    public Variable() {
        this.name = "" + System.identityHashCode(this);
    }

    public Variable(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Variable(" + name + ")";
    }
    
}
