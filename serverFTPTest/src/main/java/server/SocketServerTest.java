package server;

import org.apache.commons.net.DefaultSocketFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
 *@description:
 *
 *@author 10068921(LgyTT)
 *@date 2018/11/30 9:54
 */
public class SocketServerTest {
    public static void main(String []args) throws IOException {
        ServerSocket serverSocket=new DefaultSocketFactory().createServerSocket(2122);

        while(true) {
            Socket socket=serverSocket.accept();
            SocketServerTest test = new SocketServerTest();
            InputStream is=test.receiverMsg(socket);

            OutputStream os=test.sendMsg(socket, "Server ");
            os.close();
            is.close();
            socket.close();
        }
    }
    private InputStream receiverMsg(Socket socket){
        InputStream is=null;
        String result=null;
        try {
             is=socket.getInputStream();
            byte[]bytes=new byte[10];
            StringBuffer sb=new StringBuffer();
            while(is.read(bytes)!=-1)
                sb.append(new String (bytes));
            result=sb.toString();
            socket.shutdownInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("The consumer msg is "+result);
        return is;
    }
    private OutputStream sendMsg(Socket socket,String msg){
        try {
            OutputStream os=socket.getOutputStream();
            os.write(msg.getBytes());
            os.flush();
            return os;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
