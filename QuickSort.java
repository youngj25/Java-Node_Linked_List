public class QuickSort{  
  /* The method for sorting the numbers */
  public static void Test() {
      
      int[] list={5, 21,1, 3,5,98,32,10, 25, 13,44,66,77,199, 33, 43, 78};
      //int[] list=new int[100];
      
      //for(int x=0;x<100;x++)
        //list[x]=(int) (Math.random()*1000);
      
      quickSort(list,0,list.length-1);
      
      for(int x=0;x<list.length;x++){
          System.out.print(list[x]);
          if((x+1)<list.length) System.out.print("-");
      }
    }
    
    
  public static void quickSort(int list[], int left, int right) {
          //int pivot = partition(list, left, right);
          
          //if (left < pivot - 1) quickSort(list, left, pivot - 1);
                
          //if (pivot < right) quickSort(list, pivot, right);
  }  
    
    
  public static int partition(int[] array, int low, int high){
		    int pivot = array[low];
		    int i = low - 1;
		    int j  = high + 1;
		    while (i < j){
		        i++;
		    	while(array[i] < pivot)
		    	    i++;
		    	 
		    	
		    	j--;
		    	while(array[j] > pivot)
		    	    j--;
		    	 
		    	
		    	if (i<j){
		    		int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
		        }
		    }
		    return j;
		}
}