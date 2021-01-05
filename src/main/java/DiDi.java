import java.io.*;
import java.lang.management.ManagementFactory;
//import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.TimeUnit;
//import java.util.Scanner;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class DiDi {
	public static void main(String[] args) throws Exception {
		if (!Util.available()){
			return;
		}
		String dir = args[0];
		if (!(new File(dir).isDirectory())) {
			System.out.println("输入的路径不存在，请重新输入");
			return;
		}

		String ip = args[1];
		if (!Util.isIp(ip)) {
			System.out.println("输入的ip地址不合法!");
			return;
		}
		ip = Util.deleteSpace(ip);

		String portStr = args[2];
		if (!Util.isInteger(portStr)) {
			System.out.println("输入的端口号不合法!");
			return;
		}
		portStr = Util.deleteSpace(portStr);
		int port = Integer.valueOf(portStr);
		String udid=args[3];

		int appiumPort=Integer.parseInt(args[4]);

		Socket socket = new Socket(ip, port);

		OutputStream writeProgress = socket.getOutputStream();
		InputStream readProgress = socket.getInputStream();

		AndroidDriver driver =null;

		try {
			Util.waitForStart(readProgress);
			Util.sendProgressStartMessage(writeProgress);

			//初始化appium
			driver = Util.appiumParamSetting("com.sdu.didi.psnger", "com.didi.sdk.app.launch.LauncherActivity",writeProgress,udid,appiumPort);//

			//线程睡眠8秒等待APP启动完成
			Thread.sleep(20000);

			int x = driver.manage().window().getSize().width;
			int y = driver.manage().window().getSize().height;

			DiDi_function df = new DiDi_function(dir, x, y, writeProgress);

//			//关闭弹窗
//			if (df.isElementExits("id", "com.sdu.didi.psnger:id/popClose", driver)) {
//				driver.findElementById("com.sdu.didi.psnger:id/popClose").click();
//				Thread.sleep(2000);
//			}
//
//			if (df.isElementExits("id", "com.sdu.didi.psnger:id/close_dialog", driver)) {
//				driver.findElementById("com.sdu.didi.psnger:id/close_dialog").click();
//				Thread.sleep(2000);
//			}

			if (df.isElementExits("id", "com.sdu.didi.psnger:id/iv_upgrade_btn_ignore", driver)){
				driver.findElementById("com.sdu.didi.psnger:id/iv_upgrade_btn_ignore").click();
				Thread.sleep(5000);
			}

			df.closeWindow(driver);


			//driver.findElementByAccessibilityId("个人中心").click();
			driver.findElementById("com.sdu.didi.psnger:id/title_bar_img_btn_left").click();
			Thread.sleep(5000);
			df.closeWindow(driver);
			df.saveOrderData(driver, "滴滴出行-订单");
			df.saveWalletData(driver, "滴滴出行-钱包");
			df.savePersonalData(driver, "滴滴出行-个人信息");
			df.saveRealNameData(driver, "滴滴出行-实名详情");
			df.saveAddressData(driver, "滴滴出行-常用地址");

			Util.sendProgressEndMessage(writeProgress);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.sendExitMessage(writeProgress);
			Util.close(driver,readProgress, writeProgress, socket);
		}


	}
}
