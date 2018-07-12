import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelQuickSort{
    int[] list;
    int threshold;
    
    public ParallelQuickSort(int[] array, int thres)    {
        list = array; 
        threshold = thres;
    }
    
    public static void startMainTask(int[] array, int thres,int nThreads){
        RecursiveAction mainTask = new SortTask(array, thres);
        ForkJoinPool pool = new ForkJoinPool(nThreads);
        pool.invoke(mainTask);
    }

    private static class SortTask extends RecursiveAction{
        private int[] list;
        private int threshold;
        private int left;
        private int right;
    
        SortTask(int[] array, int thres){
            list = array;
            left = 0;
            right = list.length - 1;
            threshold = thres;
        }
        
        SortTask(int[] array, int L, int R){
            list = array;
            left = L;
            right = R;
        }

        protected void compute(){
            if (list.length < threshold)
                    Arrays.parallelSort(list);  
            else if(left < right){
                    int pivot=QuickSort.partition(list, left, right);
                    //int pivot=partition(list, left, right);
                    invokeAll(new SortTask(list,left, pivot),
                              new SortTask(list, pivot + 1, right));
                }
        }
    }
}