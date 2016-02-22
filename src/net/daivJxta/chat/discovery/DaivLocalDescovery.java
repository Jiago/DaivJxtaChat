package net.daivJxta.chat.discovery;

import net.daivJxta.chat.DaivJxta;
import net.daivJxta.chat.entity.DCPipe;
import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.protocol.DiscoveryResponseMsg;
import net.jxta.protocol.PipeAdvertisement;

import java.io.IOException;
import java.util.Enumeration;

/**
 * 发现本地管道通告，并发布--防止重复产生同一用户的不同的通告
 * Created by zsh_o on 2015/5/15.
 */
public class DaivLocalDescovery implements DiscoveryListener {
    DaivJxta daivJxta;
    DiscoveryService discoveryService;

    public DaivLocalDescovery(DaivJxta daivJxta) {
        this.daivJxta = daivJxta;
        discoveryService=daivJxta.getTheNetPeerGroup().getDiscoveryService();
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
                    PipeAdvertisement pipeAdvertisement =(PipeAdvertisement) adv;
                    DCPipe dcPipe=new DCPipe(pipeAdvertisement);
                    if(dcPipe.getPeerName().equals(DaivJxta.UserName)){
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
}
