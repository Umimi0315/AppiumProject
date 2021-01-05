import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.WeakHashMap;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Umetrip_function extends Function{
	public Umetrip_function(String dir, int x, int y, OutputStream writeProgress) {
		super(dir, x, y,writeProgress);
	}

	/**
	 * 账户信息
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveAccountData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(2, 0, "正在提取账户信息...", writeProgress);

			driver.findElementByXPath("//*[@resource-id='com.umetrip.android.msky.app:id/tv_text' and @text='我']").click();
			Thread.sleep(5000);

			driver.findElementByXPath("//*[@resource-id='com.umetrip.android.msky.app:id/tv_text' and @text='我']").click();
			Thread.sleep(5000);

			driver.findElementByXPath("//*[@resource-id='com.umetrip.android.msky.app:id/tv_item_name' and @text='账户信息']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/UMETRIP/ACCOUNTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/航旅纵横/账户信息</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Umetrip/Account Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"MOBILEPHONEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">手机号</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Mobile Phone Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"LOGINNAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">登录名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Log-in Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SINAWEIBOINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">新浪微博</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Sina Weibo Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"QQINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">QQ</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">QQ Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"WECHATINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">微信</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Wechat Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"ALIPAYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">支付宝</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Alipay Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

//    	Screenshot(driver, dataName);

			ps.append("<Tln>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.umetrip.android.msky.app:id/tv_mobile").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.umetrip.android.msky.app:id/tv_logname").getText())+"</lv>"+"\n");
			driver.swipe(x/2, y*4/5, x/2, y/5, 1000);
			Thread.sleep(5000);
//    	Screenshot(driver, dataName);
			List<MobileElement> accountList=driver.findElementsById("com.umetrip.android.msky.app:id/account_item_status");
			if (accountList.size()>0) {
				for (MobileElement accountElement : accountList) {
					ps.append("<lv>"+replace(accountElement.getText())+"</lv>"+"\n");
				}
			}

			ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			ps.close();

			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(20, 0, "账户信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(10, 1, "提取账户信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}

	/**
	 * 证件信息
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveIDCardData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(21, 0, "正在提取证件信息...", writeProgress);

			driver.findElementByXPath("//*[@resource-id='com.umetrip.android.msky.app:id/tv_item_name' and @text='我的证件']").click();
			Thread.sleep(5000);

			File xmlFile = createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/UMETRIP/IDCARDINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/航旅纵横/证件</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Umetrip/ID Card Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TYPEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">类型</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Type Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"STATUSINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">认证情况</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Status Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">姓名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NUMBERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">证件号</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Number Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);

				List<MobileElement> cardList=driver.findElementsById("com.umetrip.android.msky.app:id/rl");
				if (cardList.size()>0) {
					for (MobileElement cardElement : cardList) {
						if (isElementExits("id", "com.umetrip.android.msky.app:id/id_type", cardElement)&&
								isElementExits("id", "com.umetrip.android.msky.app:id/cert_status", cardElement)&&
								isElementExits("id", "com.umetrip.android.msky.app:id/id_name", cardElement)&&
								isElementExits("id", "com.umetrip.android.msky.app:id/id_no", cardElement)) {

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(cardElement.findElementById("com.umetrip.android.msky.app:id/id_type").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(cardElement.findElementById("com.umetrip.android.msky.app:id/cert_status").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(cardElement.findElementById("com.umetrip.android.msky.app:id/id_name").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(cardElement.findElementById("com.umetrip.android.msky.app:id/id_no").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");

						}
					}

				}


				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(5000);
				nowPageSource=driver.getPageSource();

			} while (!lastPageSource.equals(nowPageSource));

			ps.append("</InfoTable>");
			ps.flush();
			ps.close();
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(40, 0, "证件信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(30, 1, "提取证件信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}

	/**
	 * 个人资料信息
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void savePersonalData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(41, 0, "正在提取个人资料信息...", writeProgress);

			driver.findElementById("com.umetrip.android.msky.app:id/cl_person_info").click();
			Thread.sleep(5000);

			if (isElementExits("xpath", "//*[@resource-id='com.umetrip.android.msky.app:id/title' and @text='提示']", driver)) {
				driver.navigate().back();
				Thread.sleep(5000);
			}else {
				//com.umetrip.android.msky.app:id/image_1
				driver.findElementByXPath("//*[@class='android.widget.FrameLayout' and @content-desc='返回']/../android.widget.FrameLayout[@index='2']").click();
				Thread.sleep(5000);

				if (isElementExits("id", "com.umetrip.android.msky.app:id/image_1", driver)){
					driver.findElementById("com.umetrip.android.msky.app:id/image_1").click();
					Thread.sleep(5000);
				}

				if (isElementExits("id", "com.umetrip.android.msky.app:id/imageView1", driver)){
					driver.findElementById("com.umetrip.android.msky.app:id/imageView1").click();
					Thread.sleep(5000);
				}

				File xmlFile = createDataSaveFile(dataName);

				ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
				ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
				ps.append("<InfoTable>"+"\n");
				ps.append("<Node vType=\"string\" TextKey=\"/UMETRIP/PERSONALDATAINFORMATION\">"+"\n");
				ps.append("<Text Lang=\"zh_cn\">/航旅纵横/个人资料信息</Text>"+"\n");
				ps.append("<Text Lang=\"en_us\">/Umetrip/Personal Data Information</Text>"+"\n");
				ps.append("</Node>"+"\n");
				ps.append("<TCol>"+"\n");
				ps.append("<Prop vType=\"string\" TextKey=\"NICKNAMEINFORMATION\">"+"\n");
				ps.append("<Text Lang=\"zh_cn\">昵称</Text>"+"\n");
				ps.append("<Text Lang=\"en_us\">Nickname Information</Text>"+"\n");
				ps.append("</Prop>"+"\n");
				ps.append("<Prop vType=\"string\" TextKey=\"OCCUPATIONINFORMATION\">"+"\n");
				ps.append("<Text Lang=\"zh_cn\">从事职业</Text>"+"\n");
				ps.append("<Text Lang=\"en_us\">Occupation Information</Text>"+"\n");
				ps.append("</Prop>"+"\n");
				ps.append("<Prop vType=\"string\" TextKey=\"INTRODUCTIONINFORMATION\">"+"\n");
				ps.append("<Text Lang=\"zh_cn\">个人简介</Text>"+"\n");
				ps.append("<Text Lang=\"en_us\">Introduction Information</Text>"+"\n");
				ps.append("</Prop>"+"\n");
				ps.append("<Prop vType=\"string\" TextKey=\"LABELINFORMATION\">"+"\n");
				ps.append("<Text Lang=\"zh_cn\">我的标签</Text>"+"\n");
				ps.append("<Text Lang=\"en_us\">Label Information</Text>"+"\n");
				ps.append("</Prop>"+"\n");
				ps.append("</TCol>"+"\n");

//    	Screenshot(driver, dataName);

				ps.append("<Tln>"+"\n");
				if (isElementExits("xpath", "//*[@resource-id='com.umetrip.android.msky.app:id/lv_card_info']/android.widget.RelativeLayout[@index='0']/android.widget.TextView[@resource-id='com.umetrip.android.msky.app:id/tv_content']", driver)) {
					ps.append("<lv>"+replace(driver.findElementByXPath("//*[@resource-id='com.umetrip.android.msky.app:id/lv_card_info']/android.widget.RelativeLayout[@index='0']/android.widget.TextView[@resource-id='com.umetrip.android.msky.app:id/tv_content']").getText())+"</lv>"+"\n");
				}else {
					ps.append("<lv>"+"无"+"</lv>"+"\n");
				}
				if (isElementExits("xpath", "//*[@resource-id='com.umetrip.android.msky.app:id/lv_card_info']/android.widget.RelativeLayout[@index='1']/android.widget.TextView[@resource-id='com.umetrip.android.msky.app:id/tv_content']", driver)) {
					ps.append("<lv>"+replace(driver.findElementByXPath("//*[@resource-id='com.umetrip.android.msky.app:id/lv_card_info']/android.widget.RelativeLayout[@index='1']/android.widget.TextView[@resource-id='com.umetrip.android.msky.app:id/tv_content']").getText())+"</lv>"+"\n");
				}else {
					ps.append("<lv>"+"无"+"</lv>"+"\n");
				}
				if (isElementExits("xpath", "//*[@resource-id='com.umetrip.android.msky.app:id/personal_introduce']/android.widget.TextView[@resource-id='com.umetrip.android.msky.app:id/tv_content']", driver)) {
					ps.append("<lv>"+replace(driver.findElementByXPath("//*[@resource-id='com.umetrip.android.msky.app:id/personal_introduce']/android.widget.TextView[@resource-id='com.umetrip.android.msky.app:id/tv_content']").getText())+"</lv>"+"\n");
				}else {
					ps.append("<lv>"+"无"+"</lv>"+"\n");
				}
				if (isElementExits("xpath", "//*[@resource-id='com.umetrip.android.msky.app:id/personal_tag']/android.widget.TextView[@resource-id='com.umetrip.android.msky.app:id/tv_content']", driver)) {
					ps.append("<lv>"+replace(driver.findElementByXPath("//*[@resource-id='com.umetrip.android.msky.app:id/personal_tag']/android.widget.TextView[@resource-id='com.umetrip.android.msky.app:id/tv_content']").getText())+"</lv>"+"\n");
				}else {
					ps.append("<lv>"+"无"+"</lv>"+"\n");
				}

				ps.append("</Tln>"+"\n");
				ps.append("</InfoTable>");
				ps.flush();
				ps.close();
				driver.findElementById("com.umetrip.android.msky.app:id/titlebar_iv_return").click();
				Thread.sleep(5000);

/*				if (isElementExits("id", "com.umetrip.android.msky.app:id/buttonDefaultPositive", driver)){
					driver.findElementById("com.umetrip.android.msky.app:id/buttonDefaultPositive").click();
					Thread.sleep(5000);
				}*/

				driver.navigate().back();
				Thread.sleep(5000);
			}
			
			Util.writeProgress(60, 0, "个人资料信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(50, 1, "提取个人资料信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}

	/**
	 * 钱包
	 * @param driver
	 * @param dataName
	 */
	public void saveWalletData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(61, 0, "正在提取钱包信息", writeProgress);

			File xmlFile = createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");

			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/UMETRIP/WALLETINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/航旅纵横/钱包信息</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Umetrip/Wallet Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"BALANCEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">我的余额</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Balance Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"BANKCARDINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">银行卡张数</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Bank Card Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			ps.append("<Tln>"+"\n");
			if ("-".equals(driver.findElementById("com.umetrip.android.msky.app:id/tv_my_balance").getText())){
				ps.append("<lv>"+replace("0")+"</lv>"+"\n");
			}else {
				ps.append("<lv>"+replace(driver.findElementById("com.umetrip.android.msky.app:id/tv_my_balance").getText())+"</lv>"+"\n");
			}
			if ("-".equals(driver.findElementById("com.umetrip.android.msky.app:id/tv_card_quantity").getText())){
				ps.append("<lv>"+replace("0")+"</lv>"+"\n");
			}else {
				ps.append("<lv>"+replace(driver.findElementById("com.umetrip.android.msky.app:id/tv_card_quantity").getText())+"</lv>"+"\n");
			}

			ps.append("</Tln>"+"\n");

			ps.append("</InfoTable>");
			ps.flush();

			Util.writeProgress(80, 0, "钱包信息提取完成！",writeProgress);
		} catch (Exception e) {
			Util.writeProgress(70, 1, "提取钱包信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}

	/**
	 * 关注列表
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveWatchlistData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(81, 0, "正在提取关注列表...", writeProgress);

			driver.findElementByXPath("//*[@resource-id='com.umetrip.android.msky.app:id/tv_text' and @text='航班动态']").click();
			Thread.sleep(5000);
			driver.findElementById("com.umetrip.android.msky.app:id/titlebar_tv_right").click();
			Thread.sleep(5000);

			File xmlFile = createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/UMETRIP/WATCHLISTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/航旅纵横/关注列表信息</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Umetrip/Watchlist Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TYPEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">航班类型</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Type Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"STATUSINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">航班状态</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Status Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DEPARTUREINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">出发地</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Departure Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DEPARTURETIMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">出发时间</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Departure Time Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DESTINATIONINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">目的地</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Destination Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DESTINATIONTIMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">到达时间</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Destination Time Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
	//    		Screenshot(driver, dataName);
				List<MobileElement> flightList=driver.findElementsByXPath("//*[@resource-id='com.umetrip.android.msky.app:id/recycler_view']/android.widget.LinearLayout");
				if (flightList.size()>0) {
					for (MobileElement flightElement : flightList) {
						if (isElementExits("id", "com.umetrip.android.msky.app:id/tv_dept_airport", flightElement)&&isElementExits("id", "com.umetrip.android.msky.app:id/tv_dept_time", flightElement)&&
								isElementExits("id", "com.umetrip.android.msky.app:id/tv_dest_airport", flightElement)&&isElementExits("id", "com.umetrip.android.msky.app:id/tv_dest_time", flightElement)&&
								isElementExits("id", "com.umetrip.android.msky.app:id/tv_flight_info", flightElement)&&isElementExits("id", "com.umetrip.android.msky.app:id/tv_flight_status", flightElement)) {
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(flightElement.findElementById("com.umetrip.android.msky.app:id/tv_flight_info").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(flightElement.findElementById("com.umetrip.android.msky.app:id/tv_flight_status").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(flightElement.findElementById("com.umetrip.android.msky.app:id/tv_dept_airport").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(flightElement.findElementById("com.umetrip.android.msky.app:id/tv_dept_time").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(flightElement.findElementById("com.umetrip.android.msky.app:id/tv_dest_airport").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(flightElement.findElementById("com.umetrip.android.msky.app:id/tv_dest_time").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(5000);
				nowPageSource=driver.getPageSource();
			} while (!lastPageSource.equals(nowPageSource));

			ps.append("</InfoTable>");
			ps.flush();
			ps.close();
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(99, 0, "关注列表信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(90, 1, "提取关注列表信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}



}
