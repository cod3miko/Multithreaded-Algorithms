import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSort 
{
	int[] list;
	int threshold;
	
	public ParallelMergeSort(int[] a, int threshold)
	{
		this.list = a; 
		this.threshold = threshold;
	}
	
	public static void startMainTask(int[] list, int threshold) 
	{
		RecursiveAction mainTask = new SortTask(list,threshold);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(mainTask);
	}

	private static class SortTask extends RecursiveAction 
	{
		private int[] list;
		private int threshold;
    
		SortTask(int[] list, int threshold) 
		{
			this.list = list;
			this.threshold = threshold;
		}

		protected void compute() {
			if (list.length < threshold)
			{
				Arrays.sort(list);
			}
			else {
		        // Obtain the first half
		        int[] firstHalf = new int[list.length / 2];
		        System.arraycopy(list, 0, firstHalf, 0, list.length / 2);
		
		        // Obtain the second half
		        int secondHalfLength = list.length - list.length / 2;
		        int[] secondHalf = new int[secondHalfLength];
		        System.arraycopy(list, list.length / 2, 
		          secondHalf, 0, secondHalfLength);
		
		        // Recursively sort the two halves
		        SortTask first  = new SortTask (firstHalf,threshold);
		        SortTask second = new SortTask (secondHalf,threshold);
		        invokeAll( first,second );
		
		        // Merge firstHalf with secondHalf into list
		        MergeSort.merge(firstHalf, secondHalf, list);
			}
		}
	}
}