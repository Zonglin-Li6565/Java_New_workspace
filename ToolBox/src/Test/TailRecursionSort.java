package Test;

import java.util.ArrayList;

public class TailRecursionSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> list = new ArrayList<Integer>();
		int[] array = new int[100000];
		for(int i = 1; i <= 100000; i++){
			int rand = Random(0, 1000000);
			list.add(rand);
			array[i - 1] = rand;
		}
		System.out.println(list);
		TailRecursionQuickSort(array, 0, 99999);
		list = new ArrayList<Integer>();
		for(int i = 0; i < array.length; i++){
			list.add(array[i]);
		}
		System.out.println(list);
	}
	
	public static int partition(int[] array, int startIndex, int endIndex){
		int random = Random(startIndex, endIndex);
		int temp_1 = array[endIndex];
		array[endIndex] = array[random];
		array[random] = temp_1;
		int x = array[endIndex];
		int i = startIndex - 1;
		int j = startIndex;
		for(; j < endIndex; j++){
			if(array[j] < x){
				i++;
				int temp = array[j];
				array[j] = array[i];
				array[i] = temp;
			}
		}
		int temp_2 = array[endIndex];
		array[endIndex] = array[i + 1];
		array[i + 1] = temp_2;
		return i + 1;
	}
	
	public static int Random(int start, int end){
		double d = Math.random();
		d *= (end - start);
		d += start;
		return (int)d;
	}
	
	public static void TailRecursionQuickSort(int[] array, 
			int startIndex, int endIndex){
		while(startIndex < endIndex){
			int q = partition(array, startIndex, endIndex);
			TailRecursionQuickSort(array, startIndex, q - 1);
			startIndex = q + 1;
		}
		
	}

}
