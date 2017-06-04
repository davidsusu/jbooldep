package hu.webarticum.jsatbuilder.satbuilder.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hu.webarticum.jsatbuilder.sat.Solver;
import hu.webarticum.jsatbuilder.satbuilder.core.AbstractConstraint;
import hu.webarticum.jsatbuilder.satbuilder.core.Definition;

public class EqualConstraint extends AbstractConstraint {
    
    private final List<Solver.Literal> literals;

    public EqualConstraint(Definition... definitions) {
        super(false);
        this.literals = new ArrayList<Solver.Literal>(definitions.length);
        for (Definition definition: definitions) {
            this.literals.add(new Solver.Literal(definition, true));
        }
    }

    public EqualConstraint(Solver.Literal... literals) {
        super(false);
        this.literals = new ArrayList<Solver.Literal>(Arrays.asList(literals));
    }

    @Override
    public void fillSolver(Solver solver) {
        int literalCount = literals.size();
        if (literalCount > 2) {
            for (int i = 0; i < literalCount; i++) {
                int previousIndex = i == 0 ? literalCount - 1 : i - 1;
                Solver.Literal previousLiteral = literals.get(previousIndex);
                Solver.Literal currentLiteral = literals.get(i);
                solver.add(new Solver.Clause(previousLiteral, currentLiteral.getNegated()));
                solver.add(new Solver.Clause(previousLiteral.getNegated(), currentLiteral));
            }
        }
    }

    @Override
    protected void unlinkDependencies() {
        // TODO Auto-generated method stub
        
    }
    
}
