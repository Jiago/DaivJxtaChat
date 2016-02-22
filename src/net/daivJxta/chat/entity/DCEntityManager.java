package net.daivJxta.chat.entity;

import net.daivJxta.chat.DaivJxta;
import net.daivJxta.chat.discovery.DaivLocalDescovery;
import net.daivJxta.chat.ui.panel.ChatPanel;
import net.daivJxta.chat.ui.test.JtreeTest;
import net.daivJxta.chat.ui.util.tabbedPane.TabbedPane;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.protocol.PipeAdvertisement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Created by zsh_o on 2015/5/9.
 */
public class DCEntityManager {
//    DCPeer myPeer;
//    ArrayList<DCPeer> peerList;
//    ArrayList<DCPeerGroup> peerGroupList;
//    ArrayList<DCPipe> pipeList;
//
//    DiscoveryService discoveryService;
//
//    public DCEntityManager(DCPeer myPeer) {
//        this.myPeer = myPeer;
//        peerGroupList=new ArrayList<DCPeerGroup>();
//        peerList=new ArrayList<DCPeer>();
//        pipeList=new ArrayList<DCPipe>();
//    }
//
//    public boolean addPipe(DCPipe pipe){
//        for(DCPipe p:pipeList){
//            if(pipe.getGroupName().equals(p.getGroupName())&&pipe.getPeerName().equals(p.getPeerName())){
//                return false;
//            }
//        }
//        pipeList.add(pipe);
//        return true;
//    }
//
//    public boolean addPeer(DCPeer peer){
//        for(DCPeer p:peerList){
//            if(peer.getPeerName().equals(p.getPeerName())){
//                return false;
//            }
//        }
//        peerList.add(peer);
//        return true;
//    }
//
//    public boolean addPeerGroup(DCPeerGroup peerGroup){
//        for(DCPeerGroup p:peerGroupList){
//            if(peerGroup.getGroupName().equals(p.getGroupName())){
//                return false;
//            }
//        }
//        peerGroupList.add(peerGroup);
//        return true;
//    }
//
//    public boolean addPeerGroupPip(DCPipe pipe){
//        for(DCPeer p:peerList){
//            for(DCPeerGroup peerGroup:peerGroupList){
//                if(pipe.getGroupName().equals(peerGroup.getGroupName())){
//                    if(p.addGroupPipe(peerGroup,pipe))
//                        return true;
//                }
//            }
//        }
//        return false;
//    }

    ArrayList<DCPeerGroup> peerGroupList;//用来保存组群
    ArrayList<DCPipe> pipeList;//用来保存个人管道通告

    DaivJxta jxta;
    DiscoveryService discoveryService;
    DCPipe pipeMyself;

    DefaultTreeModel singleChatTreeModel;
    DefaultTreeModel multipleChatTreeModel;

    JTree singleChatTree;
    JTree multipleChatTree;
    JTree fileTree;
    DaivLocalDescovery daivLocalDescovery;

    ArrayList<DCPipe> localGroupPipes;

    int i=0;
    public void setFileTree(JTree fileTree) {
        this.fileTree = fileTree;
    }

    public JTree getFileTree() {
        return fileTree;
    }

    public JTree getMultipleChatTree() {
        return multipleChatTree;
    }

    public JTree getSingleChatTree() {
        return singleChatTree;
    }

    public void setMultipleChatTree(JTree multipleChatTree) {
        this.multipleChatTree = multipleChatTree;
    }

    public void setSingleChatTree(JTree singleChatTree) {
        this.singleChatTree = singleChatTree;
    }

    public DCEntityManager(DaivJxta jxta) {
        this.jxta=jxta;
        discoveryService=jxta.getTheNetPeerGroup().getDiscoveryService();
        pipeList=new ArrayList<DCPipe>();
        localGroupPipes=new ArrayList<DCPipe>();
        peerGroupList=new ArrayList<DCPeerGroup>();
        if(!DaivJxta.isexist){
            pipeMyself=new DCPipe(DCAdv.createPipAdv());
            pipeList.add(pipeMyself);
            try {
                discoveryService.publish(pipeMyself.getPipeAdvertisement());
            } catch (IOException e) {
                e.printStackTrace();
            }
            discoveryService.remotePublish(pipeMyself.getPipeAdvertisement());
        }else{
            daivLocalDescovery=new DaivLocalDescovery(jxta);
            Enumeration<Advertisement> advertisementEnumeration=null;
            try {
                advertisementEnumeration = discoveryService.getLocalAdvertisements(DiscoveryService.ADV,"Name","DaivChat-*");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Advertisement adv;
            if(advertisementEnumeration!=null){
                while(advertisementEnumeration.hasMoreElements()){
                    adv=(Advertisement)advertisementEnumeration.nextElement();
                    //分离出管道通告,交付管道通告处理器
                    if(adv instanceof PipeAdvertisement){
                        PipeAdvertisement pipeAdvertisement =(PipeAdvertisement) adv;
                        DCPipe dcPipe=new DCPipe(pipeAdvertisement);
                        if(dcPipe.getPeerName().equals(DaivJxta.UserName)){
                            //处理本地存在的个人管道
                            if(dcPipe.getType().equals(DCPipeType.SINGLE)){
                                pipeMyself=dcPipe;
                                pipeList.add(pipeMyself);

                            }
                            //处理本地存在的组群
                            if(dcPipe.getType().equals(DCPipeType.MULTIPLE)){
                                DCPeerGroup peerGroup=new DCPeerGroup(dcPipe.getGroupName());
                                peerGroup.getPipeList().add(dcPipe);
                                localGroupPipes.add(dcPipe);
                                peerGroupList.add(peerGroup);
                            }

                            try {
                                discoveryService.publish(pipeAdvertisement);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            discoveryService.remotePublish(pipeAdvertisement);
                        }
                    }
                }
            }

        }
        initSingleList();
        i++;
    }
    public void initLocalGroupPipe(){
        for(DCPipe pipe:localGroupPipes){
            try {
                jxta.getDcBidiPipeServer().initMultipleChatPipe(pipe);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<DCPeerGroup> getPeerGroupList() {
        return peerGroupList;
    }

    public ArrayList<DCPipe> getPipeList() {
        return pipeList;
    }

    //本地用户新建组群并广播组群和建立管道服务
    public DCPeerGroup createNewGroup(String groupName){
        //判断组群名称是否存在存在返回此组群相当于加入此组群
        for(DCPeerGroup peerGroup:peerGroupList){
            if(peerGroup.getGroupName().equals(groupName)){
                joinGroup(peerGroup);
                return peerGroup;
            }
        }
        PipeAdvertisement advertisement=DCAdv.createGroupPipAdv(groupName);
        DCPipe pipe=new DCPipe(advertisement);
//        pipeList.add(pipe);//piplist只保存个人管道
        DCPeerGroup peerGroup=new DCPeerGroup(groupName);
        peerGroup.getPipeList().add(pipe);
        try {
            discoveryService.publish(advertisement);

        } catch (IOException e) {
            e.printStackTrace();
        }
        discoveryService.remotePublish(advertisement);
        peerGroupList.add(peerGroup);
        try {
            jxta.getDcBidiPipeServer().initMultipleChatPipe(pipe);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initMultipleList();
        multipleChatTree.repaint();
        return peerGroup;
    }

    //用户加入组群，创建用户此组群的通告并发送，创建管道服务端
    public boolean joinGroup(DCPeerGroup peerGroup){
        for(DCPipe p:peerGroup.getPipeList()){
            if(p.getPeerName().equals(DaivJxta.UserName)){
                return false;//用户已经在此组群中
            }
        }
        //用户不在组群中:
        PipeAdvertisement advertisement=DCAdv.createGroupPipAdv(peerGroup.getGroupName());
        DCPipe pipe=new DCPipe(advertisement);
        peerGroup.getPipeList().add(pipe);
        try {
            discoveryService.publish(advertisement);

        } catch (IOException e) {
            e.printStackTrace();
        }

        discoveryService.remotePublish(advertisement);

        try {
            jxta.getDcBidiPipeServer().initMultipleChatPipe(pipe);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initMultipleList();
        multipleChatTree.repaint();
        return true;
    }

    public boolean addPeerGroup(DCPipe pipe){
        for(DCPeerGroup p:peerGroupList){
            if(p.getGroupName().equals(pipe.getGroupName())){
                for(DCPipe p1:p.getPipeList()){
                    if(p1.getPeerName().equals(pipe.getPeerName())){
                        return false;
                    }
                }
                p.getPipeList().add(pipe);
                initMultipleList();
                multipleChatTree.repaint();
                return true;
            }
        }
        DCPeerGroup peerGroup=new DCPeerGroup(pipe.getGroupName());
        peerGroup.getPipeList().add(pipe);
        peerGroupList.add(peerGroup);
//        try {
//            jxta.getDcBidiPipeServer().initMultipleChatPipe(pipe);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        
        initMultipleList();
        multipleChatTree.repaint();
        return true;
    }

    public boolean addPip(DCPipe pipe){
        for(DCPipe p:pipeList){
            if(p.getPeerName().equals(pipe.getPeerName())){
                return false;
            }
        }
        pipeList.add(pipe);
        initSingleList();
        singleChatTree.repaint();
        return true;
    }

    public DCPipe getPipeMyself() {
        return pipeMyself;
    }
    
    public DefaultTreeModel initSingleList(){
    	DefaultMutableTreeNode rootNode=new DefaultMutableTreeNode("SingleChatListRoot");
    	for(DCPipe pipe:pipeList){
    		rootNode.add(new DefaultMutableTreeNode(pipe));
    	}
        singleChatTreeModel=new DefaultTreeModel(rootNode);
        if(i>0)
            singleChatTree.setModel(singleChatTreeModel);
    	return singleChatTreeModel;
    }
    
    public DefaultTreeModel initMultipleList(){
    	DefaultMutableTreeNode rootNode=new DefaultMutableTreeNode("MultipleChatListRoot");
    	for(DCPeerGroup peerGroup:peerGroupList){
    		DefaultMutableTreeNode node=new DefaultMutableTreeNode(peerGroup);
    		for(DCPipe pipe:peerGroup.getPipeList()){
    			node.add(new DefaultMutableTreeNode(pipe));
    		}
    		rootNode.add(node);
    	}
    	multipleChatTreeModel=new DefaultTreeModel(rootNode);
        multipleChatTree.setModel(multipleChatTreeModel);
    	return multipleChatTreeModel;
    }
    
    public DefaultTreeModel getSingleChatTreeModel() {
		return singleChatTreeModel;
	}
    
    public DefaultTreeModel getMultipleChatTreeModel() {
		return multipleChatTreeModel;
	}

    //处理得到的个人消息，确定是哪一个并且更新JTextArea
    public void processSingleMessage(String userName,String text){
        for(DCPipe pipe:pipeList){
            if(pipe.getPeerName().equals(userName)){
                int count=jxta.getMainFrame().getTabbedPaneChat().getTabCount();
                boolean exist=false;
                for(int i=0;i<count;i++){
                    ChatPanel chatPanel=(ChatPanel)jxta.getMainFrame().getTabbedPaneChat().getComponentAt(i);
                    if(chatPanel.getPipe()!=null){
                        if(chatPanel.getPipe().getPeerName().equals(pipe.getPeerName())){//当前存在
                            exist=true;
                            break;
                        }
                    }
                }
                if(!exist){
                    ChatPanel chatPanel=new ChatPanel(jxta,pipe);
                    jxta.getMainFrame().getTabbedPaneChat().addTab("个人："+pipe.getPeerName(), null, chatPanel);
                }
                pipe.getSingleTextArea().setText(pipe.getSingleTextArea().getText()+"\n"+text);
                pipe.getSingleTextArea().setCaretPosition(pipe.getSingleTextArea().getText().length());
            }
        }
    }
    public void processMultipleMessage(String groupName,String userName,String text){
        for(DCPeerGroup peerGroup:peerGroupList){
            for(DCPipe pipe:peerGroup.getPipeList()){
                if(pipe.getGroupName().equals(groupName)&&pipe.getPeerName().equals(userName)){
                    peerGroup.getMultipleTextArea().setText(peerGroup.getMultipleTextArea().getText()+"\n"+text);
                    peerGroup.getMultipleTextArea().setCaretPosition(peerGroup.getMultipleTextArea().getText().length());
                }
            }
        }
    }

    public DefaultTreeModel initFileList(){
        File rootFile=new File(jxta.getDaivJxtaConfig().getTheConfig().getHome()+"\\files\\");
        DefaultMutableTreeNode rootNode=new DefaultMutableTreeNode("files");
        File filePeerList[]=rootFile.listFiles();
        for(int i=0;i<filePeerList.length;i++){
            DefaultMutableTreeNode peerFile=new DefaultMutableTreeNode(filePeerList[i].getName().trim());
            rootNode.add(peerFile);
            File fileList[]=filePeerList[i].listFiles();
            for(int j=0;j<fileList.length;j++){
                DefaultMutableTreeNode lFile=new DefaultMutableTreeNode(fileList[j].getName().trim());
                peerFile.add(lFile);
            }
        }
        DefaultTreeModel treeModel=new DefaultTreeModel(rootNode);
        if(i>0)
            fileTree.setModel(treeModel);
        return treeModel;
    }
}
