package Matrix;

public class MatrixTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		double[][] matrix_1 = new double[3][2];
		double[][] matrix_2 = new double[2][3];
		int k = 1;
		for(int i =0; i< 3;i++){
			for(int t =0; t<2;t++){
				matrix_1[i][t] = k;
				matrix_2[t][i] = k;
				k++;
			}
		}
		Matrix m_1 = new Matrix(matrix_1,"m_1");
		Matrix m_2 = new Matrix(matrix_2,"m_2");
		System.out.println(m_1);
		//System.out.println(m_2);
		System.out.println(Matrix.transpose(m_1));
		System.out.println(m_1);
		double[] toaddCol = {7,8,9};
		m_1.addOneCol(0, toaddCol);
		System.out.println(m_1);
	}

}
