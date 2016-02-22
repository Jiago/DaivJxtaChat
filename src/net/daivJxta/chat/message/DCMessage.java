package net.daivJxta.chat.message;

import net.daivJxta.chat.DaivJxta;
import net.daivJxta.chat.entity.DCPeerGroup;
import net.daivJxta.chat.entity.DCPipe;
import net.jxta.document.MimeMediaType;
import net.jxta.endpoint.InputStreamMessageElement;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.StringMessageElement;

import javax.swing.*;
import java.io.*;

/**
 * 用于创建聊天用的message
 * Created by zsh_o on 2015/5/10.
 */
public class DCMessage {
    //用于处理
    JTextArea singleTextArea;


    public static Message createSingleTextMessage(String text){
        Message message=new Message();
        StringMessageElement messageType=new StringMessageElement("MessageType","SingleText",null);
        StringMessageElement textMessage=new StringMessageElement("TextMessage",text,null);
        StringMessageElement userName=new StringMessageElement("UserName", DaivJxta.UserName,null);
        message.addMessageElement("DaivChat",userName);
        message.addMessageElement("DaivChat",messageType);
        message.addMessageElement("DaivChat",textMessage);
        return message;
    }
    public static Message createMultipleTextMessage(DCPeerGroup peerGroup,String text){
        Message message=new Message();
        StringMessageElement messageType=new StringMessageElement("MessageType","MultipleText",null);
        StringMessageElement textMessage=new StringMessageElement("TextMessage",text,null);
        StringMessageElement userName=new StringMessageElement("UserName", DaivJxta.UserName,null);
        StringMessageElement groupName=new StringMessageElement("GroupName",peerGroup.getGroupName(),null);
        message.addMessageElement("DaivChat",groupName);
        message.addMessageElement("DaivChat",userName);
        message.addMessageElement("DaivChat",messageType);
        message.addMessageElement("DaivChat",textMessage);
        return message;
    }
    public static Message createSingleFileMessage(File file){
        Message message=new Message();
        InputStream fileInputStream = null;
        try {
            fileInputStream=new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamMessageElement inputStreamMessageElement=null;
        try {
            inputStreamMessageElement=new InputStreamMessageElement("FileInputStream",MimeMediaType.AOS,fileInputStream,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringMessageElement messageType=new StringMessageElement("MessageType","SingleFile",null);
        StringMessageElement fileName=new StringMessageElement("FileName",file.getName(),null);
        StringMessageElement userName=new StringMessageElement("UserName", DaivJxta.UserName,null);
        message.addMessageElement("DaivChat",userName);
        message.addMessageElement("DaivChat",messageType);
        message.addMessageElement("DaivChat",fileName);
        message.addMessageElement("DaivChat",inputStreamMessageElement);
        return message;
    }

    public static Message createMultipleFileMessage(DCPeerGroup peerGroup,File file){
        Message message=new Message();
        InputStream fileInputStream = null;
        try {
            fileInputStream=new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamMessageElement inputStreamMessageElement=null;
        try {
            inputStreamMessageElement=new InputStreamMessageElement("FileInputStream",MimeMediaType.AOS,fileInputStream,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringMessageElement messageType=new StringMessageElement("MessageType","MultipleFile",null);
        StringMessageElement fileName=new StringMessageElement("FileName",file.getName(),null);
        StringMessageElement userName=new StringMessageElement("UserName", DaivJxta.UserName,null);
        StringMessageElement groupName=new StringMessageElement("GroupName",peerGroup.getGroupName(),null);
        message.addMessageElement("DaivChat",groupName);
        message.addMessageElement("DaivChat",userName);
        message.addMessageElement("DaivChat",messageType);
        message.addMessageElement("DaivChat",fileName);
        message.addMessageElement("DaivChat",inputStreamMessageElement);
        return message;
    }

    //设置显示文本对齐
    public static String transformMessageText(String userName,String messageText){
        int count=userName.length();
        String sspace=returnSpace(count+3);
        String ss[] = messageText.split("\n");
        String s=ss[0];
        if(ss.length>1){
            for(int i=1;i<ss.length;i++){
                s+=("\n"+sspace+ss[i]);
            }
        }
        return s;
    }
    //用于返回i个空格
    private static String returnSpace(int i){
        String s="";
        for(int j=0;j<i;j++){
            s+=" ";
        }
        return s;
    }
}
