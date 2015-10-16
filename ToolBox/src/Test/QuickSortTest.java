package Test;

import Sort.QuickSort;

public class QuickSortTest {
	
	public static void main(String args[]){
		int[] array = {13, 19, 9, 5, 12, 8, 7, 4, 21, 2, 6, 6, 13, 27, 89, 12,324,32, 43
				, 45,26,74,29,23};
		QuickSort.sort(array);
		int j = array.length - 1;
		//inverse
		for(int i = 0; i < j; i++){
			j = array.length - 1 - i;
			int temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		for(int i = 0; i < array.length; i++)
			System.out.print(array[i] + " ");
	}

}
