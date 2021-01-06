import java.io.File;
//import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

//import javax.naming.directory.DirContext;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class railway12306 {
	
	@SuppressWarnings("static-access")
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
            driver=Util.appiumParamSetting("com.MobileTicket","com.alipay.mobile.quinox.LauncherActivity",writeProgress,udid,appiumPort);

            //线程睡眠20秒等待APP启动完成
            Thread.sleep(20000);

            int x=driver.manage().window().getSize().width;
            int y=driver.manage().window().getSize().height;

            railway12306_function rf = new railway12306_function(dir, x, y, writeProgress);

            if (rf.isElementExits("xpath", "//*[@resource-id='com.MobileTicket.launcher:id/permission_msg_button' and @text='继续使用']", driver))
            {
                driver.findElementByXPath("//*[@resource-id='com.MobileTicket.launcher:id/permission_msg_button' and @text='继续使用']").click();
                Thread.sleep(5000);
            }


            if (rf.isElementExits("xpath", "//*[@resource-id='com.MobileTicket.common:id/cancel_btn' and @text='稍后再说']", driver)){
                driver.findElementByXPath("//*[@resource-id='com.MobileTicket.common:id/cancel_btn' and @text='稍后再说']").click();
                Thread.sleep(5000);
            }

            rf.saveAlternateOrderData(driver, "铁路12306-候补车票");
            rf.saveRiderData(driver, "铁路12306-乘车人");
            rf.savePersonalData(driver, "铁路12306-个人信息");
            rf.saveAddressData(driver, "铁路12306-地址管理");

            Util.sendProgressEndMessage(writeProgress);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.sendExitMessage(writeProgress);
            Util.close(driver,readProgress, writeProgress, socket);
        }

    }

/*	public static void init() throws Exception {
		DesiredCapabilities capabilities=new DesiredCapabilities();
        capabilities.setCapability("platforName", "Android");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("deviceName", "OnePlus");
        capabilities.setCapability("appPackage", "com.MobileTicket");
        capabilities.setCapability("appActivity", "com.alipay.mobile.quinox.LauncherActivity");
        capabilities.setCapability("noReset", "true");
        AndroidDriver driver;
        driver=new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
        Thread.sleep(8000);
        int x=driver.manage().window().getSize().width;
        int y=driver.manage().window().getSize().height;
        
//        driver.findElementById("com.MobileTicket.launcher:id/ticket_home_bottom_bar_order").click();
//        Thread.sleep(2000);
        
        System.out.println("正在初始化appium...");
        //设定路径
        System.out.println("请设定提取数据的保存路径");
        String dir=new Scanner(System.in).next();
        while (!(new File(dir).isDirectory())) {
			System.out.println("输入的路径不存在，请重新输入");
			dir=new Scanner(System.in).next();
		}
        railway12306_function rf=new railway12306_function(dir, x, y);
        
        
        
        //滑动次数
        Scanner sc=new Scanner(System.in);
        System.out.println("是否需要设定滑动次数?Y/N");
        String isSwipe=sc.nextLine();
        while (!((isSwipe.equals("Y")||isSwipe.equals("y"))==true||(isSwipe.equals("N")||isSwipe.equals("n"))==true)==true) {
			System.out.println("请输入Y或N");
			isSwipe=sc.next();
			
		}
        if (isSwipe.equals("Y")||isSwipe.equals("y")) {
        	System.out.println("请输入滑动次数");
        	String num=sc.next();
        	boolean flag=false;
        	while (flag==false) {
				for (int i = 0; i < num.length(); i++) {
					if (num.charAt(i)<'0' || num.charAt(i)>'9') {
						flag=false;
						break;
					}
					flag=true;
				}
				if (flag==false) {
						System.out.println("请输入整数");
						num=sc.next();
					}
			}
        	int frequency=Integer.parseInt(num);
        	driver.findElementById("com.MobileTicket.launcher:id/ticket_home_bottom_bar_order").click();
        	Thread.sleep(2000);
            rf.saveAlternateOrderData(driver, "铁路12306-候补车票", frequency);
            driver.findElementByAccessibilityId("返回").click();
            Thread.sleep(2000);
            driver.findElementById("com.MobileTicket.launcher:id/ticket_home_bottom_bar_mine").click();
            Thread.sleep(2000);
            driver.findElementByXPath("//*[@class='android.view.View' and @index= '0']").click();
            Thread.sleep(2000);
            rf.saveRiderData(driver, "铁路12306-乘车人", frequency);
            driver.findElementByAccessibilityId("返回").click();
            Thread.sleep(2000);
			
		}else {
            rf.saveAlternateOrderData(driver, "铁路12306-候补车票");

            rf.saveRiderData(driver, "铁路12306-乘车人");
		}
//        rf.saveAlternateOrderData(driver, "铁路12306-候补车票");
        
//        driver.findElementById("com.MobileTicket.launcher:id/ticket_home_bottom_bar_mine").click();
//        Thread.sleep(2000);
//        driver.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").click();
//        Thread.sleep(2000);
//        rf.saveRiderData(driver, "铁路12306-乘车人", 1);
        rf.savePersonalData(driver, "铁路12306-个人信息");
        
        System.out.println("数据提取完成!");
        
        driver.quit();
	}*/

}
