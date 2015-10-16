package Test;

public class Test_1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] t = new int[0][3];
		for(int i =0 ;i<t.length;i++){
			for(int j =0;j<t[i].length;j++){
				t[i][j] = 1;
			}
		}
		System.out.println(t.length);
		System.out.println(t[0].length);
	}

}
