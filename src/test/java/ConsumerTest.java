import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author 10068921(LgyTT)
 * @description:
 * @date 2018/12/24 16:13
 **/
public class ConsumerTest {
    @Test
    public void Test(){
        try {
            Socket socket=new Socket();
            socket.connect(new InetSocketAddress("10.111.24.72",2122));

            OutputStream os=  new ConsumerTest().sendMsg(socket,"cd E:\\个人软件");
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

    private OutputStream sendMsg(Socket socket, String s) throws IOException {
        OutputStream os=socket.getOutputStream();
        os.write(s.getBytes());
        os.flush();

        System.out.println("sending is successful!! ");
        return os;
    }
}
