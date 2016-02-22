package net.daivJxta.chat.entity;

import net.jxta.protocol.PipeAdvertisement;
import net.jxta.util.JxtaBiDiPipe;

import javax.swing.*;

/**
 * Created by zsh_o on 2015/5/9.
 */
public class DCPipe {
    PipeAdvertisement pipeAdvertisement;
    DCPipeType type;

    JxtaBiDiPipe biDiPipe;

    private String groupName;
    private String peerName;

    JTextArea singleTextArea;

    public DCPipe(PipeAdvertisement pipeAdvertisement) {
        this.pipeAdvertisement = pipeAdvertisement;
        String[] ss=pipeAdvertisement.getName().split("-");
        if(ss[1].equals("Multiple")){
            type=DCPipeType.MULTIPLE;
            groupName=ss[2];
            peerName=ss[3];
        }
        if(ss[1].equals("Single")){
            type=DCPipeType.SINGLE;
            groupName=null;
            peerName=ss[2];
        }

    }

    public String getPeerName() {
        return peerName;
    }

    public String getGroupName() {
        return groupName;
    }

    public PipeAdvertisement getPipeAdvertisement() {
        return pipeAdvertisement;
    }

    public DCPipeType getType() {
        return type;
    }

    public void setBiDiPipe(JxtaBiDiPipe biDiPipe) {
        this.biDiPipe = biDiPipe;
    }

    public JxtaBiDiPipe getBiDiPipe() {
        return biDiPipe;
    }
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return peerName;
    }

    public JTextArea getSingleTextArea() {
        return singleTextArea;
    }

    public void setSingleTextArea(JTextArea singleTextArea) {
        this.singleTextArea = singleTextArea;
    }
}
