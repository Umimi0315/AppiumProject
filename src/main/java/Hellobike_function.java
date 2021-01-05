import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class Hellobike_function extends Function {
    public Hellobike_function(String dir, int x, int y, OutputStream writeProgress) {
        super(dir, x, y, writeProgress);
    }

    /**
     * 钱包信息
     * @param driver
     * @param dataName
     */
    public void saveWalletData(AndroidDriver driver, String dataName) throws Exception {
        PrintStream ps=null;

        try {
            Util.writeProgress(2, 0, "正在提取钱包信息...", writeProgress);
            driver.findElementByXPath("//*[@resource-id='com.jingyao.easybike:id/tv_tab_text' and @text='钱包']").click();
            Thread.sleep(3000);
            if (isElementExits("xpath", "//*[@resource-id='com.jingyao.easybike:id/tv_tab_text' and @text='钱包']", driver)){
                driver.findElementByXPath("//*[@resource-id='com.jingyao.easybike:id/tv_tab_text' and @text='钱包']").click();
                Thread.sleep(3000);
            }


            File xmlFile=createDataSaveFile(dataName);
            ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");

            ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
            ps.append("<InfoTable>"+"\n");
            ps.append("<Node vType=\"string\" TextKey=\"/HELLOBIKE/WALLETINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">/哈啰单车/钱包</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">/Hellobike/Wallet Information</Text>"+"\n");
            ps.append("</Node>"+"\n");
            ps.append("<TCol>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"BALANCEINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">余额</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Balance Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"REDENVELOPESINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">红包</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Red Envelopes Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"CREDITINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">信用额度</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Credit Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("</TCol>"+"\n");

            ps.append("<Tln>"+"\n");
            ps.append("<lv>"+replace(driver.findElementById("com.jingyao.easybike:id/balanceView").getText())+"</lv>"+"\n");
            ps.append("<lv>"+replace(driver.findElementById("com.jingyao.easybike:id/redPacketView").getText())+"</lv>"+"\n");
            ps.append("<lv>"+replace(driver.findElementById("com.jingyao.easybike:id/borrowText").getText())+"</lv>"+"\n");
            ps.append("</Tln>"+"\n");
            ps.append("</InfoTable>");
            ps.flush();

            Util.writeProgress(20, 0, "钱包信息提取完成！", writeProgress);
        } catch (Exception e) {
            Util.writeProgress(10, 1, "提取钱包信息出错！", writeProgress);
            throw e;
        } finally {
            close(ps);
        }

    }

    /**
     * 单车卡明细
     * @param driver
     * @param dataName
     */
    public void saveBicycleCardData(AndroidDriver driver, String dataName) throws Exception {
        PrintStream ps=null;

        try {
            Util.writeProgress(21, 0, "正在提取单车卡明细信息...",writeProgress);
            driver.findElementById("com.jingyao.easybike:id/right_action").click();
            Thread.sleep(3000);
            driver.findElementById("com.jingyao.easybike:id/detail_ride_card_tv").click();
            Thread.sleep(3000);

            File xmlFile=createDataSaveFile(dataName);
            ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");

            ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
            ps.append("<InfoTable>"+"\n");
            ps.append("<Node vType=\"string\" TextKey=\"/HELLOBIKE/BICYCLECARDINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">/哈啰单车/单车卡明细</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">/Hellobike/Bicycle Card Information</Text>"+"\n");
            ps.append("</Node>"+"\n");
            ps.append("<TCol>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"TITLEINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">标题</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Title Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"TIMEINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">时间</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Time Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"SUBTITLEINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">子标题</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Subtitle Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"SUBTIMEINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">子时间</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Subtime Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("</TCol>"+"\n");

            /*String lastPageSource=null;
            String nowPageSource=driver.getPageSource();*/
            do{
//                lastPageSource=nowPageSource;

                List<MobileElement> orderList = driver.findElementsByXPath("//*[@resource-id='com.jingyao.easybike:id/card_detail_lv']/android.widget.LinearLayout");
                if (orderList.size()>0){
                    for (MobileElement orderElement : orderList) {
                        if (isElementExits("id", "com.jingyao.easybike:id/item_card_title", orderElement)&&
                        isElementExits("id", "com.jingyao.easybike:id/item_card_time", orderElement)&&
                        isElementExits("id", "com.jingyao.easybike:id/item_card_subtitle", orderElement)&&
                        isElementExits("id", "com.jingyao.easybike:id/item_card_subtime", orderElement)){
                            ps.append("<Tln>"+"\n");
                            ps.append("<lv>"+replace(orderElement.findElementById("com.jingyao.easybike:id/item_card_title").getText())+"</lv>"+"\n");
                            ps.append("<lv>"+replace(orderElement.findElementById("com.jingyao.easybike:id/item_card_time").getText())+"</lv>"+"\n");
                            ps.append("<lv>"+replace(orderElement.findElementById("com.jingyao.easybike:id/item_card_subtitle").getText())+"</lv>"+"\n");
                            ps.append("<lv>"+replace(orderElement.findElementById("com.jingyao.easybike:id/item_card_subtime").getText())+"</lv>"+"\n");
                            ps.append("</Tln>"+"\n");
                        }
                    }
                }

                driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
                Thread.sleep(2000);
//                nowPageSource=driver.getPageSource();
            }while (!isElementExits("id", "com.jingyao.easybike:id/footview_tv", driver));

            ps.append("</InfoTable>");
            ps.flush();
/*            driver.navigate().back();
            Thread.sleep(2000);*/
            driver.findElementById("com.jingyao.easybike:id/left_img").click();
            Thread.sleep(2000);
            Util.writeProgress(40, 0, "单车卡明细信息提取完成！", writeProgress);
        } catch (Exception e) {
            Util.writeProgress(30, 1, "提取单车卡明细信息出错！", writeProgress);
            throw e;
        } finally {
            close(ps);
        }
    }

    /**
     * 余额
     * @param driver
     * @param dataName
     */
    public void saveBalanceData(AndroidDriver driver, String dataName) throws Exception {
        PrintStream ps=null;

        try {
            Util.writeProgress(41, 0, "正在提取余额信息...", writeProgress);
            driver.findElementById("com.jingyao.easybike:id/detail_pay_tv").click();
            Thread.sleep(3000);

            File xmlFile=createDataSaveFile(dataName);
            ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");

            ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
            ps.append("<InfoTable>"+"\n");
            ps.append("<Node vType=\"string\" TextKey=\"/HELLOBIKE/BALANCEINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">/哈啰单车/余额明细</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">/Hellobike/Balance Information</Text>"+"\n");
            ps.append("</Node>"+"\n");
            ps.append("<TCol>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"PAYRESULTINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">支付结果</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Pay Result Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"PRICEINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">价格</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Price Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"TIMEINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">时间</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Time Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"PAYTYPEINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">支付方式</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Pay Type Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("</TCol>"+"\n");

//            String lastPageSource=null;
//            String nowPageSource=driver.getPageSource();

            do{
//                lastPageSource=nowPageSource;
                List<MobileElement> orderList = driver.findElementsByXPath("//*[@resource-id='com.jingyao.easybike:id/wallet_detail_lv']/android.widget.LinearLayout");
                if (orderList.size()>0){
                    for (MobileElement orderElement : orderList) {
                        if (isElementExits("id", "com.jingyao.easybike:id/item_detail_payresult", orderElement)&&
                        isElementExits("id", "com.jingyao.easybike:id/item_detail_price", orderElement)&&
                        isElementExits("id", "com.jingyao.easybike:id/item_detail_time", orderElement)&&
                        isElementExits("id", "com.jingyao.easybike:id/item_detail_paytype", orderElement)){
                            ps.append("<Tln>"+"\n");
                            ps.append("<lv>"+replace(orderElement.findElementById("com.jingyao.easybike:id/item_detail_payresult").getText())+"</lv>"+"\n");
                            ps.append("<lv>"+replace(orderElement.findElementById("com.jingyao.easybike:id/item_detail_price").getText())+"</lv>"+"\n");
                            ps.append("<lv>"+replace(orderElement.findElementById("com.jingyao.easybike:id/item_detail_time").getText())+"</lv>"+"\n");
                            ps.append("<lv>"+replace(orderElement.findElementById("com.jingyao.easybike:id/item_detail_paytype").getText())+"</lv>"+"\n");
                            ps.append("</Tln>"+"\n");
                        }
                    }
                }

                driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
                Thread.sleep(2000);
//                nowPageSource=driver.getPageSource();
            }while (!isElementExits("id", "com.jingyao.easybike:id/footview_tv", driver));

            ps.append("</InfoTable>");
            ps.flush();
/*            driver.navigate().back();
            Thread.sleep(2000);
            driver.navigate().back();
            Thread.sleep(2000);*/
            driver.findElementById("com.jingyao.easybike:id/left_img").click();
            Thread.sleep(2000);
            driver.findElementById("com.jingyao.easybike:id/left_img").click();
            Thread.sleep(2000);
            Util.writeProgress(60, 0, "余额信息提取完成！", writeProgress);
        } catch (Exception e) {
            Util.writeProgress(50, 1, "提取余额信息出错！", writeProgress);
            throw e;
        } finally {
            close(ps);
        }
    }

    /**
     * 订单
     * @param driver
     * @param dataName
     */
    public void saveOrderData(AndroidDriver driver, String dataName) throws Exception {
        PrintStream ps=null;

        try {
            Util.writeProgress(61, 0, "正在提取订单信息...", writeProgress);
            driver.findElementByXPath("//*[@resource-id='com.jingyao.easybike:id/tv_tab_text' and @text='我的']").click();
            Thread.sleep(3000);
            driver.findElementByXPath("//*[@resource-id='com.jingyao.easybike:id/tv_main_name' and @text='我的订单']").click();
            Thread.sleep(3000);

            File xmlFile=createDataSaveFile(dataName);
            ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");

            ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
            ps.append("<InfoTable>"+"\n");
            ps.append("<Node vType=\"string\" TextKey=\"/HELLOBIKE/ORDERINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">/哈啰单车/订单</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">/Hellobike/Order Information</Text>"+"\n");
            ps.append("</Node>"+"\n");
            ps.append("<TCol>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"TITLEINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">订单标题</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Title Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"DESCRIBEINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">订单描述</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Describe Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"PRICEINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">价格</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Price Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"STATUSINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">订单状态</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Status Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("</TCol>"+"\n");

            String lastPageSource=null;
            String nowPageSource=driver.getPageSource();

            do {
                lastPageSource=nowPageSource;

                List<MobileElement> orderList = driver.findElementsByXPath("//*[@resource-id='com.jingyao.easybike:id/recyclerView']/android.widget.FrameLayout");
                if (orderList.size()>0){
                    for (MobileElement orderElement : orderList) {
                        if (isElementExits("id", "com.jingyao.easybike:id/tv_order_title", orderElement)&&
                        isElementExits("id", "com.jingyao.easybike:id/tvDescribe", orderElement)&&
                        isElementExits("id", "com.jingyao.easybike:id/tv_price", orderElement)&&
                        isElementExits("id", "com.jingyao.easybike:id/tv_status", orderElement)){
                            ps.append("<Tln>"+"\n");
                            ps.append("<lv>"+replace(orderElement.findElementById("com.jingyao.easybike:id/tv_order_title").getText())+"</lv>"+"\n");
                            ps.append("<lv>"+replace(orderElement.findElementById("com.jingyao.easybike:id/tvDescribe").getText())+"</lv>"+"\n");
                            ps.append("<lv>"+replace(orderElement.findElementById("com.jingyao.easybike:id/tv_price").getText())+"</lv>"+"\n");
                            ps.append("<lv>"+replace(orderElement.findElementById("com.jingyao.easybike:id/tv_status").getText())+"</lv>"+"\n");
                            ps.append("</Tln>"+"\n");
                        }
                    }
                }

                driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
                Thread.sleep(2000);
                nowPageSource=driver.getPageSource();
            }while (!lastPageSource.equals(nowPageSource));

            ps.append("</InfoTable>");
            ps.flush();
/*            driver.navigate().back();
            Thread.sleep(2000);*/

            driver.findElementById("com.jingyao.easybike:id/ivBack").click();
            Thread.sleep(2000);
            Util.writeProgress(80, 0, "订单信息提取完成！", writeProgress);
        } catch (Exception e) {
            Util.writeProgress(70, 1, "提取订单信息出错！", writeProgress);
            throw e;
        } finally {
            close(ps);
        }

    }

    /**
     * 个人信息
     * @param driver
     * @param dataName
     */
    public void savePersonalData(AndroidDriver driver, String dataName) throws Exception {
        PrintStream ps=null;

        try {
            Util.writeProgress(81, 0, "正在提取个人资料信息...", writeProgress);
            driver.findElementById("com.jingyao.easybike:id/iv_avatar").click();
            Thread.sleep(3000);
            if (isElementExits("xpath", "//*[@class='android.widget.Button' and @text='忽略']", driver)){
                driver.findElementByXPath("//*[@class='android.widget.Button' and @text='忽略']").click();
                Thread.sleep(3000);
            }

            File xmlFile=createDataSaveFile(dataName);
            ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");

            ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
            ps.append("<InfoTable>"+"\n");
            ps.append("<Node vType=\"string\" TextKey=\"/HELLOBIKE/PERSONALINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">/哈啰单车/个人信息</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">/Hellobike/Personal Information</Text>"+"\n");
            ps.append("</Node>"+"\n");
            ps.append("<TCol>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"NICKNAMEINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">昵称</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Nickname Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"PHONEINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">手机号</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Phone Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"VERIFIEDINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">实名认证</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Verified Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"OWNERINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">车主认证</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Owner Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("<Prop vType=\"string\" TextKey=\"STUDENTINFORMATION\">"+"\n");
            ps.append("<Text Lang=\"zh_cn\">学生认证</Text>"+"\n");
            ps.append("<Text Lang=\"en_us\">Student Information</Text>"+"\n");
            ps.append("</Prop>"+"\n");
            ps.append("</TCol>"+"\n");

            ps.append("<Tln>"+"\n");
            ps.append("<lv>"+replace(driver.findElementById("com.jingyao.easybike:id/setinfo_nickname_tv").getText())+"</lv>"+"\n");
            ps.append("<lv>"+replace(driver.findElementById("com.jingyao.easybike:id/setinfo_phone_tv").getText())+"</lv>"+"\n");
            ps.append("<lv>"+replace(driver.findElementById("com.jingyao.easybike:id/setinfo_autonym_tv").getText())+"</lv>"+"\n");
            ps.append("<lv>"+replace(driver.findElementById("com.jingyao.easybike:id/setinfo_hitch_tv").getText())+"</lv>"+"\n");
            ps.append("<lv>"+replace(driver.findElementById("com.jingyao.easybike:id/setinfo_student_tv").getText())+"</lv>"+"\n");
            ps.append("</Tln>"+"\n");

            ps.append("</InfoTable>");
            ps.flush();
 /*           driver.navigate().back();
            Thread.sleep(2000);*/
            driver.findElementById("com.jingyao.easybike:id/left_img").click();
            Thread.sleep(2000);
            Util.writeProgress(99, 0, "个人信息提取完成！", writeProgress);
        } catch (Exception e) {
            Util.writeProgress(90, 1, "提取个人信息出错！", writeProgress);
            throw e;
        } finally {
            close(ps);
        }
    }

}
