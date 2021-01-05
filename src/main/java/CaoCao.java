import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.net.URL;
//import java.util.Scanner;

import javafx.scene.input.Dragboard;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class CaoCao {
	
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

		String pid=ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
		OutputStream writeProgress=socket.getOutputStream();
		InputStream readProgress = socket.getInputStream();

		AndroidDriver driver=null;

		try {
			Util.waitForStart(readProgress);
			Util.sendProgressStartMessage(writeProgress);

			//初始化appium
			driver=Util.appiumParamSetting("cn.caocaokeji.user","cn.caocaokeji.compat.load.LoadActivity",writeProgress,udid,appiumPort);

			//线程睡眠8秒等待APP启动完成
			Thread.sleep(20000);

			int x=driver.manage().window().getSize().width;
			int y=driver.manage().window().getSize().height;

			CaoCao_function cf=new CaoCao_function(dir, x, y, writeProgress);

			//接受协议
			if (cf.isElementExits("id", "cn.caocaokeji.user:id/platform_home_protocol_confirm", driver)){
				driver.findElementById("cn.caocaokeji.user:id/platform_home_protocol_confirm").click();
				Thread.sleep(2000);
			}

			//关闭弹窗
			if (cf.isElementExits("id", "cn.caocaokeji.user:id/iv_close", driver)) {
				driver.findElementById("cn.caocaokeji.user:id/iv_close").click();
				Thread.sleep(2000);
			}

			//关闭hf更新
			if (cf.isElementExits("id", "cn.caocaokeji.user:id/cccx_ui_middle_dialog_tv_left", driver)) {
				driver.findElementById("cn.caocaokeji.user:id/cccx_ui_middle_dialog_tv_left").click();
				Thread.sleep(2000);
			}

			cf.savePersonalData(driver, "曹操出行-个人资料");
			cf.saveStrokeData(driver, "曹操出行-行程");
			cf.saveWalletData(driver, "曹操出行-钱包");
			cf.saveContactData(driver, "曹操出行-紧急联系人");
			cf.saveAddressData(driver, "曹操出行-常用地址");

			Util.sendProgressEndMessage(writeProgress);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.sendExitMessage(writeProgress);
			Util.close(driver,readProgress, writeProgress, socket);
		}

	}


}
