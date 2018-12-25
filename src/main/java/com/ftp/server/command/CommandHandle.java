package com.ftp.server.command;


import com.ftp.server.FileSocketServer;
import com.ftp.server.common.Command;
import com.ftp.server.files.FileConsumer;
import com.ftp.server.files.FileListUtils;
import com.ftp.server.files.FtpUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

/**
 * @author 10068921(LgyTT)
 * @description: 处理命令
 * @date 2018/12/13 16:43
 **/
public class CommandHandle {
    private FileSocketServer fileSocketServer;
    private FtpUtils ftpUtils;
    final static Logger logger = LogManager.getLogger(CommandHandle.class);
    public CommandHandle(FileSocketServer fileSocketServer) {
        this.fileSocketServer=fileSocketServer;
        FileConsumer.login();
        ftpUtils=new FtpUtils();
    }

    /**
     *@description
     * 处理命令方法
     *@param  command
     *@return
     *@anthor  10068921
     */
    public  void handle(String command){
        switch (firstCommand(command)){
            case Command.CD:
                openDirectory(command);break;
            case Command.UPLOAD :
                upload(command);break;
            case Command.DOWNLOAD:
                download(command);
            case Command.LOGOUT:
                logout();break;
            default:
                notCommand(command);
               break;
        }

    }

    public  void notCommand(String command) {
        String msg="The command ["+command+"] is false,please reInput !!";
        fileSocketServer.send(msg);
        System.out.println(msg);
        fileSocketServer.closed();

    }

    private void logout() {
        if(FtpUtils.logoutFtp()){
            System.out.println("the ftpServer is logout!!");
        }
    }

    /**
     *@description
     * 下载文件
     *@param  command:命令
     *@return
     *@anthor  10068921
     */
    private void download(String command) {
        String [] parserCommands=command.substring(9).split(" ");
        if(parserCommands.length!=3){
            logger.error(command+"The Command is false,please reInput!");
            return;
        }
        String fileName=parserCommands[0];
        String filePath=parserCommands[1];
        String localPath=parserCommands[2];
        String key="\\";
        String key1="/";
        if(filePath.endsWith(key)||fileName.endsWith(key1)){
            filePath=filePath+"/"+fileName;
        } else {
            filePath=filePath+fileName;
        }
        ftpUtils.uploadFile(Command.FTPTEMP,fileName,filePath);
        ftpUtils.downloadFile(Command.FTPTEMP,fileName,localPath);
        String msg="download is successful!";
        fileSocketServer.send(msg);
    }

    /**
     *@description
     * 上传文件，解析command，去除“upload”后，前一个为文件名，后一个为文件路径
     *@param  command:命令
     *@return
     *@anthor  10068921
     */
    private void upload(String command) {
        String [] parserCommands=command.substring(7).split(" ");
        if(parserCommands.length!=2){
            logger.error(command+"The Command is false,please reInput!");
            return;
        }
        String fileName=parserCommands[0];
        String filePath=parserCommands[1];
        ftpUtils.downloadFile(Command.FTPTEMP,fileName,filePath);
        String msg="upload is successful!";
        fileSocketServer.send(msg);
    }
    private String firstCommand(String command){
        if(!Strings.isBlank(command)){
            return command.split(" ")[0];
        }
        return command;
    }
    /**
     *@description
     * 打开路径
     *@param  s
     *@return
     *@anthor  10068921
     */
    private  void openDirectory(String s) {
        String filePath=s.substring(3);
        List<String> fileList =  FileListUtils.getPath(filePath.trim());

        System.out.println("The path ["+filePath+"] has Directory that are :");
        for(String filename :fileList){
            System.out.println(filename);
        }
        fileSocketServer.send(FileListUtils.ToJson(fileList));

    }

}
