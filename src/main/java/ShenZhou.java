import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class ShenZhou {
	
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
            driver=Util.appiumParamSetting("com.szzc.ucar.pilot","com.szzc.splash.SplashActivity",writeProgress,udid,appiumPort);

            //线程睡眠8秒等待APP启动完成
            Thread.sleep(20000);

            int x=driver.manage().window().getSize().width;
            int y=driver.manage().window().getSize().height;

            ShenZhou_function sf=new ShenZhou_function(dir, x, y, writeProgress);

            //关闭更新
            if (sf.isElementExits("id", "com.szzc.ucar.pilot:id/update_dialog_leftbutton", driver)) {
                    driver.findElementById("com.szzc.ucar.pilot:id/update_dialog_leftbutton").click();
                    Thread.sleep(5000);
            }

            //关闭弹窗
            if (sf.isElementExits("id", "com.szzc.ucar.pilot:id/mall_close_layout", driver)){
                driver.findElementById("com.szzc.ucar.pilot:id/mall_close_layout").click();
                Thread.sleep(5000);
            }


            sf.savePersonalData(driver, "神州专车-个人资料");
            sf.saveVerifiedData(driver, "神州专车-实名认证");
            sf.saveWalletData(driver, "神州专车-钱包");
            sf.saveAddressData(driver, "神州专车-常用地址");
            sf.saveContactData(driver, "神州专车-亲密联络人");

            Util.sendProgressEndMessage(writeProgress);
         } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.sendExitMessage(writeProgress);
            Util.close(driver,readProgress, writeProgress, socket);
        }


    }

}
