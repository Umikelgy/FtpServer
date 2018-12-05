package com.ftp.socketClient;

import org.apache.commons.net.DefaultSocketFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
 *@description:
 *
 *@author 10068921(LgyTT)
 *@date 2018/11/28 17:01
 */
public class TestClientData {
    public static void main(String []args){
        try {
            Socket client=new DefaultSocketFactory().createSocket("10.111.24.72",2121);
            System.out.println(client.isConnected());
          InputStream is= client.getInputStream();
          byte[]bs=new byte[10];
          StringBuffer sb=new StringBuffer();
          int i=0;
          while((i=is.read(bs))!=-1){
              System.out.println("not null"+i);
              sb.append(new String(bs));
          }
          System.out.println("sb="+sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void TestServer(){
        try {
            ServerSocket  serverSocket=new DefaultSocketFactory().createServerSocket(2121);
            Socket socket=serverSocket.accept();
            OutputStream os=socket.getOutputStream();
            String msg="The commend";

            os.write(msg.getBytes());
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
