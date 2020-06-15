import java.util.LinkedList;

public class Node{

	public int[] index;
	
	public Node parent;
	public Node leftChild;
	public Node rightSibling;
	private LinkedList<Integer> occurrency; //lista in cui salvo le occorrenze!

	public boolean isLeaf;

	public Node(int i, int j, int k){

		this.index = new int[3];
		this.index[0] = i;
		this.index[1] = j;
		this.index[2] = k;

		this.parent = null;
		this.leftChild = null;
		this.rightSibling = null;
		this.occurrency = null;

		this.isLeaf = false;

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
	
	public Node getParent(){
		return this.parent;
	}
	
	public Node getLeftChild(){
		return this.leftChild;
	}
	
	public Node getRightSibling(){
		return this.rightSibling;
	}

	public int getStringIndex(){
		return this.index[0];
	}
	public int[] getSubstring(){ 
		int[] res =  {this.index[1] ,  this.index[2]};
		return res;
	}

	public int getSubstringLength(){
		return index[2] - index[1];
	}

	public void createListOfOccurrency(){
		this.occurrency = new LinkedList<>();
	}

	public LinkedList<Integer> getListOfOccurrency(){
		return this.occurrency;
	}
	public int numOfOccurrency(){
		return this.occurrency.size();
	}

	public void addOccurrency(int i){
		this.occurrency.addLast(i);
	}

	public boolean hasOccurrency(){
		return !this.occurrency.isEmpty();
	}

}
