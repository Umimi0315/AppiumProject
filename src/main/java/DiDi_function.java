import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
//import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class DiDi_function extends Function{
	public DiDi_function(String dir, int x, int y, OutputStream writeProgress) {
		super(dir, x, y,writeProgress);
	}
	/**
	 * 提取订单
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveOrderData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;
		try {
			Util.writeProgress(2, 0, "正在提取订单信息...",writeProgress);
			driver.findElementByXPath("//*[@resource-id='com.sdu.didi.psnger:id/s_tv_message_name' and @text='订单']").click();
			Thread.sleep(2000);
			closeWindow(driver);
			while (isElementExits("xpath", "//*[@resource-id='com.sdu.didi.psnger:id/try_agin_btn' and @index='2']", driver)) {
				driver.findElementByXPath("//*[@resource-id='com.sdu.didi.psnger:id/try_agin_btn' and @index='2']").click();
				Thread.sleep(2000);
			}
			//显示全部订单信息
			int flag=1;
			while (flag==1) {
				while (isElementExits("id", "com.sdu.didi.psnger:id/drop_down_list_footer_progress_bar", driver)){
					Thread.sleep(5000);
				}
				String lastPageSource=null;
				String nowPageSource=driver.getPageSource();
				do{
					lastPageSource=nowPageSource;
					driver.swipe(x/2, y*4/5, x/2, y/5, 1000);
					Thread.sleep(2000);
					nowPageSource=driver.getPageSource();
				}while(!lastPageSource.equals(nowPageSource));
//				driver.findElementById("com.sdu.didi.psnger:id/drop_down_list_footer_button").getText().equals("没有更多订单了")
				if (isElementExits("xpath", "//*[@resource-id='com.sdu.didi.psnger:id/drop_down_list_footer_button' and @text='没有更多订单了']", driver)||isElementExits("xpath", "//*[@resource-id='com.sdu.didi.psnger:id/no_record_text' and @text='您暂时还没有行程哦']", driver)) {
					flag=0;
				}else {
					driver.findElementById("com.sdu.didi.psnger:id/drop_down_list_footer_button").click();
					Thread.sleep(2000);
				}	    					
			}
			//拉回顶部
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do{
				lastPageSource=nowPageSource;
				driver.swipe(x/2, y/5, x/2, y*4/5, 1000);
				nowPageSource=driver.getPageSource();
			}while(!lastPageSource.equals(nowPageSource));

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/DIDI/ORDERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/滴滴打车/我的订单</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/DiDi/Order Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TYPEOFRIDEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">乘车类型</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Type Of Ride Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TIMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">具体时间</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Time Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DEPARTUREINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">出发地</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Departure Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DESTINATIONINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">目的地</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Destination Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			lastPageSource=null;
			nowPageSource=driver.getPageSource();
			//提取
			do{
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsByXPath("//*[@resource-id='com.sdu.didi.psnger:id/history_record_list']/android.widget.RelativeLayout[@clickable='true']");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.sdu.didi.psnger:id/business_name_tv", mobileElement)&&
			        		isElementExits("id", "com.sdu.didi.psnger:id/date_tv", mobileElement)&&
			        		isElementExits("id", "com.sdu.didi.psnger:id/time_tv", mobileElement)&&
			        		isElementExits("id", "com.sdu.didi.psnger:id/start_tv", mobileElement)&&
			        		isElementExits("id", "com.sdu.didi.psnger:id/end_tv", mobileElement)) {
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.sdu.didi.psnger:id/business_name_tv").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.sdu.didi.psnger:id/date_tv").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.sdu.didi.psnger:id/time_tv").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.sdu.didi.psnger:id/start_tv").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.sdu.didi.psnger:id/end_tv").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
			}while(!lastPageSource.equals(nowPageSource));

			ps.append("</InfoTable>");
			ps.flush();
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(20, 0, "订单信息提取完成！",writeProgress);
			closeWindow(driver);
		} catch (Exception e) {

			Util.writeProgress(10, 1, "提取订单信息出错！",writeProgress);
	        throw e;
		}finally {
			close(ps);
		}

	}
	/**
	 * 提取钱包
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveWalletData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;
		try {
			Util.writeProgress(21, 0, "正在提取钱包信息...",writeProgress);
			driver.findElementByXPath("//*[@resource-id='com.sdu.didi.psnger:id/s_tv_message_name' and @text='钱包']").click();
			Thread.sleep(5000);
			closeWindow(driver);

			for (int i=0;i<3;i++){
				if(isElementExits("id", "com.sdu.didi.psnger:id/ivCover", driver)){
					driver.findElementById("com.sdu.didi.psnger:id/ivCover").click();
					Thread.sleep(5000);
				}
			}


			driver.findElementById("com.sdu.didi.psnger:id/ivArrow").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/DIDI/WALLETINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/滴滴打车/钱包</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/DiDi/Wallet Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TOTALASSETSINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">总资产</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Total Assets Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"LOOSECHANGEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">零钱</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Loose Change Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"PASSENGERBALANCEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">乘客余额</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Passenger Balance Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SPECIALRECHARGEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">特惠充值</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Special Recharge Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FINANCIALMANAGEMENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">理财</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Financial Management Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			//Screenshot(driver, dataName);
			ps.append("<Tln>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.sdu.didi.psnger:id/tvTotalAsset").getText())+"</lv>"+"\n");
			List<MobileElement> list=driver.findElementsByXPath("//*[@resource-id='com.sdu.didi.psnger:id/assetDetailItemRecycleView']/android.view.ViewGroup");
			for (MobileElement mobileElement : list) {
				ps.append("<lv>"+replace(mobileElement.findElementById("com.sdu.didi.psnger:id/tvValue").getText())+"</lv>"+"\n");
			}
			ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			driver.findElementById("com.sdu.didi.psnger:id/common_title_bar_left_img").click();
			Thread.sleep(2000);
			Util.writeProgress(40, 0, "钱包信息提取完成！",writeProgress);
			closeWindow(driver);
		} catch (Exception e) {
			Util.writeProgress(30, 1, "提取钱包信息出错！",writeProgress);
	        throw e;
		}finally {
			close(ps);
		}
    	
	}
	/**
	 * 个人信息
	 * @param driver
	 * @param dataName
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public void savePersonalData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;
		try {
			Util.writeProgress(41, 0, "正在提取个人信息...",writeProgress);
			driver.findElementById("com.sdu.didi.psnger:id/icon_profile").click();
			Thread.sleep(5000);
			closeWindow(driver);

			File xmlFile=createDataSaveFile(dataName);
			
			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/DIDI/PERSONALINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/滴滴打车/个人信息</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/DiDi/Personal Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"USERNAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">用户名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Username Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DETIALINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">详细信息</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Detial Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SIGNATUREINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">个性签名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Signature Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			//Screenshot(driver, dataName);
			ps.append("<Tln>"+"\n");
			
			ps.append("<lv>"+replace(driver.findElementById("com.sdu.didi.psnger:id/text_user").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.sdu.didi.psnger:id/text_userinfo").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.sdu.didi.psnger:id/text_user_signature").getText())+"</lv>"+"\n");
			
			ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			Util.writeProgress(60, 0, "个人信息提取完成！",writeProgress);
			closeWindow(driver);
		} catch (Exception e) {
			Util.writeProgress(50, 1, "个人信息提取出错！",writeProgress);
			throw e;
		}finally {
			close(ps);
		}
		
	}
	
	/**
	 * 提取实名详情
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveRealNameData(AndroidDriver driver,String dataName) throws Exception {
		
		PrintStream ps=null;
		
		try {
			Util.writeProgress(61, 0, "正在提取实名详情信息...",writeProgress);
			driver.findElementById("com.sdu.didi.psnger:id/idview_realname").click();
			Thread.sleep(15000);
			closeWindow(driver);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/DIDI/REALNAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/滴滴打车/实名详情</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/DiDi/Real Name Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">姓名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"IDNUMBERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">身份证号</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">ID Number Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"ACCOUNTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">当前账号</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Account Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			//Screenshot(driver, dataName);
			ps.append("<Tln>"+"\n");
			if (isElementExits("xpath", "//*[@class='android.view.View' and @resource-id='index']/android.view.View[@index='1']", driver)) {
				ps.append("<lv>"+replace(driver.findElementByXPath("//*[@class='android.view.View' and @resource-id='index']/android.view.View[@index='1']").getText())+"</lv>"+"\n");
			}else {
				ps.append("<lv>"+"无"+"</lv>"+"\n");

			}
			if (isElementExits("xpath", "//*[@class='android.view.View' and @resource-id='index']/android.view.View[@index='2']", driver)) {
				ps.append("<lv>"+replace(driver.findElementByXPath("//*[@class='android.view.View' and @resource-id='index']/android.view.View[@index='2']").getText())+"</lv>"+"\n");
			}else {
				ps.append("<lv>"+"无"+"</lv>"+"\n");

			}
			if (isElementExits("xpath", "//*[@class='android.view.View' and @resource-id='index']/android.view.View[@index='3']/android.view.View[@index='0']", driver)) {
				ps.append("<lv>"+replace(driver.findElementByXPath("//*[@class='android.view.View' and @resource-id='index']/android.view.View[@index='3']/android.view.View[@index='0']").getText())+"</lv>"+"\n");
			}else {
				ps.append("<lv>"+"无"+"</lv>"+"\n");

			}
			
			ps.append("</Tln>"+"\n");

			ps.append("</InfoTable>");
			ps.flush();
			driver.findElementById("com.sdu.didi.psnger:id/common_title_bar_left_img").click();
			Thread.sleep(2000);
			driver.findElementById("com.sdu.didi.psnger:id/image_back").click();
			Thread.sleep(2000);
			Util.writeProgress(80, 0, "实名详情信息提取完成！",writeProgress);
		} catch (Exception e) {
			
			Util.writeProgress(70, 1, "提取实名详情信息出错！",writeProgress);
			
	        throw e;
		}finally {
			close(ps);
		}

	}

	/**
	 * 常用地址
	 * @param driver
	 * @param dataName
	 */
	public void saveAddressData(AndroidDriver driver,String dataName) throws Exception {
		PrintStream ps=null;

		try {
			Util.writeProgress(81,0,"正在提取常用地址信息...",writeProgress);
			closeWindow(driver);
			driver.findElementByXPath("//*[@resource-id='com.sdu.didi.psnger:id/s_tv_message_name' and @text='设置']").click();
			Thread.sleep(2000);
			driver.findElementByXPath("//*[@resource-id='com.sdu.didi.psnger:id/setting_list_item_title' and @text='常用地址']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/DIDI/ADDRESSINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/滴滴打车/常用地址</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/DiDi/Address Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"HOMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">家</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Home Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"COMPANYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">公司</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Company Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			//Screenshot(driver, dataName);
			ps.append("<Tln>"+"\n");

			List<MobileElement> addressList = driver.findElementsById("com.sdu.didi.psnger:id/text_content");
			for (MobileElement addressElement : addressList) {
				ps.append("<lv>"+replace(addressElement.getText())+"</lv>"+"\n");
			}

			ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			ps.close();
			driver.navigate().back();
			Thread.sleep(2000);
			driver.navigate().back();
			Thread.sleep(2000);

			Util.writeProgress(99, 0, "常用地址信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(90, 1, "提取常用地址信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}



	/**
	 * 关闭弹窗
	 * @param driver
	 * @throws InterruptedException
	 */
	public void closeWindow(AndroidDriver driver) throws InterruptedException {
		//关闭更新
		if (isElementExits("id", "com.sdu.didi.psnger:id/iv_upgrade_btn_ignore", driver)){
			driver.findElementById("com.sdu.didi.psnger:id/iv_upgrade_btn_ignore").click();
			Thread.sleep(2000);
		}

		//关闭弹窗
		if (isElementExits("id", "com.sdu.didi.psnger:id/popClose", driver)) {
			driver.findElementById("com.sdu.didi.psnger:id/popClose").click();
			Thread.sleep(2000);
		}

		if (isElementExits("id", "com.sdu.didi.psnger:id/close_dialog", driver)) {
			driver.findElementById("com.sdu.didi.psnger:id/close_dialog").click();
			Thread.sleep(2000);
		}
	}

	
}
