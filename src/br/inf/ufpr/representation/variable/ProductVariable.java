/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufpr.representation.variable;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import jmetal.core.Problem;
import jmetal.core.Variable;
import jmetal.util.JMException;
import br.inf.ufpr.pojo.Product;

/**
 *
 * @author giovaniguizzo
 */
public class ProductVariable extends Variable implements Comparable<ProductVariable> {

	private static final long serialVersionUID = 6759265998068662186L;
	
	private Product product = null;
    private double upperBound;
    private double lowerBound;
    private int size_;
    private Double[] array_;
    private Problem problem_;
    private double value;
    

    public double getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}

	public ProductVariable() {
        product = new Product();
    }
	
	public ProductVariable(int produto){
		this.upperBound = 0;
        this.lowerBound = 0;
        //product.setId(produto);
	}

    public ProductVariable(double upperBound, double lowerBound, List<Product> products, List<Product> excludedProducts) {
    	this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        Random random = new Random();
        while (product == null) {
            int nextInt = random.nextInt((int) upperBound);
            Product get = products.get(nextInt);
            product = excludedProducts.contains(get) ? null : get;
        }
        excludedProducts.add(product);
        size_ = products.size();
    }
    
    public ProductVariable(List<Product> products){
        this.array_ = new Double [size_];
        for (int i = 0; i < products.size(); i++) {
			this.array_[i] = (double) products.get(i).getId();
		}	
    }
    
//    private ProductVariable(ProductVariable productVariable) {
//        problem_ = productVariable.problem_;
//        size_ = productVariable.size_;
//        array_ = new Double[size_];
//
//        System.arraycopy(productVariable.array_, 0, array_, 0, size_);
//      } // Copy Constructor
    
    public int getLength() {
        return size_;
      }

    public Product getProduct() {
        return product;
    }
    
    

    public Double[] getArray_() {
		return array_;
	}

	protected void setProduct(Product product) {
        this.product = product;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
    }

    @Override
    public ProductVariable deepCopy() {
        ProductVariable productVariable = new ProductVariable();
        productVariable.setUpperBound(this.getUpperBound());
        productVariable.setLowerBound(this.getLowerBound());
        if (this.getProduct() != null) {
        	productVariable.setProduct(new Product(this.getProduct()));
		} else {
			productVariable.setProduct(null);	
		}
        
        return productVariable;
    }

    @Override
    public int compareTo(ProductVariable o) {
        if (o == null) {
            return -1;
        } else if (this.getProduct() == null && o.getProduct() == null) {
            return 0;
        } else if (o.getProduct() == null) {
            return -1;
        } else if (this.getProduct() == null) {
            return 1;
        } else {
            return Long.compare(this.getProduct().getId(), o.getProduct().getId());
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.product);
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
        final ProductVariable other = (ProductVariable) obj;
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return true;
    }

    @Override
    public double getValue() throws JMException {
		try {
			return product.getId();
		} catch (Exception e) {
			return 0;
			// TODO: handle exception
		}
        
    }
    
    @Override
    public void setValue(double value) throws JMException {
    	this.value = value;
    }

    @Override
    public String toString() {
    	try {
    		return String.valueOf(product.getId());
		} catch (Exception e) {
			return "0";
			// TODO: handle exception
		}
    	
        
    }
}
