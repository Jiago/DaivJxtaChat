package net.daivJxta.chat.entity;

import net.daivJxta.chat.DaivJxta;
import net.jxta.document.AdvertisementFactory;
import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;

/**
 * Created by zsh_o on 2015/5/10.
 */
public class DCAdv {
    //创建自己的个人通道通告
    public static PipeAdvertisement createPipAdv(){
        PipeAdvertisement advertisement=(PipeAdvertisement) AdvertisementFactory.newAdvertisement(PipeAdvertisement.getAdvertisementType());
        advertisement.setPipeID(IDFactory.newPipeID(PeerGroupID.defaultNetPeerGroupID));
        advertisement.setType(PipeService.UnicastType);
        advertisement.setName("DaivChat-"+"Single-"+ DaivJxta.UserName);
        return advertisement;
    }

    public static PipeAdvertisement createGroupPipAdv(String groupName){
        PipeAdvertisement advertisement=(PipeAdvertisement) AdvertisementFactory.newAdvertisement(PipeAdvertisement.getAdvertisementType());
        advertisement.setPipeID(IDFactory.newPipeID(PeerGroupID.defaultNetPeerGroupID));
        advertisement.setType(PipeService.UnicastType);
        advertisement.setName("DaivChat-"+"Multiple-"+groupName+"-"+ DaivJxta.UserName);
        return advertisement;
    }
}
