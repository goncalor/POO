package project;

import java.util.ArrayList;
import java.util.List;

public class Node {

	protected List<Node> parents;
	protected List<Node> children;
	
	public Node()
	{
		parents = new ArrayList<Node>();
		children = new ArrayList<Node>();
	}
	
	public void addParent(Node p)
	{
		parents.add(p);
	}
	
	public void remParent(Node p)
	{
		parents.remove(p);
	}
	
	public void addChild(Node c)
	{
		children.add(c);
	}
	
	public void remChild(Node c)
	{
		children.remove(c);
	}
}
