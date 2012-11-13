
public class TrinaryTree {

	
	public static class Node{
		Node leftChild;
		Node middleChild; 
		Node rightChild;
		Node parent; 

		int value;

		public Node(int value) {
		this.parent = null; 
		this.leftChild = null;
		this.rightChild = null;
		this.middleChild = null;
		this.value = value;
		}
	}
	
	/**
	 * Create a new node and insert it in the tree
	 * @param treeNode root of the tree
	 * @param value value of the new node
	 */
	public void insert(Node treeNode, int value) {
		// left insertion
		if (value < treeNode.value) {
			if(treeNode.leftChild == null){
				treeNode.leftChild = new Node(value);
				treeNode.leftChild.parent = treeNode;
			}else{
				insert(treeNode.leftChild,value);
			}
			return;
		} 
		
		// right insertion
		if (value > treeNode.value) {
			if(treeNode.rightChild == null){
				treeNode.rightChild = new Node(value);
				treeNode.rightChild.parent = treeNode;
			}else{
				insert(treeNode.rightChild,value);
			}
			return;
		} 
		
		// middle insertion
		if(treeNode.middleChild == null){
			treeNode.middleChild = new Node(value);
			treeNode.middleChild.parent = treeNode;
		}else{
			insert(treeNode.middleChild,value);
		}
	}
	
	
	/**
	 * Insert a node in a tree
	 * @param startingNode root of the tree
	 * @param newNode node to insert
	 */
	public void insert(Node startingNode, Node newNode) {
		// left insertion
		if (newNode.value < startingNode.value) {
			if(startingNode.leftChild == null){
				startingNode.leftChild = newNode;
				startingNode.leftChild.parent = startingNode;
			}else{
				insert(startingNode.leftChild,newNode.value);
			}
			return;
		} 
		
		// right insertion
		if (newNode.value > startingNode.value) {
			if(startingNode.rightChild == null){
				startingNode.rightChild = newNode;
				startingNode.rightChild.parent = startingNode;
			}else{
				insert(startingNode.rightChild,newNode.value);
			}
			return;
		} 
		
		// middle insertion
		if(startingNode.middleChild == null){
			startingNode.middleChild = newNode;
			startingNode.middleChild.parent = startingNode;
		}else{
			insert(startingNode.middleChild,newNode.value);
		}
	}
	
	/**
	 * Find a node to delete
	 * In the node is not in the tree, returns null
	 * @param treeNode
	 * @param value
	 * @return
	 */
	public Node findNode(Node treeNode, int value) {
		if (value < treeNode.value) {
			return findNode(treeNode.leftChild,value);
		}
		
		if (value > treeNode.value) {
			return findNode(treeNode.rightChild,value);
		}
		
		if (value == treeNode.value) {
			// we found the node
			return treeNode;
		}
		// node is not in the tree
		return null;
	}
	
	/**
	 * Remove a node from the tree
	 * @param treeNode root of the tree
	 * @param value value of the node to remove
	 */
	public void delete(Node treeNode, int value) {
		Node target = findNode(treeNode, value);
		
		if(target == null){
			// node is not in the tree
			return;
		}
		
		// target has middleChild, so we delete the last of the middle child
		if(target.middleChild != null){
			while (target.middleChild != null){
				target = target.middleChild;
			}
			target = null;
			return;
		}
		
		// target has no children
		if(target.rightChild == null && target.leftChild == null){
			target = null;
			return;
		}
		
		// at this point the terget nog could have left, right or both of those children
		
		// target has  left children
		if(target.leftChild != null){
			Node tmpNode = target.leftChild;
			this.insert(tmpNode, tmpNode.parent.leftChild);
			tmpNode.parent.leftChild = tmpNode;
		}
		
		// target has  right children
		if(target.rightChild != null){
			Node tmpNode = target.rightChild;
			this.insert(tmpNode.parent.rightChild, tmpNode);
			
		}
		
		target = null;
		return;
		
	}
	
	
}
