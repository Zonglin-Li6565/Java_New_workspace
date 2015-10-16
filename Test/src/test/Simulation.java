package test;

public class Simulation {
	
	public static void main(String args[]){
		long[] average = new long[28];
		for(int i = 0; i < 20000; i++){
			long[] result = prediction(30);
			for(int j = 0; j < result.length; j++){
				average[j] += result[j];
			}
		}
		System.out.print("[ ");
		for(int i = 0 ; i < average.length; i++){
			System.out.print(average[i] + " ");
		}
		System.out.print("]");
		DisplayPanel display = new DisplayPanel(average);
		display.setVisible(true);
	}
	
	public static long[] prediction(int iterations){
		long[] slots = new long[28];
		int steps = 0;
		for(int i = 1; i <= iterations; i++){
			slots[steps % 28] ++;
			steps += randomNumber();
		}
		return slots;
	}
	
	public static int randomNumber(){
		int random = (int)(Math.random()*6 + 1);
		return random;
		
	}

}
