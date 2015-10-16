package Interfaces;

public abstract class matrix {
	private double [][]  twoDElements;
	private double [] oneDElements;
	private Dimension dimension;
	
	/**
	 * default construct. NO parameter
	 */
	public matrix(){ 
		dimension = new Dimension(1, 1);
	}
	
	/**
	 * Initialized as a two dimensional matrix
	 * in the form double[number of rows][number of columns]
	 * @param Two_Delements
	 */
	public matrix (double [][] Two_Delements){
		twoDElements = Two_Delements;
		dimension = new Dimension(Two_Delements.length, Two_Delements[0].length);
	}
	
	/**
	 * Initialized as a one dimensional matrix
	 * You have to show the exact dimension of this matrix
	 * @param One_Delements
	 * @param d
	 */
	public matrix(double [] One_Delements, Dimension d){
		dimension = d;
		oneDElements = One_Delements;
	}

	/**
	 * @return the twoDElements
	 */
	public double[][] getTwoDElements() {
		return twoDElements;
	}

	/**
	 * @param twoDElements the twoDElements to set
	 * @throws Exception 
	 */
	public void setTwoDElements(double[][] twoDElements) throws Exception {
		if(dimension != null){
			throw new Exception("Already initialized");
		}
		this.twoDElements = twoDElements;
	}

	/**
	 * @return the oneDElements
	 */
	public double[] getOneDElements() {
		return oneDElements;
	}

	/**
	 * @param oneDElements the oneDElements to set
	 * @throws Exception 
	 */
	public void setOneDElements(double[] oneDElements) throws Exception {
		if(dimension != null){
			throw new Exception("Already initialized");
		}
		this.oneDElements = oneDElements;
	}

	/**
	 * @return the dimension
	 */
	public Dimension getDimension() {
		return dimension;
	}

	/**
	 * @param dimension the dimension to set
	 * @throws Exception 
	 */
	public void setDimension(Dimension dimension) throws Exception {
		if(dimension != null){
			throw new Exception("Already initialized");
		}
		this.dimension = dimension;
	}
	
	/**
	 * To transpose a matrix
	 * @param m
	 * @return
	 */
	public abstract matrix transpose(matrix m);
	
	/**
	 * Times two matrixes m_1 and m_2. 
	 * Note: the dimension of m_1 and m_2 must match
	 * @param m_1
	 * @param m_2
	 * @return
	 */
	public abstract matrix times(matrix m_1, matrix m_2);
	
	/**
	 * Time the elements in two matrixes m_1 and m_2
	 * Note: the dimension of m_1 and m_2 must be same
	 * @param m_1
	 * @param m_2
	 * @return
	 */
	public abstract matrix elementsTimes(matrix m_1 , matrix m_2);
	
	/**
	 * Dividing the elements in m_1 by elements in m_2
	 * Note: the dimension of m_1 and m_2 must be the same;
	 * @param m_1
	 * @param m_2
	 * @return
	 */
	public abstract matrix elementsDivide(matrix m_1, matrix m_2);
	
	/**
	 * Plus the elements in m_1 and m_2
	 * Note: m_1 and m_2 must of same dimension
	 * @param m_1
	 * @param m_2
	 * @return
	 */
	public abstract matrix plus(matrix m_1, matrix m_2);
	
	/**
	 * Minus the elements in m_1 by the corresponding elements in m_2
	 * Note: m_1 and m_2 must of same dimension
	 * @param m_1
	 * @param m_2
	 * @return
	 */
	public abstract matrix minus(matrix m_1, matrix m_2);
	
	/**
	 * Remove the row designated by the rowNumber
	 * Note: do the bound checking;
	 * @param m
	 * @param rowNumber
	 * @return
	 */
	public abstract matrix removeRow(matrix m, int rowNumber);
	
	/**
	 * Remove the column designated by the colNumber
	 * Note: do the bound checking;
	 * @param m
	 * @param colNumber
	 * @return
	 */
	public abstract matrix removeCol(matrix m, int colNumber);
	
	/**
	 * Add one row to the matrix m.
	 * Whether before or after the row indicated by rowNumber, depends on the value of "before"
	 * Note: do the bound checking.
	 * @param m
	 * @param rowNumber
	 * @param before
	 * @param row
	 * @return
	 */
	public abstract matrix addOneRow(matrix m, int rowNumber, boolean before, boolean[] row);
	
	/**
	 * Add one column to the matrix m.
	 * Whether before or after the column indicated by colNumber, depends on the value of "before"
	 * Note: do the bound checking.
	 * @param m
	 * @param colNumber
	 * @param before
	 * @param col
	 * @return
	 */
	public abstract matrix addOneCol(matrix m, int colNumber, boolean before, boolean[] col);
}

class Dimension{
	private int num_rows;
	private int num_cols;
	
	public Dimension(int rows, int cols){
		num_rows = rows;
		num_cols = cols;
	}

	/**
	 * @return the num_rows
	 */
	public int getNum_rows() {
		return num_rows;
	}

	/**
	 * @return the num_cols
	 */
	public int getNum_cols() {
		return num_cols;
	}
	
	/**
	 * Compares whether d_1 and d_2 are of the same.
	 * @param d_1
	 * @param d_2
	 * @return
	 */
	public static boolean same(Dimension d_1, Dimension d_2){
		return (d_1.getNum_cols() == d_2.getNum_cols())&&(d_1.getNum_rows() == d_2.getNum_rows());
	}
	
	public static boolean match(Dimension d_1, Dimension d_2){
		return d_1.getNum_cols() == d_2.getNum_rows();
	}
	
	@Override
	public String toString(){
		return num_rows + " x "+num_cols;
	}
}
