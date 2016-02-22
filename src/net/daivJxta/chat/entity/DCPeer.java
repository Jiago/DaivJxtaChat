package net.daivJxta.chat.entity;

import net.jxta.protocol.PeerAdvertisement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 删除
 * Created by zsh_o on 2015/5/9.
 */
public class DCPeer {
    HashMap<DCPeerGroup,DCPipe> groupPipeMap;//对等体所在的组群和对应的管道通告，用于群聊
    DCPipe dcPipe;//对等体对应的管道通告，用于单聊
    PeerAdvertisement peerAdvertisement;//对等体通告
    String PeerName;

    public DCPeer(PeerAdvertisement peerAdvertisement) {
        this.peerAdvertisement = peerAdvertisement;
        String[] ss=peerAdvertisement.getName().split("-");
        String name = null;
        if(ss.length>=2){
            name=ss[1];
        }
        PeerName=name;
        groupPipeMap=new HashMap<DCPeerGroup, DCPipe>();
    }

    public boolean addGroupPipe(DCPeerGroup peerGroup,DCPipe pipe){
        if(pipe.getType()==DCPipeType.MULTIPLE&&pipe.getGroupName().equals(peerGroup.getGroupName())&&PeerName.equals(pipe.getPeerName())){
            //判断对等体是否在此组群中
            String sgn=pipe.getGroupName();
            Iterator it=groupPipeMap.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry entry= (Map.Entry) it.next();
                if(sgn.equals(((DCPeerGroup)entry.getKey()).getGroupName())){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void setDcPipe(DCPipe dcPipe) {
        this.dcPipe = dcPipe;
    }

    public HashMap<DCPeerGroup, DCPipe> getGroupPipeMap() {
        return groupPipeMap;
    }

    public DCPipe getDcPipe() {
        return dcPipe;
    }

    public PeerAdvertisement getPeerAdvertisement() {
        return peerAdvertisement;
    }

    public String getPeerName() {
        return PeerName;
    }
}
