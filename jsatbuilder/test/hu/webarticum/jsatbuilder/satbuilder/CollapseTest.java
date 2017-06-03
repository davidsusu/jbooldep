package hu.webarticum.jsatbuilder.satbuilder;

import static org.junit.Assert.*;

import org.junit.Test;

public class CollapseTest {

	@Test
	public void test() throws CollapseException {
		Variable variable1 = new Variable();
		Variable variable2 = new Variable();
		Variable variable3 = new Variable();

		new TestConstraint(variable1, false);
		new TestConstraint(variable2, true);
		
		
		variable1.remove();
		
		try {
			variable2.remove();
			fail();
		} catch (CollapseException e) {

		}
		
		variable3.remove();
	}

}
