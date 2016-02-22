package net.daivJxta.chat;

import net.daivJxta.chat.discovery.DaivDiscovery;
import net.daivJxta.chat.entity.DCEntityManager;
import net.daivJxta.chat.listener.DaivListener;
import net.daivJxta.chat.pipe.DCBidiPipeClient;
import net.daivJxta.chat.pipe.DCBidiPipeServer;
import net.daivJxta.chat.ui.frame.MainFrame;
import net.jxta.exception.PeerGroupException;
import net.jxta.peergroup.PeerGroup;
import net.jxta.platform.NetworkManager;

import java.io.IOException;

/**
 * 聊天室Jxta总入口
 * Created by zsh_o on 2015/5/7.
 */
public class DaivJxta {
    public static final String Local_Peer_Name="Daiv Chat Local Peer";
    public static final String Local_Network_Manager_Name="Daiv Chat Local Network Manager";
    public static String UserName;

    public static boolean isexist=false;

    private PeerGroup TheNetPeerGroup;//默认本地组群
    public static NetworkManager networkManager;
    private DaivJxtaConfig daivJxtaConfig;

    DaivListener daivListener;
    DaivDiscovery daivDiscovery;
    DCEntityManager dcEntityManager;
    DCBidiPipeServer dcBidiPipeServer;
    DCBidiPipeClient dcBidiPipeClient;

    MainFrame mainFrame;

    public DaivJxta() {
        try {
            networkManager=new NetworkManager(NetworkManager.ConfigMode.EDGE,DaivJxta.Local_Network_Manager_Name);//创建网络管理器
        } catch (IOException e) {
            e.printStackTrace();
        }
        daivJxtaConfig=new DaivJxtaConfig(this);
//        UserName=daivJxtaConfig.getTheConfig().getName();
    }

    public boolean startNetwork(){
        try {
            TheNetPeerGroup=networkManager.startNetwork();
            return true;
        } catch (PeerGroupException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //初始化各种组件
    public void initProcess(){
    	UserName=daivJxtaConfig.getTheConfig().getName();
        dcEntityManager=new DCEntityManager(this);
        daivListener=new DaivListener(dcEntityManager);
        daivDiscovery=new DaivDiscovery(TheNetPeerGroup.getDiscoveryService(),daivListener);
        dcBidiPipeServer=new DCBidiPipeServer(this);
        dcBidiPipeClient=new DCBidiPipeClient(this);
        dcEntityManager.initLocalGroupPipe();
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public PeerGroup getTheNetPeerGroup() {
        return TheNetPeerGroup;
    }

    public DaivJxtaConfig getDaivJxtaConfig() {
        return daivJxtaConfig;
    }

    public DaivListener getDaivListener() {
        return daivListener;
    }

    public DaivDiscovery getDaivDiscovery() {
        return daivDiscovery;
    }

    public DCEntityManager getDcEntityManager() {
        return dcEntityManager;
    }

    public DCBidiPipeServer getDcBidiPipeServer() {
        return dcBidiPipeServer;
    }
    
    public static void main(String[] args) {
		DaivJxta daivJxta=new DaivJxta();
	}

    public DCBidiPipeClient getDcBidiPipeClient() {
        return dcBidiPipeClient;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}
