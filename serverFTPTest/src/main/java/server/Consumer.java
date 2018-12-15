package server;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
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

          OutputStream os=  new Consumer().sendMsg(socket,"E:\\个人软件");
            socket.shutdownOutput();
            InputStream is=socket.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is,"utf-8"));
            String msg;
            StringBuffer sb=new StringBuffer();
            while((msg=br.readLine())!=null){
                sb.append(new String(msg.getBytes(),"utf-8")+"\n");
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
