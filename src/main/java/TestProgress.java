import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.util.HashMap;

public class TestProgress {
    public static void main(String[] args) throws Exception {

/*        Socket socket= null;
        OutputStream writeProgress= null;
        InputStream readProgress = null;
        try {
            String ip=args[1];
            if (!Util.isIp(ip)) {
                System.out.println("输入的ip地址不合法!");
                return;
            }
            ip=Util.deleteSpace(ip);

            String portStr=args[2];
            if (!Util.isInteger(portStr)) {
                System.out.println("输入的端口号不合法!");
                return;
            }
            portStr=Util.deleteSpace(portStr);
            int port=Integer.valueOf(portStr);

            socket = new Socket(ip,port);

            String pid= ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
            writeProgress = socket.getOutputStream();
            readProgress = socket.getInputStream();

            Util.waitForStart(readProgress);
            Util.sendProgressStartMessage(writeProgress);

            for (int i=0;i<100;i+=2){
                Util.writeProgress(i, 0, ""+i,writeProgress);
                Thread.sleep(2000);
            }
            Util.sendProgressEndMessage(writeProgress);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.sendExitMessage(writeProgress);
            Util.close(null, readProgress, writeProgress, socket);
        }*/

        System.out.println(Util.available());



    }

}
