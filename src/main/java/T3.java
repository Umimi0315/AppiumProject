import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.Socket;

import io.appium.java_client.android.AndroidDriver;

public class T3 {
	public static void main(String[] args) throws Exception {
		if (!Util.available()){
			return;
		}
//		new T3().init();
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
			driver=Util.appiumParamSetting("com.t3go.passenger","com.t3go.passenger.SpringActivity",writeProgress,udid,appiumPort);

			//线程睡眠20秒等待APP启动完成
			Thread.sleep(20000);

			int x=driver.manage().window().getSize().width;
			int y=driver.manage().window().getSize().height;

			T3_function tf=new T3_function(dir, x, y,writeProgress);

			//关闭更新
			if (tf.isElementExits("id", "com.t3go.passenger:id/dialog_cancel_button", driver)) {
				driver.findElementById("com.t3go.passenger:id/dialog_cancel_button").click();
				Thread.sleep(2000);
			}

			//关闭弹窗
			if (tf.isElementExits("id", "com.t3go.passenger:id/dismiss", driver)) {
				driver.findElementById("com.t3go.passenger:id/dismiss").click();
				Thread.sleep(2000);
			}

			tf.saveStrokeData(driver, "T3出行-行程");
			tf.saveWalletData(driver, "T3出行-钱包");
			tf.savePersonalData(driver, "T3出行-个人资料");
			tf.saveContactData(driver, "T3出行-紧急联系人");
			tf.saveProblemData(driver, "T3出行-问题验证");
			tf.saveAddressData(driver, "T3出行-常用地址");

			Util.sendProgressEndMessage(writeProgress);

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			Util.sendExitMessage(writeProgress);
			Util.close(driver,readProgress, writeProgress, socket);
		}



	}

}
