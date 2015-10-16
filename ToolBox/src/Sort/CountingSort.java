package Sort;

public class CountingSort {
	
	/**
	 * Sort the integer Array
	 * @param A	The original array
	 * @param B	The result array
	 * @param largestValue
	 */
	public static int[] sort(int[] A, int largestValue){
		int[] B = new int[A.length];
		int[] C = new int[largestValue + 1];
		for(int i : A){					//O(n)
			C[i] ++;
		}
		for(int i = 1; i < C.length; i++){		//O(largestValue)
			C[i] += C[i-1];
		}
		for(int i = 0; i < A.length; i++){
			B[C[A[i]] - 1] = A[i];
			C[A[i]] --;
		}
		return B;
	}
	
	/**
	 * Using CountingSort algorithm to sort an Array of <code>Sortable</code>
	 * Objects. The key value of each <code>Sortable</code> Object will be converted
	 * to <code>int</code> explicitly
	 * @param A
	 * @param largestValue
	 * @return
	 */
	public static Sortable[] sort(Sortable[] A, int largestValue){
		Sortable[] B = new Sortable[A.length];
		int[] C = new int[largestValue + 1];
		for(Sortable i : A){					//O(n)
			C[(int) i.getKey()] ++;
		}
		for(int i = 1; i < C.length; i++){		//O(largestValue)
			C[i] += C[i-1];
		}
		for(int i = 0; i < A.length; i++){
			B[C[(int)A[i].getKey()] - 1] = A[i];
			C[(int)A[i].getKey()] --;
		}
		return B;
	}

}
