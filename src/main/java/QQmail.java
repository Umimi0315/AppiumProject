import java.io.File;
//import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.net.URL;
//import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
//import java.util.Set;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

//import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class QQmail {
	public static void main(String[] args) throws Exception {
        if (!Util.available()){
            return;
        }
        String dir=args[0];
        if(!(new File(dir).isDirectory())) {
            System.out.println("输入的路径不存在，请重新输入");
            return;
        }

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
        String udid=args[3];

        int appiumPort=Integer.parseInt(args[4]);

        Socket socket=new Socket(ip,port);


        String pid= ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        OutputStream writeProgress=socket.getOutputStream();
        InputStream readProgress = socket.getInputStream();
        AndroidDriver driver=null;

        try {
            Util.waitForStart(readProgress);
            Util.sendProgressStartMessage(writeProgress);

            //初始化appium
            driver=Util.appiumParamSetting("com.tencent.androidqqmail","com.tencent.qqmail.launcher.desktop.LauncherActivity",writeProgress,udid,appiumPort);

            //线程睡眠20秒等待APP启动完成
            Thread.sleep(20000);

            int x=driver.manage().window().getSize().width;
            int y=driver.manage().window().getSize().height;

            QQmail_function qf=new QQmail_function(dir, x, y, writeProgress);

            qf.saveInboxData(driver, "QQ邮箱-收件箱");
            qf.saveImportantContactMailData(driver, "QQ邮箱-重要联系人邮件");
            qf.saveImportantContactData(driver, "QQ邮箱-重要联系人");
            qf.saveStarMailData(driver, "QQ邮箱-星标邮件");
            qf.saveAddressBookData(driver, "QQ邮箱-通讯录");
            driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
            Thread.sleep(2000);
            qf.saveNotebookData(driver, "QQ邮箱-记事本");
            qf.saveGroupMailData(driver, "QQ邮箱-群邮件");
            qf.saveDraftBoxData(driver, "QQ邮箱-草稿箱");
            qf.saveSentData(driver, "QQ邮箱-已发送");
            qf.saveDeletedData(driver, "QQ邮箱-已删除");
            qf.saveTrashCanData(driver, "QQ邮箱-垃圾箱");

            Util.sendProgressEndMessage(writeProgress);

            ArrayList<String> attachmentsList=qf.getAttachments();
            if (attachmentsList.size()>0){
                Util.sendAttachments(attachmentsList, writeProgress);
            }
            Util.sendAttachmentEndMessage(writeProgress);

            qf.downloadFile(driver, readProgress, writeProgress);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.sendExitMessage(writeProgress);
            Util.close(driver,readProgress, writeProgress, socket);
        }

    }

}
