package hu.webarticum.jsatbuilder.satbuilder;

import hu.webarticum.jsatbuilder.sat.Solver;

public class TestConstraint implements Constraint {
	
	public void fillSolver(Solver solver) {
	}
	
	public TestConstraint(Variable variable, final boolean required) {
		variable.addRemovalListener(new RemovalListener() {
			
			@Override
			public void variableRemoved(Variable variable) throws CollapseException {
				if (required) {
					throw new CollapseException();
				}
			}
			
		});
	}
	
}
