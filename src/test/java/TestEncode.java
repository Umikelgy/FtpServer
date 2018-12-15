
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author 10068921(LgyTT)
 * @description:
 * @date 2018/12/13 10:44
 **/
public class TestEncode {
    @Test
    public void testEncode(){
        System.out.println(System.getProperty("file.encoding"));
    }
    @Test
    public void testCmd(){
        try {
          Process process=  Runtime.getRuntime().exec("ipconfig");
            BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream(),"gb2312"));
            StringBuffer messages=new StringBuffer();
            String msg;
            while((msg=br.readLine())!=null){
                messages.append(new String(msg.getBytes(),"gb2312"));
            }
            System.out.println(messages.toString());
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
