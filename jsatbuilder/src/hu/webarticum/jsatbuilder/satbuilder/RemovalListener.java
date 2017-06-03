package hu.webarticum.jsatbuilder.satbuilder;

public interface RemovalListener {
	
	public void variableRemoved(Variable variable) throws CollapseException;
	
}
