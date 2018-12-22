package hu.webarticum.jsatbuilder.builder.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

}
