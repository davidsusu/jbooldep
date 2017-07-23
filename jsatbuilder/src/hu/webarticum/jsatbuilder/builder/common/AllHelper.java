package hu.webarticum.jsatbuilder.builder.common;

import java.util.Collection;

import hu.webarticum.jsatbuilder.builder.core.Definition;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class AllHelper extends AbstractLiteralListHelper {

    public AllHelper(Definition... definitions) {
        super(definitions);
    }
    
    public AllHelper(DefinitionLiteral... literals) {
        super(literals);
    }
    
    public AllHelper(Collection<?> literalsOrDefinitions) {
        super(literalsOrDefinitions);
    }

    @Override
    public void fillSolver(Solver solver) {
        Solver.Clause backClause = new Solver.Clause();
        backClause.addLiteral(new Solver.Literal(this, true));
        
        for (Solver.Literal literal: getLiteralListManager().getSolverLiterals()) {
            Solver.Clause clause = new Solver.Clause();
            clause.addLiteral(literal);
            clause.addLiteral(new Solver.Literal(this, false));
            solver.add(clause);
            
            backClause.addLiteral(literal.getNegated());
        }
        
        solver.add(backClause);
    }
    
}
