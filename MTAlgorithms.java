    // GUI-related imports
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class  MTAlgorithms extends Frame implements ActionListener{
        // http://ark.intel.com/products/43544/Intel-Core-i5-540M-Processor-3M-Cache-2_53-GHz
        String[] description = new String[] {
                "This program executes single(multi)-threaded ",
                "four different sort routines",
                "  ---- Sequential MergeSort",
                "  ---- Multi-Threaded MergeSort",
                "  ---- Multi-Threaded QuickSort",
                "  ---- Java Parallel Sort",
                                " and analyzes the effect of threshold and thread count on the performance"
        };
        
        // Retrieved command code
        boolean arrayInitialized = false;
        int NDataItems = 100000;
        int[] a = new int[NDataItems];
        int nThreads;
        
        
        long[] et = new long[7];
        long[] nt;//number of threads?
        int[]  t = {NDataItems/2, NDataItems/4, NDataItems/8, NDataItems/16, NDataItems/32 };
        int threshold = NDataItems/4;
        
        MenuItem miAbout,
                 miInitArr,  
                 miSerialSort,
                 miMultiThreadedMergeSort,
                 miMultiThreadedQuickSort,
                 miJavaParallelSort,
                 miMergeSort,
                 miQuickSort,
                 miThreadCntMS,
                 miThreadCntQS;
        
        long start, elapsedTimeSerialSort, elapsedTimeParallelMergeSort,
                    elapsedTimeParallelQuickSort,elapsedTimeJavaParallelSort;
                    
        String command = "";
            
        public static void main(String[] args){
            Frame frame = new  MTAlgorithms();            
            frame.setResizable(true);
            frame.setSize(1000,500);
            frame.setVisible(true);            
        }
        
        public  MTAlgorithms(){
            setTitle("Parallel Algorithms");
            
            nThreads = Runtime.getRuntime().availableProcessors()*2;
            System.out.println("INITIALIZATION  -- nThreads --" +nThreads);
            nt = new long[nThreads+1];
            System.out.println("nThreads --" +nt.length);
            // Create Menu Bar                        
            MenuBar mb = new MenuBar();
            setMenuBar(mb);
                        
            Menu menu = new Menu("Operations");
            
            // Add it to Menu Bar                        
            mb.add(menu);
            
            // Create Menu Items
            // Add action Listener 
            // Add to "File" Menu Group            
            miAbout = new MenuItem("About");
            miAbout.addActionListener(this);
            menu.add(miAbout);
            
            miInitArr = new MenuItem("Initialize Array");
            miInitArr.addActionListener(this);
            menu.add(miInitArr);
            
            Menu mSort = new Menu ("Sort Algorithms");
            mb.add(mSort);
            
            miSerialSort = new MenuItem("Serial Sort");
            miSerialSort.addActionListener(this);
            miSerialSort.setEnabled(false);
            mSort.add(miSerialSort);
            
            miMultiThreadedMergeSort = new MenuItem("MultiThreaded MergeSort");
            miMultiThreadedMergeSort.addActionListener(this);
            miMultiThreadedMergeSort.setEnabled(false);
            mSort.add(miMultiThreadedMergeSort);
            
            miMultiThreadedQuickSort = new MenuItem("MultiThreaded QuickSort");
            miMultiThreadedQuickSort.addActionListener(this);
            miMultiThreadedQuickSort.setEnabled(false);
            mSort.add(miMultiThreadedQuickSort);
            
            miJavaParallelSort = new MenuItem("Java Parallel Sort");
            miJavaParallelSort.addActionListener(this);
            miJavaParallelSort.setEnabled(false);
            mSort.add(miJavaParallelSort);
            
            MenuItem miExit = new MenuItem("Exit");
            miExit.addActionListener(this);
            menu.add(miExit);
            
                        
            Menu menuAnalysis = new Menu("Analysis");
            
            // Add it to Menu Bar                        
            mb.add(menuAnalysis);
            
            // Create Menu Items
            // Add action Listener 
            // Add to "File" Menu Group            
            Menu mThreshold = new Menu("Threshold Analysis");
            menuAnalysis.add(mThreshold);
            
            miMergeSort = new MenuItem("Merge Sort");
            miMergeSort.addActionListener(this);
            miMergeSort.setEnabled(false);
            mThreshold.add(miMergeSort);
            
            miQuickSort = new MenuItem("Quick Sort");
            miQuickSort.addActionListener(this);
            miQuickSort.setEnabled(false);
            mThreshold.add(miQuickSort);
            
            Menu mThreadCount = new Menu ("Thread Count Analysis");
            menuAnalysis.add(mThreadCount);
            
            miThreadCntMS = new MenuItem("merge sort");
            miThreadCntMS.addActionListener(this);
            miThreadCntMS.setEnabled(false);
            mThreadCount.add(miThreadCntMS);
            
            miThreadCntQS = new MenuItem("quick sort");
            miThreadCntQS.addActionListener(this);
            miThreadCntQS.setEnabled(false);
            mThreadCount.add(miThreadCntQS);
            
            // End program when window is closed            
            WindowListener l = new WindowAdapter(){
                            
                public void windowClosing(WindowEvent ev){
                    System.exit(0);
                }
                
                public void windowActivated(WindowEvent ev){
                    repaint();
                }
                
                public void windowStateChanged(WindowEvent ev){
                    repaint();
                }
            
            };
            
            ComponentListener k = new ComponentAdapter() {
                public void componentResized(ComponentEvent e){
                    repaint();           
                }
            };
            
            // register listeners                
            this.addWindowListener(l);
            this.addComponentListener(k);
        }
        
        //******************************************************************************
        //  called by windows manager whenever the application window performs an action
        //  (select a menu item, close, resize, ....
        //******************************************************************************
        
        public void actionPerformed (ActionEvent ev) {
                // figure out which command was issued                
                command = ev.getActionCommand();
                
                // take action accordingly    
                if("About".equals(command)){                    
                    repaint();
                }
                else if("Initialize Array".equals(command)){
                    InitializeArrays();
                    arrayInitialized = true;
                     
                     miSerialSort.setEnabled(true);
                     miMultiThreadedMergeSort.setEnabled(true);
                     miMultiThreadedQuickSort.setEnabled(true);
                     miJavaParallelSort.setEnabled(true);
                     miMergeSort.setEnabled(true);
                     miQuickSort.setEnabled(true);
                     miThreadCntMS.setEnabled(true);
                     miThreadCntQS.setEnabled(true);
                    repaint();
                }
                
                else if("Serial Sort".equals(command)){
                        MergeSort k = new MergeSort();
                        int[] b = new int[a.length];
                        System.arraycopy(a, 0, b, 0, a.length);
                        
                        start = System.currentTimeMillis();
                        k.mergeSort(b);
                        elapsedTimeSerialSort = System.currentTimeMillis() - start;
                        
                        repaint();
                    }
                else if("MultiThreaded MergeSort".equals(command)){
                        // create a new array, copy original array to it
                        int[] b = new int[a.length];
                        //Copies the list into the new array
                        System.arraycopy(a, 0, b, 0, a.length);
                        
                        //Gets the number of threads available
                        nThreads = Runtime.getRuntime().availableProcessors();
                        start = System.currentTimeMillis();
                        ParallelMergeSort.startMainTask(b,threshold,nThreads);
                        
                        elapsedTimeParallelMergeSort = System.currentTimeMillis()-start;
                        if (isSorted(b))
                            repaint();
                        else
                            System.out.println("Array is not sorted ---- multiThreaded MergeSort");
                    }
                else if("MultiThreaded QuickSort".equals(command)){
                            // create a new array, copy original array to it
                            int[] b = new int[a.length];
                            System.arraycopy(a, 0, b, 0, a.length);
                            
                            start = System.currentTimeMillis();
                            ParallelQuickSort.startMainTask(b,threshold,nThreads);                            
                            elapsedTimeParallelQuickSort = System.currentTimeMillis()-start;
                            
                            if (isSorted(b))
                                repaint();
                            else
                                System.out.println("Array is not sorted ---- multiThreaded QuickSort");
                        }
                else if("Java Parallel Sort".equals(command)){
                        //create a new array, copy original array to it
                        int[] b = new int[a.length];
                        System.arraycopy(a, 0, b, 0, a.length);
                        
                        start = System.currentTimeMillis();
                        Arrays.parallelSort(b);
                        elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
                        
                        repaint();
                    }                
                /**Threshold Analysis for Merge Sort and Quick Sort**/                       
                else if("Merge Sort".equals(command)||"Quick Sort".equals(command)){
                        // create a new array, copy original array to it
                        int[] b = new int[a.length];
                        
                        System.arraycopy(a, 0, b, 0, a.length);
                        start = System.currentTimeMillis();
                        Arrays.parallelSort(b);
                        elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
                        
                        System.arraycopy(a, 0, b, 0, a.length);
                        // ********************************************************* complete the code
                        if("Merge Sort".equals(command))
                            for(int x=0;x<t.length;x++){
                                b= new int[a.length];
                                System.arraycopy(a,0,b,0,a.length);
                                
                                start = System.currentTimeMillis();
                                ParallelMergeSort.startMainTask(b,t[x],nThreads);
                                et[x] = System.currentTimeMillis()-start;                                
                            }                        
                        else if("Quick Sort".equals(command))
                            for(int x=0;x<t.length;x++){
                                b= new int[a.length];
                                System.arraycopy(a,0,b,0,a.length);
                                
                                start = System.currentTimeMillis();
                                ParallelQuickSort.startMainTask(b,t[x],nThreads);
                                et[x] = System.currentTimeMillis()-start;                                
                            }     
                            
                        repaint();
                    }                 
                /**Thread Count for Merge Sort & Quick Sort**/       
                else if("merge sort".equals(command)||"quick sort".equals(command)){
                        // create a new array, copy original array to it
                        int[] b = new int[a.length];
                                
                        System.arraycopy(a, 0, b, 0, a.length);
                        start = System.currentTimeMillis();
                        Arrays.parallelSort(b);
                        elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
                                
                        System.arraycopy(a, 0, b, 0, a.length);
                                
                        // ********************************************************* complete the code
                        
                        if("merge sort".equals(command))
                            for(int x=0;x<nt.length;x++){
                                b= new int[a.length];
                                System.arraycopy(a,0,b,0,a.length);
                                
                                start = System.currentTimeMillis();
                                ParallelMergeSort.startMainTask(b,threshold,x+1);
                                nt[x] = System.currentTimeMillis()-start;                            
                            }       
                        else if("quick sort".equals(command))
                            for(int x=0;x<nt.length;x++){
                                b= new int[a.length];
                                System.arraycopy(a,0,b,0,a.length);
                                
                                start = System.currentTimeMillis();
                                ParallelQuickSort.startMainTask(b,threshold,x+1);
                                nt[x] = System.currentTimeMillis()-start;                            
                            }  
                            
                        repaint();
                            }
                else if("Exit".equals(command)) System.exit(0);
            }
            
        //********************************************************
        // called by repaint() to redraw the screen
        //********************************************************
                
        public void paint(Graphics g){
                Font f = new Font(Font.SANS_SERIF,Font.BOLD,18);
                g.setFont(f);
                g.drawString("Number of processors is "
                             +Integer.toString( Runtime.getRuntime().availableProcessors() ),
                             350,130);
                g.drawString("Number of Data Items = "+Integer.toString(NDataItems),350, 150);                
                
                if( "Serial Sort".equals(command ) || "MultiThreaded MergeSort".equals(command)
                    || "MultiThreaded QuickSort".equals(command)|| "Java Parallel Sort".equals(command)){
                    this.setSize(1000,500);
                    g.drawString("Threshold = "+Integer.toString(threshold),350, 170);
                    g.drawString(" Method",       200, 200);
                    
                    g.drawString(" Elapsed Time", 650, 200);
                    g.drawLine(200,210,800,210);                    
                    
                    g.drawString("Serial Sort (MergeSort)", 200, 230);
                    g.drawString(Long.toString(elapsedTimeSerialSort), 700, 230);
                    
                    g.drawString("Parallel MergeSort ", 200, 255);
                    g.drawString(Long.toString(elapsedTimeParallelMergeSort), 700, 255);
                
                    g.drawString("Parallel QuickSort ", 200, 280);
                    g.drawString(Long.toString(elapsedTimeParallelQuickSort), 700, 280);
                    
                    g.drawString("Java Parallel Sort (MergeSort) ", 200, 305);
                    g.drawString(Long.toString(elapsedTimeJavaParallelSort), 700, 305);
                }                   
                else if("About".equals(command)){
                    this.setSize(1000,500);
                    int x = 200;
                    int y = 200;
                    for(int i = 0; i < description.length; i++){
                        g.drawString(description[i], x, y);
                        y = y +25;
                    }
                }
                else if("Initialize Array".equals(command)){
                        g.setColor(Color.red);
                        g.drawString("Array Initialized",450, 100);
                        g.setColor(Color.black);
                    }                    
                /**Analysis-Threshold for Merge & Quick Sort**/
                else if("Merge Sort".equals(command)||"Quick Sort".equals(command)){
                        this.setSize(1000,500);
                        // create a new array, copy original array to it
                        g.setColor(Color.red);
                        g.drawString("Java Paralel Sort Elapsed Time = ", 350, 100);
                        g.drawString(Long.toString(elapsedTimeJavaParallelSort), 650, 100);
                        g.setColor(Color.black);
                        
                        if("Merge Sort".equals(command))g.drawString("Multi Threaded MergeSort ",400, 170);
                        if("Quick Sort".equals(command))g.drawString("Multi Threaded QuickSort ",400, 170);
                        g.drawString("Threshold ",350, 200);
                        g.drawString("Elapsed Time", 600, 200);
                        g.drawLine(300, 220, 750, 220);
                        int y = 270;
                        for (int i = 0; i<t.length; i++){
                            g.drawString(Integer.toString(t[i]),350, y);
                            
                            //insert answere here
                            g.drawString(Long.toString(et[i]), 600, y);
                            
                            y=y+20;
                        }
                }
                /**Analysis-Thread Count for Merge & Quick Sort**/
                else if("merge sort".equals(command)||"quick sort".equals(command)){
                        // ********************************************************* complete the code
                        this.setSize(1000,500);
                        // create a new array, copy original array to it
                        g.setColor(Color.red);
                        g.drawString("Java Paralel Sort Elapsed Time = ", 350, 100);
                        g.drawString(Long.toString(elapsedTimeJavaParallelSort), 650, 100);
                        g.setColor(Color.black);
                        if("merge sort".equals(command))g.drawString("Multi Threaded MergeSort ",400, 170);
                        if("quick sort".equals(command)) g.drawString("Multi Threaded QuickSort ",400, 170);
                        g.drawString("#Threads ",350, 200);
                        g.drawString("Elapsed Time", 600, 200);
                        g.drawLine(300, 220, 750, 220);
                        int y = 270;
                        for (int i = 0; i<nt.length; i++){
                            g.drawString(Integer.toString(i+1)+"#",350, y);
                            
                            //insert answere here
                            g.drawString(Long.toString(nt[i]), 600, y);
                            
                            y=y+20;
                        }
                    }
                
                g.setColor(Color.blue);
                Font original = g.getFont();
                Font tr = new Font("Brush Script MT", Font.PLAIN, 40);
                g.setFont(tr);
                g.drawString("Jason A. Young ",30, 465);
                g.setFont(original);
                g.setColor(Color.black);                    
            }

        public void InitializeArrays (){
            start = elapsedTimeSerialSort =  elapsedTimeParallelMergeSort = 
                    elapsedTimeParallelQuickSort = elapsedTimeJavaParallelSort = 0;
            for (int i=0; i<a.length; i++)
                a[i] = (int) (Math.random()*400000000);
        }
        
        public boolean isSorted(int[] list){
            boolean sorted = true;
            int index = 0;
            while (sorted & index<list.length-1)
                if (list[index] > list[index+1])
                    sorted = false;
                else
                    index++;                
            return sorted;
        }
}