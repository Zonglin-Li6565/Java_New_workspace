package Matrix;

public class Dimension{
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
	 * @param num_rows the num_rows to set
	 */
	public void setNum_rows(int num_rows) {
		this.num_rows = num_rows;
	}

	/**
	 * @param num_cols the num_cols to set
	 */
	public void setNum_cols(int num_cols) {
		this.num_cols = num_cols;
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
	
	/**
	 * Test whether two dimension matches each other, so that they can be multiplied.
	 * @param d_1
	 * @param d_2
	 * @return
	 */
	public static boolean match(Dimension d_1, Dimension d_2){
		return d_1.getNum_cols() == d_2.getNum_rows();
	}
	
	/**
	 * to String method
	 */
	@Override
	public String toString(){
		return num_rows + " x "+num_cols;
	}
}
