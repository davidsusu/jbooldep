package hu.webarticum.jsatbuilder.builder.common;

import java.util.Collection;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class AnyHelper extends AbstractLiteralListHelper {

    public AnyHelper(Definition... definitions) {
        super(definitions);
    }
    
    public AnyHelper(DefinitionLiteral... literals) {
        super(literals);
    }
    
    public AnyHelper(Collection<?> literalsOrDefinitions) {
        super(literalsOrDefinitions);
    }

    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause backClause = new Solver.Clause();
        backClause.addLiteral(new Solver.Literal(this, true));
        
        for (Definition definition: getLiteralListManager().getDefinitions()) {
            Solver.Clause clause = new Solver.Clause();
            clause.addLiteral(new Solver.Literal(definition, false));
            clause.addLiteral(new Solver.Literal(this, true));
            solver.add(clause);
            
            backClause.addLiteral(new Solver.Literal(definition, true));
        }
        
        solver.add(backClause);
    }
    
}
