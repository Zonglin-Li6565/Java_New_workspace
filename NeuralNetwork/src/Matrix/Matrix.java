package Matrix;

import java.io.Serializable;

public class Matrix implements Serializable{
	private double [][]  elements;
	private Dimension dimension;
	private String name;
	
	/**
	 * default construct. NO parameter
	 */
	public Matrix(){ 
		dimension = new Dimension(2, 2);
		elements = new double[2][2];
		for(int i =0;i<2;i++){
			for(int j = 0; j<2;j++){
				elements[i][j] = 0;
			}
		}
	}
	
	/**
	 * Initialized as a two dimensional matrix
	 * in the form double[number of rows][number of columns]
	 * @param Two_Delements
	 */
	public Matrix(double [][] Two_Delements){
		elements = Two_Delements;
		dimension = new Dimension(Two_Delements.length, Two_Delements[0].length);
	}
	
	/**
	 * Initialized with one 2-D array and a string Name.
	 * @param Two_Delements
	 * @param Name
	 */
	public Matrix(double [][] Two_Delements, String Name){
		elements = Two_Delements;
		dimension = new Dimension(Two_Delements.length, Two_Delements[0].length);
		name = Name;
	}
	
	/**
	 * @return the elements
	 */
	public double[][] getElements() {
		return elements;
	}

	/**
	 * @return the dimension
	 */
	public Dimension getDimension() {
		return dimension;
	}

	/**
	 * To transpose a matrix
	 * @param m
	 * @return
	 */
	public static Matrix transpose(Matrix m){
		double[][] element = m.getElements();
		int row = m.getDimension().getNum_rows();
		int col = m.getDimension().getNum_cols();
		double[][] newElements = new double[col][row];
		for(int r = 0; r < row; r++){
			for(int c = 0; c < col; c++){
				newElements[c][r] = element[r][c];
			}
		}
		String Name = m.getName() + "Transpose"; 
		return new Matrix(newElements, Name);
	}
	
	/**
	 * Also set the name of the result matrix
	 * @param m
	 * @param name
	 * @return
	 */
	public static Matrix transpose(Matrix m, String name){
		double[][] element = m.getElements();
		int row = m.getDimension().getNum_rows();
		int col = m.getDimension().getNum_cols();
		double[][] newElements = new double[row][col];
		for(int r = 0; r < row; r++){
			for(int c = 0; c < col; c++){
				newElements[c][r] = element[r][c];
			}
		}
		return new Matrix(newElements, name);
	}
	
	/**
	 * Times two matrixes m_1 and m_2. 
	 * Note: the dimension of m_1 and m_2 must match
	 * @param m_1
	 * @param m_2
	 * @return
	 * @throws Exception 
	 */
	public static Matrix times(Matrix m_1, Matrix m_2) throws Exception{
		if(m_1.getDimension() == null || m_2.getDimension() == null){
			throw new Exception("At least one of the matrixes haven't been initialized ~~inside times");
		}
		if(!Dimension.match(m_1.getDimension(), m_2.getDimension())){
			throw new Exception("Dimensions do not match ~~inside times");
		}
		double[][] element_1 = m_1.getElements();
		double[][] element_2 = m_2.getElements();
		int row_1 = m_1.getDimension().getNum_rows();
		@SuppressWarnings("unused")
		int col_1 = m_1.getDimension().getNum_cols();
		int row_2 = m_2.getDimension().getNum_rows();
		int col_2 = m_2.getDimension().getNum_cols();
		double[][] temp = new double[row_1][col_2];
		for(int r = 0; r < row_1; r++){
			for(int c = 0; c < col_2; c++){
				double sumTemp = 0.0;
				for(int i = 0;i < row_2; i++){   					//Here, row_2 and col_1 are equivalent
					sumTemp += element_1[r][i] * element_2[i][c];
				}
				temp[r][c] = sumTemp;
			}
		}
		String Name = m_1.getName() + " times " + m_2.getName(); 
		return new Matrix(temp, Name);
	}
	
	public static Matrix times(Matrix m_1, Matrix m_2, String name) throws Exception{
		if(m_1.getDimension() == null || m_2.getDimension() == null){
			throw new Exception("At least one of the matrixes haven't been initialized ~~inside times");
		}
		if(!Dimension.match(m_1.getDimension(), m_2.getDimension())){
			throw new Exception("Dimensions do not match ~~inside times");
		}
		double[][] element_1 = m_1.getElements();
		double[][] element_2 = m_2.getElements();
		int row_1 = m_1.getDimension().getNum_rows();
		@SuppressWarnings("unused")
		int col_1 = m_1.getDimension().getNum_cols();
		int row_2 = m_2.getDimension().getNum_rows();
		int col_2 = m_2.getDimension().getNum_cols();
		double[][] temp = new double[row_1][col_2];
		for(int r = 0; r < row_1; r++){
			for(int c = 0; c < col_2; c++){
				double sumTemp = 0.0;
				for(int i = 0;i < row_2; i++){   					//Here, row_2 and col_1 are equivalent
					sumTemp += element_1[r][i] * element_2[i][c];
				}
				temp[r][c] = sumTemp;
			}
		}
		String Name = name; 
		return new Matrix(temp, Name);
	}
	
	/**
	 * Multiplies a matrix with a real number
	 * @param t
	 * @param m
	 * @param name
	 * @return
	 */
	public static Matrix times(double t, Matrix m, String name){
		double[][] elements = m.getElements();
		int row = m.getDimension().getNum_rows();
		int col = m.getDimension().getNum_cols();
		double[][] newElements = new double[row][col];
		for(int i = 0; i < elements.length; i++){
			for(int j = 0; j< elements[i].length; j++){
				newElements[i][j] = elements[i][j] * t;
			}
		}
		return new Matrix(newElements, name);
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

	/**
	 * Time the elements in two matrixes m_1 and m_2
	 * Note: the dimension of m_1 and m_2 must be same
	 * @param m_1
	 * @param m_2
	 * @return 
	 * @throws Exception 
	 */
	public static Matrix elementsMultiplication(Matrix m_1 , Matrix m_2) throws Exception{
		if(m_1.getDimension() == null || m_2.getDimension() == null){
			throw new Exception("At least one of the matrixes haven't been initialized ~~inside elementsMultiplication");
		}
		if(!Dimension.same(m_1.getDimension(), m_2.getDimension())){
			throw new Exception("Not of same dimension ~~inside elementsMultiplication");
		}
		double[][] element1 = m_1.getElements();
		double[][] element2 = m_2.getElements();
		int r_num = m_1.getDimension().getNum_rows();
		int c_num = m_1.getDimension().getNum_cols();
		double[][] newElements = new double[r_num][c_num];
		for(int r =0; r < r_num; r ++){
			for(int c =0; c < c_num; c++){
				newElements[r][c] = element1[r][c] * element2[r][c];
			}
		}
		String Name = m_1.getName() + " elements multiply " + m_2.getName(); 
		return new Matrix(newElements, Name);
	}
	
	/**
	 * Time the elements in two matrixes m_1 and m_2
	 * Note: the dimension of m_1 and m_2 must be same
	 * Setting the name of the result as designated
	 * @param m_1
	 * @param m_2
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static Matrix elementsMultiplication(Matrix m_1 , Matrix m_2, String name) throws Exception{
		if(m_1.getDimension() == null || m_2.getDimension() == null){
			throw new Exception("At least one of the matrixes haven't been initialized ~~inside elementsMultiplication");
		}
		if(!Dimension.same(m_1.getDimension(), m_2.getDimension())){
			throw new Exception("Not of same dimension ~~inside elementsMultiplication");
		}
		double[][] element1 = m_1.getElements();
		double[][] element2 = m_2.getElements();
		int r_num = m_1.getDimension().getNum_rows();
		int c_num = m_1.getDimension().getNum_cols();
		double[][] newElements = new double[r_num][c_num];
		for(int r =0; r < r_num; r ++){
			for(int c =0; c < c_num; c++){
				newElements[r][c] = element1[r][c] * element2[r][c];
			}
		}
		return new Matrix(newElements, name);
	}
	
	/**
	 * Dividing the elements in m_1 by elements in m_2
	 * Note: the dimension of m_1 and m_2 must be the same;
	 * @param m_1
	 * @param m_2
	 * @return
	 * @throws Exception 
	 */
	public static Matrix elementsDivision(Matrix m_1, Matrix m_2, String name) throws Exception{
		if(!Dimension.same(m_1.getDimension(), m_2.getDimension())){
			throw new Exception("Not of same dimension ~~inside elementsDivision");
		}
		double[][] element1 = m_1.getElements();
		double[][] element2 = m_2.getElements();
		int r_num = m_1.getDimension().getNum_rows();
		int c_num = m_1.getDimension().getNum_cols();
		double[][] newElements = new double[r_num][c_num];
		for(int r =0; r < r_num; r ++){
			for(int c =0; c < c_num; c++){
				newElements[r][c] = element1[r][c] / element2[r][c];
			}
		}
		return new Matrix(newElements, name);
	}
	
	/**
	 * Divides the Matrix m by a number n (m ./ n)
	 * @param m
	 * @param n
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static Matrix dividedByaNumber(Matrix m, double n, String name) throws Exception{
		double[][] elements = m.getElements();
		int rows = m.getDimension().getNum_rows();
		int cols = m.getDimension().getNum_cols();
		double[][] newElements = new double[rows][cols];
		for(int r = 0; r < elements.length;r ++){
			for(int c = 0;c < elements[r].length;c++){
				newElements[r][c] = elements[r][c] / n;
			}
		}
		return new Matrix(newElements, name);
	}
	
	/**
	 * Divide the number n by every elements in the Matrix m (n ./ m)
	 * @param m
	 * @param n
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static Matrix dividesaNumber(Matrix m, double n, String name) throws Exception{
		double[][] elements = m.getElements();
		int rows = m.getDimension().getNum_rows();
		int cols = m.getDimension().getNum_cols();
		double[][] newElements = new double[rows][cols];
		for(int r = 0; r < elements.length;r ++){
			for(int c = 0;c < elements[r].length;c++){
				newElements[r][c] = n / elements[r][c];
			}
		}
		return new Matrix(newElements, name);
	}
	
	/**
	 * Sum all values for the host matrix
	 * @return
	 */
	public double sum(){
		double temp = 0.0;
		for(int i = 0; i < elements.length; i++){
			for(int j = 0; j < elements[i].length; j++){
				temp += elements[i][j];
			}
		}
		return temp;
	}
	
	public static Matrix log(Matrix m){
		double[][] elements = m.getElements();
		int rows = m.getDimension().getNum_rows();
		int cols = m.getDimension().getNum_cols();
		double[][] newElements = new double[rows][cols];
		for(int r = 0; r < elements.length;r ++){
			for(int c = 0;c < elements[r].length;c++){
				newElements[r][c] = Math.log(elements[r][c]);
			}
		}
		return new Matrix(newElements);
	}
	
	/**
	 * Plus the elements in m_1 and m_2
	 * Note: m_1 and m_2 must of same dimension
	 * @param m_1
	 * @param m_2
	 * @return
	 * @throws Exception 
	 */
	public static Matrix plus(Matrix m_1, Matrix m_2) throws Exception{
		if(!Dimension.same(m_1.getDimension(), m_2.getDimension())){
			throw new Exception("Not of same dimension ~~inside plus");
		}
		double[][] element1 = m_1.getElements();
		double[][] element2 = m_2.getElements();
		int r_num = m_1.getDimension().getNum_rows();
		int c_num = m_1.getDimension().getNum_cols();
		double[][] newElements = new double[r_num][c_num];
		for(int r =0; r < r_num; r ++){
			for(int c =0; c < c_num; c++){
				newElements[r][c] = element1[r][c] + element2[r][c];
			}
		}
		String Name = m_1.getName() + " adds " + m_2.getName(); 
		return new Matrix(newElements, Name);
	}
	
	/**
	 * plus every elements in the matrix m with value d.
	 * @param d
	 * @param m
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static Matrix plus(double d, Matrix m, String name) throws Exception{
		double[][] elements = m.getElements();
		int rows = m.getDimension().getNum_rows();
		int cols = m.getDimension().getNum_cols();
		double[][] newElements = new double[rows][cols];
		for(int r =0; r < elements.length; r ++){
			for(int c =0; c < elements[r].length; c++){
				newElements[r][c] = elements[r][c] + d;
			}
		}
		return new Matrix(newElements, name);
	}
	
	/**
	 * Minus the elements in m_1 by the corresponding elements in m_2
	 * Note: m_1 and m_2 must of same dimension
	 * @param m_1
	 * @param m_2
	 * @return
	 * @throws Exception 
	 */
	public static Matrix minus(Matrix m_1, Matrix m_2) throws Exception{
		if(!Dimension.same(m_1.getDimension(), m_2.getDimension())){
			throw new Exception("Not of same dimension ::inside minus");
		}
		double[][] element1 = m_1.getElements();
		double[][] element2 = m_2.getElements();
		int r_num = m_1.getDimension().getNum_rows();
		int c_num = m_1.getDimension().getNum_cols();
		double[][] newElements = new double[r_num][c_num];
		for(int r =0; r < r_num; r ++){
			for(int c =0; c < c_num; c++){
				newElements[r][c] = element1[r][c] - element2[r][c];
			}
		}
		String Name = m_1.getName() + " substracts " + m_2.getName(); 
		return new Matrix(newElements, Name);
	}
	
	/**
	 * Minus the elements in m_1 by the corresponding elements in m_2
	 * Note: m_1 and m_2 must of same dimension
	 * @param m_1
	 * @param m_2
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static Matrix minus(Matrix m_1, Matrix m_2, String name) throws Exception{
		if(!Dimension.same(m_1.getDimension(), m_2.getDimension())){
			throw new Exception("Not of same dimension ~~inside minus");
		}
		double[][] element1 = m_1.getElements();
		double[][] element2 = m_2.getElements();
		int r_num = m_1.getDimension().getNum_rows();
		int c_num = m_1.getDimension().getNum_cols();
		double[][] newElements = new double[r_num][c_num];
		for(int r =0; r < r_num; r ++){
			for(int c =0; c < c_num; c++){
				newElements[r][c] = element1[r][c] - element2[r][c];
			}
		}
		return new Matrix(newElements, name);
	}
	
	/**
	 * Using a single number d to subtract every numbers in Matrix m (d .- m);
	 * @param d
	 * @param m
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static Matrix minus(double d, Matrix m, String name) throws Exception{
		double[][] elements = m.getElements();
		int r_num = m.getDimension().getNum_rows();
		int c_num = m.getDimension().getNum_cols();
		double[][] newElements = new double[r_num][c_num];
		for(int r =0; r < r_num; r ++){
			for(int c =0; c < c_num; c++){
				newElements[r][c] = d - elements[r][c];
			}
		}
		return new Matrix(newElements, name);
	}
	
	/**
	 * Using every elements in Matrix m to substract d(m .- d);
	 * @param m
	 * @param d
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static Matrix minus(Matrix m,double d, String name) throws Exception{
		double[][] elements = m.getElements();
		int r_num = m.getDimension().getNum_rows();
		int c_num = m.getDimension().getNum_cols();
		double[][] newElements = new double[r_num][c_num];
		for(int r =0; r < r_num; r ++){
			for(int c =0; c < c_num; c++){
				newElements[r][c] = elements[r][c] - d;
			}
		}
		return new Matrix(newElements, name);
	}
	
	/**
	 * Remove the row designated by the rowNumber
	 * Retrun a matrix of the row being removed
	 * Note: do the bound checking;
	 * @param m
	 * @param rowNumber
	 * @return
	 * @throws Exception 
	 */
	public Matrix removeRow(int rowNumber) throws Exception{
		if(this.getDimension() == null){
			throw new Exception("Haven't been initialized! ::in addOneCol");
		}else if(this.getDimension().getNum_rows() <= rowNumber || rowNumber < 0
				||this.getDimension().getNum_rows() == 1){
			throw new Exception("Dimension Error ~~in removeCol");
		}
		int mrows = this.getDimension().getNum_rows();
		int mcols = this.getDimension().getNum_cols();
		double[][] mElements = this.getElements();
		double[][] temp = new double[mrows - 1][mcols];
		for(int r = 0; r < rowNumber ; r ++){
			for(int c =0; c < mcols; c ++){
				temp[r][c] = mElements[r][c];
			}
		}
		for(int r = rowNumber + 1; r < mrows; r ++){
			for(int c =0; c < mcols; c ++){
				temp[r - 1][c] = mElements[r][c];
			}
		}
		this.elements = temp;
		this.dimension.setNum_rows(dimension.getNum_rows() - 1);
		return new Matrix(temp);
	}
	
	/**
	 * Remove the column designated by the colNumber
	 * Return a matrix of the column being removed
	 * Note: do the bound checking;
	 * @param m
	 * @param colNumber
	 * @return
	 * @throws Exception 
	 */
	public Matrix removeCol(int colNumber) throws Exception{
		if(this.getDimension() == null){
			throw new Exception("Haven't been initialized! ::in addOneCol");
		}else if(this.getDimension().getNum_cols() <= colNumber || colNumber < 0
				||this.getDimension().getNum_cols() == 1){
			throw new Exception("Dimension Error ~~in removeCol");
		}
		int mrows = this.getDimension().getNum_rows();
		int mcols = this.getDimension().getNum_cols();
		double[][] mElements = this.getElements();
		double[][] temp = new double[mrows][mcols - 1];
		for(int c = 0;c<colNumber;c++){
			for(int r = 0; r < mrows; r++){
				temp[r][c] = mElements[r][c];
			}
		}
		for(int c = colNumber + 1; c < mcols; c++){
			for(int r = 0; r < mrows; r++){
				temp[r][c - 1] = mElements[r][c];
			}
		}
		this.elements = temp;
		this.dimension.setNum_cols(dimension.getNum_cols() - 1);
		return new Matrix(temp);
	}
	
	/**
	 * Add one row to the matrix m.
	 * The Row will be inserted in the entry indicated by rowNumber
	 * Note: do the bound checking.
	 * @param m
	 * @param rowNumber
	 * @param row
	 * @return
	 * @throws Exception 
	 */
	public Matrix addOneRow(int rowNumber, double[] row) throws Exception{
		if(this.getDimension() == null){
			throw new Exception("Haven't been initialized! ::in addOneCol");
		}else if(this.getDimension().getNum_cols() != row.length){
			throw new Exception("Dimension mismatch ~~in addOneRow");
		}
		int mrows = this.getDimension().getNum_rows();
		int mcols = this.getDimension().getNum_cols();
		double[][] mElements = this.getElements();
		double[][] temp = new double[mrows + 1][mcols];
		for(int r = 0; r < rowNumber ; r ++){
			for(int c =0; c < mcols; c ++){
				temp[r][c] = mElements[r][c];
			}
		}
		for(int c =0; c < mcols; c ++){
			temp[rowNumber][c] = row[c];
		}
		for(int r = rowNumber + 1; r < mrows + 1; r ++){
			for(int c =0; c < mcols; c ++){
				temp[r][c] = mElements[r - 1][c];
			}
		}
		this.elements = temp;
		this.dimension.setNum_rows(dimension.getNum_rows() + 1);
		return new Matrix(temp);
	}

	/**
	 * Add one column to the matrix m.
	 * The Column will be inserted in the entry indicated by colNumber
	 * return a Matrix with column added
	 * Note: do the bound checking.
	 * @param m
	 * @param colNumber
	 * @param col
	 * @return
	 * @throws Exception
	 */
	public Matrix addOneCol(int colNumber, double[] col) throws Exception{
		if(this.getDimension() == null){
			throw new Exception("Haven't been initialized! ~~in addOneCol");
		}else if(this.getDimension().getNum_rows() != col.length){
			throw new Exception("Dimension mismatch ~~in addOneCol");
		}
		int mrows = this.getDimension().getNum_rows();
		int mcols = this.getDimension().getNum_cols();
		double[][] mElements = this.getElements();
		double[][] temp = new double[mrows][mcols + 1];
		for(int c = 0;c<colNumber;c++){
			for(int r = 0; r < mrows; r++){
				temp[r][c] = mElements[r][c];
			}
		}
		for(int r = 0; r < mrows; r++){
			temp[r][colNumber] = col[r];
		}
		for(int c = colNumber + 1; c < mcols + 1; c++){
			for(int r = 0; r < mrows; r++){
				temp[r][c] = mElements[r][c-1];
			}
		}
		this.elements = temp;
		this.dimension.setNum_cols(dimension.getNum_cols() + 1);
		return new Matrix(temp);
	}

	/**
	 * Set a single value at the location (row, col)
	 * @param v
	 * @param row
	 * @param col
	 */
	public void set(double v, int row, int col){
		elements[row][col] = v;
	}
	
	/**
	 * Get the value at the location (row, col)
	 * @param row
	 * @param col
	 * @return
	 */
	public double get(int row, int col){
		return elements[row][col];
	}
	
	/**
	 * Get a single row in a matrix
	 * name for the retruned matrix
	 * @param row
	 * @param name
	 * @return
	 */
	public Matrix getRow(int row, String name){
		double [][] oneRow = new double[1][this.dimension.getNum_cols()];
		for(int i = 0; i < dimension.getNum_cols(); i++){
			oneRow[0][i] = elements[row][i];
		}
		return new Matrix(oneRow, name);
	}
	
	/**
	 * Get a single column in a matrix
	 * name for the returned matrix
	 * @param col
	 * @param name
	 * @return
	 */
	public Matrix getCol(int col, String name){
		double[][] oneCol = new double[this.dimension.getNum_rows()][1];
		for(int i = 0; i < dimension.getNum_rows(); i ++){
			oneCol[i][0] = elements[i][col];
		}
		return new Matrix(oneCol, name);
	}
	
	public void randomize(double lowerBound, double upperBound) throws Exception{
		double range = upperBound - lowerBound;
		if(range < 0){
			throw new Exception("upperBound less than lowerBound ~~inside randomize");
		}
		for(int r = 0; r < elements.length; r++){
			for(int c = 0; c < elements[r].length; c++){
				elements[r][c] = Math.random() * range + lowerBound;
			}
		}
	}
	
	@Override
	public String toString(){
		String s = "";
		if(name != null){
			s += name + " = " + "\n";
			for(int r = 0; r < dimension.getNum_rows(); r++){
				s += "\t";
				for(int c = 0;c < dimension.getNum_cols(); c++){
					s += elements[r][c] + " ";
				}
				s += "\n";
			}
		}else{
			s += " = " + "\n";
			for(int r = 0; r < dimension.getNum_rows(); r++){
				s += "\t";
				for(int c = 0;c < dimension.getNum_cols(); c++){
					s += elements[r][c] + " ";
				}
				s += "\n";
			}
		}
		return s;
	}
}