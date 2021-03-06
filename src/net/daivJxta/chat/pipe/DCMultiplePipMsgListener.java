package net.daivJxta.chat.pipe;

import net.daivJxta.chat.DaivJxta;
import net.daivJxta.chat.message.DCMessage;
import net.jxta.document.MimeMediaType;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.MessageElement;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;

import java.io.*;

/**
 * 群聊消息监听
 * Created by zsh_o on 2015/5/10.
 */
public class DCMultiplePipMsgListener implements PipeMsgListener {

    DaivJxta daivJxta;

    public DCMultiplePipMsgListener(DaivJxta daivJxta) {
        this.daivJxta = daivJxta;
    }

    //处理群聊消息
    @Override
    public void pipeMsgEvent(PipeMsgEvent pipeMsgEvent) {
        String text=null;
        Message message=pipeMsgEvent.getMessage();
        if(message!=null){
            String userName=message.getMessageElement("DaivChat","UserName").toString();
            String groupName=message.getMessageElement("DaivChat","GroupName").toString();//组群名称
            text=message.getMessageElement("DaivChat","MessageType").toString();
            if(text!=null&&text.equals("MultipleText")){
                text=message.getMessageElement("DaivChat","TextMessage").toString();
                if(text!=null){
                    //增加消息到借面的textarea中
                    String stext= DCMessage.transformMessageText(userName, text);
                    String recvText="["+userName+"]"+":"+stext;
                    daivJxta.getDcEntityManager().processMultipleMessage(groupName,userName, recvText);
                }
            }
            if(text!=null&&text.equals("MultipleFile")) {
                String fileName=message.getMessageElement("DaivChat","FileName").toString();
                InputStream inputStream=null;
                MessageElement element=message.getMessageElement("DaivChat","FileInputStream");
                if(element.getMimeType().equals(MimeMediaType.AOS)){
                    try {
                        inputStream=element.getStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                OutputStream outputStream= null;
                try {
                    File file=new File(daivJxta.getDaivJxtaConfig().getTheConfig().getHome()+"\\files\\"+userName+"\\"+fileName);
                    if(!file.getParentFile().exists()){
                        file.getParentFile().mkdir();
                    }
                    if(!file.exists()){
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    outputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                int read=0;
                byte[] bytes=new byte[1024];
                try {
                    while ((read=inputStream.read(bytes))!=-1){
                        outputStream.write(bytes,0,read);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                daivJxta.getDcEntityManager().initFileList();
                daivJxta.getDcEntityManager().getFileTree().repaint();
            }
        }
    }
}
