package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.management.RuntimeErrorException;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap {

	private IRedBlackTree tree = new RedBlackTree();
	private int size=0;
	private ArrayList<Entry<T, V>> arr = new ArrayList<>();
//	private Set<Comparable<T>> keys = new LinkedHashSet<>();
//	private Collection values = new LinkedHashSet<>();
//	private Set<Entry<T, V>> entries = new LinkedHashSet<>();

	@Override
	public Entry ceilingEntry(Comparable key) { if(key == null ) throw new RuntimeErrorException(null);
	INode node = ceilingnode(key);
	if(node == null ) return null;
	else   return new  AbstractMap.SimpleEntry(node.getKey(), node.getValue());
	}

	@Override
	public Comparable ceilingKey(Comparable key) { if(key == null ) throw new RuntimeErrorException(null);
		INode node = ceilingnode(key);
		if(node == null ) return null;
		else return node.getKey();		
	}

	@Override
	public void clear() {
	tree.clear();
	}

	@Override
	public boolean containsKey(Comparable key) {
		
		return tree.contains(key);
	}

	@Override
	public   boolean containsValue(Object value) { if(value== null) throw new RuntimeErrorException(null);
		if(tree.isEmpty()) return false;
		 Set<Entry<T, V>> entries = new LinkedHashSet<>();
	 	 inorder( tree.getRoot(),entries);
		 for(int i=0;i<arr.size();i++) {
			 if(arr.get(i).getValue().equals(value)) return true;
		 }
		return false;
	}

	@Override
	public Set entrySet() {
	 Set<Entry<T, V>> entries = new LinkedHashSet<>();
       inorder( tree.getRoot(),entries);
       
		return entries;
	}

	@Override
	public Entry firstEntry() {
		if(tree.isEmpty() ) return null; 
		INode node = Min(tree.getRoot());
		return new  AbstractMap.SimpleEntry(node.getKey(), node.getValue());
	}

	@Override
	public Comparable firstKey() {
		if(tree.isEmpty() ) return null; 
		INode node = Min(tree.getRoot());
		return node.getKey();
	}

	@Override
	public Entry floorEntry(Comparable key) { if(key == null ) throw new RuntimeErrorException(null);
	INode node = flooringnode(key);
	if(node == null ) return null;
	else   return new  AbstractMap.SimpleEntry(node.getKey(), node.getValue());
		
	}

	@Override
	public Comparable floorKey(Comparable key) {if(key == null ) throw new RuntimeErrorException(null);
	INode node = flooringnode(key);
	if(node == null ) return null;
	else   return node.getKey();
		
	}

	@Override
	public Object get(Comparable key) {if(key == null ) throw new RuntimeErrorException(null);
	return tree.search(key);
	}

	@Override
	public ArrayList headMap(Comparable toKey) {
	  Set<Entry<T, V>> entries = new LinkedHashSet<>();
      inorder(tree.getRoot(),entries); int index =arr.size()-1;
      while(index>=0 && arr.get(index).getKey().compareTo((T) toKey) >= 0) {
    	  arr.remove(index); index=arr.size()-1;
      }
		return arr;
	}

	@Override
	public ArrayList headMap(Comparable toKey, boolean inclusive) {
		 Set<Entry<T, V>> entries = new LinkedHashSet<>();
		 inorder(tree.getRoot(),entries); int index =arr.size()-1;
	      while(index>=0 && arr.get(index).getKey().compareTo((T) toKey) > 0) {
	    	  arr.remove(index); index=arr.size()-1;
	      }
	      if(index>=0 && arr.get(index).getKey().compareTo((T) toKey) == 0 && !inclusive)
	    	  arr.remove(index);
			return arr;
	}

	@Override
	public Set keySet() {
		inorder(tree.getRoot(),new LinkedHashSet<>());
	  Set<Comparable<T>> keys = new LinkedHashSet<>();
  for(int i=0;i<arr.size();i++)
	  keys.add(arr.get(i).getKey());
		return keys;
	}

	@Override
	public Entry lastEntry() {
		if(tree.isEmpty() ) return null;
		INode node = Max(tree.getRoot());
   return new  AbstractMap.SimpleEntry(node.getKey(), node.getValue());
	}

	@Override
	public Comparable lastKey() {
		if(tree.isEmpty()) return null;
		INode node = Max(tree.getRoot());
		return node.getKey();
	}

	@Override
	public Entry pollFirstEntry() { 
	 if(tree.isEmpty()) return null;
	 Entry temp =  this.firstEntry();
	 tree.delete(this.firstKey()); size--;
	 return temp;
	}

	@Override
	public Entry pollLastEntry() {
		if(tree.isEmpty()) return null;
		 Entry temp =  this.lastEntry();
		 tree.delete(this.lastKey()); size--;
		 return  temp;
	}

	@Override
	public void put(Comparable key, Object value) {
		if(tree.search(key) !=null ) size--;
		tree.insert(key, value); size++;
	}

	@Override
	public void putAll(Map map) { if(map == null) throw new RuntimeErrorException(null);
	  Set<Comparable<T>> keys = new LinkedHashSet<>();
	   keys=map.keySet();
		 Iterator keysit = keys.iterator();
		 Iterator valuesit = values().iterator();
		 while(keysit.hasNext()) {
			 Comparable<T> ke=  (T) keysit.next();
			if(tree.contains(ke)) size--;
			tree.insert(ke, map.get(ke)); size++;
		 }
		
	}

	@Override
	public boolean remove(Comparable key) {
		if(! tree.contains(key)) size++;
		size--;
		return tree.delete(key);
	}

	@Override
	public int size() { if(tree.isEmpty()) return 0;
	
	return size;
	}

	@Override
	public Collection values() {		
		inorder(tree.getRoot(),new LinkedHashSet<>() );
		 Collection values = new LinkedHashSet<>();
    for(int i=0;i<arr.size();i++)
    	values.add(arr.get(i).getValue());
		return values;
	}
	
	 INode Min(INode node) {
			while( node.getLeftChild() !=null &&node.getLeftChild().isNull() ==false )
				node=node.getLeftChild();
			return node;
		}
		 INode Max(INode node) {
			while( node.getRightChild() != null && node.getRightChild().isNull()==false )
				node=node.getRightChild();
			return node;
		}
	
		 public INode ceilingnode(Comparable key) { if(key == null) throw  new RuntimeErrorException(null);
			INode node = tree.getRoot(),temp=null; 
	//		if( tree.search(key)  !=null ) {  node.setKey(key);node.setValue(tree.search(key));  return node; }
			node=tree.getRoot(); 
			while( node!= null && node.getKey()!= null ) { temp=node;
				if(key.compareTo(node.getKey()) == 0 ) return  node;
				else if(key.compareTo(node.getKey())>0  ) node= node.getRightChild();
				else if( key.compareTo(node.getKey())<0 && node.getLeftChild().getKey()==null  ) return node;
				else if( key.compareTo(node.getKey())<0 &&  node.getLeftChild().getKey().compareTo(key)<0  ) {
					if(node.getLeftChild().getRightChild().getKey() == null  ) return node;			
					else node=node.getLeftChild();
				}
				else if(key.compareTo(node.getKey())<0) node=node.getLeftChild(); 
			}
			while( temp.getParent()!=null && temp.getKey().compareTo(key)<0 ) {
				temp=temp.getParent();
			}
			return temp;
		}
			public INode flooringnode(Comparable key) { if(key == null) throw  new RuntimeErrorException(null);
			INode node = tree.getRoot(),temp=null;
	//		if( tree.search(key)  !=null ) {  node.setKey(key);node.setValue(tree.search(key));  return node; }
			while( node!= null && node.getKey()!= null ) { temp=node;
				if(key.compareTo(node.getKey()) == 0 ) return  node;
				else if(key.compareTo(node.getKey())<0  ) node= node.getLeftChild();
				else if( key.compareTo(node.getKey())>0 && node.getRightChild().getKey()==null  ) return node;
				else if( key.compareTo(node.getKey())>0 &&  node.getRightChild().getKey().compareTo(key)>0  ) {
					if(node.getRightChild().getLeftChild().getKey() == null  ) return node;
					else node=node.getRightChild();
				}
				else if(key.compareTo(node.getKey())>0) node=node.getRightChild(); 
			}
			while(temp != null && temp.getParent().getKey().compareTo(key)>0 )
				temp=temp.getParent();
			return temp;
		}
		 
		 
		 
	@SuppressWarnings("unchecked")
	private <T> void inorder( INode node,  Set<Entry<T, V>> entries ) {
		if(node==null || node.getKey() == null ) return ;
		inorder( node.getLeftChild(),  entries );
		arr.add(new  AbstractMap.SimpleEntry(node.getKey(), node.getValue()) );
		entries.add(new  AbstractMap.SimpleEntry(node.getKey(), node.getValue()) );
//		keys.add( (node.getKey()) );
	//	values.add((V) node.getValue());
		inorder( node.getRightChild(),entries);
	}
	
	
	

}
