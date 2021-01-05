import java.io.File;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.util.ArrayList;


import io.appium.java_client.android.AndroidDriver;

public class BaiduNetdisk {
	
	
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
			driver=Util.appiumParamSetting("com.baidu.netdisk","com.baidu.netdisk.ui.Navigate",writeProgress,udid,appiumPort);

			//线程睡眠20秒等待APP启动完成
			Thread.sleep(30000);

			int x=driver.manage().window().getSize().width;
			int y=driver.manage().window().getSize().height;

			BaiduNetdisk_function bf=new BaiduNetdisk_function(dir, x, y, writeProgress);

			if (bf.isElementExits("id", "com.baidu.netdisk:id/dialog_button_cancel", driver)){
				driver.findElementById("com.baidu.netdisk:id/dialog_button_cancel").click();
				Thread.sleep(5000);
			}

			driver.findElementById("com.baidu.netdisk:id/rb_filelist").click();
			Thread.sleep(10000);

			if (bf.isElementExits("id", "com.baidu.netdisk:id/dialog_button_cancel", driver)){
				driver.findElementById("com.baidu.netdisk:id/dialog_button_cancel").click();
				Thread.sleep(5000);
			}

			if(bf.isElementExits("id", "com.baidu.netdisk:id/point_text_1",driver)){
				driver.findElementById("com.baidu.netdisk:id/point_text_1").click();
				Thread.sleep(2000);
			}

			bf.saveVideoData(driver, "百度网盘-视频");
			bf.saveDocumentationData(driver, "百度网盘-文档");
			bf.saveAudioData(driver, "百度网盘-音频");
			bf.saveApplicationData(driver, "百度网盘-应用");
			bf.saveBTSeedData(driver, "百度网盘-BT种子");
			bf.saveOtherData(driver, "百度网盘-其他");


			Util.sendProgressEndMessage(writeProgress);

			ArrayList<String> attachmentsList=bf.getAttachments();
			if (attachmentsList.size()>0){
				Util.sendAttachments(attachmentsList, writeProgress);
			}
			Util.sendAttachmentEndMessage(writeProgress);

			bf.downloadFile(driver, readProgress, writeProgress);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.sendExitMessage(writeProgress);
			Util.close(driver, readProgress, writeProgress, socket);
		}
	}

}
