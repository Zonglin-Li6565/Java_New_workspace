import java.util.Scanner;


public class PacmanVsTA {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean bad = false;
		int size = 12;
		Scanner sca = new Scanner(System.in);
		int tax =  -1, tay = -1, x = -1, y = - 1;
		do{
			tax = sca.nextInt();
			tay = sca.nextInt();
			x = sca.nextInt();
			y = sca.nextInt();
			bad = tax < 0 || tay < 0 || x < 0 || y < 0;
			if(bad) System.out.println("No!"); else System.out.println("OK");
		}while(bad);
		int food[][] = new int[size + 2][size + 2];
		int count[][] = new int[size][size];
		for(int i = 1; i < size + 1; i++){
			for(int j = 1; j < size + 1; j ++){
				//food[i][j] = sca.nextInt();
				food[i][j] = 1;
			}
		}
		int max = solve(food, count, x + 1, y + 1);
		System.out.println("MAX="+max);
		if(count[tax][tay]>0) System.out.println("LOOKUP!"); else System.out.println("SAFE");
	}
	
	static int solve (int food[][], int Count[][], int x, int y)
	{
	    int max_1 = 0, max_2 = 0, max_3 = 0, max_4 = 0;
	    boolean next = false;
	    food[x][y] --;
	    if(food[x - 1][y] > 0){
	    	//System.out.println("food[" + (x - 1) + "][" + y + "] = " + food[x - 1][y]);
	        max_1 = solve(food, Count, x - 1, y);
	        next = true;
	    }
	    if(food[x + 1][y] > 0){
	        max_2 = solve(food, Count, x + 1, y);
	        next = true;
	    }
	    if(food[x][y - 1] > 0){
	        max_1 = solve(food, Count, x, y - 1);
	        next = true;
	    }
	    if(food[x][y + 1] > 0){
	        max_1 = solve(food, Count, x, y + 1);
	        next = true;
	    }
	    food[x][y] ++;
	    if(!next){
	    	x --;
		    y --;
	        Count[x][y]++;
	        return 1;
	    }
	    max_1 = max_1 > max_3 ? max_1 : max_3;
	    max_2 = max_2 > max_4 ? max_2 : max_4;
	    return 1 + (max_1 > max_2 ? max_1 : max_2);
	}

}
