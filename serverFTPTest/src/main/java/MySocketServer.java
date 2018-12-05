import Commond.HandleCmd;
import org.apache.commons.net.DefaultSocketFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
 *@description:
 *
 *@author 10068921(LgyTT)
 *@date 2018/11/29 13:40
 */
public class MySocketServer {
    public static void main(String []args){
        /*try {
            ServerSocket serverSocket=new DefaultSocketFactory().createServerSocket(2311);
            Socket  socket=serverSocket.accept();

            String msg="Comd from Server";
            byte[] bytes=new byte[10];
            StringBuffer sb=new StringBuffer();
            OutputStream os=socket.getOutputStream();
            os.write(bytes);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        System.out.println(new HandleCmd().resultCmd());
    }

}
