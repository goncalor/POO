package project;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class GUI {
	private static JLabel label = new JLabel("Dynamic Bayesian Networks");
	private static JButton codeRun = new JButton("Run Code!");
	static GUI gui = new GUI();
	private static JTree tree;
	
	public static void main(String[] args) {
		// build frame 
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Projecto POO");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GUI.setupJTree();
		
		// build painel with text
		JPanel pane = new JPanel();
		pane.add(tree);
		pane.add(new JScrollPane(tree));
		pane.add(label);
		pane.add(codeRun);
		codeRun.addActionListener(gui.new runHandler());
		 
		frame.setContentPane(pane);
		frame.pack();
		frame.setVisible(true);
	}
	
	private static void setupJTree(){
		//create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Datasets");
        //create the child nodes
        DefaultMutableTreeNode trainNode = new DefaultMutableTreeNode("Train");
        DefaultMutableTreeNode testNode = new DefaultMutableTreeNode("Test");
        
        testNode.add(new DefaultMutableTreeNode("test-data.csv"));
        testNode.add(new DefaultMutableTreeNode("test-data-1.csv"));
        testNode.add(new DefaultMutableTreeNode("test-data-2.csv"));

		trainNode.add(new DefaultMutableTreeNode("train-data.csv"));
		trainNode.add(new DefaultMutableTreeNode("train-data-1.csv"));
		trainNode.add(new DefaultMutableTreeNode("train-data-2.csv"));
 
        //add the child nodes to the root node
        root.add(testNode);
        root.add(trainNode);
        
         
        //create the tree by passing in the root node
        tree = new JTree(root);
	}
	
	private class runHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			label.setText("Button was pressed!");
		}
		
	}

}
