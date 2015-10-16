import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Solution {
	static int size;
	static int visited;
	static final String alphs = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
	
	public static void main(String args[]) throws Exception {
		Hashtable<Character, Node> nodes = new Hashtable<Character, Node>();
		Scanner sca = new Scanner(System.in);
		while(sca.hasNext()){
			String s = sca.nextLine();
			//if(s.equals("end")) break;
			char x = s.charAt(0);
			char y = s.charAt(2);
			Node n = nodes.get(x);
			if(n == null){
				n = new Node(x);
				nodes.put(x, n);
			}
			Node m = nodes.get(y);
			if(m == null){
				m = new Node(y);
				nodes.put(y, m);
			}
			n.nodes.add(m);
		}
		size = nodes.size();
		for(int i = 0; i < 52; i++){
			Node next = nodes.get(alphs.charAt(i));
			if(next == null) continue;
			visited = 0;
			if(solve(next) && visited == size){
				System.out.println("true");
				return;
			}
			for(int j = 0; j < 52; j++){
				Node t = nodes.get(alphs.charAt(j));
				if(t != null) t.visited = false;
			}
			next = nodes.elements().nextElement();
			i++;
		}
		System.out.println("false");
		sca.close();
	}
	
	public static boolean solve(Node n){
		//System.out.println("now at " + n.name);
		if(n.visited) return false;
		visited ++;
		n.visited = true;
		for(Node m : n.nodes){
			if(!solve(m)) return false;
		} 
		return true;
	}
}

class Node{
	public ArrayList<Node> nodes = new ArrayList<Node>();
	public char name;
	public boolean visited;
	public Node(char c){
		name = c;
		visited = false;
	}
}