
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

public class ShouYue {
	
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
            driver=Util.appiumParamSetting("com.ichinait.gbpassenger","com.ichinait.gbpassenger.splash.SplashActivity",writeProgress,udid,appiumPort);
            //线程睡眠20秒等待APP启动完成
            Thread.sleep(20000);

            int x=driver.manage().window().getSize().width;
            int y=driver.manage().window().getSize().height;

            ShouYue_function sf=new ShouYue_function(dir, x, y, writeProgress);

            if (sf.isElementExits("id", "com.ichinait.gbpassenger:id/dialog_ads_close", driver)) {
                driver.findElementById("com.ichinait.gbpassenger:id/dialog_ads_close").click();
                Thread.sleep(5000);
            }

            sf.savePersonalData(driver, "首汽约车-个人资料");
            sf.saveWalletData(driver, "首汽约车-钱包");
            sf.saveContactData(driver, "首汽约车-紧急联系人");
            sf.saveAddressData(driver, "首汽约车-常用地址");


            Util.sendProgressEndMessage(writeProgress);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.sendExitMessage(writeProgress);
            Util.close(driver,readProgress, writeProgress, socket);
        }

    }

}
