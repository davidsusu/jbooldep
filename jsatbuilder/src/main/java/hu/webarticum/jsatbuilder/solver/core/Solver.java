package hu.webarticum.jsatbuilder.solver.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Solver {

    public enum STATUS {INITIAL, RUNNING, ABORTED, UNDECIDED, SAT, UNSAT}

    public void add(Clause clause);

    public void addCardinality(Clause clause, int minimum);

    public void addCardinality(Clause clause, int minimum, int maximum);

    public void addOptional(Clause clause, int weight);

    public void addImportantOptional(Clause clause, int weight);

    public boolean run();
    
    public STATUS getStatus();
    
    public Model getModel();
    
    public List<String> getMessages();
    
    public void close();
    
    public class Literal {

        protected final Object variable;
        
        protected final boolean positive;
        
        public Literal(Object variable, boolean positive) {
            this.variable = variable;
            this.positive = positive;
        }

        public Object getVariable() {
            return variable;
        }
        
        public boolean isPositive() {
            return positive;
        }
        
        public Literal getNegated() {
            return new Literal(variable, !positive);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Literal)) {
                return false;
            }
            Literal otherLiteral = (Literal)obj;
            return otherLiteral.variable.equals(variable) && otherLiteral.positive == positive;
        }
        
        @Override
        public int hashCode() {
            return variable.hashCode() * (positive ? 1 : -1);
        }

        @Override
        public String toString() {
            return "'" + variable.toString() + "'=" + (positive ? "1" : "0");
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
        
        public void removeVariable(Object variable) {
            for (Iterator<Literal> iterator=literals.iterator(); iterator.hasNext(); ) {
                Literal literal = iterator.next();
                if (literal.getVariable() == variable) {
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
        public boolean equals(Object obj) {
            if (!(obj instanceof Clause)) {
                return false;
            }
            Clause otherClause = (Clause)obj;
            return literals.equals(otherClause.literals);
        }
        
        @Override
        public int hashCode() {
            return literals.hashCode();
        }
        
        @Override
        public String toString() {
            return literals.toString();
        }
        
    }
    
    public class Model implements Iterable<Literal> {
        
        private Map<Object, Boolean> variableValueMap = new HashMap<Object, Boolean>();
        
        public void put(Literal literal) {
            put(literal.getVariable(), literal.isPositive());
        }

        public void put(Object variable, boolean value) {
            variableValueMap.put(variable, value);
        }
        
        public boolean containsVariable(Object variable) {
            return variableValueMap.containsKey(variable);
        }
        
        public boolean get(Object variable) {
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
        
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Model)) {
                return false;
            }
            Model otherModel = (Model)obj;
            return variableValueMap.equals(otherModel.variableValueMap);
        }
        
        @Override
        public String toString() {
            return variableValueMap.toString();
        }
        
        private class ModelIterator implements Iterator<Literal> {
            
            Iterator<Map.Entry<Object, Boolean>> variableValueMapIterator;
            
            ModelIterator() {
                variableValueMapIterator = variableValueMap.entrySet().iterator();
            }

            @Override
            public boolean hasNext() {
                return variableValueMapIterator.hasNext();
            }

            @Override
            public Literal next() {
                Map.Entry<Object, Boolean> entry = variableValueMapIterator.next();
                Object variable = entry.getKey();
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
