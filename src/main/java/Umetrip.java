import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.sun.org.apache.xml.internal.security.Init;

import io.appium.java_client.android.AndroidDriver;

public class Umetrip {
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
            driver=Util.appiumParamSetting("com.umetrip.android.msky.app","com.umetrip.android.msky.app.module.startup.SplashActivity",writeProgress,udid,appiumPort);

            //线程睡眠20秒等待APP启动完成
            Thread.sleep(20000);

            int x=driver.manage().window().getSize().width;
            int y=driver.manage().window().getSize().height;

            Umetrip_function uf = new Umetrip_function(dir, x, y, writeProgress);



            if (uf.isElementExits("id", "com.umetrip.android.msky.app:id/btn_upgrade", driver)) {
                driver.tap(1, x*540/1080, y*1754/2340, 1000);
                Thread.sleep(2000);
            }

            uf.saveAccountData(driver, "航旅纵横-账户信息");
            uf.saveIDCardData(driver, "航旅纵横-证件信息");
            uf.savePersonalData(driver, "航旅纵横-个人资料信息");
            uf.saveWalletData(driver, "航旅纵横-钱包");
            uf.saveWatchlistData(driver, "航旅纵横-关注列表");

            Util.sendProgressEndMessage(writeProgress);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.sendExitMessage(writeProgress);
            Util.close(driver,readProgress, writeProgress, socket);
        }
    }

}
