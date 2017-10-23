package Game;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {
	private Node<T> root;
	private ArrayList<Node<T>> nodes;
	
	public Tree(Node<T> root) {
		this.root = root;
		this.nodes.add(root);
	}
	
	public void addNode(Node<T> node) {
		this.nodes.add(node);
	}
	
	public Node<T> getRoot() {
		return root;
	}
	
	public List<Node<T>> getNodes() {
		return nodes;
	}
}
