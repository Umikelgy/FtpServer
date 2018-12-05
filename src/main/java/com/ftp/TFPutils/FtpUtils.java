package com.ftp.TFPutils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.net.ftp.FtpClient;

import java.io.*;
import java.net.MalformedURLException;

/*
 *@description:
 *
 *@author 10068921(LgyTT)
 *@date 2018/11/26 15:47
 */
public class FtpUtils {
    public String hostName="10.111.24.72";
    public Integer port=2121;
    public String username="admin";
    public String passwd="1234";
    public static FTPClient client=null;

    final static Logger logger = LogManager.getLogger(FtpUtils.class);
    /**
     *@description
     * 初始化登陆ftp
     *@param
     *@return
     *@anthor  10068921(lgyTT)
     *@date  2018/11/26
     *@other
     */
    public static boolean  initFtpClient(UserMessage message){//登陆ftp
        boolean flag=false;
        client=new FTPClient();
        client.setControlEncoding("utf-8");

        try {
            client.connect(message.getHostName(),message.getPort());
            client.login(message.getName(),message.getPasswd());
            int replyCode=client.getReplyCode();//是否登陆服务器
            if(!FTPReply.isPositiveCompletion(replyCode))
                logger.error("Connect failed---ftp");
            flag=true;
            System.out.println("connect successful---ftp");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return flag;
    }
    /**
     *@description
     * 退出登录
     *@param
     *@return
     *@anthor  10068921(lgyTT)
     *@date  2018/11/27
     *@other
     */
    public static boolean logoutFtp() {
        boolean flag=false;

            try{
                client.logout();
                client.disconnect();
                flag=true;
            }catch (FTPConnectionClosedException e){
                logger.error("the connection is close!");
                e.printStackTrace();
            }catch (IOException e){
                logger.error("this is an error occurs while disconnecting");
                e.printStackTrace();
            }

        return flag;

    }
    public void initFtpClient(){
        client=new FTPClient();
        client.setControlEncoding("UTF-8");

        try {
            client.connect(hostName,port);
            client.login(username,passwd);
            int replyCode=client.getReplyCode();//是否登陆服务器
            if(!FTPReply.isPositiveCompletion(replyCode))
                logger.error("connect failed ---ftp");
            System.out.println("connect successful ---ftp");
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String []args){
//        new FtpUtils().uploadFile("E:\\FTPSeverPath","testExcel.xlsx","E:\\testFile\\testExcel.xlsx");
//        new FtpUtils().getHostDirectory();
    }

    /**
     *@description
     * 上传文件
     *@param  pathname ftp服务器保存地址
     * @param  fileName 上传到ftp的文件名
     * @param originFileName 待上传文件名称（绝对地址）
     *@return
     *@anthor  10068921(lgyTT)
     *@date  2018/11/26
     *@other
     */
    public boolean uploadFile(String pathname,String fileName,String originFileName){
        boolean flag=false;
        InputStream inputStream=null;
        try {
            inputStream=new FileInputStream(new File(originFileName));
          //  initFtpClient();登陆
            client.setFileType(client.BINARY_FILE_TYPE);
            client.makeDirectory(pathname);
            client.changeWorkingDirectory(pathname);
            client.storeFile(fileName,inputStream);
            inputStream.close();
//            client.logout();
            flag=true;
            System.out.println("upload successful!!");

        }catch (Exception e){
            logger.error("upload failed!!");
            e.printStackTrace();
        }finally {
            if(client.isConnected()) {
                try {
                    client.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if(inputStream!=null)
                try {
                    inputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
        }
        return flag;
    }
/**
 *@description
 * 上传文件
 *@param  pathName ftp服务保存地址
 * @param filename 上传文件名称
 * @param inputStream 输入文件流
 *@return
 *@anthor  10068921(lgyTT)
 *@date  2018/11/26
 *@other
 */
public boolean uploadFile(String pathName,String filename,InputStream inputStream){
    boolean flag=false;
    try{
       // initFtpClient();
        client.setFileType(client.BINARY_FILE_TYPE);
        client.makeDirectory(pathName);

        client.changeWorkingDirectory(pathName);
        client.storeFile(filename,inputStream);
        inputStream.close();
//        client.logout();
        flag=true;
        System.out.println("upload successful!!");

    }catch (Exception e){
        e.printStackTrace();
    }finally {
        if (client.isConnected()) {
            try {
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStream!= null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    return flag;
}
/**
 *@description
 * 更改目录路径
 *@param
 *@return
 *@anthor  10068921(lgyTT)
 *@date  2018/11/26
 *@other
 */
    public boolean changeWorkingDirectory(String directory){
        boolean flag=false;
        try {
            flag=client.changeWorkingDirectory(directory);
            if(flag)
                System.out.println("enter The '"+directory+"' is successful!!");

            else
                logger.error("entering the '"+directory+"' is failed and create new directory!!");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     *@description
     * 创建多层目录文件，如果ftp服务器已存在该文件，则不创建，否则，创建文件目录
     *@param
     *@return
     *@anthor  10068921(lgyTT)
     *@date  2018/11/26
     *@other
     */
    public boolean CreateDirectory(String remote) throws IOException {
        boolean flag=false;
        String directory=remote+"/";
        //如果远程目录不存在，这递归创建远程服务器目录
        if(!directory.equalsIgnoreCase("/")&&!changeWorkingDirectory(new String(directory))){
            int start=0;
            int end=0;
            if(directory.startsWith("/")){
                start=1;
            }else {
                start=0;
            }
            end=directory.indexOf("/",start);
            String path="";
            String paths="";
            while(true){
                String subDirectory=new String (remote.substring(start,end).getBytes("GBK"),"iso-8859-1");
                path=path+"/"+subDirectory;
                if(!existFile(path))
                    if(makeDirectory(subDirectory)){
                        changeWorkingDirectory(subDirectory);
                        flag=true;
                    }

                    else {
                        logger.error("create directory '"+subDirectory+"' is failed!!");
                    }
                    else
                        changeWorkingDirectory(subDirectory);
                    paths=paths+"/"+subDirectory;
                    start=end+1;
                    end=directory.indexOf("/",start);
                    if(end<=start)
                        break;
            }
        }
        return flag;
    }
//创建目录
    private boolean makeDirectory(String subDirectory) {
        boolean flag=false;
        try{
            flag=client.makeDirectory(subDirectory);
            if(flag)
                System.out.println("create directory is successful!!");
            else
                logger.error("create directory is failed!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    //判断文件是否存在
    private boolean existFile(String path) throws IOException {
        boolean flag=false;
        FTPFile[] ftpFileArr=client.listFiles();
        if(ftpFileArr.length>0)
            flag=true;
        return flag;
    }
    /** * 下载文件 *
     * @param pathname FTP服务器文件目录 *
     * @param filename 文件名称 *
     * @param localPath 下载后的文件路径 *
     * @return */
    public  boolean downloadFile(String pathname, String filename, String localPath){
        boolean flag = false;
        OutputStream os=null;
        try {
            System.out.println("开始下载文件");
//            initFtpClient();
            //切换FTP目录
            client.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = client.listFiles();
            for(FTPFile file : ftpFiles){
                if(filename.equalsIgnoreCase(file.getName())){
                    File localFile = new File(localPath + "/" + file.getName());
                    os = new FileOutputStream(localFile);
                    client.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
//            client.logout();
            flag = true;
            System.out.println("下载文件成功");
        } catch (Exception e) {
            System.out.println("下载文件失败");
            e.printStackTrace();
        } finally{
            if(client.isConnected()){
                try{
                    client.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /** * 删除文件 *
     * @param pathname FTP服务器保存目录 *
     * @param filename 要删除的文件名称 *
     * @return */
    public boolean deleteFile(String pathname, String filename){
        boolean flag = false;
        try {
            System.out.println("开始删除文件");
//            initFtpClient();
            //切换FTP目录
            client.changeWorkingDirectory(pathname);
            client.dele(filename);
//            client.logout();
            flag = true;
            System.out.println("删除文件成功");
        } catch (Exception e) {
            System.out.println("删除文件失败");
            e.printStackTrace();
        } finally {
            if(client.isConnected()){
                try{
                    client.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
}
