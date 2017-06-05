package hu.webarticum.jsatbuilder.satbuilder.common;

import hu.webarticum.jsatbuilder.sat.Solver;
import hu.webarticum.jsatbuilder.satbuilder.core.Definition;

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
