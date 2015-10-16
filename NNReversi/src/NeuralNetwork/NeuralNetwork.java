
package NeuralNetwork;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

import Interfaces.StandardNetwork;
import Matrix.Dimension;
import Matrix.Matrix;


/**
 * This is a Neural Network implemented with logistic regression.
 * Note: for each layer, the activations will be stored in a 1 x n matrix ( n is the layer size).
 * When constructing a net work, using any one of the constructors,
 * the layer size should not include the biased terms.
 * The output of the net work will be the same size for output unit for each single input set.
 * The biased terms should always be the first ones of the layers.
 * @author Zonglin Li ������
 *
 */
public class NeuralNetwork implements StandardNetwork, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6841137415182793612L;
	private int [] layerSizes;
	private Matrix[] thetas;
	private Matrix[] a_Values;
	private String name;
	private double lambda;
	
	/**
	 * Construct a neural network with the designated layerSizes.
	 * The regularize parameter is 0.0 (default)
	 * Note: All the layers should not have included the biased terms.
	 * The first layer is input layer
	 * @param layerSizes
	 */
	public NeuralNetwork (int [] layerSizes){
		this.layerSizes = layerSizes;
		this.thetas = new Matrix[layerSizes.length - 1];
		this.a_Values = new Matrix[layerSizes.length];
		// Random initialize Thetas
		for(int i = 0; i< thetas.length; i++){
			double eInitial = Math.sqrt(6.0 / (layerSizes[i] + layerSizes[i + 1]));
			double[][] theta = new double[layerSizes[i] + 1][layerSizes[i + 1]];
			for(int j = 0; j < theta.length ; j++){
				for(int k = 0; k < theta[j].length; k++){
					theta[j][k] = (Math.random() * 2 - 1) * eInitial;
				}
			}
			thetas[i] = new Matrix(theta, "Theta_" + i);
		}
		lambda = 0.0;
	}
	/**
	 * Construct a neural network with the designated layerSizes.
	 * The regularize parameter is 0.0 (default)
	 * Note: All the layers should not have included the biased terms.
	 * The first layer is input layer
	 * @param layerSizes
	 * @param Name
	 */
	public NeuralNetwork (int [] layerSizes, String Name){
		this.layerSizes = layerSizes;
		this.a_Values = new Matrix[layerSizes.length];
		this.name = Name;
		thetas = new Matrix[layerSizes.length - 1];
		// Random initialize Thetas
		for(int i = 0; i< thetas.length; i++){
			double eInitial = Math.sqrt(6.0 / (layerSizes[i] + layerSizes[i + 1]));
			double[][] theta = new double[layerSizes[i] + 1][layerSizes[i + 1]];
			for(int j = 0; j < theta.length ; j++){
				for(int k = 0; k < theta[j].length; k++){
					theta[j][k] = (Math.random() * 2 - 1) * eInitial;
				}
			}
			thetas[i] = new Matrix(theta, "Theta_" + i);
			//For test
			System.out.println(thetas[i]);
		}
		lambda = 0.0;
	}
	
	/**
	 * Construct a neural network with the designated layerSizes.
	 * The regularize parameter is represented by the field Lambda
	 * Note: All the layers should not have included the biased terms.
	 * The first layer is input layer
	 * @param layerSizes
	 * @param Lambda
	 */
	public NeuralNetwork (int [] layerSizes, double Lambda){
		this.layerSizes = layerSizes;
		this.thetas = new Matrix[layerSizes.length - 1];
		this.a_Values = new Matrix[layerSizes.length];
		// Random initialize Thetas
		for(int i = 0; i< thetas.length; i++){
			double eInitial = Math.sqrt(6.0 / (layerSizes[i] + layerSizes[i + 1]));
			double[][] theta = new double[layerSizes[i] + 1][layerSizes[i + 1]];
			for(int j = 0; j < theta.length ; j++){
				for(int k = 0; k < theta[j].length; k++){
					theta[j][k] = (Math.random() * 2 - 1) * eInitial;
				}
			}
			thetas[i] = new Matrix(theta, "Theta_" + i);
			//For test
			System.out.println(thetas[i]);
		}
		lambda = Lambda;
	}
	
	/**
	 * Construct a neural network with the designated layerSizes.
	 * The regularize parameter is represented by the field Lambda
	 * Note: All the layers should not have included the biased terms.
	 * The first layer is input layer
	 * @param layerSizes
	 * @param Lambda
	 * @param Name
	 */
	public NeuralNetwork (int [] layerSizes, double Lambda, String Name){
		this.layerSizes = layerSizes;
		this.a_Values = new Matrix[layerSizes.length];
		this.name = Name;
		thetas = new Matrix[layerSizes.length - 1];
		// Random initialize Thetas
		for(int i = 0; i< thetas.length; i++){
			double eInitial = Math.sqrt(6.0 / (layerSizes[i] + layerSizes[i + 1]));
			double[][] theta = new double[layerSizes[i] + 1][layerSizes[i + 1]];
			for(int j = 0; j < theta.length ; j++){
				for(int k = 0; k < theta[j].length; k++){
					theta[j][k] = (Math.random() * 2 - 1) * eInitial;
				}
			}
			thetas[i] = new Matrix(theta, "Theta_" + i);
		}
		lambda = Lambda;
	}

	/**
	 * The sigmoid function. Return the result with the same name as x.
	 * @param x
	 * @return
	 */
	public Matrix sigmoid(Matrix x){
		double[][] elements = x.getElements();
		for(int r = 0; r < elements.length; r++){
			for(int c = 0; c < elements[r].length ; c++){
				elements[r][c] = 1.0 / (1.0 + Math.exp( - elements[r][c]));
			}
		}
		return new Matrix(elements, x.getName());
	}
	
	/**
	 * The sigmoid function. The result will have the name as designated in field "name".
	 * @param x
	 * @param name
	 * @return
	 */
	public Matrix sigmoid(Matrix x, String name){
		double[][] elements = x.getElements();
		for(int r = 0; r < elements.length; r++){
			for(int c = 0; c < elements[r].length ; c++){
				elements[r][c] = 1.0 / (1.0 + Math.exp( - elements[r][c]));
			}
		}
		return new Matrix(elements, name);
	}
	
	@Override
	public Matrix predict(Matrix X) throws Exception {
		// TODO Auto-generated method stub
		Dimension d = X.getDimension();
		int m = d.getNum_rows();
		int n = d.getNum_cols();
		Matrix[] a_Values = new Matrix[layerSizes.length];
		if(thetas.length != layerSizes.length - 1){
			throw new Exception("The dimension of initialThetas is incorrect  ~~inside calculateGradient");
		}
		/******************************************Forward Propagate******************************************/
		if(n != layerSizes[0]){
			throw new Exception("input matrix dimension of column does not match that of the input layer  ~~inside Neuralnetwork");
		}
		Matrix[] z_Values = new Matrix[a_Values.length];
		z_Values[0] = X;
		double[] biasedTerms = new double[m];
		Arrays.fill(biasedTerms, 1.0);
		for(int i = 0; i < thetas.length; i++){
			a_Values[i] = z_Values[i].addOneCol( 0, biasedTerms);
			a_Values[i].setName("a_"+i);
			z_Values[i + 1] = Matrix.times(a_Values[i], thetas[i], "a_"+(i + 1));
			z_Values[i + 1] = sigmoid(z_Values[i + 1]);
		}
		a_Values[a_Values.length - 1] = z_Values[a_Values.length - 1];
		return a_Values[a_Values.length - 1];
	}
	
	@Override
	public Matrix[] calculateGradient(Matrix[] initialThetas, Matrix X, Matrix Y, double lambda,
			int[] layerSizes) throws Exception {
		// TODO Auto-generated method stub
		Dimension d = X.getDimension();
		int m = d.getNum_rows();
		int n = d.getNum_cols();
		Matrix[] grad = new Matrix[layerSizes.length - 1];
		Matrix[] a_Values = new Matrix[layerSizes.length];
		if(initialThetas.length != layerSizes.length - 1){
			throw new Exception("The dimension of initialThetas is incorrect  ~~inside calculateGradient");
		}
		//check is Y a logical array
		for(double[] tp1 : Y.getElements()){
			for(double tp2 : tp1){
				if(tp2 == 0.0 || tp2 == 1.0){
					continue;
				}
				throw new Exception("Y is not a logical array  ~~inside calculateGradient");
			}
		}
		/******************************************Forward Propagate******************************************/
		if(n != layerSizes[0]){
			throw new Exception("input matrix dimension of column does not match that of the input layer  ~~inside Neuralnetwork");
		}
		Matrix[] z_Values = new Matrix[a_Values.length];
		z_Values[0] = X;
		double[] biasedTerms = new double[m];
		Arrays.fill(biasedTerms, 1.0);
		for(int i = 0; i < initialThetas.length; i++){
			a_Values[i] = z_Values[i].addOneCol( 0, biasedTerms);
			a_Values[i].setName("a_"+i);
			z_Values[i + 1] = Matrix.times(a_Values[i], initialThetas[i], "a_"+(i + 1));
			z_Values[i + 1] = sigmoid(z_Values[i + 1]);
		}
		a_Values[a_Values.length - 1] = z_Values[a_Values.length - 1];
		/****************************************End Forward Propagate****************************************/
		/********************************************Back Propagate*******************************************/
		Matrix[] d_Values = new Matrix[a_Values.length];
		Matrix[] D_Values = new Matrix[grad.length];
		d_Values[d_Values.length-1] = Matrix.minus(a_Values[a_Values.length - 1], Y, "d_" + (d_Values.length-1));
		for(int i = d_Values.length-2; i > 0; i--){
			d_Values[i] = Matrix.elementsMultiplication(Matrix.times(d_Values[i + 1], Matrix.transpose(initialThetas[i])), 
							Matrix.elementsMultiplication(a_Values[i], Matrix.minus(1.0, a_Values[i], "any one")), "d_" + i);
			d_Values[i].removeCol(0);
		}
		for(int i = 0; i < initialThetas.length; i++){
			D_Values[i] = Matrix.times(Matrix.transpose(a_Values[i]), d_Values[i + 1], "D_" + i);
		}
		/******************************************End Back Propagate*****************************************/
		/********************************************Regularization*******************************************/
		//Improvements needed!! Now there may be some waste of memory and calculation resources
		for(int l = 0; l < grad.length; l++){
			double[][] D_elements = D_Values[l].getElements();
			double[][] Theta_elements = initialThetas[l].getElements();
			for(int r = 1; r < D_elements.length; r++){
				for(int c = 0; c < D_elements[r].length; c++){
					D_elements[r][c] += lambda * Theta_elements[r][c];
				}
			}
			grad[l] = Matrix.dividedByaNumber(D_Values[l], m, "grad_" + l);
		}
		/******************************************End Regularization*****************************************/
		X.removeCol(0);
		return grad;
	}

	@Override
	public double costFunction(Matrix[] initialThetas, Matrix X, Matrix Y, double lambda,
			int[] layerSizes) throws Exception{
		Dimension d = X.getDimension();
		int m = d.getNum_rows();
		int n = d.getNum_cols();
		Matrix[] a_Values = new Matrix[layerSizes.length];
		if(initialThetas.length != layerSizes.length - 1){
			throw new Exception("The dimension of initialThetas is incorrect  ~~inside costFunction");
		}
		/******************************************Forward Propagate******************************************/
		if(n != layerSizes[0]){
			throw new Exception("input matrix dimension of column does not match that of the input layer  ~~inside Neuralnetwork");
		}
		Matrix[] z_Values = new Matrix[a_Values.length];
		z_Values[0] = X;
		double[] biasedTerms = new double[m];
		Arrays.fill(biasedTerms, 1.0);
		for(int i = 0; i < initialThetas.length; i++){
			a_Values[i] = z_Values[i].addOneCol(0, biasedTerms);
			a_Values[i].setName("a_" + i);
			z_Values[i + 1] = Matrix.times(a_Values[i], initialThetas[i], "a_"+(i + 1));
			z_Values[i + 1] = sigmoid(z_Values[i + 1]);
		}
		a_Values[a_Values.length - 1] = z_Values[a_Values.length - 1];
		/****************************************End Forward Propagate****************************************/
		/***************************************Cost without regularize***************************************/
		Matrix temp_MatrixJ = Matrix.plus(Matrix.elementsMultiplication(Y, Matrix.log(a_Values[a_Values.length - 1])), 
											Matrix.elementsMultiplication(Matrix.minus(1.0, Y, "Oh"), Matrix.log(Matrix.minus(1.0, a_Values[a_Values.length - 1], "Hi"))));
		
		double cost = 0 - temp_MatrixJ.sum()/m;
		temp_MatrixJ = null;
		double reg = 0.0;
		for(int i = 0; i < initialThetas.length; i++){
			Matrix temp = initialThetas[i];
			reg += Matrix.elementsMultiplication(temp, temp).sum();
			reg -= Matrix.elementsMultiplication(temp.getRow(0, "as you want"), temp.getRow(0, "as you want")).sum();
		}
		reg = reg * lambda / (2 * m);
		cost += reg;	
		X.removeCol(0);
		return cost;
	}
	
	/**
	 * Train the training set, and choose the number of iterations automatically. The one you input is only the 
	 * max value of iterations.
	 * When the difference of cost is less than criticalValue, then this method finishes.
	 * @param maxIterations
	 * @param X
	 * @param Y
	 * @param criticalValue
	 * @return
	 * @throws Exception
	 */
	public Matrix training(int maxIterations, Matrix X, Matrix Y, double criticalValue) throws Exception{
		double[][] costs = new double[maxIterations][1];
		for(int i = 1; i < maxIterations; i++){
			Matrix[] grads = calculateGradient(this.thetas, X, Y, this.lambda, layerSizes);
			costs[i][0] = this.costFunction(thetas, X, Y, this.lambda, layerSizes);
			if(i >= 2 && (costs[i - 1][0] - costs[i][0]) < criticalValue && (costs[i - 1][0] - costs[i][0]) > 0){
				break;
			}
			for(int j = 0; j < grads.length; j++){
				thetas[j] = Matrix.minus(thetas[j], grads[j], "theta_" + j);
			}
			System.out.println("iterations: "+ i + " | cost = " + costs[i][0]);
		}
		return new Matrix(costs, "costs");
	}
	
	/**
	 * Save the data of the weights learned to the file indicated by the String "location".
	 * @param location
	 * @param Thetas
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void savingWeights(String location, Matrix[] Thetas) throws FileNotFoundException, IOException{
		File f = new File(location);
		if(f.exists()){
			f.delete();
		}
		ObjectOutputStream out = new ObjectOutputStream(
				new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f))));
		out.writeObject(Thetas);
		out.close();
	}

	@Override
	public double checkGradient(double lambda) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return the layerSizes
	 */
	public int[] getLayerSizes() {
		return layerSizes;
	}

	/**
	 * @return the thetas
	 */
	public Matrix[] getThetas() {
		return thetas;
	}

	/**
	 * @return the a_Values
	 */
	public Matrix[] getA_Values() {
		return a_Values;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return "Name of this network is: " + name;
	}
}
