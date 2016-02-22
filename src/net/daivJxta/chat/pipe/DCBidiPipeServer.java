package net.daivJxta.chat.pipe;

import net.daivJxta.chat.DaivJxta;
import net.daivJxta.chat.entity.DCEntityManager;
import net.daivJxta.chat.entity.DCPipe;
import net.jxta.util.JxtaBiDiPipe;
import net.jxta.util.JxtaServerPipe;

import java.io.IOException;
import java.net.SocketException;

/**
 * 管道服务总入口
 * Created by zsh_o on 2015/5/10.
 */
public class DCBidiPipeServer {
    DCEntityManager dcEntityManager;
    DaivJxta daivJxta;

    DCMultiplePipMsgListener dcMultiplePipMsgListener;
    DCSinglePipMsgListener dcSinglePipMsgListener;

    public DCBidiPipeServer(DaivJxta daivJxta) {
        this.daivJxta = daivJxta;
        dcEntityManager=daivJxta.getDcEntityManager();
        dcSinglePipMsgListener=new DCSinglePipMsgListener(daivJxta);
        dcMultiplePipMsgListener=new DCMultiplePipMsgListener(daivJxta);

        initSingleChatPipe();

    }

    //初始化个人聊天服务管道
    public void initSingleChatPipe() {
        JxtaServerPipe serverPipe = null;
        try {
            serverPipe = new JxtaServerPipe(daivJxta.getTheNetPeerGroup(),dcEntityManager.getPipeMyself().getPipeAdvertisement());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serverPipe.setPipeTimeout(0);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        final JxtaServerPipe finalServerPipe = serverPipe;
        Thread thread= new Thread() {
            @Override
            public void run() {
                while(true){
                    JxtaBiDiPipe biDiPipe = null;
                    try {
                        biDiPipe= finalServerPipe.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(biDiPipe!=null){
                        final JxtaBiDiPipe finalBiDiPipe = biDiPipe;
                        Thread thread1 = new Thread(){
                            @Override
                            public void run(){
                                finalBiDiPipe.setMessageListener(dcSinglePipMsgListener);
                            }
                        };
                        thread1.start();
                    }
                    try {
                        Thread.sleep(3*1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    //添加群聊组群
    public void initMultipleChatPipe(DCPipe pipe) throws IOException {
        final JxtaServerPipe serverPipe=new JxtaServerPipe(daivJxta.getTheNetPeerGroup(),pipe.getPipeAdvertisement());
        serverPipe.setPipeTimeout(0);
        Thread thread= new Thread() {
            @Override
            public void run() {
                while(true){
                    JxtaBiDiPipe biDiPipe = null;
                    try {
                        biDiPipe=serverPipe.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(biDiPipe!=null){
                        final JxtaBiDiPipe finalBiDiPipe = biDiPipe;
                        Thread thread1 = new Thread(){
                            @Override
                            public void run(){
                                finalBiDiPipe.setMessageListener(dcMultiplePipMsgListener);
                            }
                        };
                        thread1.start();
                    }
                    try {
                        Thread.sleep(3*1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

}
