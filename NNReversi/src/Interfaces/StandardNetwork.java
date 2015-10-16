package Interfaces;

import Matrix.Matrix;

public interface StandardNetwork {
	/**
	 * Returns a array of Matrix, which denotes the gradient for each Theta.
	 * X is a matrix of all train sets. Size: m * num of input units.
	 * Y is a matrix of all train sets. Size: m * num of output units.
	 * lambda is a variable for regularization.
	 * layreSizes contains all inforamtion of the size of each layer.
	 * @param initialThetas
	 * @param X
	 * @param Y
	 * @param lambda
	 * @param layerSizes
	 * @return
	 */
	
	/**
	 * Return the difference between the simulated gradient and calculated by network
	 * @param lambda
	 * @return
	 */
	public double checkGradient(double lambda);
	
	/**
	 * Using the input matrix to make prediction. Returns a Matrix of the values of the output layer
	 * @param input
	 * @return
	 * @throws Exception 
	 */
	public Matrix predict(Matrix input) throws Exception;

	/**
	 * Returns a array of Matrix, which denotes the gradient for each Theta.
	 * X is a matrix of all train sets. Size: m * num of input units.
	 * Y is a matrix of all train sets. Size: m * num of output units.
	 * Important: Y should already has been a logical array!!
	 * lambda is a variable for regularization.
	 * layreSizes contains all inforamtion of the size of each layer.
	 * @param initialThetas
	 * @param X
	 * @param Y
	 * @param lambda
	 * @param layerSizes
	 * @param num_Iterations
	 * @return
	 * @throws Exception 
	 */
	public Matrix[] calculateGradient(Matrix[] initialThetas, Matrix X, Matrix Y,
			double lambda, int[] layerSizes) throws Exception;

	double costFunction(Matrix[] initialThetas, Matrix X, Matrix Y,
			double lambda, int[] layerSizes) throws Exception; 
}
