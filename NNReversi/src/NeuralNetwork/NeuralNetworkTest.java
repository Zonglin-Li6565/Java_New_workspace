package NeuralNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import Matrix.Dimension;
import Matrix.Matrix;

public class NeuralNetworkTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		NeuralNetworkTest test = new NeuralNetworkTest();
		String location = "A:\\newWorkspace\\eclipse\\NNReversi";
		BufferedReader X_in=new BufferedReader(new FileReader(location + "\\X.txt"));
		BufferedReader Y_in=new BufferedReader(new FileReader(location + "\\Y.txt"));
		System.out.println(Y_in.readLine());
		Matrix[] XaY = test.loader(location + "\\X.txt", location + "\\Y.txt", 5000, 400, 5000, 1);
		Matrix X = XaY[0];
		Matrix Y_temp = XaY[1];
		System.out.println(Y_temp);
	    double elements[][] = new double[5000][10];
	    for(int i = 0 ; i< elements.length; i++){
	    	elements[i][(int)Y_temp.getElements()[i][0] % 10] = 1;
	    }
	    Matrix Y = new Matrix(elements, "Y");
	    //System.out.println(Y);
		int[] layerSizes = {400, 20, 20, 20, 10};
		double lambda = 0;
		NeuralNetwork myNetwork = new NeuralNetwork(layerSizes, lambda, "My Network");
		Matrix costs = myNetwork.training(100, X, Y, 0.001);
		System.out.println(costs);
		for(int i = 0; i< 10; i++){
			int t = (int)(Math.random() * 5000);
			System.out.println("The " + t + " th training example: ");
			Matrix X_test = X.getRow(t, "training Example_" + t);
			Matrix p = myNetwork.predict(X_test);
			double[][] p_elements = p.getElements();
			int max = 0;
			for(int j = 0; j < p_elements[0].length; j++){
				if(p_elements[0][max] < p_elements[0][j]){
					max = j;
				}
			}
			System.out.print(Y_temp.getRow(t, "y"));
			//System.out.println(p);
			System.out.println("predicted: " + max);
		}
		/*int m = 20;
		double[][] x = new double[m][50];
		double[][] y = new double[m][2];
		for(int i = 0; i < m; i++){
			int temp = 0;
			for(int j = 0; j < 50; j++){
				int x_v = (int)(Math.random() * 80);
				temp += x_v;
				x[i][j] = x_v;
			}
			if(temp % 2 ==0){ y[i][1] = 1; }
			else{y[i][0] = 1;}
		}
		Matrix X = new Matrix(x, "X_train");
		Matrix Y = new Matrix(y, "Y_train");
		System.out.println(Y);
		int[] layerSizes = {50,25,5,3,2};
		double lambda = 0;
		NeuralNetwork myNetwork = new NeuralNetwork(layerSizes, lambda, "My Network");
		Matrix costs = myNetwork.training(400, X, Y);
		System.out.println(costs);
		@SuppressWarnings("resource")
		Scanner sca = new Scanner(System.in);
		System.out.println("Now, type in your test examples");
		String x1_x2 = sca.nextLine();
		while(! x1_x2.equals("exit")){
			double[][] X_predict = new double[1][50];
			int temp = 0;
			for(int j = 0; j < 50; j++){
				int x_v = (int)(Math.random() * 80);
				temp += x_v;
				X_predict[0][j] = x_v;
			}
			if(temp % 2 == 0){
				System.out.println("Even: " + "sum = "+ temp);
			}else{
				System.out.println("Odd: " + "sum = "+ temp);
			}
			Matrix X_p = new Matrix(X_predict);
			Matrix predict = myNetwork.predict(X_p);
			double[][] elements = predict.getElements();
			if(elements[0][1] > elements[0][0]){
				System.out.println("( 0 , 1 )");
			}else{
				System.out.println("( 1 , 0 )");
			}
			x1_x2 = sca.nextLine();
		}*/
	}
	
	public Matrix[] loader(String filelocation_X, String filelocation_Y, int x_row, int x_col, int y_row, int y_col) throws IOException{
		Matrix[] readed = new Matrix[2];
		BufferedReader X_in=new BufferedReader(new FileReader(filelocation_X));
		BufferedReader Y_in=new BufferedReader(new FileReader(filelocation_Y));
		double[][] x = new double[x_row][x_col];
		double[][] y = new double[y_row][y_col];
		/******************************* Loading X *******************************/
		String reading = X_in.readLine();
		int i = 0;
		int j = 0;
		String temp = "";
		int r = 0;
		int c = 0;
		int lineTracker = 1;
		while(reading != null && lineTracker <= x_row ){
			System.out.println("reading " + lineTracker + "line");
			//System.out.println(reading);
			//reading = reading.substring(1, reading.length());
			while(j < reading.length()){
				if(reading.substring(j, j + 1).equals(" ")){
					temp = reading.substring(i, j);
					i = j + 1;
				}
				if(temp != null && ! temp.equals("")){
					x[r][c] = Double.parseDouble(temp);
					temp = null;
					c++;
				}
				j++;
			}
			i = 0;
			j = 0;
			r++;
			c = 0;
			lineTracker ++;
			reading = X_in.readLine();
		}
		readed[0] = new Matrix(x, "X");
		/******************************* Loading Y *******************************/
		String reading_Y = Y_in.readLine();
		i = 0;
		j = 0;
		temp = "";
		r = 0;
		c = 0;
		lineTracker = 1;
		while(reading_Y != null && lineTracker <= y_row ){
			System.out.println("reading Y: " + lineTracker + "line");
			System.out.println(reading_Y);
			//reading = reading.substring(1, reading.length());
			while(j < reading_Y.length()){
				if(reading_Y.substring(j, j + 1).equals(" ") || reading_Y.substring(j, j + 1).equals("\n")){
					temp = reading_Y.substring(i, j);
					i = j + 1;
				}
				if((j + 1) == reading_Y.length()){
					temp = reading_Y.substring(i, j + 1);
				}
				if(temp != null && ! temp.equals("")){
					y[r][c] = Double.parseDouble(temp);
					temp = null;
					c++;
				}
				j++;
			}
			i = 0;
			j = 0;
			r++;
			c = 0;
			lineTracker ++;
			reading_Y = Y_in.readLine();
		}
		readed[1] = new Matrix(y, "Y");
		return readed;
	}
	
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
		/*
		 * For implementation convenience, we assume the input layer is of size 100, output layer of size 50
		 * the training set X is: 2000 x 100, Y is 2000 x 50.
		 */
		if(n != layerSizes[0]){
			throw new Exception("input matrix dimension of column does not match that of the input layer  ~~inside Neuralnetwork");
		}
		Matrix[] z_Values = new Matrix[a_Values.length];
		z_Values[0] = X;															//Dimension: 2000 x 100
		double[] biasedTerms = new double[m];
		Arrays.fill(biasedTerms, 1.0);
		for(int i = 0; i < initialThetas.length; i++){
			a_Values[i] = z_Values[i].addOneCol( 0, biasedTerms);	//Dimension: 2000 x 101
			a_Values[i].setName("a_"+i);
			z_Values[i + 1] = Matrix.times(a_Values[i], initialThetas[i], "a_"+(i + 1));
			z_Values[i + 1] = sigmoid(z_Values[i + 1]);
			//System.out.println(a_Values[i]);
		}
		a_Values[a_Values.length - 1] = z_Values[a_Values.length - 1];
		/****************************************End Forward Propagate****************************************/
		/********************************************Back Propagate*******************************************/
		Matrix[] d_Values = new Matrix[a_Values.length];
		Matrix[] D_Values = new Matrix[grad.length];
		d_Values[d_Values.length-1] = Matrix.minus(a_Values[a_Values.length - 1], Y, "d_" + (d_Values.length-1));
		System.out.println("dimension of "+d_Values[d_Values.length-1].getName()+" is: "+d_Values[d_Values.length-1].getDimension());
		System.out.println(d_Values[d_Values.length-1]);
		for(int i = d_Values.length-2; i > 0; i--){
			//d_2 = d_3 * Theta2 .* (a_2.*(1-a_2));
			System.out.println("dimension of "+initialThetas[i].getName()+" is: "+initialThetas[i].getDimension());
			System.out.println("dimension of "+d_Values[i + 1].getName()+" is: "+d_Values[i + 1].getDimension());
			d_Values[i] = Matrix.elementsMultiplication(Matrix.times(d_Values[i + 1], Matrix.transpose(initialThetas[i])), 
							Matrix.elementsMultiplication(a_Values[i], Matrix.minus(1.0, a_Values[i], "any one")), "d_" + i);
			d_Values[i].removeCol(0);
			System.out.println(d_Values[i]);
		}
		for(int i = 0; i < initialThetas.length; i++){
			D_Values[i] = Matrix.times(Matrix.transpose(a_Values[i]), d_Values[i + 1], "D_" + i);
			System.out.println(D_Values[i]);
		}
		/******************************************End Back Propagate*****************************************/
		/********************************************Regularization*******************************************/
		//Improvements needed!! Now may be some waste of memory and calculation resources
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
		return grad;
	}

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
		z_Values[0] = X;															//Dimension: 2000 x 100
		double[] biasedTerms = new double[m];
		Arrays.fill(biasedTerms, 1.0);
		for(int i = 0; i < initialThetas.length; i++){
			a_Values[i] = z_Values[i].addOneCol(0, biasedTerms);	//Dimension: 2000 x 101
			a_Values[i].setName("a_" + i);
			z_Values[i + 1] = Matrix.times(a_Values[i], initialThetas[i], "a_"+(i + 1));
			z_Values[i + 1] = sigmoid(z_Values[i + 1]);
			System.out.println(a_Values[i]);
		}
		a_Values[a_Values.length - 1] = z_Values[a_Values.length - 1];
		System.out.println(a_Values[a_Values.length - 1]);
		/****************************************End Forward Propagate****************************************/
		/***************************************Cost without regularize***************************************/
		Matrix temp_MatrixJ = Matrix.plus(Matrix.elementsMultiplication(Y, Matrix.log(a_Values[a_Values.length - 1])), 
											Matrix.elementsMultiplication(Matrix.minus(1.0, Y, "Oh"), Matrix.log(Matrix.minus(1.0, a_Values[a_Values.length - 1], "Hi"))));
		
		double cost = 0 - temp_MatrixJ.sum()/m;
		//double cost = temp_MatrixJ.sum();
		System.out.println("cost withour reg = " + cost);
		temp_MatrixJ = null;
		double reg = 0.0;
		for(int i = 0; i < initialThetas.length; i++){
			Matrix temp = initialThetas[i];
			reg += Matrix.elementsMultiplication(temp, temp).sum();
			reg -= Matrix.elementsMultiplication(temp.getRow(0, "as you want"), temp.getRow(0, "as you want")).sum();
		}
		reg = reg * lambda / (2 * m);
		System.out.println("reg = "+ reg);
		cost += reg;		
		return cost;
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

	public double gradientChecking() throws Exception{
		int[] layerSize = {3,2,2};
		Matrix[] thetas = new Matrix[2];
		double[][] x = new double[2][3];
		double[][] y = new double[2][2];
		Matrix X = new Matrix(x,"X");
		Matrix Y = new Matrix(y,"Y");
		double lambda = 0.3;
		for(int i = 0; i < thetas.length; i++){
			double [][] temp = new double[layerSize[i] + 1][layerSize[i + 1]];
			Matrix Temp = new Matrix(temp,"Theta_"+i);
			Temp.randomize(-1.25, 1.25);
			thetas[i] = Temp;
		}
		X.randomize(20, 80);
		System.out.println(X.getDimension());
		Matrix[] grad = this.calculateGradient(thetas, X, Y, lambda, layerSize);
		X.removeCol(0);
		double d = this.costFunction(thetas, X, Y, lambda, layerSize);
		System.out.println("cost: "+ d);
		//System.out.println(thetas);
		//System.out.println(X);
		//System.out.println(Y);
		X.removeCol(0);
		double delta = 0.01;
		Matrix[] derivative = new Matrix[thetas.length];
		for(int i = 0 ; i< derivative.length; i++){
			double cost = this.costFunction(thetas, X, Y, lambda, layerSize);
			X.removeCol(0);
			System.out.println("cost: "+ cost);
			double[][] deri = new double[thetas[i].getDimension().getNum_rows()][thetas[i].getDimension().getNum_cols()];
			derivative[i] = new Matrix(deri, "derivative_" + i); 
			double[][] elements = derivative[i].getElements();
			for(int r = 0; r < elements.length; r ++){
				for(int c = 0; c< elements[r].length; c++) {
					double initial = thetas[i].getElements()[r][c];
					thetas[i].getElements()[r][c] += delta;
					double cost_1 = this.costFunction(thetas, X, Y, lambda, layerSize);
					X.removeCol(0);
					thetas[i].getElements()[r][c] -= 2 * delta;
					double cost_2 = this.costFunction(thetas, X, Y, lambda, layerSize);
					X.removeCol(0);
					thetas[i].getElements()[r][c] = initial;
					elements[r][c] = (cost_1 - cost_2) / (2 * delta);
				}
			}
		}
		
		double difference = 0;
		for(int i = 0; i < grad.length; i ++){
			difference += Matrix.minus(grad[i], derivative[i]).sum();
			System.out.println(grad[i]);
			System.out.println(derivative[i]);
		}
		System.out.println("difference is: "+difference);
		return difference;
	}

}
