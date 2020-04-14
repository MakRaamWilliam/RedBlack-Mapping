package eg.edu.alexu.csd.filestructure.redblacktree;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestTime {

	public static void main(String[] args) throws FileNotFoundException    {
		   	  PrintStream add = new PrintStream(new FileOutputStream("add.txt"));
		    	  PrintStream delete = new PrintStream(new FileOutputStream("delete.txt"));
		    	  PrintStream search = new PrintStream(new FileOutputStream("search.txt"));
		    	  long t;  int ip; Random r = new Random();
				HashMap<Integer, String> map = new HashMap<>();
		        ITreeMap<Integer, String> tree = new TreeMap();
		      
		      
		       for(int i=1000;i<=500000;i+=500) {
			    	  long start= System.currentTimeMillis();
		    	   for(int j=0;j<i;j++) { 
		          int key=r.nextInt(50000); 				    	   
		    	  tree.put(key, "value"+key  ); 
		    	   }  
		    	   t= System.currentTimeMillis()-start;
			    	  System.setOut(search);
			   	      System.out.println(i + "\t" + t);		
			   	  tree.clear();     
		       } 		        
	}
	
}
