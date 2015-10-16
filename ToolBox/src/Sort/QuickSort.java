package Sort;

public class QuickSort {
	
	public static void sort(Sortable[] array, int startIndex, int endIndex){
		if(startIndex >= endIndex){
			return;
		}
		int q = partition(array, startIndex, endIndex);
		sort(array, startIndex, q - 1);
		sort(array, q + 1, endIndex);
	}
	
	public static int partition(Sortable[] array, int startIndex, int endIndex){
		double x = array[endIndex].getKey();
		int i = startIndex - 1;
		int j = startIndex;
		for(; j < endIndex - 1; j++){
			if(array[j].getKey() < x){
				i++;
				Sortable temp = array[j];
				array[j] = array[i];
				array[i] = temp;
			}
		}
		Sortable temp = array[endIndex];
		array[endIndex] = array[i + 1];
		array[i + 1] = temp;
		return i + 1;
	}
	
	public static void sort(int[] array, int startIndex, int endIndex){
		if(startIndex >= endIndex){
			return;
		}
		int q = partition(array, startIndex, endIndex);
		sort(array, startIndex, q - 1);
		sort(array, q + 1, endIndex);
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
	
	public static void sort(int[] array){
		int startIndex = 0;
		int endIndex = array.length - 1;
		if(startIndex >= endIndex){
			return;
		}
		int q = partition(array, startIndex, endIndex);
		sort(array, startIndex, q - 1);
		sort(array, q + 1, endIndex);
	}
	
	public static void sort(Sortable[] array){
		int startIndex = 0;
		int endIndex = array.length - 1;
		if(startIndex >= endIndex){
			return;
		}
		int q = partition(array, startIndex, endIndex);
		sort(array, startIndex, q - 1);
		sort(array, q + 1, endIndex);
	}
	
	public static int Random(int start, int end){
		double d = Math.random();
		d *= (end - start);
		d += start;
		return (int)d;
	}
}
