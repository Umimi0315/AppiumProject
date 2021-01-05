import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class railway12306_function extends Function{
	public railway12306_function(String dir, int x, int y, OutputStream writeProgress) {
		super(dir, x, y,writeProgress);
	}

	/**
	 * 候补订单，全部遍历
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveAlternateOrderData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(2, 0, "正在提取候补订单信息...", writeProgress);
			driver.findElementById("com.MobileTicket.launcher:id/ticket_home_bottom_bar_order").click();
			Thread.sleep(3000);
			driver.findElementById("com.MobileTicket.launcher:id/ticket_home_bottom_bar_order").click();
			Thread.sleep(3000);

			driver.findElementById("com.MobileTicket.launcher:id/notice_icon").click();
			Thread.sleep(3000);
			driver.navigate().back();
			Thread.sleep(3000);

			driver.findElementByAccessibilityId("候补订单").click();
			Thread.sleep(3000);

			File xmlFile = createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/RAILWAY12306/ALTERNATEORDERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/铁路12306/候补订单</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Railway12306/Alternate order Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"STATIONINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">车站</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Station Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"ALTERNATENUMBERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">候补单号</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Alternate Number Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"ALTERNATETRAINNUMBERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">候补车次</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Alternate Train Number Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"RIDERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">乘车人</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Rider Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"EXCHANGESITUATIONINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">兑换情况</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Exchange Situation Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
//				Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsByXPath("//*[@class='android.widget.ListView' and @index= '1']/android.view.View");
			if (list.size()>0) {
				for (MobileElement mobileElement : list) {
					if (isElementExits("xpath", "//*[@class='android.view.View' and @index='1']", mobileElement)&&
							isElementExits("xpath", "//*[@class='android.view.View' and @index='5']", mobileElement)&&
							isElementExits("xpath", "//*[@class='android.view.View' and @index='8']", mobileElement)&&
							isElementExits("xpath", "//*[@class='android.view.View' and @index='10']", mobileElement)&&
							isElementExits("xpath", "//*[@class='android.view.View' and @index='6']", mobileElement)) {
								ps.append("<Tln>"+"\n");
								ps.append("<lv>"+replace(mobileElement.findElementByXPath("//*[@class='android.view.View' and @index='1']").getAttribute("content-desc"))+"</lv>"+"\n");
								ps.append("<lv>"+replace(mobileElement.findElementByXPath("//*[@class='android.view.View' and @index='5']").getAttribute("content-desc"))+"</lv>"+"\n");
								ps.append("<lv>"+replace(mobileElement.findElementByXPath("//*[@class='android.view.View' and @index='8']").getAttribute("content-desc"))+"</lv>"+"\n");
								ps.append("<lv>"+replace(mobileElement.findElementByXPath("//*[@class='android.view.View' and @index='10']").getAttribute("content-desc"))+"</lv>"+"\n");
								ps.append("<lv>"+replace(mobileElement.findElementByXPath("//*[@class='android.view.View' and @index='6']").getAttribute("content-desc"))+"</lv>"+"\n");
								ps.append("</Tln>"+"\n");
						 }
				}
			}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();
			}while(!(lastPageSource.equals(nowPageSource))==true);

			ps.append("</InfoTable>");
			ps.flush();
			ps.close();
			driver.findElementByAccessibilityId("返回").click();
			Thread.sleep(5000);
			Util.writeProgress(25, 0, "候补订单信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(12, 1, "提取候补订单信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 乘车人，全部遍历
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveRiderData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(26, 0, "正在提取乘车人信息...", writeProgress);

			driver.findElementById("com.MobileTicket.launcher:id/ticket_home_bottom_bar_mine").click();
			Thread.sleep(5000);
			driver.findElementByXPath("//*[@class='android.view.View' and @index= '0']").click();
			Thread.sleep(5000);
			driver.findElementByAccessibilityId("乘车人").click();
			Thread.sleep(5000);

			File xmlFile = createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/RAILWAY12306/RIDERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/铁路12306/乘车人</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Railway12306/Rider Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">姓名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"IDENTITYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">身份</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Identity Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"IDNUMBERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">证件号</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">ID Number Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
	//			Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsByClassName("android.widget.ListView");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						List<MobileElement> viewList=mobileElement.findElementsByXPath("//android.view.View");
						for (MobileElement viewElement : viewList) {
							if (isElementExits("xpath", "//android.view.View/android.view.View[@index= '0']", viewElement)&&
							isElementExits("xpath", "//android.view.View/android.view.View[@index= '1']", viewElement)&&
							isElementExits("xpath", "//android.view.View/android.view.View[@index= '2']", viewElement)) {
								if (viewElement.findElementByXPath("//android.view.View/android.view.View[@index= '0']").getAttribute("content-desc").equals("")) {
									continue;
								}
								ps.append("<Tln>"+"\n");
								ps.append("<lv>"+replace(viewElement.findElementByXPath("//android.view.View/android.view.View[@index= '0']").getAttribute("content-desc"))+"</lv>"+"\n");
								ps.append("<lv>"+replace(viewElement.findElementByXPath("//android.view.View/android.view.View[@index= '1']").getAttribute("content-desc"))+"</lv>"+"\n");
								ps.append("<lv>"+replace(viewElement.findElementByXPath("//android.view.View/android.view.View[@index= '2']").getAttribute("content-desc"))+"</lv>"+"\n");
								ps.append("</Tln>"+"\n");
							}

						}

					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();
			}while(!(lastPageSource.equals(nowPageSource))==true);

			ps.append("</InfoTable>");
			ps.flush();
			ps.close();
			driver.findElementByAccessibilityId("返回").click();
			Thread.sleep(5000);
			Util.writeProgress(50, 0, "乘车人信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(38, 1, "提取乘车人信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 个人资料
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void savePersonalData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(51, 0, "正在提取个人资料信息...", writeProgress);
			driver.findElementByAccessibilityId("个人资料").click();
			Thread.sleep(5000);

			File xmlFile = createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/RAILWAY12306/PERSONALINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/铁路12306/个人资料</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Railway12306/Personal Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"USERNAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">用户名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">User Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">姓名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"COUNTRYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">国家地区</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Country Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CERTIFICATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">证件类型</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Certificate Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"IDNUMBERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">证件号码</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">ID Number Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"APPROVALSTATUSINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">审核状态</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Approval Status Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"MOBILEPHONEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">手机号码</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Mobile Phone Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
//    	ps.append("<Prop vType=\"string\" TextKey=\"MOBILEPHONEAPPROVALINFORMATION\">"+"\n");
//    	ps.append("<Text Lang=\"zh_cn\">手机号码审核状态</Text>"+"\n");
//    	ps.append("<Text Lang=\"en_us\">Mobile Phone Approval Information</Text>"+"\n");
//    	ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TELEPHONEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">固定电话</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Telephone Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"EMAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">电子邮箱</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">E-mail Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"ADDRESSINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">地址</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Address Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"POSTCODEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮编</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Postcode Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
//    	ps.append("<Prop vType=\"string\" TextKey=\"TRAVELLERTYPEINFORMATION\">"+"\n");
//    	ps.append("<Text Lang=\"zh_cn\">旅客类型</Text>"+"\n");
//    	ps.append("<Text Lang=\"en_us\">Traveller Type Information</Text>"+"\n");
//    	ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

//		Screenshot(driver, dataName);
			List<MobileElement> list=driver.findElementsByXPath("//*[@class='android.view.View' and @resource-id= 'form']/android.view.View");
			if (list.size()>0) {
				int i=0;
				ps.append("<Tln>"+"\n");
				while (list.get(i).getAttribute("content-desc").equals("联系方式")==false) {
					if (list.get(i).getAttribute("content-desc").equals("基本信息")||
							list.get(i).getAttribute("content-desc").equals("详细信息")||
							list.get(i).getAttribute("content-desc").equals("附加信息")) {
						i++;
						continue;
					}
					if (list.get(i).getAttribute("content-desc").equals("用  户  名：")||
							list.get(i).getAttribute("content-desc").equals("姓      名：")||
							list.get(i).getAttribute("content-desc").equals("性      别：")||
							list.get(i).getAttribute("content-desc").equals("国家地区：")||
							list.get(i).getAttribute("content-desc").equals("证件类型：")||
							list.get(i).getAttribute("content-desc").equals("证件号码：")||
							list.get(i).getAttribute("content-desc").equals("审核状态：")) {
						if (list.get(i+1).getAttribute("content-desc").equals("用  户  名：")||
								list.get(i+1).getAttribute("content-desc").equals("姓      名：")||
								list.get(i+1).getAttribute("content-desc").equals("性      别：")||
								list.get(i+1).getAttribute("content-desc").equals("国家地区：")||
								list.get(i+1).getAttribute("content-desc").equals("证件类型：")||
								list.get(i+1).getAttribute("content-desc").equals("证件号码：")||
								list.get(i+1).getAttribute("content-desc").equals("审核状态：")) {
							ps.append("<lv>"+replace("无")+"</lv>"+"\n");
							i++;
							continue;
						}else {
							i++;
							continue;
						}
					}
					ps.append("<lv>"+replace(list.get(i).getAttribute("content-desc"))+"</lv>"+"\n");
					i++;
				}
			}
			driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
//		Screenshot(driver, dataName);
			List<MobileElement> list2=driver.findElementsByXPath("//*[@class='android.view.View' and @resource-id= 'form']/android.view.View");
			if (list2.size()>0) {
				int i=0;
				for (int j = 0; j < list.size(); j++) {
					if (list2.get(j).getAttribute("content-desc").equals("联系方式")) {
						i=j;
						break;
					}
				}
				while (i<list2.size()) {
					if (list2.get(i).getAttribute("content-desc").equals("基本信息")||
							list2.get(i).getAttribute("content-desc").equals("详细信息")||
							list2.get(i).getAttribute("content-desc").equals("联系方式")) {
						i++;
						continue;
					}
					if (list2.get(i).getAttribute("content-desc").equals("手机号码：")||
							list2.get(i).getAttribute("content-desc").equals("固定电话：")||
							list2.get(i).getAttribute("content-desc").equals("电子邮箱：")||
							list2.get(i).getAttribute("content-desc").equals("地      址：")||
							list2.get(i).getAttribute("content-desc").equals("邮      编：")) {
						if (list2.get(i).getAttribute("content-desc").equals("邮      编：") && i==list2.size()-2) {
							ps.append("<lv>"+replace("无")+"</lv>"+"\n");
							i++;
							break;
						}
						if (list2.get(i).getAttribute("content-desc").equals("手机号码：")
								&&(list2.get(i+1).getAttribute("content-desc").equals("固定电话：")==false)
								&&(list2.get(i+2).getAttribute("content-desc").equals("固定电话：")==false)) {
							ps.append("<lv>"+replace(list2.get(i+1).getAttribute("content-desc"))+replace(list2.get(i+2).getAttribute("content-desc"))+"</lv>"+"\n");
							i=i+3;
							continue;
						}
						if (list2.get(i+1).getAttribute("content-desc").equals("手机号码：")||
								list2.get(i+1).getAttribute("content-desc").equals("固定电话：")||
								list2.get(i+1).getAttribute("content-desc").equals("电子邮箱：")||
								list2.get(i+1).getAttribute("content-desc").equals("地      址：")||
								list2.get(i+1).getAttribute("content-desc").equals("邮      编：")) {
							ps.append("<lv>"+replace("无")+"</lv>"+"\n");
							i++;
							continue;
						}else {
							i++;
							continue;
						}
					}
					ps.append("<lv>"+replace(list2.get(i).getAttribute("content-desc"))+"</lv>"+"\n");
					i++;
				}
				ps.append("</Tln>"+"\n");
	//				List<MobileElement> list3=list2.get(list2.size()-1).findElementsByClassName("android.view.View");
	//				if (list3.size()>0) {
	////					for (int j = 0; j < list3.size(); j++) {
	////						if (list3.get(j).getAttribute("content-desc").equals("附加信息")) {
	////							i=j;
	////							break;
	////						}
	////					}
	//					for (int j = 0; j < list3.size(); j++) {
	//						if (list3.get(j).getAttribute("content-desc").equals("附加信息")) {
	//							continue;
	//						}
	//						if (list3.get(j).getAttribute("content-desc").equals("旅客类型：")) {
	//							if (j==list3.size()-1) {
	//								ps.append("<lv>"+replace("无")+"</lv>"+"\n");
	//								continue;
	//							}else {
	//								continue;
	//							}
	//						}
	//						ps.append("<lv>"+replace(list2.get(j).getAttribute("content-desc"))+"</lv>"+"\n");
	//					}
	//					ps.append("</Tln>"+"\n");
	//				}
			}

			ps.append("</InfoTable>");
			ps.flush();
			ps.close();
			driver.findElementByAccessibilityId("返回").click();
			Thread.sleep(5000);
			Util.writeProgress(75, 0, "个人资料信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(63, 1, "提取个人资料信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}

	/**
	 * 地址管理
	 * @param driver
	 * @param dataName
	 */
	public void saveAddressData(AndroidDriver driver,String dataName) throws Exception {
		PrintStream ps=null;

		try {
			Util.writeProgress(76, 0, "正在提取地址管理信息...", writeProgress);
			driver.findElementByAccessibilityId("地址管理").click();
			Thread.sleep(5000);

			if(isElementExits("id", "com.MobileTicket.common:id/sure", driver)){
				driver.findElementById("com.MobileTicket.common:id/sure").click();
				Thread.sleep(5000);
			}

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/RAILWAY12306/ADDRESSINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/铁路12306/地址管理</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Railway12306/Address Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"ADDRESSINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">地址信息</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Address Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">姓名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"PHONEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">手机号</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Phone Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");


			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();

			do{
				lastPageSource=nowPageSource;
				List<MobileElement> addressList = driver.findElementsByXPath("//*[@class='android.webkit.WebView' and @content-desc='地址管理']/android.view.View/android.widget.ListView[@index='1']/android.view.View");
				if (addressList.size()>0){
					for (MobileElement addressElement : addressList) {
						if (isElementExits("xpath", ".//android.view.View[@index='1']/../android.view.View[@index=0]", addressElement)&&
								isElementExits("xpath", ".//android.view.View[@index='1']", addressElement)&&
								isElementExits("xpath", ".//android.view.View[@index='2']", addressElement)){
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(addressElement.findElementByXPath(".//android.view.View[@index='1']/../android.view.View[@index=0]").getAttribute("content-desc"))+"</lv>"+"\n");
							ps.append("<lv>"+replace(addressElement.findElementByXPath(".//android.view.View[@index='1']").getAttribute("content-desc"))+"</lv>"+"\n");
							ps.append("<lv>"+replace(addressElement.findElementByXPath(".//android.view.View[@index='2']").getAttribute("content-desc"))+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						}
					}
				}

				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(5000);
				nowPageSource=driver.getPageSource();
			}while (!lastPageSource.equals(nowPageSource));

			ps.append("</InfoTable>");
			ps.flush();
			driver.findElementByAccessibilityId("返回").click();
			Thread.sleep(5000);
			Util.writeProgress(99, 0, "地址管理信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(87, 1, "提取地址管理信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}
	}
}
