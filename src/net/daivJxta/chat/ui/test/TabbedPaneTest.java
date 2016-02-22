package net.daivJxta.chat.ui.test;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import net.daivJxta.chat.ui.util.tabbedPane.Tab;
import net.daivJxta.chat.ui.util.tabbedPane.TabbedPane;
import net.daivJxta.chat.ui.util.tabbedPane.TabbedPaneListener;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;

public class TabbedPaneTest extends JFrame {

	private JPanel contentPane;
	int a=0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TabbedPaneTest frame = new TabbedPaneTest();
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
	public TabbedPaneTest() {
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("New button");
		
		final TabbedPane tabbedPane = new TabbedPane();
		tabbedPane.setCloseButtonEnabled(true);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(17)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnNewButton)
							.addContainerGap())
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(23)
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnNewButton)
					.addGap(22))
		);
		contentPane.setLayout(gl_contentPane);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.addTab(String.valueOf(a), null, new JLabel("测试" + a));
				a++;
			}
		});
		tabbedPane.addTabbedPaneListener(new TabbedPaneListener(){

			@Override
			public void tabRemoved(Tab tab, Component component, int index) {
				// TODO Auto-generated method stub
				System.out.println("Close");
			}

			@Override
			public void tabAdded(Tab tab, Component component, int index) {
				// TODO Auto-generated method stub
				System.out.println("Add:"+index);
			}

			@Override
			public void tabSelected(Tab tab, Component component, int index) {
				// TODO Auto-generated method stub
			}

			@Override
			public void allTabsRemoved() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean canTabClose(Tab tab, Component component) {
				// TODO Auto-generated method stub
				return false;
			}
			
		});
	}
}
