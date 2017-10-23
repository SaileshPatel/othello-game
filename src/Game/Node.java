package Game;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
	private T data = null;	
	private Node<T> parent = null;
	private List<Node<T>> children = new ArrayList<Node<T>>();
	
	public Node(T data) {
		this.data = data;
	}
	
	public void addChild(Node<T> child) {
		child.setParent(this);
		this.children.add(child);
	}
	
	public void setParent(Node<T> parent) {
		this.parent = parent;
	}
	
	public T getData() {
		return this.data;
	}
	
	public Node<T> getParent() {
		return this.parent;
	}
	
	public List<Node<T>> getChildren() {
		return this.children;
	}
	
	public boolean isRoot() {
		if (this.parent == null)
			return true;
		return false;
	}
	
	public boolean isLeaf() {
		if (this.getChildren().size() == 0)
			return true;
		return false;
	}
}
