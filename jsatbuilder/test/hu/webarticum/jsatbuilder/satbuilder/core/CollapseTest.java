package hu.webarticum.jsatbuilder.satbuilder.core;

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
            assertSame(constraint2, e.getConstraint());
        }

        assertTrue(variable1.isRemoved());
        assertTrue(variable2.isRemoved());
        assertFalse(variable3.isRemoved());
        assertTrue(constraint1.isRemoved());
        assertTrue(constraint2.isRemoved());
        
        variable3.remove();

        assertTrue(variable3.isRemoved());
    }

    public class TestConstraint extends AbstractConstraint {
        
        final Definition definition;
        
        final RemovalListener removalListener;
        
        public TestConstraint(Definition definition, boolean required) {
            super(required);
            this.definition = definition;
            definition.addRemovalListener(removalListener = new RemovalListener() {
                
                @Override
                public void definitionRemoved(Definition definition) throws CollapseException {
                    remove();
                }
                
            });
        }

        @Override
        public void fillSolver(Solver solver) {
        }

        @Override
        protected void unlinkDependencies() {
            definition.removeRemovalListener(removalListener);
        }
        
    }

}