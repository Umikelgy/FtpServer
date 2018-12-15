package fromserver.socketclient;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfiguration;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *@description:
 *
 *@author 10068921(LgyTT)
 *@date 2018/11/26 16:11
 */
public class MyFtpServer {
    public static void main(String []args){
        FtpServerFactory serverFactory=new FtpServerFactory();
        ListenerFactory factory=new ListenerFactory();

//设置端口
        factory.setPort(2122);
//替换默认监听
        serverFactory.addListener("default",factory.createListener());
        factory=new ListenerFactory();
        factory.setPort(2121);
        serverFactory.addListener("listener",factory.createListener());
//设置用户信息
        BaseUser user=new BaseUser();
        user.setName("admin");
        user.setPassword("1234");
        //设置用户主目录
        user.setHomeDirectory("E:\\FTPSeverPath");
        //添加权限
        List<Authority> authorities=new ArrayList<Authority>();
        authorities.add(new WritePermission());
        user.setAuthorities(authorities);

        Map<String , Listener> map=serverFactory.getListeners();
        System.out.println(map.size());
        SslConfiguration configuration=map.get("listener").getSslConfiguration();
        try {
            serverFactory.getUserManager().save(user);
            FtpServer server=serverFactory.createServer();
            server.start();
        } catch (FtpException e) {
            e.printStackTrace();
        }

    }

    public FtpServer StartServerFtp(ServerMessage messages) throws IOException {
        //开启ftp服务器用于文件传输
        FtpServerFactory serverFactory=new FtpServerFactory();
        ListenerFactory factory=new ListenerFactory();
        //设置端口
        factory.setPort(messages.FtpPort());
        //替换默认监听
        serverFactory.addListener(messages.ListenName(),factory.createListener());
        //设置用户信息
        BaseUser  user=new BaseUser();
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
                    LogManager.getLogger(MyFtpServer.class).error("connect ftp is failed!! and return null！！");
                    return null;
                }
        /**
         * 也可以使用配置文件来管理用户
         */
//      PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
//      userManagerFactory.setFile(new File("users.properties"));
//      serverFactory.setUserManager(userManagerFactory.createUserManager());
    }


}
