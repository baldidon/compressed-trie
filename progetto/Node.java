public class Node{

	public int[] index;
	
	public Node parent;
	public Node leftChild;
	public Node rightSibling;

	public boolean isLeaf;

	public String subString;

	public Node(int i, int j, int k, String subString){

		this.index = new int[3];
		this.index[0] = i;
		this.index[1] = j;
		this.index[2] = k;

		this.parent = null;
		this.leftChild = null;
		this.rightSibling = null;

		this.isLeaf = false;

		this.subString = subString;

	} // end constructor


	public void setIndex(int i, int j, int k){ //method for change index of the node

		this.index[0] = i;
		this.index[1] = j;
		this.index[2] = k;

	}

	public void setParent(Node parent){ 
		this.parent = parent;
	}

	public void setLeftChild(Node leftChild){
		this.leftChild = leftChild;
	}

	public void setRightSibling(Node rightSibling){
		this.rightSibling = rightSibling;
	}

	public void setLeaf(boolean isLeaf){
		this.isLeaf = isLeaf;
	}

	public void setSubString(String subString){
		this.subString = subString;
	}
	
	public Node getParent(){
		return this.parent;
	}
	
	public Node getLeftChild(){
		return this.leftChild;
	}
	
	public Node getRightSibling(){
		return this.rightSibling;
	}

	public String getSubString(){
		return this.subString;
	}

}
