package hu.webarticum.jsatbuilder.satbuilder;

import static org.junit.Assert.*;

import org.junit.Test;

import hu.webarticum.jsatbuilder.sat.Solver;

public class CollapseTest {

    @Test
    public void test() throws CollapseException {
        Variable variable1 = new Variable();
        Variable variable2 = new Variable();
        Variable variable3 = new Variable();

        Constraint constraint1 = new TestConstraint(variable1, false);
        Constraint constraint2 = new TestConstraint(variable2, true);

        assertFalse(variable1.isRemoved());
        assertFalse(variable2.isRemoved());
        assertFalse(variable3.isRemoved());
        assertFalse(constraint1.isRemoved());
        assertFalse(constraint2.isRemoved());
        
        
        variable1.remove();

        assertTrue(variable1.isRemoved());
        assertFalse(variable2.isRemoved());
        assertFalse(variable3.isRemoved());
        assertTrue(constraint1.isRemoved());
        assertFalse(constraint2.isRemoved());
        
        try {
            variable2.remove();
            fail();
        } catch (CollapseException e) {

        }

        assertTrue(variable1.isRemoved());
        assertTrue(variable2.isRemoved());
        assertFalse(variable3.isRemoved());
        assertTrue(constraint1.isRemoved());
        assertTrue(constraint2.isRemoved());
        
        variable3.remove();

        assertTrue(variable3.isRemoved());
    }

    public class TestConstraint implements Constraint {
        
        private boolean removed = false;
    
        public TestConstraint(Definition definition, final boolean required) {
            definition.addRemovalListener(new RemovalListener() {
                
                @Override
                public void definitionRemoved(Definition definition) throws CollapseException {
                    removed = true;
                    if (required) {
                        throw new CollapseException();
                    }
                }
                
            });
        }
    
        @Override
        public void fillSolver(Solver solver) {
        }
        
        @Override
        public boolean isRemoved() {
            return removed;
        }
        
    }

}
