package com.ftp.fileHandle;

import com.ftp.TFPutils.FtpUtils;
import com.ftp.TFPutils.server.MyFtpServer;
import com.ftp.TFPutils.ServerMessage;
import com.ftp.TFPutils.UserMessage;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.*;

/*
 *@description:
 *
 *@author 10068921(LgyTT)
 *@date 2018/11/27 9:02
 */
public class FileHandle {
    private static final Logger log= LogManager.getLogger(FileHandle.class);
    public void initStartFtp () throws IOException {//启动ftp服务器
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
            System.out.println("start server is successful!!");
        } catch (FtpException e) {
            log.error("connect FTP is failed!!");
            e.printStackTrace();
        }
    }
    public  boolean initConnectionFtp(){//连接ftp
        UserMessage message=UserMessage.newBuilder()
                .setHostName("10.111.24.72")
                .setName("admin")
                .setPasswd("1234")
                .setPort(2121)
                .builder();
      return   FtpUtils.initFtpClient(message);

    }
    public void initConnectionFtp(ServerMessage messages) throws IOException {//自定义 连接ftp
        FtpServer server=  new MyFtpServer().StartServerFtp(messages);
        try {
            server.start();
        } catch (FtpException e) {
            log.error("connect FTP is failed!!");
            e.printStackTrace();
        }
    }
    //上传文件（有一个计算机上传至另一个计算机，通过ftp服务器为中转）
    public void upload(String filePath,String ftpPath,String ItemFilePath){
        FtpUtils ftpUtils=new FtpUtils();
      if(!initConnectionFtp()){
          log.error("login is failed");
          return;
      }
      String filename=fileName(filePath);
      if (filePath==null&&ftpPath.equals(""))
          try {
              ftpPath=FtpUtils.client.printWorkingDirectory();
          } catch (IOException e) {
              log.error("get WorkingDirectory is failed!");
              e.printStackTrace();
          }
          ftpUtils.uploadFile(ftpPath,filename,filePath);//上传文件至服务器
        try {
            ftpUtils.downloadFile(FtpUtils.client.printWorkingDirectory(),filename,ItemFilePath);//从服务器上下载
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //文件地址中截取文件名
    private String fileName(String filePath) {
        int start=filePath.lastIndexOf("\\")+1;
        int end=filePath.length();
       return filePath.substring(start,end);
    }

    public static void main(String[]args) {
        FileHandle file = new FileHandle();

        try {
            file.initStartFtp();
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.initConnectionFtp();
//        file.upload("E:\\testFile\\test.properties","E:\\FTPSeverPath","E:\\down");

        try {
//            if(FtpUtils.logoutFtp());//退出登录
//            System.out.println("退出登录成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void TestPath(){
      File[ ]files=File.listRoots();
      for(File file:files)
          System.out.println(file.getPath());
    }

    }

