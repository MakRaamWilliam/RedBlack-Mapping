package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.management.RuntimeErrorException;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree {

	public class Node<T extends Comparable<T>, V> implements INode {
      private Node parent,lefChild,rightChild;
      T key;
      V value;
      boolean black; 
      boolean isleft;
      public Node(T key, V value) {
	this.key = key; this.value=value;
	parent = lefChild = rightChild =null;
	black = isleft =false;
	}
		
		@Override
		public void setParent(INode parent) {
		this.parent =(Node) parent;
			
		}

		@Override
		public INode getParent() {
			
			return parent;
		}

		@Override
		public void setLeftChild(INode leftChild) {
			this.lefChild = (Node) leftChild;
			
		}

		@Override
		public INode getLeftChild() {
			return  lefChild;
		}

		@Override
		public void setRightChild(INode rightChild) {
		this.rightChild = (Node) rightChild;
		}

		@Override
		public INode getRightChild() {
			return rightChild;
		}

		@Override
		public Comparable getKey() {
			return key;
		}

		@Override
		public void setKey(Comparable key) {
         this.key = (T) key;			
		}

		@Override
		public Object getValue() {
			return value;
		}

		@Override
		public void setValue(Object value) {
         this.value = (V) value;			
		}

		@Override
		public boolean getColor() {
			return  !(black);
		}

		@Override
		public void setColor(boolean color) {
          this.black = (color);			
		}

		@Override
		public boolean isNull() {
		
			return (key == null) ;
		}
		
	}
	private Node nill = new Node(null, null);
	
	private Node root=nill ; 
	private int size=0;
	private boolean update;
	public RedBlackTree() {
      nill.black=true;  root =nill; size=0; 
	}

	@Override
	public INode getRoot() {
		return root;
	}

	@Override
	public boolean isEmpty() {
		return (root == nill);
	}
	public int size() {
		return size;
	}

	@Override
	public void clear() {
     clear(root);
     
	}
	
	

	private void clear(Node root2) { size=0;
		if(root2 == nill) {
			root=nill; return;
		}
		clear((Node) root2.getLeftChild());
		clear((Node) root2.getRightChild());
	}

	@Override
	public Object search(Comparable key) { if(key == null) throw  new RuntimeErrorException(null);
		INode node = root;
		while( node!= null && node.getKey()!= null ) { 
			if(key.compareTo(node.getKey()) == 0 ) return node.getValue();
			else if(key.compareTo(node.getKey())>0) node= node.getRightChild();
			else node =node.getLeftChild();
		}
		return null;
	}

	@Override
	public boolean contains(Comparable key) { if(key == null) throw  new RuntimeErrorException(null);
  return(search(key) != null );
	} 
	private INode searchNode(Comparable key) { if(key == null) throw  new RuntimeErrorException(null);
	INode node = root;
	while(node != null && node.getKey()!= null ) {
		if(key.compareTo(node.getKey()) == 0 ) return node;
		else if(key.compareTo(node.getKey())>0) node= node.getRightChild();
		else node =node.getLeftChild();
	}
	return null;
}
	

	@Override
	public void insert(Comparable key, Object value) {  if(key == null || value ==null) throw  new RuntimeErrorException(null);
	     if(root == nill || root ==null) {
        	root = new Node( key, (V) value); root.setLeftChild(nill); root.setRightChild(nill);
        	size++; root.black=true;
        	return ;
        } Node node = new Node(key, value); 
        update=false;
        add(root,node) ;
       node.setLeftChild(nill); node.setRightChild(nill); 
	if ( !update ) { 
       CheckColor(node);
       getRoot().setColor(true);
    //   CheckColor(node);
       } 
	}

	private void add(Node parent, Node node) {
		if(node.key.compareTo(parent.getKey()) == 0 ) { 
			parent.setValue(node.value); update=true; return ; }
	   else if(node.key.compareTo(parent.getKey()) > 0 ) {
			if(parent.getRightChild()== null || parent.getRightChild()==nill ) {
				node.parent=parent; 
				parent.rightChild=node; 
				size++; return ;
			} add((Node) parent.getRightChild(), node);
		} else {
			if(parent.getLeftChild() == null || parent.getLeftChild() == nill) {
	        node.setParent(parent);
	        parent.setLeftChild(node);
	        node.isleft= true;
	        size++; return ;
			} 
			add((Node) parent.getLeftChild(),node);
  		} 
	}
 
	private void CheckColor(Node node) {
      if(node == root ) return;
      if(!node.black && !node.parent.black) {
       CorrecetColor(node);	                    
      } if(node.parent == null || node.parent == nill) return ;
      CheckColor(node.parent);
	}

	
	private void CorrecetColor(Node node) {
		if(node.parent.isleft) {
     if(node.parent.parent.rightChild == null || node.parent.parent.rightChild == nill || node.parent.parent.rightChild.black) {
      Rotate(node);	 return ;
     } if(node.parent.parent.rightChild != null || node.parent.parent.rightChild != nill) {
    	node.parent.parent.rightChild.black=true; 
    	node.parent.parent.black=false; 
    	node.parent.black=true; return ;
     }
	 } else {
		  if(node.parent.parent.lefChild == null ||node.parent.parent.lefChild == nill || node.parent.parent.lefChild.black) {
		       Rotate(node);	 return ; 
		     } if(node.parent.parent.lefChild != null) {
		    	node.parent.parent.lefChild.black=true; 
		    	node.parent.parent.black=false; 
		    	node.parent.black=true; return ;
		     }
	 }
	} 
	
	private void Rotate(Node node) {
		if(node.isleft) {
			if(node.parent.isleft) { 
		RightRotate(node.parent.parent);
		node.black=false; 
		node.parent.black=true;
		if(node.parent.rightChild.isNull() ==false ) node.parent.rightChild.setColor(false); 
			}else {
				RightLeftRotate(node.parent.parent);
		node.black=true;
	 	node.lefChild.setColor(false);
	 	node.rightChild.setColor(false);
			}
		return ;
		}else { 
			if(node.parent.isleft == false) {
				LeftRotate(node.parent.parent);
				node.setColor(false);
				node.parent.setColor(true);
				if(node.parent.lefChild.isNull() == false) node.parent.lefChild.setColor(false);
			}else {
				LeftRightRotat(node.parent.parent);
				node.setColor(true);
			    node.lefChild.setColor(false);
			    node.rightChild.setColor(false);
			}
		}
	}

	private void LeftRightRotat(Node node) {
     LeftRotate(node.lefChild);
     RightRotate(node);
	}

	private void RightLeftRotate(Node node) {
		RightRotate(node.rightChild);
		LeftRotate(node);
	}

	private void RightRotate(Node node) {
		Node temp=node.lefChild;
		node.lefChild= temp.rightChild;
		if( node.lefChild != nill) {
			node.lefChild.setParent(node);
			node.lefChild.isleft= true;  //
		}temp.setParent(node.getParent());
		if(node.parent == null || node.parent == nill) { root = temp;  }
		else {
			if(node.isleft) {
				temp.isleft = true;
				temp.parent.lefChild=temp;
			}else {
				temp.isleft = false;
				temp.parent.rightChild = temp;
			}
		}
		temp.setRightChild(node);
		node.isleft =false;
		node.setParent(temp);
		
	}

	private void LeftRotate(Node node) {
		Node temp=node.rightChild;
		node.rightChild= temp.lefChild;
		if( node.rightChild != nill) {
			node.rightChild.setParent(node);
			node.rightChild.isleft= false;
		}temp.setParent(node.getParent());
		if( node.parent == nill || node.parent == null) { root = temp;  }
		else {
			if(node.isleft) {
				temp.isleft = true;
				temp.parent.lefChild=temp;
			}else {
				temp.isleft = false;
				temp.parent.rightChild = temp;
			}
		}
		temp.setLeftChild(node);
		node.isleft =true;
		node.setParent(temp);
	}
	
	

/*	@Override
	public boolean delete(Comparable key) { if(key == null ) throw  new RuntimeErrorException(null);
		Node sel = (Node) searchNode(key);
		if(sel == null ) return false;
		Node del = Getdeleted(sel); 
		sel.value=del.value; sel.key=del.key; 
		del.key=null; size--;
		if(del.black == false ) {         //delete leaf red node
			if(del.isleft) del.parent.lefChild = null;
			else del.parent.rightChild=null;
			System.out.println("--305");		 return true;
		} if(del == root) {
			root =null;
			size=0; System.out.println("--308");  return true;
		}Node parent = del.parent,sibling; 
		 if(del.isleft ) {   parent.lefChild=null;
		 if( parent.rightChild ==null) { System.out.println("--311");  return true; }
		 sibling= parent.rightChild;
		 if(sibling.lefChild != null && sibling.lefChild.black == false  ) {
			 RightRotate(sibling);
			 LeftRotate(parent);
			 parent.black=true;
			 sibling.black=true;
			 parent.parent.black=false;
			 if(parent.parent.parent == null) { root = parent.parent; parent.parent.black=true; }
			 System.out.println("--320");		  return true;
		 }if(sibling.rightChild != null && sibling.rightChild.black == false  ) {
			 LeftRotate(parent);
			 sibling.lefChild.black=true;
			 sibling.rightChild.black=true;
			 sibling.black=false;
			 if(sibling.parent == null) { root=sibling; sibling.black=true; }
			 System.out.println("--327");		 return true;
		 } 		 
		 if(sibling.black == true ) { parent.black=true;
			 sibling.black= false; System.out.println("--330"); pushup(parent);  return true;
		 }else  {
			 LeftRotate(parent);
			 sibling.black=sibling.rightChild.black=sibling.lefChild.black=true;
			 if(parent.rightChild != null) parent.rightChild.black=false;
			 System.out.println("--335");		  return true;
		 }
		 } else {   del.key=null; parent.rightChild=null;
		 if( parent.lefChild==null) { System.out.println("--338"); return true; }
		 sibling= parent.lefChild;
		 if(sibling.rightChild != null 	 && sibling.rightChild.black == false  ) {
			 LeftRotate(sibling);
			 RightRotate(parent);
			 parent.black=true;
			 sibling.black=true;
			 parent.parent.black=false;
			 if(parent.parent.parent == null) { root = parent.parent; parent.parent.black=true; }
			 System.out.println("--348");			  return true;
		 }if(sibling.lefChild != null && sibling.lefChild.black == false  ) {
			 RightRotate(parent);
			 sibling.lefChild.black=true;
			 sibling.rightChild.black=true;
			 sibling.black=false;
			 if(sibling.parent == null) { root=sibling; sibling.black=true; }
			 System.out.println("--355");	  return true;
		 }if(sibling.black == true ) { parent.black=true;
		 System.out.println("--357");		 sibling.black= false;  return true;
	 }else {
		 RightRotate(parent);
		 sibling.black=sibling.rightChild.black=sibling.lefChild.black=true;
		 if(parent.lefChild != null ) parent.lefChild.black=false;
		System.out.println("--362"); return true;
	 } 		 			 
		 }		 
	}

	private void pushup(Node parent) {
		   if(parent == null || parent.parent == null) return;
			if(parent.isleft ) {
				if(parent.parent.rightChild !=null && parent.parent.rightChild.black==true ) { 
					parent.parent.rightChild.black=false; parent.parent.black=true;
				}
				else {
				LeftRotate(parent.parent);	parent.black=false; pushup(parent.lefChild);
				}
			}else {
				if(parent.parent.lefChild !=null && parent.parent.lefChild.black==true ) parent.parent.lefChild.black=false;
			//	else RightRotate(parent.parent);
			} 
		
		
	}

	private Node Getdeleted(Node sel) { Node temp;
		if(sel.getLeftChild() == null && sel.getRightChild() == null ) return sel;
		if(sel.lefChild ==null ) { temp = sel.rightChild;
		while(temp.getLeftChild() != null ) temp = (Node) temp.getLeftChild();
			return temp;
		}if(sel.getRightChild() == null ) { temp = sel.lefChild;
		while(temp.getRightChild() != null) temp=(Node) temp.getRightChild();
			return temp;
		} 
		    temp = sel.lefChild;
			while(temp.getRightChild() != null) temp=(Node) temp.getRightChild();
			if(temp.black == false ) return temp; 
			temp = sel.rightChild;
			while(temp.getLeftChild() != null ) temp = (Node) temp.getLeftChild();
				return temp;
	} 
	
	
	@Override
	public boolean delete(Comparable key) {
		if (key == null)  throw new RuntimeErrorException(null);	
		if(root == nill) return false;
		INode sel = searchNode(key);		
		if(sel == null || sel ==nill) return false;
		if(sel == root && sel.getLeftChild() == nill && sel.getRightChild() == nill) {
			root =nill;  return true;
		}
		if(sel.getRightChild() == nill ) {
			INode del = deletleaf(sel);
			if(!sel.getColor() ) 
				correctdel(del); 
	CheckColor((Node) sel);		return true;
		}
	  INode del=Min(sel.getRightChild()); 
	  sel.setKey(del.getKey());
	  sel.setValue(del.getValue());
	  sel= del;
	  INode leaf = deletleaf(sel);
	  if(!sel.getColor() ) correctdel(leaf);
	CheckColor((Node) leaf);	return true;
	} */
     
	@Override
	public boolean delete(Comparable key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		
		if (root == nill) {
			return false;
		}
		
		INode<T,V> node = searchNode(key);
		
		if(node == nill || node == null)
			return false;
		size--;
		if (node == root && node.getLeftChild() == nill && node.getRightChild() == nill) {
			root = nill;
			return true;
		}		
		if(node.getRightChild() == nill) {
			INode<T,V>node2 = deletleaf(node);
			if(!node.getColor())
				correctdel(node2);
			return true;
			
		}
		INode<T,V> deletedNode = Min(node.getRightChild());
		node.setKey(deletedNode.getKey());
		node.setValue(deletedNode.getValue());
		node = deletedNode;
		INode<T,V>node2 = deletleaf(node);
		//System.out.println(node2.getKey() + "node 2");
		if(!node.getColor())
			correctdel(node2);
		
		return true;
	}

	 INode Min(INode node) {
		while( node.getLeftChild() != nill )
			node=node.getLeftChild();
		return node;
	}
	 INode Max(INode node) {
		while( node.getRightChild() != nill )
			node=node.getRightChild();
		return node;
	}
	  private void correctdel(INode node) { 
		   while(node.getColor() == false && node != root) {
				if(node.getParent().getLeftChild() == node)
					node = correctLeft(node);
				else
					node = correctRight(node);
		   }
		   node.setColor(true);
		}
	   
		private INode correctLeft(INode<T,V>node) { 
				INode  sibling = node.getParent().getRightChild(); // boolean black=false,red=true;
				if(sibling.getColor() == true) {
					sibling.setColor(true);
					node.getParent().setColor(false);
					LeftRotate((Node) node.getParent());
					sibling = node.getParent().getRightChild();
				}
				if(sibling.getLeftChild().getColor() == false && sibling.getRightChild().getColor() == false) {
					sibling.setColor(false);
					node = node.getParent();
				}else {
					if(sibling.getRightChild().getColor() == false) {
						sibling.getLeftChild().setColor(true);
						sibling.setColor(false);
						RightRotate((Node) sibling);
						sibling = node.getParent().getRightChild();
					}
					sibling.setColor(! sibling.getParent().getColor());
					node.getParent().setColor(true);
					sibling.getRightChild().setColor(true);
					LeftRotate((Node) node.getParent());
					node = root;
					
				}
			return node;
		}
		
		
		private INode correctRight(INode<T,V>node) {
				INode sibling = node.getParent().getLeftChild(); //boolean black=false,red=true;
				if(sibling.getColor() == true) {
					sibling.setColor(true);
					node.getParent().setColor(false);
					RightRotate((Node) node.getParent());
					sibling = node.getParent().getLeftChild();
				}
				if(sibling.getLeftChild().getColor() == false && sibling.getRightChild().getColor() == false) {
					sibling.setColor(false);
					node = node.getParent();
				}else {
					if(sibling.getLeftChild().getColor() == false) {
						sibling.getRightChild().setColor(true);
						sibling.setColor(false);
						LeftRotate((Node) sibling);
						sibling = node.getParent().getLeftChild();
					}
					sibling.setColor( !sibling.getParent().getColor());
					node.getParent().setColor(true);
					sibling.getLeftChild().setColor(true);
					RightRotate((Node) node.getParent());
					node = root;
					
				}
			return node;
		}
	
	

	private INode deletleaf(INode node) {
		if (node == root) {
			root = (Node) root.getLeftChild();
			return root;
		}
		INode<T,V> p = node.getParent();
		INode<T,V> term = nill;
		if(node.getLeftChild() != nill)
		{
			term = node.getLeftChild();
		}
			
		else {
			term = node.getRightChild();

		}
		
		if(p.getLeftChild() == node)
			p.setLeftChild(term);
		else
			p.setRightChild(term);
		
		term.setParent(p);
		
		return term;
	}	
	
	
	




}


