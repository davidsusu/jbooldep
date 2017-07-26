package hu.webarticum.jsatbuilder.builder.core;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import hu.webarticum.jsatbuilder.builder.common.DefaultLiveManager;
import hu.webarticum.jsatbuilder.solver.core.Solver;

public class CoreTest {

    @Test
    public void testCollapse() throws CollapseException {
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
        
        final LiveManager liveManager;
        
        public TestConstraint(Definition definition, boolean required) {
            super(required);
            this.definition = definition;
            getDependencyLinker().linkDependency(definition);
            liveManager = new DefaultLiveManager(definition);
        }

        @Override
        public void fillSolver(Solver solver) {
        }

        @Override
        public List<Definition> getDependencies() {
            return Arrays.asList(definition);
        }
        
        @Override
        public LiveManager getLiveManager() {
            return liveManager;
        }

        @Override
        protected void freeDefinition(Definition definition) {
            // nothing to do
        }

    }

}
