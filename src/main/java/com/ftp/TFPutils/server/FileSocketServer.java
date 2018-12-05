package com.ftp.TFPutils.server;

import com.ftp.TFPutils.ServerMessage;
import org.apache.commons.net.DefaultSocketFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
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
//             initStartFtp();
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
    public String Receiver(){
        if(!socket.isConnected()){
            log.error("socket is not connected!");
            return null;
        }
        StringBuffer sb=new StringBuffer();
        byte[]bytes=new byte[10];
        try {
            is=socket.getInputStream();
            int n=is.available();
            while(is.read(bytes)!=-1){
                String s=new String(bytes).trim();
                sb.append(s);

            }
            sb.append(n);
            socket.shutdownInput();
            return sb.toString();
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
return null;
    }
    public void Send(String msg){
        if(!socket.isConnected()){
            log.error("socket is not connected!");
            return;
        }
        try {
            os=socket.getOutputStream();
            os.write(msg.getBytes("iso-8859-1"));
            os.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void initStartFtp () throws IOException {//启动ftp服务器
        ServerMessage messages=ServerMessage.newBuilder()
                .Name("admin")
                .Passwd("1234")
                .HomeDirectory("E:\\FTPSeverPath")
                .ListenName("default")
                .FtpPort(2121)
                .build();
        FtpServer Server= new MyFtpServer().StartServerFtp(messages);
        try {
            Server.start();
            System.out.println("start server is successful!!!");
        } catch (FtpException e) {
            log.error("connect FTP is failed!!");
            e.printStackTrace();
        }
    }
    public void closed(){
        try {
            os.close();
            is.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
