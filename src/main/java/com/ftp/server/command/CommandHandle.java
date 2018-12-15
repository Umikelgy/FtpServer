package com.ftp.server.command;


import com.ftp.server.common.Command;

import java.util.List;

/**
 * @author 10068921(LgyTT)
 * @description: 处理命令
 * @date 2018/12/13 16:43
 **/
public class CommandHandle {
    /**
     *@description
     * 处理命令静态方法
     *@param  command
     *@return
     *@anthor  10068921
     */
    public  void handle(String command){
        List<String> commandList= parser(command);
        switch (command){
            case Command.CD:openDirectory(commandList.get(0));
                break;
                default:
                    break;
        }

    }
/**
 *@description
 * 打卡路径
 *@param  s
 *@return
 *@anthor  10068921
 */
    private  void openDirectory(String s) {


    }
/**
 *@description
 * 解析命令
 *@param  command ：命令
 *@return  解析之后的命令以list方式返回
 *@anthor  10068921
 */
    private  List<String> parser(String command) {
        return null;
    }
}
