package net.daivJxta.chat.ui.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.tools.Tool;

import net.daivJxta.chat.DaivJxta;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	
	private DaivJxta daivJxta;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LoginFrame frame = new LoginFrame();
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
	public LoginFrame(final DaivJxta daivJxta) {
		this.daivJxta=daivJxta;

		setTitle("登陆");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		int width=(int)screensize.getWidth();
		int height=(int)screensize.getHeight();
		setLocation(width/2-225, height/2-150);
		setSize(new Dimension(450,300));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel label = new JLabel("用户名：");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		passwordField = new JPasswordField();

		JLabel label_1 = new JLabel("密码：");

		JButton button = new JButton("确认");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//设置用户名检测密码是否正确
				if(!textField.getText().equals("")&&!passwordField.getText().equals("")){
					String password=daivJxta.getDaivJxtaConfig().getPassword();
					if(textField.getText().equals(daivJxta.getDaivJxtaConfig().getTheConfig().getName())&&passwordField.getText().equals(password)){

						daivJxta.startNetwork();
						daivJxta.initProcess();
						try {
							Thread.sleep(2000L);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						MainFrame mainFrame=new MainFrame(daivJxta);
						daivJxta.setMainFrame(mainFrame);
						mainFrame.setVisible(true);
						setVisible(false); 
					}
				}
			}
		});
		
		JButton button_1 = new JButton("取消");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//退出
				setVisible(true);
				System.exit(0);
			}
		});
		
		JLabel labelTip = new JLabel("");
		labelTip.setForeground(Color.RED);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(56)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED, 1, Short.MAX_VALUE)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
														.addGroup(gl_contentPane.createSequentialGroup()
																.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_contentPane.createSequentialGroup()
																.addComponent(label, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(textField, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)))
												.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(labelTip, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
												.addGap(29))
										.addGroup(gl_contentPane.createSequentialGroup()
												.addGap(63)
												.addComponent(button, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
												.addGap(59)
												.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
												.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap(59, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addGap(3)
												.addComponent(label)))
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(labelTip, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_1))
								.addGap(65)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(button_1)
										.addComponent(button))
								.addGap(36))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
