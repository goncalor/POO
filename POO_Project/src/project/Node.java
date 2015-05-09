package project;

import java.util.ArrayList;
import java.util.List;

public class Node {

	/** list of parents of this node */
	protected List<Node> parents;
	/** list of children of this node */
	protected List<Node> children;
	
	public Node()
	{
		parents = new ArrayList<Node>();
		children = new ArrayList<Node>();
	}
	
	/** add parent {@code p} to this node */
	public void addParent(Node p)
	{
		parents.add(p);
	}
	
	/** remove parent {@code p} from this node, if {@code p} is a parent */
	public void remParent(Node p)
	{
		parents.remove(p);
	}
	
	/** add child {@code c} to this node */
	public void addChild(Node c)
	{
		children.add(c);
	}
	
	/** remove child {@code c} from this node, if {@code c} is a child */
	public void remChild(Node c)
	{
		children.remove(c);
	}
	
	/** checks if {@code c} is a parent of this node */
	public boolean isParent(Node c)
	{
		return this.children.contains(c);
	}
	
	/** check if {@code p} is a child of this node */
	public boolean isChild(Node p)
	{
		return this.parents.contains(p);
	}
}
