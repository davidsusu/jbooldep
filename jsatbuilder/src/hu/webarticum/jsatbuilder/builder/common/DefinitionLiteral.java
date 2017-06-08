package hu.webarticum.jsatbuilder.builder.common;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.solver.Solver;

public class DefinitionLiteral {
    
    private final Definition definition;
    
    private final boolean positive;
    
    public DefinitionLiteral(Definition definition, boolean positive) {
        this.definition = definition;
        this.positive = positive;

    }
    
    public Definition getDefinition() {
        return definition;
    }
    
    public boolean isPositive() {
        return positive;
    }
    
    public Solver.Literal toSolverLiteral() {
        return new Solver.Literal(definition, positive);
    }
    
}
