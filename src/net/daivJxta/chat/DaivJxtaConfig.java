package net.daivJxta.chat;

import net.daivJxta.chat.ui.frame.LoginFrame;
import net.daivJxta.chat.ui.frame.RegistFrame;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;

import javax.security.cert.CertificateException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * 查看本地是否有配置文件，没有则创建有则加载
 * Created by zsh_o on 2015/5/9.
 */
public class DaivJxtaConfig {
    private NetworkManager networkManager;
    private NetworkConfigurator TheConfig;
    private boolean LocalConfigExists=false;//本地配置文件是否存在
    private String Principal;
    private String Password;
    private DaivJxta daivJxta;

    public NetworkConfigurator getTheConfig() {//get方法获取网络配置器
        return TheConfig;
    }

    public DaivJxtaConfig(DaivJxta daivJxta) {	
    	this.daivJxta=daivJxta;
        this.networkManager = daivJxta.getNetworkManager();

        networkManager.setConfigPersistent(true);
        networkManager.setUseDefaultSeeds(true);
        try {
            TheConfig=networkManager.getConfigurator();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(TheConfig.exists()){
            LocalConfigExists=true;
            DaivJxta.isexist=true;
            File LocalConfig=new File(TheConfig.getHome(),"PlatformConfig");//获得配置文件的文件句柄
            try {
                TheConfig.load(LocalConfig.toURI());//加载配置文件
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            }
            Password=TheConfig.getPassword();
            Principal=TheConfig.getPrincipal();

            //加载登陆界面
            LoginFrame loginFrame=new LoginFrame(daivJxta);
        	loginFrame.setVisible(true);
        }
        else{
            DaivJxta.isexist=false;
        	LocalConfigExists=false;
        	//加载注册界面
        	RegistFrame registFrame=new RegistFrame(daivJxta);
        	registFrame.setVisible(true);
        }
    }

    public String getPrincipal() {
        if(LocalConfigExists){
            return Principal;
        }else{
            return null;
        }
    }

    public String getPassword() {
        if(LocalConfigExists){
            File file=new File(daivJxta.getDaivJxtaConfig().getTheConfig().getHome()+"\\config\\"+"password.xml");
            XMLDecoder xmlDecoder=null;
            String password=null;
            if(!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                xmlDecoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if(xmlDecoder!=null){
                password=(String)xmlDecoder.readObject();
                Password=password;
            }
            return Password;
        }else{
            return null;
        }
    }

    public void setName(String name){
        if(!LocalConfigExists){
            TheConfig.setName(name);
        }
    }

    public void setPrincipal(String principal) {
        if(!LocalConfigExists){
            TheConfig.setPrincipal(principal);
            this.Principal=principal;
        }
    }

    //xml方法保存密码
    public void setPassword(String password) {
        if(!LocalConfigExists){
            File file=new File(daivJxta.getDaivJxtaConfig().getTheConfig().getHome()+"\\config\\"+"password.xml");
            BufferedOutputStream oop=null;
            if(!file.exists()){
                try {
                    file.createNewFile();
                    oop=new BufferedOutputStream(new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(oop!=null){
                XMLEncoder xmlEncoder=new XMLEncoder(oop);
                xmlEncoder.flush();
                xmlEncoder.writeObject(password);
                xmlEncoder.close();
                try {
                    oop.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.Password=password;
        }
    }
    public boolean save(){
        try {
            TheConfig.save();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
