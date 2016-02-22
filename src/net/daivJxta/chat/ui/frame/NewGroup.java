package net.daivJxta.chat.ui.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import net.daivJxta.chat.DaivJxta;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewGroup extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldGroupName;
	
	private DaivJxta daivJxta;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					NewGroup frame = new NewGroup();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public NewGroup(final DaivJxta daivJxta) {
		this.daivJxta=daivJxta;
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		int width=(int)screensize.getWidth();
		int height=(int)screensize.getHeight();
		setLocation(width/2-225, height/2-150);
		setSize(new Dimension(450,300));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel label = new JLabel("组群名称：");
		
		textFieldGroupName = new JTextField();
		textFieldGroupName.setColumns(10);
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//调用新建组群
				String name=textFieldGroupName.getText();
				if(!textFieldGroupName.getText().equals("")) {
					daivJxta.getDcEntityManager().createNewGroup(textFieldGroupName.getText());
					setVisible(false);
				}
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(72)
							.addComponent(label)
							.addGap(30)
							.addComponent(textFieldGroupName, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(179)
							.addComponent(btnNewButton)))
					.addContainerGap(105, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(83)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldGroupName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(label)))
					.addPreferredGap(ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
					.addComponent(btnNewButton)
					.addGap(46))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
