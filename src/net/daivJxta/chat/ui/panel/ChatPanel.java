package net.daivJxta.chat.ui.panel;

import javax.swing.*;

import java.awt.*;

import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;

import net.daivJxta.chat.DaivJxta;
import net.daivJxta.chat.entity.DCPeerGroup;
import net.daivJxta.chat.entity.DCPipe;
import net.daivJxta.chat.message.DCMessage;
import net.jxta.endpoint.Message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class ChatPanel extends JPanel {
	
	private DaivJxta daivJxta;
	private DCPipe pipe;
	private DCPeerGroup peerGroup;
	
	/**
	 * Create the panel.
	 */
	public ChatPanel(final DaivJxta daivJxta, final DCPipe pipe) {
		this.daivJxta=daivJxta;
		this.pipe=pipe;

		//启动一个线程初始化聊天管道
		Thread thread=new Thread(){
			@Override
			public void run(){
				daivJxta.getDcBidiPipeClient().initSingleChat(pipe);//初始化聊天管道
			}
		};
		thread.start();

		setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.6);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(splitPane);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		final JTextArea textAreaReceive = new JTextArea();
		textAreaReceive.setFont(new Font("宋体", Font.PLAIN, 16));//设置字体，确保自宽一致
		textAreaReceive.setEditable(false);
		pipe.setSingleTextArea(textAreaReceive);
		scrollPane.setViewportView(textAreaReceive);
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		SpringLayout sl_panel_2 = new SpringLayout();
		panel_2.setLayout(sl_panel_2);
		
		JPanel panel_3 = new JPanel();
		sl_panel_2.putConstraint(SpringLayout.NORTH, panel_3, 0, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.WEST, panel_3, 0, SpringLayout.WEST, panel_2);
		panel_2.add(panel_3);
		
		JPanel panel_4 = new JPanel();
		sl_panel_2.putConstraint(SpringLayout.SOUTH, panel_3, -1, SpringLayout.NORTH, panel_4);
		sl_panel_2.putConstraint(SpringLayout.EAST, panel_3, 0, SpringLayout.EAST, panel_4);
		sl_panel_2.putConstraint(SpringLayout.NORTH, panel_4, -34, SpringLayout.SOUTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.WEST, panel_4, 0, SpringLayout.WEST, panel_2);
		sl_panel_2.putConstraint(SpringLayout.SOUTH, panel_4, 0, SpringLayout.SOUTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, panel_4, 0, SpringLayout.EAST, panel_2);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_3.add(scrollPane_1, BorderLayout.CENTER);
		
		final JTextArea textAreaSend = new JTextArea();
		{
			//添加键盘监听Shift+Enter
			textAreaSend.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					super.keyTyped(e);
				}

				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER&&e.isShiftDown()){
						Message message= DCMessage.createSingleTextMessage(textAreaSend.getText());
						if(daivJxta.getDcBidiPipeClient().sendSingleMessage(pipe,message)){
							String stext= DCMessage.transformMessageText(DaivJxta.UserName, textAreaSend.getText());
							String recvText="["+DaivJxta.UserName+"]"+":"+stext;
							pipe.getSingleTextArea().setText(pipe.getSingleTextArea().getText()+"\n"+recvText);
							textAreaSend.setText("");
						}
					}
					super.keyPressed(e);
				}

				@Override
				public void keyReleased(KeyEvent e) {
					super.keyReleased(e);
				}
			});
		}
		scrollPane_1.setViewportView(textAreaSend);
		panel_2.add(panel_4);
		SpringLayout sl_panel_4 = new SpringLayout();
		panel_4.setLayout(sl_panel_4);
		
		JButton buttonSend = new JButton("发送");
		sl_panel_4.putConstraint(SpringLayout.NORTH, buttonSend, 0, SpringLayout.NORTH, panel_4);
		sl_panel_4.putConstraint(SpringLayout.WEST, buttonSend, -91, SpringLayout.EAST, panel_4);
		sl_panel_4.putConstraint(SpringLayout.SOUTH, buttonSend, 34, SpringLayout.NORTH, panel_4);
		sl_panel_4.putConstraint(SpringLayout.EAST, buttonSend, 0, SpringLayout.EAST, panel_4);
		{
			buttonSend.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//发送
					Message message= DCMessage.createSingleTextMessage(textAreaSend.getText());
					if(daivJxta.getDcBidiPipeClient().sendSingleMessage(pipe,message)){
						String recvText="["+DaivJxta.UserName+"]"+":"+textAreaSend.getText();
						pipe.getSingleTextArea().setText(pipe.getSingleTextArea().getText()+"\n"+recvText);
						textAreaSend.setText("");
					}
				}
			});
		}
		panel_4.add(buttonSend);

		final JButton button = new JButton("发送文件");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser=new JFileChooser();
				fileChooser.setDialogTitle("选择文件");
				fileChooser.showOpenDialog(button);
				File file=fileChooser.getSelectedFile();
				System.out.println();
				Message message= DCMessage.createSingleFileMessage(file);
				daivJxta.getDcBidiPipeClient().sendSingleMessage(pipe,message);
				Message message1=DCMessage.createSingleTextMessage("发送了一个文件："+file.getName().trim());

				if(daivJxta.getDcBidiPipeClient().sendSingleMessage(pipe,message1)){
					String recvText="["+DaivJxta.UserName+"]"+":"+"发送了一个文件："+file.getName().trim();
					pipe.getSingleTextArea().setText(pipe.getSingleTextArea().getText()+"\n"+recvText);
				}
			}
		});
		sl_panel_4.putConstraint(SpringLayout.NORTH, button, 0, SpringLayout.NORTH, buttonSend);
		sl_panel_4.putConstraint(SpringLayout.WEST, button, 0, SpringLayout.WEST, panel_4);
		sl_panel_4.putConstraint(SpringLayout.SOUTH, button, 0, SpringLayout.SOUTH, buttonSend);
		sl_panel_4.putConstraint(SpringLayout.EAST, button, 91, SpringLayout.WEST, panel_4);
		panel_4.add(button);

	}
	public ChatPanel(final DaivJxta daivJxta, final DCPeerGroup peerGroup) {
		this.daivJxta=daivJxta;
		this.peerGroup=peerGroup;

		//启动一个线程初始化聊天管道
		Thread thread=new Thread(){
			@Override
			public void run(){
				daivJxta.getDcBidiPipeClient().initMultipleChat(peerGroup);//初始化聊天管道
			}
		};
		thread.start();

		setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.6);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(splitPane);

		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		JTextArea textAreaReceive = new JTextArea();
		textAreaReceive.setFont(new Font("宋体", Font.PLAIN, 16));//设置字体，确保自宽一致
		textAreaReceive.setEditable(false);
		peerGroup.setMultipleTextArea(textAreaReceive);
		scrollPane.setViewportView(textAreaReceive);

		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		SpringLayout sl_panel_2 = new SpringLayout();
		panel_2.setLayout(sl_panel_2);

		JPanel panel_3 = new JPanel();
		sl_panel_2.putConstraint(SpringLayout.NORTH, panel_3, 0, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.WEST, panel_3, 0, SpringLayout.WEST, panel_2);
		panel_2.add(panel_3);

		JPanel panel_4 = new JPanel();
		sl_panel_2.putConstraint(SpringLayout.SOUTH, panel_3, -1, SpringLayout.NORTH, panel_4);
		sl_panel_2.putConstraint(SpringLayout.EAST, panel_3, 0, SpringLayout.EAST, panel_4);
		sl_panel_2.putConstraint(SpringLayout.NORTH, panel_4, -34, SpringLayout.SOUTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.WEST, panel_4, 0, SpringLayout.WEST, panel_2);
		sl_panel_2.putConstraint(SpringLayout.SOUTH, panel_4, 0, SpringLayout.SOUTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, panel_4, 0, SpringLayout.EAST, panel_2);
		panel_3.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_3.add(scrollPane_1, BorderLayout.CENTER);

		final JTextArea textAreaSend = new JTextArea();
		{
			//添加键盘监听Shift+Enter
			textAreaSend.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					super.keyTyped(e);
				}

				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER&&e.isShiftDown()){
						Message message= DCMessage.createMultipleTextMessage(peerGroup, textAreaSend.getText());
						daivJxta.getDcBidiPipeClient().sendMultipleMessage(peerGroup, message);
						textAreaSend.setText("");
					}
					super.keyPressed(e);
				}

				@Override
				public void keyReleased(KeyEvent e) {
					super.keyReleased(e);
				}
			});
		}
		scrollPane_1.setViewportView(textAreaSend);
		panel_2.add(panel_4);
		SpringLayout sl_panel_4 = new SpringLayout();
		panel_4.setLayout(sl_panel_4);

		JButton buttonSend = new JButton("发送");
		{
			buttonSend.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//发送
					Message message= DCMessage.createMultipleTextMessage(peerGroup, textAreaSend.getText());
					daivJxta.getDcBidiPipeClient().sendMultipleMessage(peerGroup, message);
					textAreaSend.setText("");

				}
			});
		}
		sl_panel_4.putConstraint(SpringLayout.NORTH, buttonSend, 0, SpringLayout.NORTH, panel_4);
		sl_panel_4.putConstraint(SpringLayout.WEST, buttonSend, -91, SpringLayout.EAST, panel_4);
		sl_panel_4.putConstraint(SpringLayout.SOUTH, buttonSend, 34, SpringLayout.NORTH, panel_4);
		sl_panel_4.putConstraint(SpringLayout.EAST, buttonSend, 0, SpringLayout.EAST, panel_4);
		panel_4.add(buttonSend);

		final JButton button = new JButton("发送文件");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser=new JFileChooser();
				fileChooser.setDialogTitle("选择文件");
				fileChooser.showOpenDialog(button);
				File file=fileChooser.getSelectedFile();
				Message message=DCMessage.createMultipleFileMessage(peerGroup,file);
				daivJxta.getDcBidiPipeClient().sendMultipleMessage(peerGroup,message);
				Message message1=DCMessage.createMultipleTextMessage(peerGroup,"发送了一个文件："+file.getName().trim());
				daivJxta.getDcBidiPipeClient().sendMultipleMessage(peerGroup,message1);

			}
		});
		sl_panel_4.putConstraint(SpringLayout.NORTH, button, 0, SpringLayout.NORTH, buttonSend);
		sl_panel_4.putConstraint(SpringLayout.WEST, button, 0, SpringLayout.WEST, panel_4);
		sl_panel_4.putConstraint(SpringLayout.SOUTH, button, 0, SpringLayout.SOUTH, buttonSend);
		sl_panel_4.putConstraint(SpringLayout.EAST, button, 91, SpringLayout.WEST, panel_4);
		panel_4.add(button);

	}

	public DCPipe getPipe() {
		return pipe;
	}

	public DCPeerGroup getPeerGroup() {
		return peerGroup;
	}
}
