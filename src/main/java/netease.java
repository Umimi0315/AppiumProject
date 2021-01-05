import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.PrintStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.net.URL;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

//import org.apache.commons.io.FileUtils;
//import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

//import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class netease {
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
            //driver=Util.appiumParamSetting("com.netease.mobimail","com.netease.mobimail.activity.LaunchActivity",writeProgress);
            driver=Util.appiumParamSetting("com.netease.mobimail","com.netease.mobimail.activity.TabActivity",writeProgress,udid,appiumPort);

            //线程睡眠20秒等待APP启动完成
            Thread.sleep(20000);

            int x=driver.manage().window().getSize().width;
            int y=driver.manage().window().getSize().height;

            netease_function nf=new netease_function(dir, x, y, writeProgress);

            if(nf.isElementExits("id", "com.netease.mobimail:id/alert_dialog_btnCancel", driver)){
                driver.findElementById("com.netease.mobimail:id/alert_dialog_btnCancel").click();
                Thread.sleep(5000);
            }

            if (nf.isElementExits("id", "com.netease.mobimail:id/iv_close", driver)){
                driver.findElementById("com.netease.mobimail:id/iv_close").click();
                Thread.sleep(5000);
            }

            nf.saveInboxData(driver, "网易邮箱-收件箱");
            nf.saveRedFlagMailData(driver, "网易邮箱-红旗邮件");
            nf.saveDraftBoxData(driver, "网易邮箱-草稿箱");
            nf.saveOutboxData(driver, "网易邮箱-发件箱");
            nf.saveSubscribeMailData(driver, "网易邮箱-订阅邮件");
            nf.saveAdvertisementMailData(driver, "网易邮箱-广告邮件");
            nf.saveSpamData(driver, "网易邮箱-垃圾邮件");
            nf.saveDeletedData(driver, "网易邮箱-已删除");
            nf.saveContactData(driver, "网易邮箱-通讯录");

            Util.sendProgressEndMessage(writeProgress);

            ArrayList<String> attachmentsList=nf.getAttachments();
            if (attachmentsList.size()>0){
                Util.sendAttachments(attachmentsList, writeProgress);
            }
            Util.sendAttachmentEndMessage(writeProgress);

            nf.downloadFile(driver, readProgress, writeProgress);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.sendExitMessage(writeProgress);
            Util.close(driver,readProgress, writeProgress, socket);
        }
    }
}
