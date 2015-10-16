import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;


public class DNA_Sorting {
	public static void main(String[] args){
		boolean first = true;
		Scanner sca = new Scanner(System.in);
		//int times = sca.nextInt();
		/*for(int t = 0; t < times; t ++){
			if(first) first = false; else System.out.println();
			int length = sca.nextInt(), number = sca.nextInt();
			DNA[] dnas = new DNA[number];
			sca.nextLine();
			for(int i = 0; i < number; i++){
				String s = sca.nextLine();
				dnas[i] = new DNA(s, inverseValue(s));
			}
			Arrays.sort(dnas);
			for(int i = 0 ; i < number; i++){
				System.out.println(dnas[i]);
			}
		}*/
		int length = sca.nextInt(), number = sca.nextInt();
		DNA[] dnas = new DNA[number];
		//sca.nextLine();
		for(int i = 0; i < number; i++){
			String s = sca.nextLine();
			dnas[i] = new DNA(s, inverseValue(s));
		}
		Arrays.sort(dnas);
		for(int i = 0 ; i < number; i++){
			System.out.println(dnas[i]);
		}
	}
	
	public static int inverseValue(String s){
		char c = 0;
		int t = 0;
		for(int i = 0; i < s.length(); i++){
			c = s.charAt(i);
			for(int j = i; j < s.length(); j++){
				if(c > s.charAt(j)) t ++;
			}
		}
		return t;
	}

	private static class DNA implements Comparable{
		String s;
		int inverse;
		public DNA(String string, int i){
			s = string;
			inverse = i;
		}
		@Override
		public int compareTo(Object o) {
			// TODO Auto-generated method stub
			DNA dna = (DNA) o;
			if(inverse > dna.inverse) return 1;
			if(inverse < dna.inverse) return -1;
			return 0;
		}
		
		public String toString(){
			return s;
		}
	}
}
