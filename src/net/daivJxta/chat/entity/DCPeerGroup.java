package net.daivJxta.chat.entity;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by zsh_o on 2015/5/9.
 */
public class DCPeerGroup {
//    PeerGroupAdvertisement peerGroupAdvertisement;//组群通告
//    String GroupName;
//    public DCPeerGroup(PeerGroupAdvertisement peerGroupAdvertisement) {
//        this.peerGroupAdvertisement = peerGroupAdvertisement;
//        String[] ss=peerGroupAdvertisement.getName().split("-");
//        String name = null;
//        if(ss.length>=2){
//            name=ss[1];
//        }
//        GroupName=name;
//    }
//
//    public PeerGroupAdvertisement getPeerGroupAdvertisement() {
//        return peerGroupAdvertisement;
//    }
//
//    public String getGroupName() {
//        return GroupName;
//    }

    ArrayList<DCPipe> pipeList;
    String GroupName;
    JTextArea multipleTextArea;

    public DCPeerGroup(String groupName) {
        GroupName = groupName;
        pipeList=new ArrayList<DCPipe>();
    }

    public ArrayList<DCPipe> getPipeList() {
        return pipeList;
    }

    public String getGroupName() {
        return GroupName;
    }

    @Override
    public String toString() {
        return  GroupName;
    }

    public JTextArea getMultipleTextArea() {
        return multipleTextArea;
    }

    public void setMultipleTextArea(JTextArea multipleTextArea) {
        this.multipleTextArea = multipleTextArea;
    }
}
