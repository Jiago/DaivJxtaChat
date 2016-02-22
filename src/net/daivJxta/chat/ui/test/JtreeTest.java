package net.daivJxta.chat.ui.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JTree;

public class JtreeTest extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JtreeTest frame = new JtreeTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JtreeTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		final JTree tree = new JTree();
		contentPane.add(tree, BorderLayout.CENTER);
		
		{
			DefaultMutableTreeNode node1=new DefaultMutableTreeNode("node1");
			node1.add(new DefaultMutableTreeNode(new User("a1")));
			node1.add(new DefaultMutableTreeNode(new User("a2")));
			
			DefaultMutableTreeNode node2=new DefaultMutableTreeNode("node2");
			node2.add(new DefaultMutableTreeNode(new User("b1")));
			node2.add(new DefaultMutableTreeNode(new User("b2")));
			
			DefaultMutableTreeNode top=new DefaultMutableTreeNode(new User("nodeTop"));
			top.add(node1);
			top.add(node2);
			
			DefaultTreeModel treeModel=new DefaultTreeModel(top);
			tree.setModel(treeModel);
			tree.setRootVisible(false);
		}
		{
//			tree.addTreeSelectionListener(new TreeSelectionListener() {
//				
//				@Override
//				public void valueChanged(TreeSelectionEvent e) {
//					// TODO Auto-generated method stub
//					DefaultMutableTreeNode node=(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
//					if(node!=null){
//						Object object=node.getUserObject();
//						if(object instanceof User){
//							User user=(User)object;
//							System.out.println(user.toString());
//						}
//					}
//				}
//			});
			
			tree.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					if(e.getClickCount()==2){
						DefaultMutableTreeNode node=(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
						if(node!=null){
							Object object=node.getUserObject();
							if(object instanceof User){
								User user=(User)object;
								System.out.println(user.toString());
								node.add(new DefaultMutableTreeNode(new User("zzz")));
								tree.repaint();
							}
						}
					}
					
					super.mouseClicked(e);
				}
			});
		}
	}

}
class User{
	private String nameString;

	public User(String nameString) {
		super();
		this.nameString = nameString;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nameString;
	}
}
