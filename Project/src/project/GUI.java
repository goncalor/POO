package project;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class GUI {
	private static JLabel label = new JLabel("Dynamic Bayesian Networks");
	private static JLabel trainLabel = new JLabel("Choose train file");
	private static JLabel testLabel = new JLabel("Choose test file");
	private static JLabel paramLabel = new JLabel("<- Choose scoring");
	private static JButton codeRun = new JButton("Run Code!");
	static GUI gui = new GUI();
	private static JTree treeTrain;
	private static JTree treeTest;

	static String textArea = "";
	static int iterator =0;
	
	public static void main(String[] args) {
		// build frame 
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Projecto POO");
		JTextField textField = new JTextField(20);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final String newline = "\n";
		
		GUI.setupJTreeTrain();
		GUI.setupJTreeTest();
		
		// build painel with text
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(3,3));
		pane.add(treeTrain);
		pane.add(new JScrollPane(treeTrain));
		pane.add(trainLabel);
		pane.add(codeRun);
		pane.add(treeTest);
		pane.add(new JScrollPane(treeTest));
		pane.add(testLabel);
		pane.add(label);
		pane.add(textField);
		pane.add(paramLabel);
		codeRun.addActionListener(gui.new runHandler());
		textField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
			    String text = textField.getText();
			    textArea +=text;
			    textField.selectAll();
			    System.out.println(textArea);
			    switch (iterator) {
			    case 0:
			    	paramLabel.setText("<- Number of random restarts");
			    	iterator++;
			    	break;
			    case 1:
			    	paramLabel.setText("<- Choose Var");
			    	iterator++;
			    	break;
			    case 2:
			    	paramLabel.setText("<- Choose scoring");
			    	iterator = 0;
			    	break;
			    }
			}
		});
        treeTrain.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
        		//Returns the last path element of the selection.
        		//This method is useful only when the selection model allows a single selection.
        	    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
        	                       treeTrain.getLastSelectedPathComponent();

        	    if (node == null)
        	    //Nothing is selected.     
        	    return;

        	    Object nodeInfo = node.getUserObject();
        	    if (node.isLeaf()) {
        	        String file = (String)nodeInfo;
        	        trainLabel.setText(file);
        	    }
            }
        });
        treeTest.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
        		//Returns the last path element of the selection.
        		//This method is useful only when the selection model allows a single selection.
        	    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
        	                       treeTest.getLastSelectedPathComponent();

        	    if (node == null)
        	    //Nothing is selected.     
        	    return;

        	    Object nodeInfo = node.getUserObject();
        	    if (node.isLeaf()) {
        	        String file = (String)nodeInfo;
        	        testLabel.setText(file);
        	    }
            }
        });
		frame.setContentPane(pane);
		frame.pack();
		frame.setSize(600,400);
		frame.setVisible(true);
	}
	
	public static void layoutTree(JTree tree){
		Dimension size = tree.getPreferredSize();
		tree.setBounds(100,100,size.width,size.height);
	}
	
	
	private static void setupJTreeTrain(){
		//create the root node
        DefaultMutableTreeNode trainNode = new DefaultMutableTreeNode("Train");

        //add the child nodes to the root node
        
		trainNode.add(new DefaultMutableTreeNode("train-data.csv"));
		trainNode.add(new DefaultMutableTreeNode("train-data-1.csv"));
		trainNode.add(new DefaultMutableTreeNode("train-data-2.csv"));
        
         
        //create the tree by passing in the root node
        treeTrain = new JTree(trainNode);
        treeTrain.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}
	
	private static void setupJTreeTest(){
		//create the root node
        DefaultMutableTreeNode testNode = new DefaultMutableTreeNode("Test");
        
        //add the child nodes to the root node
           
        testNode.add(new DefaultMutableTreeNode("test-data.csv"));
        testNode.add(new DefaultMutableTreeNode("test-data-1.csv"));
        testNode.add(new DefaultMutableTreeNode("test-data-2.csv"));
         
        //create the tree by passing in the root node
        treeTest = new JTree(testNode);
        treeTest.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}
	
	private class runHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			label.setText("Button was pressed!");
		}
		
	}

}
