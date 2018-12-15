package com.ftp.server.files;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *@description:
 *获取本地文件结构
 *@author 10068921(LgyTT)
 *@date 2018/11/28 15:16
 */
public class FileListUtils {
    private final  static File[] files=File.listRoots();
    private final static Logger log= LogManager.getLogger(FileListUtils.class);

    /**
     * //返回某文件夹下的所有文件，如果文件路径（Path）为空，则返回可用磁盘根目录（如：C：\)
     * @param path
     * @return
     */
    public static List<String>  getPath(String path){
        List<String >result=new ArrayList<>();
        if(path==null){
            for(File file:files){

                result.add(file.getPath());
            }
            return result;
        }
        try {
            String addString="\\";
            File[] pFiles = new File(path).listFiles();
            if (!path.endsWith(addString)){
                path = path + "\\";
            }
            for (File file : pFiles){

                result.add(path + file.getName());
            }
    }catch (Exception e){
            log.error("NullPointerException :Path is not exist!");
        }
        return result;

    }
    public static String ToJson(List<String> paths) {
        return JSON.toJSONString(paths);


    }
}
