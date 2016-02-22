package net.daivJxta.chat.ui.test;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import net.daivJxta.chat.ui.util.tabbedPane.Tab;
import net.daivJxta.chat.ui.util.tabbedPane.TabbedPane;
import net.daivJxta.chat.ui.util.tabbedPane.TabbedPaneListener;

/**
 * Test
 * @author Tom
 *
 */
public class TestDemo {

	public static void main(String[] args) {
		
		try {
			String feel = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(feel);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		JFrame frame = new JFrame();
		frame.setTitle("可关闭Tab测试");
		frame.setSize(300, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		TabbedPane tabbedPane = new TabbedPane();
		tabbedPane.setCloseButtonEnabled(true);
		tabbedPane.addTab("测试�?", null, new JLabel("测试�?"));
		tabbedPane.addTab("测试�?", null, new JLabel("测试�?"));
		tabbedPane.addTab("测试�?", null, new JLabel("测试�?"));

		
		
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
		
		
		frame.add(tabbedPane);
		frame.setVisible(true);
	}
	
}
