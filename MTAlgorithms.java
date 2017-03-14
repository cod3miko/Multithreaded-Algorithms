// GUI-related imports
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class  MTAlgorithms extends Frame implements ActionListener
{
	String[] description = new String[] {
			"This program examines and displays the effect of MultiThreading via Sorting Algorithms."
	};
	
	static int threshold = 1000;
	
	// Retrieved command code
	boolean arrayInitialized = false;
	int NDataItems = 10000000;
	int[] a = new int[NDataItems];
	
	int maximumSerial;
	int maximumParallel;
	
	MenuItem miAbout,
	         miInitArr,
	         miSerialSort, anSerialSort,
	         miMultiThreadedMergeSort, anMultiThreadedMergeSort,
	         miMultiThreadedQuickSort, anMultiThreadedQuickSort,
	         miJavaParallelSort, anJavaParallelSort;
	
	long start, elapsedTimeSerialMax, elapsedTimeParallelMax,
				elapsedTimeSerialSort, elapsedTimeParallelMergeSort,
				elapsedTimeParallelQuickSort,elapsedTimeJavaParallelSort;
	
	String command = "";
			
	public static void main(String[] args)
	{
		Frame frame = new  MTAlgorithms();
		
		frame.setResizable(true);
		frame.setSize(800,500);
		frame.setVisible(true);
	}
		
	public  MTAlgorithms()
	{
		setTitle("Parallel Algorithms");
		
		// Create Menu Bar	
		MenuBar mb = new MenuBar();
		setMenuBar(mb);
					
		Menu menu = new Menu("Operations");
		// Add it to Menu Bar	
		mb.add(menu);
		
		Menu analysis = new Menu("Analysis");
		// Add it to Menu Bar	
		mb.add(analysis);
		
		// Create Menu Items
		// Add action Listener 
		// Add to "File" Menu Group
		
		miAbout = new MenuItem("About");
		miAbout.addActionListener(this);
		menu.add(miAbout);
		
	    miInitArr = new MenuItem("Initialize Array");
		miInitArr.addActionListener(this);
		menu.add(miInitArr);
		
		miSerialSort = new MenuItem("Serial Sort");
		miSerialSort.addActionListener(this);
		miSerialSort.setEnabled(false);
		menu.add(miSerialSort);
		
		miMultiThreadedMergeSort = new MenuItem("MultiThreaded MergeSort");
		miMultiThreadedMergeSort.addActionListener(this);
		miMultiThreadedMergeSort.setEnabled(false);
		menu.add(miMultiThreadedMergeSort);
		
		miMultiThreadedQuickSort = new MenuItem("MultiThreaded QuickSort");
		miMultiThreadedQuickSort.addActionListener(this);
		miMultiThreadedQuickSort.setEnabled(false);
		menu.add(miMultiThreadedQuickSort);
		
		miJavaParallelSort = new MenuItem("Java Parallel Sort");
		miJavaParallelSort.addActionListener(this);
		miJavaParallelSort.setEnabled(false);
		menu.add(miJavaParallelSort);
		
		anSerialSort = new MenuItem("Serial Sort Threshold");
		anSerialSort.addActionListener(this);
		anSerialSort.setEnabled(false);
		analysis.add(anSerialSort);
		
		anMultiThreadedMergeSort = new MenuItem("MultiThreaded MergeSort Threshold");
		anMultiThreadedMergeSort.addActionListener(this);
		anMultiThreadedMergeSort.setEnabled(false);
		analysis.add(anMultiThreadedMergeSort);
		
		anMultiThreadedQuickSort = new MenuItem("MultiThreaded QuickSort Threshold");
		anMultiThreadedQuickSort.addActionListener(this);
		anMultiThreadedQuickSort.setEnabled(false);
		analysis.add(anMultiThreadedQuickSort);
		
		anJavaParallelSort = new MenuItem("Java Parallel Sort Threshold");
		anJavaParallelSort.addActionListener(this);
		anJavaParallelSort.setEnabled(false);
		analysis.add(anJavaParallelSort);
		
		MenuItem miExit = new MenuItem("Exit");
		miExit.addActionListener(this);
		menu.add(miExit);
			
		// End program when window is closed
		WindowListener l = new WindowAdapter()
		{			
			public void windowClosing(WindowEvent ev)
			{System.exit(0);}
			
			public void windowActivated(WindowEvent ev)
			{repaint();}
			
			public void windowStateChanged(WindowEvent ev)
			{repaint();}
		};
		
		ComponentListener k = new ComponentAdapter()
		{
			public void componentResized(ComponentEvent e) 
			{repaint();}
		};
		
		// register listeners
		this.addWindowListener(l);
		this.addComponentListener(k);
	}
		
	//******************************************************************************
	//  called by windows manager whenever the application window performs an action
	//  (select a menu item, close, resize, ....
	//******************************************************************************

	public void actionPerformed (ActionEvent ev) 
	{
		// figure out which command was issued
		command = ev.getActionCommand();
				
		// take action accordingly
		if("About".equals(command))
		{
			repaint();
		}
		else
		if("Initialize Array".equals(command))
		{
			InitializeArrays();
			arrayInitialized = true;
			miSerialSort.setEnabled(true);
			miMultiThreadedMergeSort.setEnabled(true);
			miMultiThreadedQuickSort.setEnabled(true);
			miJavaParallelSort.setEnabled(true);
			anSerialSort.setEnabled(true);
			anMultiThreadedMergeSort.setEnabled(true);
			anMultiThreadedQuickSort.setEnabled(true);
			anJavaParallelSort.setEnabled(true);
			repaint();
		}
		else
		if("Serial Sort".equals(command))
		{
			MergeSort k = new MergeSort();
			int[] b = new int[a.length];
			System.arraycopy(a, 0, b, 0, a.length);
			
			start = System.currentTimeMillis();
			k.mergeSort(b);
			elapsedTimeSerialSort = System.currentTimeMillis() - start;
			
			repaint();
		}
		else
		if("MultiThreaded MergeSort".equals(command))
		{
			// create a new array, copy original array to it
			int[] b = new int[a.length];
			System.arraycopy(a, 0, b, 0, a.length);
				
			start = System.currentTimeMillis();
			ParallelMergeSort.startMainTask(b,threshold);
			
			elapsedTimeParallelMergeSort = System.currentTimeMillis()-start;
				
			if (isSorted(b)){
				repaint();
			}else{
				System.out.println("Array is not sorted ---- multiThreaded MergeSort");
			}
		}
		else
		if("MultiThreaded QuickSort".equals(command))
		{
			// create a new array, copy original array to it
			int[] b = new int[a.length];
			System.arraycopy(a, 0, b, 0, a.length);
			
			start = System.currentTimeMillis();
			ParallelQuickSort.startMainTask(b,threshold);
					
			elapsedTimeParallelQuickSort = System.currentTimeMillis()-start;
			
			if (isSorted(b)){
				repaint();
			}else{
				System.out.println("Array is not sorted ---- multiThreaded MergeSort");
			}
		}
		else
		if("Java Parallel Sort".equals(command))
		{
			//create a new array, copy original array to it
			int[] b = new int[a.length];
			System.arraycopy(a, 0, b, 0, a.length);
			
			start = System.currentTimeMillis();
			Arrays.parallelSort(b);
			elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
			
			repaint();
		}
		else
		if("Serial Sort Threshold".equals(command))
		{
			repaint();
		}
		else
		if("MultiThreaded MergeSort Threshold".equals(command))
		{
			repaint();
		}
		else
		if("MultiThreaded QuickSort Threshold".equals(command))
		{
			repaint();
		}
		else
		if("Java Parallel Sort Threshold".equals(command))
		{
			repaint();
		}
		else
		if("Exit".equals(command))
		{
			System.exit(0);
		}
	}
	//********************************************************
	// called by repaint() to redraw the screen
	//********************************************************
			
	public void paint(Graphics g)
	{
		g.drawString(
			"Number of processors is "+Integer.toString( Runtime.getRuntime().availableProcessors() ),300,130);
		g.drawString("Number of Data Items = "+Integer.toString(NDataItems),300, 150);
		
		if( "Serial Sort".equals(command) 
			|| "MultiThreaded MergeSort".equals(command)
			|| "MultiThreaded QuickSort".equals(command)
			|| "Java Parallel Sort".equals(command))
		{
			g.drawString("Threshold = "+Integer.toString(threshold),300, 170);
			g.drawString("Method", 250, 200); g.drawString("Elapsed Time (ms)", 475, 200);
			g.drawLine(200, 210, 600, 210);
			g.drawString("Serial Sort (MergeSort)", 225, 230);
				g.drawString(Float.toString(elapsedTimeSerialSort), 475, 230);
			g.drawString("Parallel MergeSort", 225, 250);
				g.drawString(Float.toString(elapsedTimeParallelMergeSort), 475, 250);
			g.drawString("Parallel QuickSort", 225, 270);
				g.drawString(Float.toString(elapsedTimeParallelQuickSort), 475, 270);
			g.drawString("Java Parallel Sort (MergeSort)", 225, 290);
				g.drawString(Float.toString(elapsedTimeJavaParallelSort), 475, 290);
		}	
		else	
		if("Serial Sort Threshold".equals(command))
		{
			g.drawString("Serial Sort (MergeSort)", 325, 180);
			g.drawString("Threshold", 250, 200); g.drawString("Elapsed Time (ms)", 475, 200);
			g.drawLine(200, 210, 600, 210);
			
			MergeSort k = new MergeSort();
			int[] b = new int[a.length];
			
			int y = 230;
			for (int i=1; i<=4; i++)
			{
				System.arraycopy(a, 0, b, 0, a.length);
				g.drawString(Integer.toString(threshold), 250, y);
				start = System.currentTimeMillis();
				k.mergeSort(b);
				elapsedTimeSerialSort = System.currentTimeMillis() - start;
				g.drawString(Float.toString(elapsedTimeSerialSort), 475, y);
				y=y+20;
				threshold = threshold * 10;
			}
			threshold = 2000000;
			System.arraycopy(a, 0, b, 0, a.length);
			g.drawString(Integer.toString(threshold), 250, 310);
			start = System.currentTimeMillis();
			k.mergeSort(b);
			elapsedTimeSerialSort = System.currentTimeMillis() - start;
			g.drawString(Float.toString(elapsedTimeSerialSort), 475, 310);
			
			threshold = 2500000;
			System.arraycopy(a, 0, b, 0, a.length);
			g.drawString(Integer.toString(threshold), 250, 330);
			start = System.currentTimeMillis();
			k.mergeSort(b);
			elapsedTimeSerialSort = System.currentTimeMillis() - start;
			g.drawString(Float.toString(elapsedTimeSerialSort), 475, 330);
			//restart the threshold
			threshold = 1000;
			
			System.arraycopy(a, 0, b, 0, a.length);
			start = System.currentTimeMillis();
			Arrays.parallelSort(b);
			elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
			g.setColor(Color.red);
			g.drawString("Java Parallel Sort Elapsed Time:", 250, 370);
			g.drawString(Float.toString(elapsedTimeJavaParallelSort), 475, 370);
			g.drawString(" w/ Threshold = 1000", 525, 370);
		}
		else	
		if("MultiThreaded MergeSort Threshold".equals(command))
		{
			g.drawString("MultiThreaded MergeSort", 325, 180);
			g.drawString("Threshold", 250, 200); g.drawString("Elapsed Time (ms)", 475, 200);
			g.drawLine(200, 210, 600, 210);
			
			int[] b = new int[a.length];
				
			int y = 230;
			for (int i=1; i<=4; i++)
			{
				g.drawString(Integer.toString(threshold), 250, y);
				System.arraycopy(a, 0, b, 0, a.length);
				start = System.currentTimeMillis();
				ParallelMergeSort.startMainTask(b,threshold);
				elapsedTimeParallelMergeSort = System.currentTimeMillis()-start;
				g.drawString(Float.toString(elapsedTimeParallelMergeSort), 475, y);
				y=y+20;
				threshold = threshold * 10;
			}
			threshold = 2000000;
			System.arraycopy(a, 0, b, 0, a.length);
			g.drawString(Integer.toString(threshold), 250, 310);
			start = System.currentTimeMillis();
			ParallelMergeSort.startMainTask(b,threshold);
			elapsedTimeParallelMergeSort = System.currentTimeMillis() - start;
			g.drawString(Float.toString(elapsedTimeParallelMergeSort), 475, 310);
			
			threshold = 2500000;
			System.arraycopy(a, 0, b, 0, a.length);
			g.drawString(Integer.toString(threshold), 250, 330);
			start = System.currentTimeMillis();
			ParallelMergeSort.startMainTask(b,threshold);
			elapsedTimeParallelMergeSort = System.currentTimeMillis() - start;
			g.drawString(Float.toString(elapsedTimeParallelMergeSort), 475, 330);
			//restart the threshold
			threshold = 1000;
			
			System.arraycopy(a, 0, b, 0, a.length);
			start = System.currentTimeMillis();
			Arrays.parallelSort(b);
			elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
			g.setColor(Color.red);
			g.drawString("Java Parallel Sort Elapsed Time:", 250, 370);
			g.drawString(Float.toString(elapsedTimeJavaParallelSort), 475, 370);
			g.drawString(" w/ Threshold = 1000", 525, 370);
		}
		else	
		if("MultiThreaded QuickSort Threshold".equals(command))
		{
			g.drawString("MultiThreaded QuickSort", 325, 180);
			g.drawString("Threshold", 250, 200); g.drawString("Elapsed Time (ms)", 475, 200);
			g.drawLine(200, 210, 600, 210);
			

			int[] b = new int[a.length];
			int y = 230;
			for (int i=1; i<=4; i++)
			{

				System.arraycopy(a, 0, b, 0, a.length);
				g.drawString(Integer.toString(threshold), 250, y);
				start = System.currentTimeMillis();
				ParallelQuickSort.startMainTask(b,threshold);
				elapsedTimeParallelQuickSort = System.currentTimeMillis()-start;
				g.drawString(Float.toString(elapsedTimeParallelQuickSort), 475, y);
				y=y+20;
				threshold = threshold * 10;
			}
			threshold = 2000000;
			System.arraycopy(a, 0, b, 0, a.length);
			g.drawString(Integer.toString(threshold), 250, 310);
			start = System.currentTimeMillis();
			ParallelQuickSort.startMainTask(b,threshold);
			elapsedTimeParallelQuickSort = System.currentTimeMillis() - start;
			g.drawString(Float.toString(elapsedTimeParallelQuickSort), 475, 310);
			
			threshold = 2500000;
			System.arraycopy(a, 0, b, 0, a.length);
			g.drawString(Integer.toString(threshold), 250, 330);
			start = System.currentTimeMillis();
			ParallelQuickSort.startMainTask(b,threshold);
			elapsedTimeParallelQuickSort = System.currentTimeMillis() - start;
			g.drawString(Float.toString(elapsedTimeParallelQuickSort), 475, 330);
			//restart the threshold
			threshold = 1000;
			
			System.arraycopy(a, 0, b, 0, a.length);
			start = System.currentTimeMillis();
			Arrays.parallelSort(b);
			elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
			g.setColor(Color.red);
			g.drawString("Java Parallel Sort Elapsed Time:", 250, 370);
			g.drawString(Float.toString(elapsedTimeJavaParallelSort), 475, 370);
			g.drawString(" w/ Threshold = 1000", 525, 370);
		}
		else
		if("Java Parallel Sort Threshold".equals(command))
		{
			g.drawString("Java Parallel Sort (MergeSort)", 325, 180);
			g.drawString("Threshold", 250, 200); g.drawString("Elapsed Time (ms)", 475, 200);
			g.drawLine(200, 210, 600, 210);
			
			int[] b = new int[a.length];
				
			int y = 230;
			for (int i=1; i<=4; i++)
			{
				System.arraycopy(a, 0, b, 0, a.length);
				g.drawString(Integer.toString(threshold), 250, y);
				start = System.currentTimeMillis();
				Arrays.parallelSort(b);
				elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
				g.drawString(Float.toString(elapsedTimeJavaParallelSort), 475, y);
				y=y+20;
				threshold = threshold * 10;
			}
			threshold = 2000000;
			System.arraycopy(a, 0, b, 0, a.length);
			g.drawString(Integer.toString(threshold), 250, 310);
			start = System.currentTimeMillis();
			Arrays.parallelSort(b);
			elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
			g.drawString(Float.toString(elapsedTimeJavaParallelSort), 475, 310);
			
			threshold = 2500000;
			System.arraycopy(a, 0, b, 0, a.length);
			g.drawString(Integer.toString(threshold), 250, 330);
			start = System.currentTimeMillis();
			Arrays.parallelSort(b);
			elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
			g.drawString(Float.toString(elapsedTimeJavaParallelSort), 475, 330);
			//restart the threshold
			threshold = 1000;
			
			System.arraycopy(a, 0, b, 0, a.length);
			start = System.currentTimeMillis();
			Arrays.parallelSort(b);
			elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
			g.setColor(Color.red);
			g.drawString("Java Parallel Sort Elapsed Time:", 250, 370);
			g.drawString(Float.toString(elapsedTimeJavaParallelSort), 475, 370);
			g.drawString(" w/ Threshold = 1000", 525, 370);
		}
		else	
		if("About".equals(command))
		{
			int x = 200;
			int y = 200;
			for(int i = 0; i < description.length; i++)
			{
				g.drawString(description[i], x, y);
				y = y +25;
			}
		}
		else
		if("Initialize Array".equals(command))
		{
			g.drawString("Array Initialized",200, 100);
		}	
		else
		if("Initialize Array".equals(command))
		{
			g.drawString("Array Initialized",200, 100);
		}
		
	}

	public void InitializeArrays ()
	{
		maximumSerial=	maximumParallel = -1;
		
		start = elapsedTimeSerialMax = elapsedTimeParallelMax =
				elapsedTimeSerialSort =  elapsedTimeParallelMergeSort = 
				elapsedTimeParallelQuickSort = elapsedTimeJavaParallelSort = 0;
		
		for (int i=0; i<a.length; i++)
			a[i] = (int) (Math.random()*400000000);
	}
	
	public boolean isSorted(int[] list)
	{
		boolean sorted = true;
		int index = 0;
		
		while (sorted & index<list.length-1)
		{
			if (list[index] > list[index+1])
				sorted = false;
			else
				index++;	
		}
		return sorted;
	}
}