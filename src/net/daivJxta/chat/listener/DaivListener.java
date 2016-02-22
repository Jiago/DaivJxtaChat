package net.daivJxta.chat.listener;

import net.daivJxta.chat.entity.DCEntityManager;
import net.daivJxta.chat.entity.DCPipe;
import net.daivJxta.chat.entity.DCPipeType;
import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.document.Advertisement;
import net.jxta.protocol.DiscoveryResponseMsg;
import net.jxta.protocol.PipeAdvertisement;

import java.util.Enumeration;

/**
 * 聊天室通告总监听，接收到的监听分成组群监听、对等体监听和通告监听
 * Created by zsh_o on 2015/5/9.
 */
public class DaivListener implements DiscoveryListener {

    DCEntityManager dcEntityManager;

    public DaivListener(DCEntityManager dcEntityManager) {
        this.dcEntityManager = dcEntityManager;
    }

    @Override
    public void discoveryEvent(DiscoveryEvent discoveryEvent) {
        DiscoveryResponseMsg drm=discoveryEvent.getResponse();

        Advertisement adv;
        Enumeration en=drm.getAdvertisements();//获取通告枚举
        if(en!=null){
            while(en.hasMoreElements()){
                adv=(Advertisement)en.nextElement();
                //分离出管道通告,交付管道通告处理器
                if(adv instanceof PipeAdvertisement){
                    processPipAdv((PipeAdvertisement)adv);
                }
            }
            //刷新列表
        }
    }

    public void processPipAdv(PipeAdvertisement padv){
        DCPipe dcPipe=new DCPipe(padv);
        if(dcPipe.getType().equals(DCPipeType.SINGLE)){
            dcEntityManager.addPip(dcPipe);
        }
        if(dcPipe.getType().equals(DCPipeType.MULTIPLE)){
            dcEntityManager.addPeerGroup(dcPipe);
        }
    }
}
