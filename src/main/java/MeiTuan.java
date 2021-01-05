import java.io.File;
//import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class MeiTuan {
	
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
            driver=Util.appiumParamSetting("com.sankuai.meituan","com.meituan.android.pt.homepage.activity.Welcome",writeProgress,udid,appiumPort);

            //线程睡眠20秒等待APP启动完成
            Thread.sleep(20000);

            int x=driver.manage().window().getSize().width;
            int y=driver.manage().window().getSize().height;

            MeiTuan_function mf=new MeiTuan_function(dir, x, y, writeProgress);
            //System.out.println(x+"  "+y);

            if (mf.isElementExits("id", "com.sankuai.meituan:id/btn_cancel", driver)){
                driver.findElementById("com.sankuai.meituan:id/btn_cancel").click();
                Thread.sleep(5000);
            }
            Thread.sleep(10000);

/*            try{
                driver.findElementByXPath("//*[@class='android.widget.Image' and @content-desc='8HP3u6fvxcwMoAAAAASUVORK5CYII=']").click();
                Thread.sleep(5000);
            }catch(Exception e){

            }*/
//            System.out.println((402*x/810)+"   "+(1200*y/1440));

            if(mf.isElementExits("xpath", "//*[@class='android.widget.Image' and @content-desc='8HP3u6fvxcwMoAAAAASUVORK5CYII=']", driver)){
                driver.findElementByXPath("//*[@class='android.widget.Image' and @content-desc='8HP3u6fvxcwMoAAAAASUVORK5CYII=']").click();
                Thread.sleep(5000);
            }

            driver.tap(1, 402*x/810, 1200*y/1440, 10);
            Thread.sleep(2000);

            mf.saveStrokeData(driver, "美团-行程信息");
            mf.saveContactData(driver, "美团-紧急联系人");
            mf.saveAddressData(driver, "美团-常用地址");
            mf.saveWalletData(driver, "美团-钱包信息");
            mf.saveBankCardData(driver, "美团-银行卡信息");
            mf.saveIdentityData(driver, "美团-身份信息");
            mf.saveOrderData(driver, "美团-订单信息");
            mf.saveRidingData(driver, "美团-骑行信息");

            Util.sendProgressEndMessage(writeProgress);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.sendExitMessage(writeProgress);
            Util.close(driver,readProgress, writeProgress, socket);
        }

    }


}
