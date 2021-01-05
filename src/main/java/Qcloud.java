import java.io.File;
//import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.util.ArrayList;

import io.appium.java_client.android.AndroidDriver;

public class Qcloud {
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

        Socket socket=new Socket(ip,port);
        String udid=args[3];

        int appiumPort=Integer.parseInt(args[4]);

        String pid= ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        OutputStream writeProgress=socket.getOutputStream();
        InputStream readProgress = socket.getInputStream();
        AndroidDriver driver=null;

        try {
            Util.waitForStart(readProgress);
            Util.sendProgressStartMessage(writeProgress);

            //初始化appium
            driver=Util.appiumParamSetting("com.qq.qcloud","com.qq.qcloud.activity.WeiyunRootActivity",writeProgress,udid,appiumPort);

            //线程睡眠20秒等待APP启动完成
            Thread.sleep(20000);

            int x=driver.manage().window().getSize().width;
            int y=driver.manage().window().getSize().height;

            Qcloud_function qf=new Qcloud_function(dir, x, y, writeProgress);

            driver.findElementById("com.qq.qcloud:id/ct_classify").click();
            Thread.sleep(2000);
            driver.findElementById("com.qq.qcloud:id/search_entrance_fl").click();
            Thread.sleep(2000);

            qf.saveDocumentationData(driver, "腾讯微云-文档");
            qf.savePictureData(driver, "腾讯微云-图片");
            qf.saveVideoData(driver, "腾讯微云-视频");
            qf.saveNotesData(driver, "腾讯微云-笔记");
            qf.saveMusicData(driver, "腾讯微云-音乐");
            qf.saveOtherData(driver, "腾讯微云-其他");

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
            Util.close(driver, readProgress, writeProgress, socket);
        }

    }

}
