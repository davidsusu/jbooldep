package hu.webarticum.jsatbuilder.sat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Solver {

    // TODO: UNDECIDED
    public enum STATUS {INITIAL, RUNNING, ABORTED, SAT, UNSAT};

    public enum CLAUSE_PRIORITY {LOW, MEDIUM, HIGH};
    
    public void add(Clause clause);

    public void addUnique(Clause clause);

    public void addSpecial(Clause clause, Integer minimum, Integer maximum);

    public void addOptional(Clause clause);

    public void addOptional(Clause clause, CLAUSE_PRIORITY priority);

    public void shuffle();

    public boolean run();
    
    public STATUS getStatus();
    
    public Model getModel();
    
    public List<String> getMessages();
    
    public void close();
    
    public class Variable {
        
        protected String label;

        public Variable() {
            this.label = "Item."+System.identityHashCode(this);
        }

        public Variable(String label) {
            this.label = label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public String toString() {
            return getLabel();
        }
        
    }

    public class Literal {

        protected final Variable variable;
        
        protected final boolean positive;
        
        public Literal(Variable variable, boolean positive) {
            this.variable = variable;
            this.positive = positive;
        }

        public Variable getVariable() {
            return variable;
        }
        
        public boolean isPositive() {
            return positive;
        }
        
        public Literal getNegated() {
            return new Literal(variable, !positive);
        }

        @Override
        public String toString() {
            return (positive?"":"NOT ")+variable.getLabel();
        }
        
    }

    public class Clause {
        
        List<Literal> literals = new ArrayList<Literal>();

        public Clause(Literal... literals) {
            for (Literal literal: literals) {
                this.literals.add(literal);
            }
        }

        public Clause(Collection<Literal> literals) {
            for (Literal literal: literals) {
                this.literals.add(literal);
            }
        }
        
        public void addLiteral(Literal literal) {
            literals.add(literal);
        }
        
        public void removeVariable(Variable variable) {
            for (Iterator<Literal> iterator=literals.iterator(); iterator.hasNext(); ) {
                Literal literal = iterator.next();
                if (literal.getVariable()==variable) {
                    iterator.remove();
                }
            }
        }

        public List<Literal> getLiterals() {
            return literals;
        }

        public void shuffle() {
            Collections.shuffle(literals);
        }
        
        @Override
        public String toString() {
            return literals.toString();
        }
        
    }
    
    public class Model implements Iterable<Literal> {
        
        private Map<Variable, Boolean> variableValueMap = new HashMap<Variable, Boolean>();
        
        public void put(Literal literal) {
            put(literal.getVariable(), literal.isPositive());
        }

        public void put(Variable variable, boolean value) {
            variableValueMap.put(variable, value);
        }
        
        public boolean containsVariable(Variable variable) {
            return variableValueMap.containsKey(variable);
        }
        
        public boolean get(Variable variable) {
            Boolean result = variableValueMap.get(variable);
            if (result == null) {
                return false;
            }
            return result;
        }
        
        @Override
        public Iterator<Literal> iterator() {
            return new ModelIterator();
        }
        
        private class ModelIterator implements Iterator<Literal> {
            
            Iterator<Map.Entry<Variable, Boolean>> variableValueMapIterator;
            
            ModelIterator() {
                variableValueMapIterator = variableValueMap.entrySet().iterator();
            }

            @Override
            public boolean hasNext() {
                return variableValueMapIterator.hasNext();
            }

            @Override
            public Literal next() {
                Map.Entry<Variable, Boolean> entry = variableValueMapIterator.next();
                Variable variable = entry.getKey();
                boolean value = entry.getValue();
                return new Literal(variable, value);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

        }
        
    }
    
}
