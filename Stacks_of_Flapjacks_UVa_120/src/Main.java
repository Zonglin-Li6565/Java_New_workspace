import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
	static Pancake array[] = new Pancake[40];
	static PriorityQueue<Pancake> heap;
	static int length;
	
	public static void main(String args[]){
		Scanner sca = new Scanner(System.in);
		ArrayList<Pancake> arrayBuilder = new ArrayList<Pancake>();
		heap = new PriorityQueue<Pancake>();
		while(sca.hasNextLine()){
			length = 0;
			arrayBuilder.clear();
			heap.clear();
			String s = sca.nextLine();
			for(int i = 0; i < s.length(); i++){
				char c = s.charAt(i);
				if(c == ' ') continue;
				Pancake p = new Pancake(c - '0', length);
				arrayBuilder.add(p);
				heap.add(p);
				length++;
			}
			arrayBuilder.toArray(array);
			solve(length - 1);
			System.out.println();
		}
		sca.close();
	}
	
	public static void flip(int bottomIndex){
		System.out.print((length - bottomIndex) + " ");
		int a = 0, b = length - 1;
		while(a < b){
			Pancake p = array[a];
			array[a] = array[b];
			array[b] = p;
			array[a].location = a;
			array[b].location = b;
			a++;
			b--;
		}
	}
	
	public static void solve(int indextoPut){
		Pancake p = heap.poll();
		if(p == null) {System.out.println("0"); return;}
		System.out.println("--poped out = " + p.radii);
		if(p.location != indextoPut){
			if(p.location != 0) 
				flip(p.location);
			flip(indextoPut);
		}
		solve(indextoPut - 1);
	}
}

@SuppressWarnings("rawtypes")
class Pancake implements Comparable{
	public int radii;
	public int location;
	
	public Pancake(int radii, int location){
		this.radii = radii;
		this.location = location;
	}

	@Override
	public int compareTo(Object o) {
		Pancake p = (Pancake)o;
		if(p.radii > radii) return 1;
		if(p.radii < radii) return -1;
		return 0;
	}
}