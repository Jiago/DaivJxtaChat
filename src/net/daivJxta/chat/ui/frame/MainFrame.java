package net.daivJxta.chat.ui.frame;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import net.daivJxta.chat.entity.DCPeerGroup;
import net.daivJxta.chat.entity.DCPipe;
import net.daivJxta.chat.ui.util.tabbedPane.Tab;
import net.daivJxta.chat.ui.util.tabbedPane.TabbedPaneListener;
import net.miginfocom.swing.MigLayout;

import net.daivJxta.chat.DaivJxta;
import net.daivJxta.chat.ui.util.tabbedPane.TabbedPane;

import net.daivJxta.chat.ui.panel.ChatPanel;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	
	private DaivJxta daivJxta;

	TabbedPane tabbedPaneChat;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainFrame frame = new MainFrame();
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
	public MainFrame(final DaivJxta daivJxta) {
		this.daivJxta=daivJxta;
		
		setTitle("DaivChat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		int width=(int)screensize.getWidth();
		int height=(int)screensize.getHeight();
		setLocation(width/2-500, height/2-325);
		setSize(new Dimension(1000,650));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setToolTipText("");
		splitPane.setResizeWeight(0.15);
		contentPane.add(splitPane);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.85);
		splitPane.setRightComponent(splitPane_1);
		
		JPanel panel_1 = new JPanel();
		splitPane_1.setLeftComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		tabbedPaneChat = new TabbedPane();
		tabbedPaneChat.setCloseButtonEnabled(true);
		panel_1.add(tabbedPaneChat, BorderLayout.CENTER);
		tabbedPaneChat.addTabbedPaneListener(new TabbedPaneListener() {

			@Override
			public void tabRemoved(Tab tab, Component component, int index) {
				// TODO Auto-generated method stub
				ChatPanel chatPanel=(ChatPanel)component;
				if(chatPanel.getPipe()!=null){
					daivJxta.getDcBidiPipeClient().closeSingleChat(chatPanel.getPipe());
				}
				if(chatPanel.getPeerGroup()!=null){
					daivJxta.getDcBidiPipeClient().closeMultipleChat(chatPanel.getPeerGroup());
				}
			}

			@Override
			public void tabAdded(Tab tab, Component component, int index) {
				// TODO Auto-generated method stub
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
		
//		ChatPanel chatPanel = new ChatPanel(daivJxta);
//		tabbedPaneChat.addTab("测试", null, chatPanel);
		
		JPanel panel_2 = new JPanel();
		splitPane_1.setRightComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		panel_2.add(tabbedPane_2, BorderLayout.CENTER);
		
		JPanel panelFile = new JPanel();
		tabbedPane_2.addTab("文件", null, panelFile, null);
		panelFile.setLayout(new BorderLayout(0, 0));
		
		final JTree treeFileList = new JTree();
		daivJxta.getDcEntityManager().setFileTree(treeFileList);
		daivJxta.getDcEntityManager().initFileList();
		{
			treeFileList.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					if (e.getClickCount() == 2) {
						TreePath path=treeFileList.getPathForLocation(e.getX(),e.getY());
						DefaultMutableTreeNode node=null;
						if(path.getLastPathComponent()!=null){
							node = (DefaultMutableTreeNode) path.getLastPathComponent();
						}
						if (node != null&&node.isLeaf()) {
							Object object = node.getUserObject();
							if (object instanceof String) {
								try {
									Desktop.getDesktop().open(new File(daivJxta.getDaivJxtaConfig().getTheConfig().getHome()+"\\files\\"+((DefaultMutableTreeNode)node.getParent()).getUserObject()+"\\"+(String)object));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}
					}

					super.mouseClicked(e);
				}
			});
		}
		panelFile.add(treeFileList, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelSingle = new JPanel();
		tabbedPane.addTab("个人", null, panelSingle, null);
		panelSingle.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelSingle.add(scrollPane, BorderLayout.CENTER);
		
		final JTree treeSingleList = new JTree();
		daivJxta.getDcEntityManager().setSingleChatTree(treeSingleList);
		scrollPane.setViewportView(treeSingleList);
		{
			treeSingleList.setRootVisible(false);
			treeSingleList.setModel(daivJxta.getDcEntityManager().getSingleChatTreeModel());
			//添加双击监听
			treeSingleList.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					if (e.getClickCount() == 2) {
						TreePath path=treeSingleList.getPathForLocation(e.getX(),e.getY());
						DefaultMutableTreeNode node=null;
						if(path.getLastPathComponent()!=null){
							node = (DefaultMutableTreeNode) path.getLastPathComponent();
						}

						if (node != null) {
							Object object = node.getUserObject();
							if (object instanceof DCPipe) {
								DCPipe pipe =(DCPipe)object;
								boolean exist=false;
								for(int i=0;i<tabbedPaneChat.getTabCount();i++){
									ChatPanel chatPanel=(ChatPanel)tabbedPaneChat.getComponentAt(i);
									if(chatPanel.getPipe()!=null){
										if(pipe.getPeerName().equals(chatPanel.getPipe().getPeerName())){
											exist=true;
											tabbedPaneChat.setSelectedIndex(i);
										}
									}
								}
								if(!exist){
									ChatPanel chatPanel = new ChatPanel(daivJxta,pipe);
									tabbedPaneChat.addTab("个人："+pipe.getPeerName(), null, chatPanel);
									tabbedPaneChat.setSelectedIndex(tabbedPaneChat.getTabCount()-1);
								}
							}
						}
					}

					super.mouseClicked(e);
				}
			});
		}
		
		JPanel panelMultiple = new JPanel();
		tabbedPane.addTab("组群", null, panelMultiple, null);
		panelMultiple.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panelMultiple.add(scrollPane_1, BorderLayout.CENTER);
		
		final JTree treeGroupList = new JTree();
		treeGroupList.setRootVisible(false);
		daivJxta.getDcEntityManager().setMultipleChatTree(treeGroupList);
		daivJxta.getDcEntityManager().initMultipleList();
		treeGroupList.repaint();
		scrollPane_1.setViewportView(treeGroupList);
		{
			treeGroupList.setModel(daivJxta.getDcEntityManager().getMultipleChatTreeModel());

			treeGroupList.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					if (e.getClickCount() == 2) {
						TreePath path=treeGroupList.getPathForLocation(e.getX(),e.getY());
						DefaultMutableTreeNode node=null;
						if(path.getLastPathComponent()!=null){
							node = (DefaultMutableTreeNode) path.getLastPathComponent();
						}

						if (node != null) {
							Object object = node.getUserObject();
							if (object instanceof DCPipe) {
								DefaultMutableTreeNode parentNode=(DefaultMutableTreeNode)node.getParent();
								if(parentNode.getUserObject() instanceof DCPeerGroup){
									DCPeerGroup peerGroup= (DCPeerGroup) parentNode.getUserObject();
									daivJxta.getDcEntityManager().joinGroup(peerGroup);
									try {
										Thread.sleep(1000L);
									} catch (InterruptedException e1) {
										e1.printStackTrace();
									}
									boolean exist=false;
									for(int i=0;i<tabbedPaneChat.getTabCount();i++){
										ChatPanel chatPanel=(ChatPanel)tabbedPaneChat.getComponentAt(i);
										if(chatPanel.getPeerGroup()!=null){
											if(peerGroup.getGroupName().equals(chatPanel.getPeerGroup().getGroupName())){
												exist=true;
												tabbedPaneChat.setSelectedIndex(i);
											}
										}
									}
									if(!exist){
										ChatPanel chatPanel = new ChatPanel(daivJxta,peerGroup);
										tabbedPaneChat.addTab("组群："+peerGroup.getGroupName(), null, chatPanel);
										tabbedPaneChat.setSelectedIndex(tabbedPaneChat.getTabCount()-1);
									}
								}
							}
						}
					}

					super.mouseClicked(e);
				}
			});
		}
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(treeGroupList, popupMenu);
		
		JMenuItem menuItemNewGroup = new JMenuItem("新建组群");
		popupMenu.add(menuItemNewGroup);
		
		menuItemNewGroup.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				//新建组群
				NewGroup newGroup=new NewGroup(daivJxta);
				newGroup.setVisible(true);
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public TabbedPane getTabbedPaneChat() {
		return tabbedPaneChat;
	}
}
