package hu.webarticum.jsatbuilder.satbuilder;

import hu.webarticum.jsatbuilder.sat.Solver;

public interface Constraint {
	
	public void fillSolver(Solver solver);
	
}
