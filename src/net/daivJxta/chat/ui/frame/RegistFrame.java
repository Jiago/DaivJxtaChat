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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPasswordField;

import net.daivJxta.chat.DaivJxta;

public class RegistFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textUsername;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldRep;
	
	private DaivJxta daivJxta;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					RegistFrame frame = new RegistFrame();
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
	public RegistFrame(final DaivJxta daivJxta) {
		this.daivJxta=daivJxta;
		
		setTitle("注册");
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
		
		JLabel label_1 = new JLabel("密码：");
		
		JLabel label_2 = new JLabel("确认密码：");
		
		textUsername = new JTextField();
		textUsername.setColumns(10);
		
		JLabel labelTip = new JLabel("");
		labelTip.setForeground(Color.RED);
		
		JButton btnOk = new JButton("确认");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//加载主界面
				if(textUsername.getText()!=null&&passwordField.getText()!=null&&passwordFieldRep.getText()!=null&&passwordField.getText().equals(passwordFieldRep.getText())){
					daivJxta.getDaivJxtaConfig().setPrincipal(textUsername.getText());
					daivJxta.getDaivJxtaConfig().setPassword(passwordField.getText());
					daivJxta.getDaivJxtaConfig().setName(textUsername.getText());
					System.out.println("OK");
					daivJxta.getDaivJxtaConfig().save();
					daivJxta.startNetwork();
					daivJxta.initProcess();
					try {
						Thread.sleep(2000L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					MainFrame mainFrame=new MainFrame(daivJxta);
					daivJxta.setMainFrame(mainFrame);
					mainFrame.setVisible(true);
					//关闭注册界面
					setVisible(false); 
					
				}
			}
		});
		
		
		JButton btnCancel = new JButton("取消");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//退出
				setVisible(true);
				System.exit(0);
			}
		});
		
		passwordField = new JPasswordField();
		
		passwordFieldRep = new JPasswordField();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(63)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(label)
						.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
						.addComponent(label_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
						.addComponent(passwordFieldRep, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
						.addComponent(textUsername, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
					.addGap(38)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(labelTip, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addGap(20))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(115)
					.addComponent(btnOk)
					.addGap(93)
					.addComponent(btnCancel)
					.addContainerGap(112, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(51)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(textUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(labelTip, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_1)
								.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_2)
								.addComponent(passwordFieldRep, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnCancel)
								.addComponent(btnOk))
							.addGap(40))))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
