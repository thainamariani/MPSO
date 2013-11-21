/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufpr.representation.problem;

//import br.inf.ufpr.main.NSGAIIExperiment;
import br.inf.ufpr.pojo.Mutant;
import br.inf.ufpr.pojo.Product;
import br.inf.ufpr.pojo.ProductMutant;
import br.inf.ufpr.representation.solution.ProductArraySolutionType;
import br.inf.ufpr.representation.variable.ProductVariable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.util.JMException;

/**
 *
 * @author giovaniguizzo
 */
public class TestCaseMinimizationProblem extends Problem {

    private final List<Product> products;
    private final List<Mutant> mutants;

    public TestCaseMinimizationProblem(List<Product> products, List<Mutant> mutants) {
        this.products = products;
        this.mutants = mutants;
        numberOfObjectives_ = 2;
        numberOfConstraints_ = 0;
        numberOfVariables_ = products.size();
        lowerLimit_ = new double[numberOfVariables_];
        upperLimit_ = new double[numberOfVariables_]; 
        problemName_ = "Mutant Based Test Case Minimization";
        solutionType_ = new ProductArraySolutionType(this, products);
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Mutant> getMutants() {
        return mutants;
    }

    @Override
    public void evaluate(Solution solution) throws JMException {
    	
    	int var = 0;
    	
        ProductVariable[] decisionVariables = (ProductVariable[]) solution.getDecisionVariables();
        HashSet<Mutant> hash = new HashSet<>();
        for (ProductVariable productVariable : decisionVariables) {
        	if (productVariable.getProduct() != null) {
                Product product = productVariable.getProduct();
                for (ProductMutant productMutant : product.getProductMutantList()) {
                    if (productMutant.isKilled()) {
                        hash.add(productMutant.getMutant());
                    }
                }	
			}

        }
        
        for (int i = 0; i < decisionVariables.length; i++) {
        	if (decisionVariables[i].getProduct() != null) {
				var += 1;
			}
		}
        
        solution.setObjective(0, var);
        solution.setObjective(1, mutants.size() - hash.size());

//        System.out.println("Fitness: " + solution.getObjective(0) + " / " + solution.getObjective(1));
    }

    public void writeHypervolumeParetoFront(String filePath) {
        FileOutputStream fos = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);

            bw.write(this.getProducts().size() + " 0.0");
            bw.newLine();
            bw.write("0.0 " + this.getMutants().size());

            bw.flush();
            bw.close();
        } catch (IOException ex) {
            //Logger.getLogger(NSGAIIExperiment.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                //Logger.getLogger(NSGAIIExperiment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
