package hu.webarticum.jsatbuilder.builder.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ListIterator;

import hu.webarticum.jsatbuilder.builder.core.Definition;

public class DefinitionLiteralList extends ArrayList<DefinitionLiteral> {
    
    private static final long serialVersionUID = 1L;

    public DefinitionLiteralList() {
        super();
    }
    
    public DefinitionLiteralList(Definition... definitions) {
        this(Arrays.asList(definitions));
    }

    public DefinitionLiteralList(DefinitionLiteral... literals) {
        this(Arrays.asList(literals));
    }

    public DefinitionLiteralList(Collection<?> literalsOrDefinitions) {
        super(new LiteralListManager(literalsOrDefinitions).getLiterals());
    }

    public DefinitionLiteralList append(Definition definition) {
        add(new DefinitionLiteral(definition, true));
        return this;
    }

    public DefinitionLiteralList append(DefinitionLiteral literal) {
        add(literal);
        return this;
    }
    
    public DefinitionLiteralList setAll(boolean value) {
        ListIterator<DefinitionLiteral> iterator = listIterator();
        while (iterator.hasNext()) {
            DefinitionLiteral currentLiteral = iterator.next();
            if (currentLiteral.isPositive() != value) {
                iterator.set(new DefinitionLiteral(currentLiteral.getDefinition(), value));
            }
        }
        return this;
    }

    public DefinitionLiteralList negateAll() {
        ListIterator<DefinitionLiteral> iterator = listIterator();
        while (iterator.hasNext()) {
            DefinitionLiteral currentLiteral = iterator.next();
            iterator.set(new DefinitionLiteral(currentLiteral.getDefinition(), !currentLiteral.isPositive()));
        }
        return this;
    }
    
}
