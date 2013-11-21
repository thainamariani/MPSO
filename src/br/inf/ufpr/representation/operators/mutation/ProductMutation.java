/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufpr.representation.operators.mutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jmetal.core.Solution;
import jmetal.operators.mutation.Mutation;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;
import br.inf.ufpr.pojo.Product;
import br.inf.ufpr.representation.solution.ProductArraySolutionType;
import br.inf.ufpr.representation.variable.ProductVariable;

/**
 *
 * @author giovaniguizzo
 */
public class ProductMutation extends Mutation {

    private Double crossoverProbability = 0D;

    public ProductMutation(HashMap<String, Object> parameters) {
        super(parameters);
        if (parameters.get("probability") != null) {
            this.crossoverProbability = (Double) parameters.get("probability");
        }
    }

    @Override
    public Object execute(Object object) throws JMException {
        Solution solution = (Solution) object;
        if (!addVariableMutation(solution, crossoverProbability)) {
            if (!changeVariableMutation(solution, crossoverProbability)) {
                removeVariableMutation(solution, crossoverProbability);
            }
        }
        return solution;
    }

    public boolean removeVariableMutation(Solution solution, Double probability) {
        if (PseudoRandom.randDouble() < probability) {
            ProductVariable[] decisionVariables = (ProductVariable[]) solution.getDecisionVariables();
            if (decisionVariables.length > 1) {
                int index = PseudoRandom.randInt(0, decisionVariables.length - 1);
                ProductVariable[] newVariables = new ProductVariable[decisionVariables.length - 1];
                for (int i = 0, j = 0; j < newVariables.length; i++, j++) {
                    if (index == i) {
                        j--;
                    } else {
                        newVariables[j] = (ProductVariable) decisionVariables[i].deepCopy();
                    }
                }
                solution.setDecisionVariables(newVariables);
                return true;
            }
        }
        return false;
    }

    public boolean addVariableMutation(Solution solution, Double probability) {
        if (PseudoRandom.randDouble() < probability) {
            ProductVariable[] decisionVariables = (ProductVariable[]) solution.getDecisionVariables();
            ProductArraySolutionType solutionType = (ProductArraySolutionType) solution.getType();
            if (decisionVariables.length < solutionType.getUpperBound()) {
                ProductVariable[] newVariables = Arrays.copyOf(decisionVariables, decisionVariables.length + 1);
                List<Product> excluded = new ArrayList<>();
                for (ProductVariable ProductVariable : decisionVariables) {
                    excluded.add(ProductVariable.getProduct());
                }
                newVariables[newVariables.length - 1] = new ProductVariable(solutionType.getUpperBound(), solutionType.getLowerBound(), solutionType.getProducts(), excluded);
                solution.setDecisionVariables(newVariables);
                return true;
            }
        }
        return false;
    }

    public boolean changeVariableMutation(Solution solution, Double probability) {
        if (PseudoRandom.randDouble() < probability) {
            ProductVariable[] decisionVariables = (ProductVariable[]) solution.getDecisionVariables();
            ProductArraySolutionType solutionType = (ProductArraySolutionType) solution.getType();
            if (decisionVariables.length < solutionType.getUpperBound()) {
                List<Product> excluded = new ArrayList<>();
                for (ProductVariable ProductVariable : decisionVariables) {
                    excluded.add(ProductVariable.getProduct());
                }
                int index = PseudoRandom.randInt(0, decisionVariables.length - 1);
                decisionVariables = Arrays.copyOf(decisionVariables, decisionVariables.length);
                decisionVariables[index] = new ProductVariable(solutionType.getUpperBound(), solutionType.getLowerBound(), solutionType.getProducts(), excluded);
                solution.setDecisionVariables(decisionVariables);
                return true;
            }
        }
        return false;
    }
}
