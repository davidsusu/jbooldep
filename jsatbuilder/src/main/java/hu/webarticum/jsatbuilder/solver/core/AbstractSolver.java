package hu.webarticum.jsatbuilder.solver.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractSolver implements Solver {

    protected volatile STATUS status = STATUS.INITIAL;

    protected List<Object> variables = new ArrayList<Object>();

    protected List<Clause> normalClauses = new ArrayList<Clause>();

    protected List<CardinalityClauseWrapper> cardinalityClauses = new ArrayList<CardinalityClauseWrapper>();

    protected List<WeightedClauseWrapper> optionalClauses = new ArrayList<WeightedClauseWrapper>();

    protected List<WeightedClauseWrapper> importantOptionalClauses = new ArrayList<WeightedClauseWrapper>();

    protected Model model = new Model();
    
    protected List<String> messages = new ArrayList<String>();
    
    @Override
    public void add(Clause clause) {
        normalClauses.add(clause);
        registerClauseItems(clause);
    }

    @Override
    public void addCardinality(Clause clause, int minimum) {
        cardinalityClauses.add(new CardinalityClauseWrapper(clause, minimum));
        registerClauseItems(clause);
    }

    @Override
    public void addCardinality(Clause clause, int minimum, int maximum) {
        cardinalityClauses.add(new CardinalityClauseWrapper(clause, minimum, maximum));
        registerClauseItems(clause);
    }

    @Override
    public void addOptional(Clause clause, int weight) {
        optionalClauses.add(new WeightedClauseWrapper(clause, weight));
        registerClauseItems(clause);
    }

    @Override
    public void addImportantOptional(Clause clause, int weight) {
        importantOptionalClauses.add(new WeightedClauseWrapper(clause, weight));
        registerClauseItems(clause);
    }

    public void shuffle() {
        Collections.shuffle(variables);
        
        Collections.shuffle(normalClauses);
        for (Clause clause: normalClauses) {
            clause.shuffle();
        }
        
        Collections.shuffle(cardinalityClauses);
        for (CardinalityClauseWrapper cardinalityClauseWrapper: cardinalityClauses) {
            cardinalityClauseWrapper.clause.shuffle();
        }

        Collections.shuffle(optionalClauses);
        for (WeightedClauseWrapper weightedClauseWrapper: optionalClauses) {
            weightedClauseWrapper.clause.shuffle();
        }

        Collections.shuffle(importantOptionalClauses);
        for (WeightedClauseWrapper weightedClauseWrapper: importantOptionalClauses) {
            weightedClauseWrapper.clause.shuffle();
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
        
        resultBuilder.append("Solver with " + variables.size() + " item(s)");
        
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
    
    protected List<Clause> getClausesForDecision() {
        ArrayList<Clause> clauses = new ArrayList<Clause>(normalClauses);
        for (WeightedClauseWrapper weightedClauseWrapper: importantOptionalClauses) {
            clauses.add(weightedClauseWrapper.clause);
        }
        return clauses;
    }

    protected class CardinalityClauseWrapper {
        
        public final Clause clause;
        
        public final boolean hasMaximum;
        
        public final int minimum;
        
        public final int maximum;

        public CardinalityClauseWrapper(Clause clause, int minimum) {
            this.clause = clause;
            this.hasMaximum = false;
            this.minimum = minimum;
            this.maximum = -1;
        }

        public CardinalityClauseWrapper(Clause clause, int minimum, int maximum) {
            this.clause = clause;
            this.hasMaximum = true;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        public boolean isAlways() {
            return (!hasMaximum && minimum == 0);
        }

        public boolean isAtLeast() {
            return !hasMaximum;
        }

        public boolean isAtMost() {
            return !hasMaximum;
        }

        public boolean isBound() {
            return (hasMaximum && minimum > 0);
        }

        public boolean isExactly() {
            return (hasMaximum && minimum == maximum);
        }

        public boolean isOne() {
            return (hasMaximum && minimum == 1 && maximum == 1);
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