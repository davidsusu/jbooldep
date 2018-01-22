package hu.webarticum.jsatbuilder.builder.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import hu.webarticum.jsatbuilder.builder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.builder.core.Viability;

public abstract class AbstractLiteralListConstraint extends AbstractConstraint {
    
    private final LiteralListManager literalListManager;

    private final Viability viability;
    
    public AbstractLiteralListConstraint(boolean required, Definition... definitions) {
        this(required, Arrays.asList(definitions));
    }
    
    public AbstractLiteralListConstraint(boolean required, DefinitionLiteral... literals) {
        this(required, Arrays.asList(literals));
    }
    
    public AbstractLiteralListConstraint(boolean required, Collection<?> literalsOrDefinitions) {
        super(required);
        literalListManager = new LiteralListManager(literalsOrDefinitions);
        List<Definition> definitions = literalListManager.getDefinitions();
        for (Definition definition: definitions) {
            getDependencyLinker().linkDependency(definition);
        }
        viability = createViability(definitions);
    }
    
    public LiteralListManager getLiteralListManager() {
        return literalListManager;
    }

    @Override
    public List<Definition> getDependencies() {
        return literalListManager.getDefinitions();
    }

    @Override
    public Viability getViability() {
        return viability;
    }

    @Override
    protected void freeDefinition(Definition definition) {
        literalListManager.dependencyRemoved(definition);
    }

    protected Viability createViability(List<Definition> definitions) {
        return new DefaultViability(definitions);
    }

    public static String literalsListToString(List<DefinitionLiteral> definitionLiterals) {
        List<String> literalDescriptions = new ArrayList<String>();
        for (DefinitionLiteral literal: definitionLiterals) {
            String description = literal.getDefinition().toString();
            if (literal.isPositive()) {
                description = "NOT(" + description + ")";
            }
            literalDescriptions.add(description);
        }
        return String.join(", ", literalDescriptions.toArray(new String[literalDescriptions.size()]));
    }

    protected String getLiteralListString() {
        return literalsListToString(literalListManager.getLiterals());
    }
    
    @Override
    public String getInfo() {
        return getClass().getSimpleName() + "([" + getLiteralListString() + "])";
    }
    
}
