package hu.webarticum.jsatbuilder.builder.common;

import static org.junit.Assert.*;

import org.junit.Test;

import hu.webarticum.jsatbuilder.builder.core.CollapseException;
import hu.webarticum.jsatbuilder.builder.core.Helper;
import hu.webarticum.jsatbuilder.builder.core.Variable;


public class HelperMapTest {

    @Test
    public void test() throws CollapseException {
        Variable variable_1_1 = new Variable();
        Variable variable_1_2 = new Variable();
        Variable variable_1_3 = new Variable();
        Helper helper_1 = new AnyHelper(variable_1_1, variable_1_2, variable_1_3).setLabel("Helper.1");
        
        Variable variable_2_1 = new Variable();
        Variable variable_2_2 = new Variable();
        Variable variable_2_3 = new Variable();
        Helper helper_2 = new AnyHelper(variable_2_1, variable_2_2, variable_2_3).setLabel("Helper.2");
        
        HelperMap<Integer> helperMap = new HelperMap<Integer>();
        
        assertTrue(helperMap.isEmpty());
        
        helperMap.put(1, helper_1);
        helperMap.put(2, helper_2);
        
        assertEquals(2, helperMap.size());

        assertFalse(helper_1.isRemoved());
        assertFalse(helper_2.isRemoved());
        
        variable_1_2.remove();
        variable_2_2.remove();

        assertFalse(helper_1.isRemoved());
        assertFalse(helper_2.isRemoved());

        assertEquals(2, helperMap.size());
        
        variable_1_1.remove();
        variable_1_3.remove();
        
        assertTrue(helper_1.isRemoved());
        assertFalse(helper_2.isRemoved());
        
        assertEquals(1, helperMap.size());
        
        assertFalse(helperMap.containsKey(1));
        assertFalse(helperMap.containsValue(helper_1));
        assertTrue(helperMap.containsKey(2));
        assertTrue(helperMap.containsValue(helper_2));

        variable_2_1.remove();
        variable_2_3.remove();

        assertTrue(helper_1.isRemoved());
        assertTrue(helper_2.isRemoved());

        assertTrue(helperMap.isEmpty());
    }

}
