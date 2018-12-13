package com.ftp.server;

/**
 *@description:
 *
 *@author 10068921(LgyTT)
 *@date 2018/11/27 9:05
 */
public class ServerMessage {
    private String name;
    private String password;
    private String homeDirectory;
    private String listenName;
    private int ftpPort;


    public ServerMessage(Builder builder) {
        name = builder.name;
        password = builder.passwd;
        homeDirectory = builder.homeDirectory;
        listenName = builder.listenName;
        ftpPort = builder.ftpPort;
    }

    public static Builder newBuilder(){
        return new Builder();
    }
    public static class Builder {
        String name;
        String passwd;
        String homeDirectory;
        String listenName;
        int ftpPort;

        public Builder Name(String name){
        this.name=name;
        return this;
        }
        public Builder Passwd(String passwd){
            this.passwd=passwd;
            return this;
        }
        public Builder HomeDirectory(String homeDirectory){
            this.homeDirectory=homeDirectory;
            return this;
        }
        public Builder ListenName(String listenName){
            this.listenName=listenName;
            return this;
        }
        public Builder FtpPort(int port){
            this.ftpPort=port;
            return this;
        }

        public ServerMessage build() {
            return new ServerMessage(this);
        }
    }
    public String Name(){
        return name;
    }
    public String Passwd(){
        return password;
    }
    public String HomeDirectory(){
        return homeDirectory;
    }
    public String ListenName(){
        return listenName;
    }
    public int FtpPort(){
        return ftpPort;
    }


}
