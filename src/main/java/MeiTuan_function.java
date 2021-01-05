import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class MeiTuan_function extends Function{
	public MeiTuan_function(String dir,int x,int y, OutputStream writeProgress) {
		// TODO Auto-generated constructor stub
		super(dir, x, y, writeProgress);
	}
	/**
	 * 行程信息
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveStrokeData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(2, 0, "正在提取行程信息...",writeProgress);
			driver.findElementById("com.sankuai.meituan:id/search_layout_area").click();
			Thread.sleep(5000);
			driver.findElementById("com.sankuai.meituan:id/search_edit").sendKeys("打车");
			Thread.sleep(5000);
			driver.findElementById("com.sankuai.meituan:id/search").click();
			Thread.sleep(5000);
			if (isElementExits("id", "com.sankuai.meituan:id/qcsc_dialog_button_cancel", driver)) {
				driver.findElementById("com.sankuai.meituan:id/qcsc_dialog_button_cancel").click();
				Thread.sleep(5000);
			}

			if(isElementExits("xpath", "//*[@class='android.view.View' and @text='\uE011']", driver)){
				driver.findElementByXPath("//*[@class='android.view.View' and @text='\uE011']").click();
				Thread.sleep(5000);
			}


			if (isElementExits("id", "com.sankuai.meituan:id/operation_btn_close", driver)){
				driver.findElementById("com.sankuai.meituan:id/operation_btn_close").click();
				Thread.sleep(5000);
			}
			driver.findElementByXPath("//*[@content-desc='\uE01F']").click();
			Thread.sleep(5000);
			driver.findElementByXPath("//*[@class='android.view.View' and contains(@content-desc,'我的行程')]").click();
			Thread.sleep(5000);
			//显示全部订单信息
			int flag=1;
			while (flag==1) {
				String lastPageSource=null;
				String nowPageSource=driver.getPageSource();
				do{
					lastPageSource=nowPageSource;
					driver.swipe(x/2, y*4/5, x/2, y/5, 1000);
					nowPageSource=driver.getPageSource();
				  }while(!lastPageSource.equals(nowPageSource));
				  if (isElementExits("xpath", "//*[@class='android.widget.TextView' and contains(@text,'点此查看')]", driver)) {
					driver.findElementByXPath("//*[@class='android.widget.TextView' and contains(@text,'点此查看')]").click();
					Thread.sleep(5000);
				  }else {
					flag=0;
				  }
			}
			//拉到顶部
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				driver.swipe(x/2, y/5, x/2, y*4/5, 1000);
				nowPageSource=driver.getPageSource();

			} while (!lastPageSource.equals(nowPageSource));

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/MEITUAN/STROKEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/美团/我的行程</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/MeiTuan/Stroke Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TYPEOFRIDEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">乘车类型</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Type Of Ride Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"PRICEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">价格</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Price Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
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
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> orderList=driver.findElementsByXPath("//*[@class='android.widget.ScrollView' and @index='3']/android.view.ViewGroup[@index='0']/android.view.ViewGroup");
				if (orderList.size()>0) {
					for (MobileElement orderElement : orderList) {
						if (isElementExits("xpath", ".//android.view.ViewGroup[@index='0']/android.widget.TextView[@index='0']", orderElement)&&
								isElementExits("xpath", ".//android.view.ViewGroup[@index='0']/android.widget.TextView[@index='1']", orderElement)&&
								isElementExits("xpath", ".//android.view.ViewGroup[@index='0']/android.widget.TextView[@index='3']", orderElement)&&
								isElementExits("xpath", ".//android.view.ViewGroup[@index='0']/android.widget.TextView[@index='6']", orderElement)&&
								isElementExits("xpath", ".//android.view.ViewGroup[@index='0']/android.widget.TextView[@index='8']", orderElement)) {

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(orderElement.findElementByXPath(".//android.view.ViewGroup[@index='0']/android.widget.TextView[@index='0']").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(orderElement.findElementByXPath(".//android.view.ViewGroup[@index='0']/android.widget.TextView[@index='1']").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(orderElement.findElementByXPath(".//android.view.ViewGroup[@index='0']/android.widget.TextView[@index='3']").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(orderElement.findElementByXPath(".//android.view.ViewGroup[@index='0']/android.widget.TextView[@index='6']").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(orderElement.findElementByXPath(".//android.view.ViewGroup[@index='0']/android.widget.TextView[@index='8']").getText())+"</lv>"+"\n");
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

			driver.navigate().back();
			Thread.sleep(5000);

			Util.writeProgress(12, 0, "行程信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(6, 1, "提取行程信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}


	}
	/**
	 * 紧急联系人
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveContactData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;
		try {
			Util.writeProgress(13, 0, "正在提取紧急联系人信息...", writeProgress);

			driver.findElementByXPath("//*[@class='android.widget.TextView' and @text='设置']").click();
			Thread.sleep(5000);
			driver.findElementByXPath("//*[@class='android.widget.TextView' and @text='紧急联系人']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/MEITUAN/CONTACTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/美团/紧急联系人</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/MeiTuan/Contact Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">姓名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"PHONENUMBERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">电话</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Phone Number Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> contactList=driver.findElementsByXPath("//*[@resource-id='com.sankuai.meituan:id/mil_container']/android.webkit.WebView/android.webkit.WebView[@content-desc='紧急联系人']/android.view.View");
				if (contactList.size()>1) {
					int num=0;
					for (MobileElement contactElement : contactList) {
						String contactElementContent=contactElement.getAttribute("content-desc");
						if (contactElementContent==null||"删除".equals(contactElementContent)||"添加紧急联系人".equals(contactElementContent)||"为保证您的行程安全，紧急联系人将用于自动分享行程".equals(contactElementContent)||"".equals(contactElementContent)||"长按可删除紧急联系人".equals(contactElementContent)||"\uE000".equals(contactElementContent)){
							continue;
						}
						if (num==0){
							ps.append("<Tln>"+"\n");
						}
						ps.append("<lv>"+replace(contactElementContent)+"</lv>"+"\n");

						if (num==1){
							ps.append("</Tln>"+"\n");
							num=0;
							continue;
						}
						num++;

					}
				}

				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(5000);
				nowPageSource=driver.getPageSource();
			} while (!lastPageSource.equals(nowPageSource));

			ps.append("</InfoTable>");
			ps.flush();
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(24, 0, "紧急联系人信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(18, 1, "提取紧急联系人信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 常用地址
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveAddressData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(25, 0, "正在提取常用地址信息...", writeProgress);
			driver.findElementByXPath("//*[@class='android.widget.TextView' and @text='常用地址']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/MEITUAN/ADDRESSINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/美团/常用地址</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/MeiTuan/Address Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"ADDRESSNAME\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">地址名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Address Name</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DETAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">详细地址</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Detail Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> addrList=driver.findElementsByXPath("//*[@class='android.widget.ScrollView' and @index='1']/android.view.ViewGroup/android.view.ViewGroup");
				if (addrList.size()>0) {
					for (MobileElement addrElement : addrList) {
						ps.append("<Tln>"+"\n");
						ps.append("<lv>"+replace(addrElement.findElementByXPath(".//android.view.ViewGroup[@index='0']/android.view.ViewGroup/android.widget.TextView[@index='1']").getText())+"</lv>"+"\n");
						ps.append("<lv>"+replace(addrElement.findElementByXPath(".//android.view.ViewGroup[@index='0']/android.view.ViewGroup/android.widget.TextView[@index='3']").getText())+"</lv>"+"\n");
						ps.append("</Tln>"+"\n");
					}
				}

				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();

			} while (!lastPageSource.equals(nowPageSource));

			ps.append("</InfoTable>");
			ps.flush();

			while(!isElementExits("xpath", "//*[@class='android.view.View' and @content-desc='我的']", driver)) {
				driver.navigate().back();
				Thread.sleep(2000);
			}

			Util.writeProgress(36, 0, "常用地址信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(30, 1, "提取常用地址信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}

	/**
	 * 钱包信息
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveWalletData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(37, 0, "正在提取钱包信息...", writeProgress);
			driver.findElementByXPath("//*[@class='android.view.View' and @content-desc='我的']").click();
			Thread.sleep(2000);
			driver.findElementByXPath("//*[@resource-id='com.sankuai.meituan:id/usermine_listview']/android.widget.LinearLayout[@index='1']/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[@index='0']").click();
			Thread.sleep(4000);

//			driver.tap(1, x*1000/1080, y*125/1920, 1000);
//			Thread.sleep(2000);
//			driver.navigate().back();
//			Thread.sleep(2000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/MEITUAN/WALLETINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/美团/钱包</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/MeiTuan/Wallet Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"BALANCEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">余额</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Balance Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"BANKCARDINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">银行卡个数</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Bank Card Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			//Screenshot(driver, dataName);
			ps.append("<Tln>"+"\n");


			ps.append("<lv>"+replace(driver.findElementByXPath("//*[@class='android.view.View' and @content-desc='余额(元)']/../android.view.View[@index='4']").getAttribute("content-desc"))+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementByXPath("//*[@class='android.view.View' and @content-desc='余额(元)']/../android.view.View[@index='6']").getAttribute("content-desc"))+"</lv>"+"\n");


			ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			Thread.sleep(2000);
			Util.writeProgress(48, 0, "钱包信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(42, 1, "提取钱包信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 银行卡信息
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveBankCardData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(49, 0, "正在提取银行卡信息...", writeProgress);
			driver.findElementByXPath("//*[@class='android.view.View' and @content-desc='银行卡(张)']").click();
			Thread.sleep(4000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/MEITUAN/BANKCARDINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/美团/银行卡信息</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/MeiTuan/Bank Card Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"BANKINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">银行</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Bank Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CARDTYPEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">卡类型</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Card Type Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CARDNUMBERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">卡号</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Card Number Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> cardList=driver.findElementsByXPath("//*[@resource-id='com.sankuai.meituan:id/mil_container']/android.webkit.WebView[@index='0']/android.webkit.WebView[@content-desc='我的银行卡']/android.view.View/android.view.View");
				if (cardList.size()>0) {
					int num=0;
					for (MobileElement cardElement : cardList) {

						String cardElementContent=cardElement.getAttribute("content-desc");
						if (cardElementContent==null||"开通美团储值卡，充值返现".equals(cardElementContent)||"一键添加 平安银行借记卡".equals(cardElementContent)||"一键添加 平安银行信用卡".equals(cardElementContent)||"一键添卡，无需输入卡号".equals(cardElementContent)||"美团支付".equals(cardElementContent)||"".equals(cardElementContent)||"充值返现，支付立减".equals(cardElementContent)){
							continue;
						}

						if (num==0){
							ps.append("<Tln>"+"\n");
						}

						ps.append("<lv>"+replace(cardElementContent)+"</lv>"+"\n");

						if (num==2){
							ps.append("</Tln>"+"\n");
							num=0;
							continue;
						}
						num++;
					}
				}

				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();

			}while(!lastPageSource.equals(nowPageSource));

			ps.append("</InfoTable>");
			ps.flush();
			driver.navigate().back();
			Thread.sleep(2000);
			Util.writeProgress(60, 0, "银行卡信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(54, 1, "提取银行卡信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}


	}

	/**
	 * 身份信息
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void saveIdentityData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(61, 0, "正在提取身份信息...", writeProgress);

			driver.findElementByXPath("//*[@resource-id='com.sankuai.meituan:id/mil_container']/android.webkit.WebView/android.webkit.WebView[@content-desc='美团钱包']/android.view.View/android.view.View[@index='1']").click();
			Thread.sleep(2000);

			driver.findElementByXPath("//*[@class='android.view.View' and @content-desc='实名认证']").click();
			Thread.sleep(2000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/MEITUAN/IDENTITYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/美团/身份信息</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/MeiTuan/Identity Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"VERIFIEDINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">实名状态</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Verified Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">姓名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"IDNUMBERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">证件号码</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">ID Number Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			//Screenshot(driver, dataName);

			ps.append("<Tln>"+"\n");
			if (driver.findElementByXPath("//*[@resource-id='com.sankuai.meituan:id/mil_container' and @class='android.view.ViewGroup']/android.webkit.WebView[@index='0']/android.webkit.WebView[@index='0']/android.view.View[@index='0']").getAttribute("content-desc").equals("已实名")) {
				ps.append("<lv>"+replace(driver.findElementByXPath("//*[@resource-id='com.sankuai.meituan:id/mil_container' and @class='android.view.ViewGroup']/android.webkit.WebView[@index='0']/android.webkit.WebView[@index='0']/android.view.View[@index='0']").getAttribute("content-desc"))+"</lv>"+"\n");
				ps.append("<lv>"+replace(driver.findElementByXPath("//*[@resource-id='com.sankuai.meituan:id/mil_container' and @class='android.view.ViewGroup']/android.webkit.WebView[@index='0']/android.webkit.WebView[@index='0']/android.view.View[@index='2']").getAttribute("content-desc"))+"</lv>"+"\n");
				ps.append("<lv>"+replace(driver.findElementByXPath("//*[@resource-id='com.sankuai.meituan:id/mil_container' and @class='android.view.ViewGroup']/android.webkit.WebView[@index='0']/android.webkit.WebView[@index='0']/android.view.View[@index='3']").getAttribute("content-desc"))+"</lv>"+"\n");
			}else {
				ps.append("<lv>"+"未实名"+"</lv>"+"\n");
				ps.append("<lv>"+"无"+"</lv>"+"\n");
				ps.append("<lv>"+"无"+"</lv>"+"\n");
				ps.append("<lv>"+"无"+"</lv>"+"\n");
			}

			ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			while(!isElementExits("xpath", "//*[@class='android.view.View' and @content-desc='我的']", driver)) {
				driver.navigate().back();
				Thread.sleep(2000);
			}
			Util.writeProgress(72, 0, "身份信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(66, 1, "提取身份信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}

	/**
	 * 全部订单
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveOrderData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(73, 0, "正在提取订单信息...",writeProgress);
			driver.findElementByXPath("//*[@class='android.view.View' and @content-desc='订单']").click();
			Thread.sleep(4000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/MEITUAN/ORDERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/美团/订单信息</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/MeiTuan/Order Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"ORDERTYPEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">订单类型</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Order Type Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"COMPLETIONINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">完成情况</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Completion Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DESCRIPTIONINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">描述信息</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Description Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> orderList=driver.findElementsById("com.sankuai.meituan:id/order_item");
				if (orderList.size()>0) {
					for (MobileElement orderElement : orderList) {
						if (isElementExits("id", "com.sankuai.meituan:id/title", orderElement)&&
								isElementExits("id", "com.sankuai.meituan:id/status", orderElement)) {
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(driver.findElementById("com.sankuai.meituan:id/title").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(driver.findElementById("com.sankuai.meituan:id/status").getText())+"</lv>"+"\n");
							List<MobileElement> descriptionList=orderElement.findElementsByXPath("//*[@resource-id='com.sankuai.meituan:id/info_container']/android.widget.TextView")	;
							if (descriptionList.size()>0) {
								ps.append("<lv>");
								for (MobileElement descriptionElement : descriptionList) {
									ps.append(replace(descriptionElement.getText())+" ");
								}
								ps.append("</lv>"+"\n");
							}
							ps.append("</Tln>"+"\n");
						}
					}

				}

				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
			} while (!lastPageSource.equals(nowPageSource));

			ps.append("</InfoTable>");
			ps.flush();
			Util.writeProgress(84, 0, "订单信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(78, 1, "提取订单信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}

	/**
	 * 骑行信息
	 * @param driver
	 * @param dataName
	 */
	public void saveRidingData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(85, 0, "正在提取骑行信息...", writeProgress);
			driver.findElementByAccessibilityId("首页").click();
			Thread.sleep(5000);
			driver.findElementByXPath("//*[@resource-id='com.sankuai.meituan:id/util_area_text' and @text='骑车']").click();
			Thread.sleep(5000);

			if (isElementExits("xpath", "//*[@resource-id='com.sankuai.meituan:id/mil_container']/android.webkit.WebView/android.webkit.WebView/android.view.View[@index='1']",driver)){
				driver.findElementByXPath("//*[@resource-id='com.sankuai.meituan:id/mil_container']/android.webkit.WebView/android.webkit.WebView/android.view.View[@index='1']").click();
				Thread.sleep(3000);
			}

			driver.findElementById("com.sankuai.meituan:id/mobike_user_home").click();
			Thread.sleep(5000);
			driver.findElementByAccessibilityId("行程记录").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/MEITUAN/RIDINGINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/美团/骑行信息</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/MeiTuan/Riding Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TYPEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">骑行类型</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Type Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TIMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">时间与时长</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Time Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"AMOUNTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">金额</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Amount Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				List<MobileElement> orderList = driver.findElementsByXPath("//*[@resource-id='com.sankuai.meituan:id/mil_container']/android.webkit.WebView/android.webkit.WebView/android.view.View[@index='2']/android.widget.ListView[@index='1']/android.view.View");
				if (orderList.size()>0){
					for (MobileElement orderElement : orderList) {
						if (isElementExits("xpath", "//*[@class='android.view.View' and @index='0' and contains(@content-desc,'单车')]", orderElement)&&
						isElementExits("xpath", "//*[@class='android.view.View' and @index='1']/android.view.View[@index='0']", orderElement)&&
						isElementExits("xpath", "//*[@class='android.view.View' and @index='1']/android.view.View[@index='1']", orderElement)){
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(orderElement.findElementByXPath("//*[@class='android.view.View' and @index='0' and contains(@content-desc,'单车')]").getAttribute("content-desc"))+"</lv>"+"\n");
							ps.append("<lv>"+replace(orderElement.findElementByXPath("//*[@class='android.view.View' and @index='1']/android.view.View[@index='0']").getAttribute("content-desc"))+"</lv>"+"\n");
							ps.append("<lv>"+replace(orderElement.findElementByXPath("//*[@class='android.view.View' and @index='1']/android.view.View[@index='1']").getAttribute("content-desc"))+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();

			}while (!lastPageSource.equals(nowPageSource));
			Util.writeProgress(99, 0, "骑行信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(93,1,"提取骑行信息出错！",writeProgress);
			throw e;
		} finally {
			close(ps);
		}
	}

}
