package net.daivJxta.chat.discovery;

import net.daivJxta.chat.listener.DaivListener;
import net.jxta.discovery.DiscoveryService;

/**
 * Created by zsh_o on 2015/5/9.
 */
public class DaivDiscovery {
    DiscoveryService discoveryService;
    DaivListener daivListener;

    long waittime= 5*1000L;//1分钟

    public DaivDiscovery(final DiscoveryService discoveryService, DaivListener daivListener) {
        this.discoveryService = discoveryService;
        this.daivListener = daivListener;
        discoveryService.addDiscoveryListener(daivListener);
        Thread thread= new Thread() {
            @Override
            public void run() {
                while(true){
                    discoveryService.getRemoteAdvertisements(
                            null,
                            DiscoveryService.ADV,
                            "Name",
                            "DaivChat-*",
                            1000
                            );
                    try {
                        Thread.sleep(waittime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

}
