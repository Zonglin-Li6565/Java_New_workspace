import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sca = new Scanner(System.in);
		for(int d = 0; d < 10000; d++){
			int length = 0;
			length = sca.nextInt();
			if(length <= 2){
				System.out.println("1");
				continue;
			}
			Character[] previous_1 = {'1'};
			Character[] previous_2 = {'1'};
			for(int i = 2; i < length; i++){
				Character[] current = add(previous_1, previous_2);
				previous_1 = previous_2;
				previous_2 = current;
			}
			for(int i = 0; i < previous_2.length; i++){
				System.out.print(previous_2[i]);
			}
			System.out.println("\n" + previous_2.length + " digits");
		}
		sca.close();
	}
	
	public static Character[] add(Character[] x, Character[] y){
		ArrayList<Character> result = new ArrayList<Character>();
		int sum = 0, carry = 0, a = 0, b = 0, indexX = x.length - 1, indexY = y.length - 1;
		while(indexX >= 0 || indexY >= 0){
			if(indexX < 0) a = 0; else a = x[indexX] - '0';
			if(indexY < 0) b = 0; else b = y[indexY] - '0';
			sum = a + b + carry;
			carry = sum / 10;
			sum %= 10;
			result.add(0, (char) (sum + '0'));
			indexX--;
			indexY--;
		}
		if(carry > 0) result.add(0, (char) (carry + '0'));
		Character[] array = new Character[result.size()];
		result.toArray(array);
		return array;
	}
}
