package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/*
 *@description:
 *
 *@author 10068921(LgyTT)
 *@date 2018/11/30 10:01
 */
public class Consumer {
    public static void main(String[]args){
        try {
            Socket socket=new Socket();
            socket.connect(new InetSocketAddress("10.111.24.72",2122));
            System.out.println(socket.isBound());

          OutputStream os=  new Consumer().sendMsg(socket,"E:\\ChromeDowns");
            socket.shutdownOutput();
            InputStream is=socket.getInputStream();
            byte[]bytes=new byte[10];
            StringBuffer sb=new StringBuffer();
            int i=-2;
            while(i!=-1&&i!=0){
                i=is.read(bytes);
                sb.append(new String(bytes,"utf-8"));
//                System.out.println(i+" receiver msg "+sb.toString()) ;
            }

            System.out.println("The directory is "+sb.toString());
             is.close();
             os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private  OutputStream sendMsg(Socket socket ,String msg) throws IOException {
        OutputStream os=socket.getOutputStream();
        os.write(msg.getBytes());
        os.flush();
        System.out.println("sending is successful!! ");
        return os;
    }
}
