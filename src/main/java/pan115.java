import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.ArrayList;

//import org.apache.commons.io.FileUtils;
//import org.openqa.selenium.OutputType;

import io.appium.java_client.android.AndroidDriver;

public class pan115 {
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
            driver=Util.appiumParamSetting("com.ylmf.androidclient","com.main.partner.user.activity.LogActivity",writeProgress,udid,appiumPort);

            //线程睡眠20秒等待APP启动完成
            Thread.sleep(20000);

            int x=driver.manage().window().getSize().width;
            int y=driver.manage().window().getSize().height;

            pan115_function pf=new pan115_function(dir, x, y, writeProgress);

            if (pf.isElementExits("xpath", "//*[@class='android.widget.ImageButton' and @content-desc='转到上一层级']",driver)){
                driver.findElementByXPath("//*[@class='android.widget.ImageButton' and @content-desc='转到上一层级']").click();
                Thread.sleep(5000);
            }

            if (pf.isElementExits("xpath", "//*[@resource-id='android:id/button1' and @text='忽略风险']", driver)){
                driver.findElementByXPath("//*[@resource-id='android:id/button1' and @text='忽略风险']").click();
                Thread.sleep(5000);
            }

            driver.findElementById("com.ylmf.androidclient:id/ll_transfer").click();
            Thread.sleep(5000);

            pf.saveReceiveData(driver, "115网盘-接收");
            pf.saveSaveToData(driver, "115网盘-转存");
            pf.saveLinkTaskData(driver, "115网盘-链接任务");
            pf.saveUploadData(driver, "115网盘-上传");
            pf.saveDownloadData(driver, "115网盘-下载");
            driver.findElementByAccessibilityId("转到上一层级").click();
            Thread.sleep(2000);
            driver.findElementById("com.ylmf.androidclient:id/tv_file").click();
            Thread.sleep(2000);
            pf.saveVideoData(driver, "115网盘-视频");
            pf.saveMusicData(driver, "115网盘-音乐");
            pf.savePictureData(driver, "115网盘-图片");
            pf.saveDocData(driver, "115网盘-文档");
            pf.saveAppData(driver, "115网盘-应用");
            pf.saveArchiveData(driver, "115网盘-压缩包");

            Util.sendProgressEndMessage(writeProgress);

            ArrayList<String> attachmentsList=pf.getAttachments();
            if (attachmentsList.size()>0){
                Util.sendAttachments(attachmentsList, writeProgress);
            }
            Util.sendAttachmentEndMessage(writeProgress);

            pf.downloadFile(driver, readProgress, writeProgress);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.sendExitMessage(writeProgress);
            Util.close(driver, readProgress, writeProgress, socket);
        }
    }
}
