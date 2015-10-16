import java.util.Scanner;

public class Main {
	static Node[] nodes;
	static int[][] table;
	static int[] status;
	static boolean broken;
	
	public static void main(String args[]){
		Scanner sca = new Scanner(System.in);
		String firstLine = sca.nextLine();
		boolean first = true;
		while(firstLine != null && firstLine.equals("START")){
			if(first) first = false; else System.out.println();
			initialize();
			for(int i = 1; i <= 4; i++ ){
				for(int j = 1; j <= 4; j++){
					table[i][j] = sca.nextInt();
				}
			}
			for(int i = 1; i <= 4;i ++){
				for(int j = 1; j <= 4; j++){
					System.out.print(table[i][j] + " ");
				}
				System.out.println();
			}
			for(int i = 1; i <= 3; i++){
				for(int j = 1; j <= 3; j++){
					Node current = nodes[i * j];
					current.addOverNode(nodes[table[i][j]]);
					current.addOverNode(nodes[table[i + 1][j + 1]]);
					current.addOverNode(nodes[table[i][j + 1]]);
					current.addOverNode(nodes[table[i + 1][j]]);
				}
			}
			for(int i = 1; i <= 9; i++){
				if(!solve(nodes[i])){
					broken = true;
					break;
				}
			}
			System.out.print(broken ? "THESE WINDOWS ARE BROKEN" : "THESE WINDOWS ARE CLEAN");
			sca.nextLine();
			sca.nextLine();
			firstLine = sca.nextLine();
		}
		sca.close();
	}
	
	public static void initialize(){
		broken = false;
		nodes = new Node[10];
		status = new  int[10];
		table = new int[5][5];
		for(int i = 1; i <= 9; i++){
			nodes[i] = new Node(i);
		}
	}
	
	public static boolean solve(Node n){
		status[n.name] = - 1;
		boolean childrenLoop = true;
		for(Node m : n.overIt){
			if(m != null){
				if(status[m.name] < 0) return false;
				childrenLoop = (childrenLoop && solve(m));
			}
		}
		status[n.name] = 1;
		return childrenLoop;
	}

}

class Node{
	public Node[] overIt;
	public int name;
	public Node(int c){
		name = c;
		overIt = new Node[4];
	}
	public void addOverNode(Node n){
		if(n.equals(this)) return;
		for(int i = 0; i < 4; i++){
			if(overIt[i] == null) {
				overIt[i] = n;
				return;
			}
			if(overIt[i].equals(n)) return;
		}
	}
	public boolean equals(Object o){
		Node  n = (Node) o;
		return n.name == name;
	}
}
