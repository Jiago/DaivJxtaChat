package net.daivJxta.chat.pipe;

import net.daivJxta.chat.DaivJxta;
import net.daivJxta.chat.entity.DCPeerGroup;
import net.daivJxta.chat.entity.DCPipe;
import net.jxta.endpoint.Message;
import net.jxta.util.JxtaBiDiPipe;

import java.io.IOException;

/**
 * 用于连接管道服务并发送消息
 * Created by zsh_o on 2015/5/10.
 */
public class DCBidiPipeClient {
    DaivJxta daivJxta;

    DCSinglePipMsgListener dcSinglePipMsgListener;
    DCMultiplePipMsgListener dcMultiplePipMsgListener;
    public DCBidiPipeClient(DaivJxta daivJxta) {
        this.daivJxta = daivJxta;
        dcMultiplePipMsgListener=new DCMultiplePipMsgListener(daivJxta);
        dcSinglePipMsgListener=new DCSinglePipMsgListener(daivJxta);
    }


    //初始化个人聊天
    public boolean initSingleChat(DCPipe dcPipe){
        JxtaBiDiPipe biDiPipe = null;
        try {
            biDiPipe=new JxtaBiDiPipe(daivJxta.getTheNetPeerGroup(),dcPipe.getPipeAdvertisement(),dcSinglePipMsgListener);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if(biDiPipe!=null){
            dcPipe.setBiDiPipe(biDiPipe);
        }else {
            return false;
        }
        return true;
    }
    public boolean sendSingleMessage(DCPipe dcPipe,Message message){
        try {
            dcPipe.getBiDiPipe().sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //当用户关闭个人聊天界面,关闭个人聊天的管道
    public boolean closeSingleChat(DCPipe dcPipe){
        try {
            dcPipe.getBiDiPipe().close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //初始化群聊的管道
    public boolean initMultipleChat(DCPeerGroup peerGroup){
        boolean yes=false;
        for(DCPipe p:peerGroup.getPipeList()){
            JxtaBiDiPipe biDiPipe=null;
            try {
                biDiPipe=new JxtaBiDiPipe(daivJxta.getTheNetPeerGroup(),p.getPipeAdvertisement(),3000,dcMultiplePipMsgListener);//3s
                yes=true;
            } catch (IOException e) {
                e.printStackTrace();
                yes=false;
            }
            if(biDiPipe!=null){
                p.setBiDiPipe(biDiPipe);
                yes=true;
            }else{
                yes=false;
            }
        }
        return yes;
    }
    public boolean sendMultipleMessage(DCPeerGroup peerGroup,Message message){
        boolean yes=false;
        for(DCPipe p:peerGroup.getPipeList()){
            try {
                p.getBiDiPipe().sendMessage(message);
                yes=true;
            } catch (IOException e) {
                e.printStackTrace();
                yes=false;
            }
        }
        return yes;
    }
    public boolean closeMultipleChat(DCPeerGroup peerGroup){
        boolean yes=false;
        for(DCPipe pipe:peerGroup.getPipeList()){
            try {
                pipe.getBiDiPipe().close();
                yes=true;
            } catch (IOException e) {
                e.printStackTrace();
                yes=false;
            }
        }
        return yes;
    }

}
