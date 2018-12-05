package com.ftp.files;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 *@description:
 *
 *@author 10068921(LgyTT)
 *@date 2018/11/28 15:16
 */
public class FileListUtils {
    private final  static File[] files=File.listRoots();
    private final static Logger log= LogManager.getLogger(FileListUtils.class);
    //返回某文件夹下的所有文件，如果文件路径（Path）为空，则返回可用磁盘根目录（如：C：\)
    public  List<String> getPath(String Path){
        List<String >result=new ArrayList<>();
        if(Path==null){
            for(File file:files)
                result.add(file.getPath());
            return result;
        }
        try {
            File[] Pfiles = new File(Path).listFiles();
            if (!Path.endsWith("\\"))
                Path = Path + "\\";
            for (File file : Pfiles)
                result.add(Path + file.getName());
    }catch (Exception e){
            log.error("NullPointerException :Path is not exist!");
//            System.exit(1);
        }
        return result;

    }
    public static String ToJson(List<String> paths){
        return JSON.toJSONString(paths);


    }
    @Test
    public void TestP(){
        List<String >list=new FileListUtils().getPath("E:\\");
        System.out.println(list);
    }
}
