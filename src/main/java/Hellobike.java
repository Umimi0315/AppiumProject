import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;

public class Hellobike {

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
            driver=Util.appiumParamSetting("com.jingyao.easybike","com.hellobike.atlas.business.portal.PortalActivity",writeProgress,udid,appiumPort);

            //线程睡眠8秒等待APP启动完成
            Thread.sleep(5000);

            int x=driver.manage().window().getSize().width;
            int y=driver.manage().window().getSize().height;

            Hellobike_function hf = new Hellobike_function(dir, x, y, writeProgress);

            hf.saveWalletData(driver, "哈啰单车-钱包");
            hf.saveBicycleCardData(driver, "哈啰单车-单车卡明细");
            hf.saveBalanceData(driver, "哈啰单车-余额");
            hf.saveOrderData(driver, "哈啰单车-订单");
            hf.savePersonalData(driver, "哈啰单车-个人信息");

            Util.sendProgressEndMessage(writeProgress);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.sendExitMessage(writeProgress);
            Util.close(driver, readProgress, writeProgress, socket);
        }
    }
}
