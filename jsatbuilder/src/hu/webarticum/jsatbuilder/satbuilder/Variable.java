package hu.webarticum.jsatbuilder.satbuilder;

import java.util.LinkedList;
import java.util.List;

/*

Util.transformToCnf(expression[, variable])
--> http://fmv.jku.at/limboole/
--> https://github.com/dbasedow/aima-propositional-logic/blob/master/aima-core/src/main/java/aima/core/logic/propositional/visitors/ConvertToCNF.java

*/

public class Variable {
    
	private List<RemovalListener> removalListeners = new LinkedList<RemovalListener>();
	
	public void remove() throws CollapseException {
		for (RemovalListener removalListener: removalListeners) {
			removalListener.variableRemoved(this);
		}
	}
	
	public void addRemovalListener(RemovalListener removalListener) {
		removalListeners.add(removalListener);
	}
	
}
