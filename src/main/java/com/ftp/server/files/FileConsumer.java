package com.ftp.server.files;

import com.ftp.server.message.UserMessage;

/**
 * @author 10068921(LgyTT)
 * @description:
 * 文件客户端，用于本地文件与ftp服务的交互
 * @date 2018/12/24 14:26
 **/
public class FileConsumer {
    /**
     *@description
     * fileTfpLogin
     *@param
     *@return
     *@anthor  10068921
     */
    public static void login(){
         String hostName="10.111.24.72";
         Integer port=2121;
         String username="admin";
         String passwd="1234";
        UserMessage message=UserMessage.newBuilder()
                .setHostName(hostName)
                .setName(username)
                .setPasswd(passwd)
                .setPort(port)
                .builder();
     FtpUtils.loginFtp(message);
    }

}
