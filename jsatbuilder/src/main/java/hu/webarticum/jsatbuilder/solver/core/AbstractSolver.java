package hu.webarticum.jsatbuilder.solver.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractSolver implements Solver {

    protected volatile STATUS status = STATUS.INITIAL;

    protected List<Object> variables = new ArrayList<Object>();

    protected List<Clause> normalClauses = new ArrayList<Clause>();

    protected List<SpecialClauseWrapper> specialClauseWrappers = new ArrayList<SpecialClauseWrapper>();

    protected List<Clause> lowPriorityOptionalClauses = new ArrayList<Clause>();

    protected List<Clause> mediumPriorityOptionalClauses = new ArrayList<Clause>();

    protected List<Clause> highPriorityOptionalClauses = new ArrayList<Clause>();

    protected Model model = new Model();
    
    protected List<String> messages = new ArrayList<String>();
    
    @Override
    public void add(Clause clause) {
        normalClauses.add(clause);
        registerClauseItems(clause);
    }

    @Override
    public void addUnique(Clause clause) {
        addSpecial(clause, 1, 1);
    }

    @Override
    public void addSpecial(Clause clause, Integer minimum, Integer maximum) {
        specialClauseWrappers.add(new SpecialClauseWrapper(clause, minimum, maximum));
        registerClauseItems(clause);
    }

    @Override
    public void addOptional(Clause clause) {
        addOptional(clause, CLAUSE_PRIORITY.MEDIUM);
    }

    @Override
    public void addOptional(Clause clause, CLAUSE_PRIORITY priority) {
        switch (priority) {
            case LOW:
                lowPriorityOptionalClauses.add(clause);
                break;
            case MEDIUM:
                mediumPriorityOptionalClauses.add(clause);
                break;
            case HIGH:
                highPriorityOptionalClauses.add(clause);
                break;
            default:
                return;
        }
        registerClauseItems(clause);
    }

    @Override
    public void shuffle() {
        Collections.shuffle(variables);
        
        Collections.shuffle(normalClauses);
        for (Clause clause: normalClauses) {
            clause.shuffle();
        }
        
        Collections.shuffle(specialClauseWrappers);
        for (SpecialClauseWrapper specialClauseWrapper: specialClauseWrappers) {
            specialClauseWrapper.clause.shuffle();
        }
        
        Collections.shuffle(lowPriorityOptionalClauses);
        for (Clause clause: lowPriorityOptionalClauses) {
            clause.shuffle();
        }
        
        Collections.shuffle(mediumPriorityOptionalClauses);
        for (Clause clause: mediumPriorityOptionalClauses) {
            clause.shuffle();
        }
        
        Collections.shuffle(highPriorityOptionalClauses);
        for (Clause clause: highPriorityOptionalClauses) {
            clause.shuffle();
        }
    }
    
    @Override
    public STATUS getStatus() {
        return status;
    }

    @Override
    public Model getModel() {
        return model;
    }
    
    @Override
    public List<String> getMessages() {
        return messages;
    }
    
    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder();
        
        resultBuilder.append("Solver with " + variables.size() + " item(s) {");
        resultBuilder.append(clausesToString("normal", normalClauses));
        resultBuilder.append("\n");
        resultBuilder.append(specialClauseWrappersToString("special", specialClauseWrappers));
        resultBuilder.append("\n");
        resultBuilder.append(clausesToString("low-priority", lowPriorityOptionalClauses));
        resultBuilder.append("\n");
        resultBuilder.append(clausesToString("medium-priority", mediumPriorityOptionalClauses));
        resultBuilder.append("\n");
        resultBuilder.append(clausesToString("high-priority", highPriorityOptionalClauses));
        
        return resultBuilder.toString();
    }

    protected String clausesToString(String name, List<Clause> clauses) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("\n    " + name + " {");
        for (Clause clause: clauses) {
            resultBuilder.append("\n        ");
            resultBuilder.append(clause.toString());
        }
        resultBuilder.append("\n    }");
        return resultBuilder.toString();
    }

    protected String specialClauseWrappersToString(String name, List<SpecialClauseWrapper> specialClauseWrappers) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("\n    " + name + " {");
        for (SpecialClauseWrapper specialClauseWrapper: specialClauseWrappers) {
            resultBuilder.append("\n        ");
            resultBuilder.append("[" + specialClauseWrapper.minimum + ", " + specialClauseWrapper.maximum + "]" + specialClauseWrapper.clause.toString());
        }
        resultBuilder.append("\n    }");
        return resultBuilder.toString();
    }
    
    protected void registerClauseItems(Clause clause) {
        for (Literal literal: clause.getLiterals()) {
            registerVariable(literal.getVariable());
        }
    }
    
    protected void registerVariable(Object variable) {
        if (!variables.contains(variable)) {
            variables.add(variable);
        }
    }
    
    protected String clauseToString(Clause clause) {
        StringBuilder resultBuilder = new StringBuilder("Clause( ");
        for (Literal literal: clause.getLiterals()) {
            Object variable = literal.getVariable();
            int variableIndex = variables.indexOf(variable) + 1;
            String sign = literal.isPositive() ? "" : "-";
            resultBuilder.append(sign + variableIndex + "; ");
        }
        resultBuilder.append(")");
        return resultBuilder.toString();
    }
    
    protected List<Clause> getNormalClausesForDecision() {
        ArrayList<Clause> clauses = new ArrayList<Clause>(normalClauses);
        clauses.addAll(highPriorityOptionalClauses);
        return clauses;
    }

    protected List<WeightedClauseWrapper> getWeightedClauseWrappers() {
        return getWeightedClauseWrappers(1, 32, 1024);
    }

    protected List<WeightedClauseWrapper> getWeightedClauseWrappers(int level1, int level2, int level3) {
        int lowPriorityWeight = level1;
        int mediumPriorityWeight = level1;
        int highPriorityWeight = level1;
        if (lowPriorityOptionalClauses.isEmpty()) {
            if (!mediumPriorityOptionalClauses.isEmpty()) {
                highPriorityWeight = level2;
            }
        } else {
            if (mediumPriorityOptionalClauses.isEmpty()) {
                highPriorityWeight = level2;
            } else {
                mediumPriorityWeight = level2;
                highPriorityWeight = level3;
            }
        }
        List<WeightedClauseWrapper> result = new ArrayList<WeightedClauseWrapper>();
        for (Clause clause: lowPriorityOptionalClauses) {
            result.add(new WeightedClauseWrapper(clause, lowPriorityWeight));
        }
        for (Clause clause: mediumPriorityOptionalClauses) {
            result.add(new WeightedClauseWrapper(clause, mediumPriorityWeight));
        }
        for (Clause clause: highPriorityOptionalClauses) {
            result.add(new WeightedClauseWrapper(clause, highPriorityWeight));
        }
        return result;
    }
    
    protected class SpecialClauseWrapper {
        
        public final Clause clause;
        
        public final Integer minimum;
        
        public final Integer maximum;
        
        public SpecialClauseWrapper(Clause clause, Integer minimum, Integer maximum) {
            this.clause = clause;
            this.minimum = minimum;
            this.maximum = maximum;
        }
        
    }
    
    protected class WeightedClauseWrapper {

        public final Clause clause;
        
        public final Integer weight;
        
        public WeightedClauseWrapper(Clause clause, Integer weight) {
            this.clause = clause;
            this.weight = weight;
        }
        
    }
    
}