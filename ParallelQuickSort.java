import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelQuickSort 
{
	int[] list;
	int threshold;
	int start;
	int end;
	
	public ParallelQuickSort(int[] a, int threshold)
	{
		this.list = a; 
		this.threshold = threshold;
        this.start = 0;
        this.end = list.length - 1;
	}
	
	public static void startMainTask(int[] list, int threshold) 
	{
		RecursiveAction mainTask = new SortTask(list, threshold);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(mainTask);
	}

	private static class SortTask extends RecursiveAction 
	{
		private int[] list;
		private int threshold;
	    private int start;
	    private int end;
    
		SortTask(int[] list, int threshold) 
		{
		    this.list = list;
		    start = 0;
		    end = list.length - 1;
			this.threshold = threshold;
		}
		
		SortTask(int[] list, int start, int end) 
		{
			this.list = list;
			this.start = start;
			this.end = end;
		}

		protected void compute() {
			if (list.length < threshold)
			{
				Arrays.sort(list);
			}
			else {
			    if(start < end){
			    	int pivot = partition(list, start, end);
			    	invokeAll(new SortTask(list,start, pivot),
			                  new SortTask(list, pivot + 1, end));
			    }
			}
		}

		private int partition(int[] array, int low, int high) 
		{
		    int pivot = array[low];
		    int i = low - 1;
		    int j  = high + 1;
		    while (true){
		    	do {
		    		i++;
		    	}
		    	while (array[i] < pivot);
		    	do {
		    		j--;
		    	}
		    	while (array[j] > pivot);
		    	if (i >= j)
		    		return j;
		    	swap(array, i, j);
		    }
		}
		private void swap(int[] array, int i, int j) 
		{
		    int temp = array[i];
		    array[i] = array[j];
		    array[j] = temp;
		}
	}
}