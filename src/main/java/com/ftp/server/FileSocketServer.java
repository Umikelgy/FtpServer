package com.ftp.server;


import com.ftp.server.message.ServerMessage;
import org.apache.commons.net.DefaultSocketFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *@description:
 *
 *@author 10068921(LgyTT)
 *@date 2018/11/30 16:54
 */
public class FileSocketServer {
    final static Logger log= LogManager.getLogger(FileSocketServer.class);
    Socket socket=null;
    InputStream is=null;
    OutputStream os=null;
    ServerSocket serverSocket=null;
    public FileSocketServer() {
        try {
             serverSocket=new DefaultSocketFactory().createServerSocket(2122);
             initStartFtp();
        } catch (IOException e) {
            log.error("socketServer open failed!");
            e.printStackTrace();
        }


    }
    public  Socket  accept() {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                log.error("socketServer accept is failed!");
                e.printStackTrace();
            }
return socket;
    }
    public String receiver(){
        if(!socket.isConnected()){
            log.error("socket is not connected!");
            return null;
        }
        StringBuffer sb=new StringBuffer();
        try {
            is=socket.getInputStream();
            //使用inputStream的read会出现“填满数组”的情况.
           BufferedReader br=new BufferedReader(new InputStreamReader(is));
           String msg;
                while(( msg=br.readLine())!=null){
                    sb.append(new String(msg.getBytes(),"utf-8"));
                }
            socket.shutdownInput();
            return sb.toString();

        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
return null;
    }
    public void send(String msg){
        if(!socket.isConnected()){
            log.error("socket is not connected!");
            return;
        }
        try {
            os=socket.getOutputStream();
            os.write(msg.getBytes("utf-8"));
            os.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     *@description
     * //启动ftp服务器
     *@param
     *@return
     *@anthor  10068921
     */
    public static void initStartFtp () throws IOException {
        ServerMessage messages=ServerMessage.newBuilder()
                .Name("admin")
                .Passwd("1234")
                .HomeDirectory("E:\\FTPSeverPath")
                .ListenName("default")
                .FtpPort(2121)
                .build();
        FtpServer ftpServer= startServerFtp(messages);
        try {
            ftpServer.start();
            System.out.println("start server is successful!!!");
        } catch (FtpException e) {
            log.error("connect FTP is failed!!");
            e.printStackTrace();
        }
    }

    private static FtpServer startServerFtp(ServerMessage messages) {
        //开启ftp服务器用于文件传输
        FtpServerFactory serverFactory=new FtpServerFactory();
        ListenerFactory factory=new ListenerFactory();
        //设置端口
        factory.setPort(messages.FtpPort());
        //替换默认监听
        serverFactory.addListener(messages.ListenName(),factory.createListener());
        //设置用户信息
        BaseUser user=new BaseUser();
        user.setName(messages.Name());
        user.setPassword(messages.Passwd());
        user.setHomeDirectory(messages.HomeDirectory());

        //添加用户权限
        List<Authority> authorities=new ArrayList<Authority>();
        authorities.add(new WritePermission());
        user.setAuthorities(authorities);
        try{
            serverFactory.getUserManager().save(user);
            //配置文件位置（如使用配置文件方式初始化ftp
            return serverFactory.createServer();
        }catch (FtpException e){
            e.fillInStackTrace();
            return null;
        }
        /**
         * 也可以使用配置文件来管理用户
         */
//      PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
//      userManagerFactory.setFile(new File("users.properties"));
//      serverFactory.setUserManager(userManagerFactory.createUserManager());
    }


    public void closed(){
        if(os!=null&&is!=null) {
            try {
                os.close();
                is.close();
                socket.close();
            } catch (IOException e) {
                log.error(e.getMessage());
//                e.printStackTrace();
            }
        }
    }
}
