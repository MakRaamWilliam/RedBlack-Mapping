package eg.edu.alexu.csd.filestructure.redblacktree;


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
		
	INode node = searchNode(key);
	if(node == null) return null;
		return node.getValue();
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
        	root = new Node( key, (V) value); 
        	root.setLeftChild(nill); root.setRightChild(nill);
        	size++; root.black=true;
        	return ;
        } Node node = new Node(key, value); 
        update=false;
        add(root,node) ;
       node.setLeftChild(nill); node.setRightChild(nill); 
	if ( !update ) { 
       CheckColor(node);
       getRoot().setColor(true);
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
	
	


	@Override
	public boolean delete(Comparable key) { if(key == null ) throw  new RuntimeErrorException(null);
		
		if (root == nill) 
			return false;
				
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
		INode<T,V> leafnode = Min(node.getRightChild());
		node.setKey(leafnode.getKey());
		node.setValue(leafnode.getValue());
		node = leafnode;
		INode<T,V>sibling = deletleaf(node);
		if(!node.getColor())
			correctdel(sibling);
		
		return true;
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
				INode  sibling = node.getParent().getRightChild(); 
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
				INode sibling = node.getParent().getLeftChild(); 
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
		INode  parent = node.getParent();
		INode  sibling = nill;
		if(node.getLeftChild() != nill)
   			 sibling = node.getLeftChild();			
		else 
			 sibling = node.getRightChild();		
		
		if(parent.getLeftChild() == node)
			 parent.setLeftChild(sibling);
		else
			 parent.setRightChild(sibling);
		
		sibling.setParent(parent);
		
		return sibling;
	}	
	
	private	 INode Min(INode node) {
		while( node.getLeftChild() != nill )
			node=node.getLeftChild();
		return node;
	}

    private	 INode Max(INode node) {
		while( node.getRightChild() != nill )
			node=node.getRightChild();
		return node;
	}

	




}


