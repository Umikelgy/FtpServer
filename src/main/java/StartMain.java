import com.ftp.server.FileSocketServer;
import com.ftp.server.command.CommandHandle;

/**
 *@description:
 *服务运行类（主类）
 *@author 10068921(LgyTT)
 *@date 2018/11/30 17:20
 */
public class StartMain {
    public static void main(String []args ) {
        FileSocketServer fileSocketServer= new FileSocketServer();
        System.out.println("服务已开启，端口号为：socket：2122，ftpServer:2121");
        while (true) {
            fileSocketServer.accept();
            String receiver = fileSocketServer.receiver();

            CommandHandle commandHandle=new CommandHandle(fileSocketServer);

            commandHandle.handle(receiver);


            fileSocketServer.closed();
        }
    }
}
