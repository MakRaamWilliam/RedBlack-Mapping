package eg.edu.alexu.csd.filestructure.redblacktree;

import java.io.ObjectInputStream.GetField;import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.junit.Assert;

public class Testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RedBlackTree tree = new RedBlackTree<>();
//		 System.out.println(tree.getRoot().getColor());
	//	for (int i = 0; i < 100; ++i) {			int key = r.nextInt(1000);
///			tree.insert(key, "toto" + key);
///			System.out.println(key+ "  " + tree.getRoot().getKey());
	//	//	Assert.assertTrue((boolean) this.verifyProps(redBlackTree.getRoot()));
	//	}
/*		HashSet<Integer> list = new HashSet<Integer>();
		for (int i = 0; i < 100000; ++i) {
			int key = r.nextInt(10000);
			list.add(key);
			tree.insert(key, "soso" + key);
		}
		for (Integer elem : list) {
      System.out.println(tree.delete(elem));		
		}
		System.out.println("------------");
		System.out.println(tree.getRoot().isNull()); 
	*/	 
		tree.insert(29, 29);
		tree.insert(36, 36);
     	tree.insert(70, 70);
		tree.insert(20, 20);
		tree.insert(33, 33);
		tree.insert(55, 55); 
		tree.insert(2, 2); 
		tree.insert(40,40);
		tree.insert(78,78);
     	tree.insert(85,85);
		tree.insert(90,90);
		tree.insert(12,12);
		tree.insert(31,31); 
		tree.insert(7,7); 
		tree.insert(100,100);
		tree.delete(55);
		tree.delete(20);
		RBTreePrinter rb = new RBTreePrinter();
		rb.print(tree.getRoot());
//		tree.inorder(tree.getRoot());
/*		Random r = new Random();
		HashSet<Integer> list = new HashSet<Integer>();
		for(int i=1;i<=200;i++) { 
			int key = r.nextInt(10000);
			list.add(key);
			tree.insert(key, "soso" + key);
		}
		for (Integer elem : list) { if(elem %2 == 0)
			 System.out.println(tree.delete(elem) + " " + elem);
		}
		System.out.println(tree.getRoot().getKey());
		System.out.println( verifyProperty5(tree.getRoot()) );
		*/
	
		
	
	}
	
}
