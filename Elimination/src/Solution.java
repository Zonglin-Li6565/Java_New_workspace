import java.io.*;
import java.util.Scanner;

public class Solution {
	public static void main(String args[] ) throws Exception {
		/* Enter your code here. Read input from STDIN. Print output to STDOUT */
		Scanner sca = new Scanner(System.in);
		int N = sca.nextInt(), M = sca.nextInt(), counter = N;
		Node current = new Node(1);
		Node first = current;
		for(int i = 2; i <= N; i++){
			Node next = new Node(i);
			next.previous = current;
			current.next = new Node(i);
			current = current.next;
		}
		current.next = first;
		//current = first;
		while(counter >= M){
			for(int i = 0; i < M; i++){
				
			}
		}
	}
	
	private static class Node{
		public int number;
		public Node next;
		public Node previous;
		
		public Node(int number){
			this.number = number;
		}
	}
}
