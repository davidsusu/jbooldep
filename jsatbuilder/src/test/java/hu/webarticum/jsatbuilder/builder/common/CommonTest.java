package hu.webarticum.jsatbuilder.builder.common;

import static org.junit.Assert.*;

import org.junit.Test;

import hu.webarticum.jsatbuilder.builder.core.ConstraintSetSolverFiller;
import hu.webarticum.jsatbuilder.builder.core.Variable;
import hu.webarticum.jsatbuilder.solver.core.Solver;


public class CommonTest {

    @Test
    public void test1() throws Exception {
        ConstraintSetSolverFiller constraints = new ConstraintSetSolverFiller(true);

        Variable v1_1 = new Variable("1.1");
        Variable v1_2 = new Variable("1.2");
        Variable v1_3 = new Variable("1.3");
        Variable v2_1 = new Variable("2.1");
        Variable v2_2 = new Variable("2.2");

        constraints.add(new OneConstraint(v1_1, v1_2, v1_3));
        constraints.add(new OneConstraint(v2_1, v2_2));
        constraints.add(new OneConstraint(v1_1, v2_1));
        constraints.add(new OrConstraint(v1_2, v1_3));
        constraints.add(new OrConstraint(v1_2, v2_2));
        
        Solver solver = createSolverWith(constraints);
        
        if (!solver.run()) {
            fail("Solution not found!");
        }
        
        Solver.Model model = solver.getModel();
        
        if (model.get(v1_1)) {
            fail("Wrong solution (1.1 must be false)");
        }

        if (!model.get(v1_2)) {
            fail("Wrong solution (1.2 must be true)");
        }

        if (model.get(v1_3)) {
            fail("Wrong solution (1.3 must be false)");
        }

        if (!model.get(v2_1)) {
            fail("Wrong solution (2.1 must be true)");
        }

        if (model.get(v2_2)) {
            fail("Wrong solution (2.2 must be false)");
        }
    }

    @Test
    public void test2AIsTrue() throws Exception {
        doSimpleTest(true);
    }

    @Test
    public void test2AIsFalse() throws Exception {
        doSimpleTest(false);
    }
    
    private void doSimpleTest(boolean aIsTrue) throws Exception {
        Variable a = new Variable("a");
        Variable b = new Variable("b");
        Variable c = new Variable("c");
        Variable d = new Variable("d");
        
        ConstraintSetSolverFiller constraints = new ConstraintSetSolverFiller();
        constraints.add(new OrConstraint(a, b));
        constraints.add(new EqualConstraint(
            new DefinitionLiteral(c, true),
            new DefinitionLiteral(d, false))
        );
        constraints.add(new EqualConstraint(a, c));
        constraints.add(new EqualConstraint(b, d));
        constraints.add(new GeneralClauseSetConstraint(new DefinitionLiteral[][] {
            { new DefinitionLiteral(a, aIsTrue) }
        }));

        Solver solver = createSolverWith(constraints);
        
        if (!solver.run()) {
            fail("Model was not found");
        }
        
        Solver.Model model = solver.getModel();

        assertEquals(aIsTrue, model.get(a));
        assertEquals(!aIsTrue, model.get(b));
        assertEquals(aIsTrue, model.get(c));
        assertEquals(!aIsTrue, model.get(d));
    }
    
    private Solver createSolverWith(ConstraintSetSolverFiller constraints) throws Exception {
        String className = "hu.webarticum.jsatbuilder.solver.sat4j.LightSat4jSolver";
        Solver solver = (Solver)Class.forName(className).newInstance();
        constraints.fillSolver(solver);
        return solver;
    }
    
}
