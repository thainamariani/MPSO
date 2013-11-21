/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufpr.representation.solution;

import br.inf.ufpr.pojo.Product;
import br.inf.ufpr.representation.variable.ProductVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import jmetal.core.Problem;
import jmetal.core.SolutionType;
import jmetal.core.Variable;
import jmetal.util.JMException;

public class ProductArraySolutionType extends SolutionType {

    private final Random random;
    private final List<Product> products;

    public ProductArraySolutionType(Problem problem, List<Product> products) {
        super(problem);
        random = new Random();
        this.products = products;
    }

    @Override
    public Variable[] createVariables() throws ClassNotFoundException {
        int numberOfVariables = random.nextInt(products.size()) + 1;
        ProductVariable[] variables = new ProductVariable[products.size()];
        List<Product> excludeProducts = new ArrayList<>();
        
    	ProductVariable falseProd = new ProductVariable(products.size() + 1);
        
        for (int var = 0; var < numberOfVariables; var++) {
            ProductVariable prod = new ProductVariable(products.size(), 0, products, excludeProducts);
				try {
					variables[(int)prod.getValue()] = prod;
				} catch (JMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

        }
        
        for (int var = 0; var < variables.length; var++){
        	if(variables[var]==null)
        		variables[var] = falseProd;
        }
        
        return variables;
    }

    public int getUpperBound() {
        return products.size();
    }
    
    public int getLowerBound() {
		return 0;
	}

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public ProductVariable[] copyVariables(Variable[] vars) {
        ProductVariable[] variables;

        ProductVariable falseProd = new ProductVariable(products.size() + 1);
        
        variables = new ProductVariable[vars.length];
        for (int var = 0; var < vars.length; var++) {
            variables[var] = (ProductVariable) vars[var].deepCopy();
        } // for

        return variables;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.products);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductArraySolutionType other = (ProductArraySolutionType) obj;
        if (!Objects.equals(this.products, other.products)) {
            return false;
        }
        return true;
    }
}
