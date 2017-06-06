package hu.webarticum.jsatbuilder.satbuilder.common;

import java.util.Collection;

import hu.webarticum.jsatbuilder.sat.Solver;
import hu.webarticum.jsatbuilder.satbuilder.core.Definition;

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
