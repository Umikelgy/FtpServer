import com.ftp.TFPutils.server.FileSocketServer;
import com.ftp.files.FileListUtils;

import java.util.List;

/*
 *@description:
 *
 *@author 10068921(LgyTT)
 *@date 2018/11/30 17:20
 */
public class StartMain {
    public static void main(String []args ) {
        FileSocketServer server = new FileSocketServer();
        while (true) {
            server.accept();
            String receiver = server.Receiver();
            List<String> FileList = new FileListUtils().getPath(receiver.trim());

            System.out.println("The path ["+receiver+"] has Directory that are :");
            for(String filename :FileList)
            System.out.println(filename);

            server.Send(FileListUtils.ToJson(FileList));

            server.closed();
        }
    }
}
