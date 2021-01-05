
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.WeakHashMap;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class ShouYue_function extends Function {
	public ShouYue_function(String dir, int x, int y, OutputStream writeProgress) {
		// TODO Auto-generated constructor stub
		super(dir, x, y,writeProgress);
	}
	/**
	 * 个人资料
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void savePersonalData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(2, 0, "正在提取个人资料信息",writeProgress);
			driver.findElementByXPath("//*[@resource-id='com.ichinait.gbpassenger:id/main_title_bar']/android.widget.FrameLayout[@index='1']/android.widget.ImageView[@index='0']").click();
			Thread.sleep(5000);
			driver.findElementById("com.ichinait.gbpassenger:id/user_info_header").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/SHOUYUE/PERSONALINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/首汽约车/个人资料</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/ShouYue/Personal Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"LEVELINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">会员等级</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Level Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FREQUENCYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">用车次数或距离</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Frequency Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NICKNAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">昵称</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Nickname Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SEXINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">性别</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Sex Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"MOBILEPHONEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">电话</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Mobile Phone Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"BIRTHDAYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">生日</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Birthday Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"IDCARDINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">身份证</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">ID Card Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			//Screenshot(driver, dataName);
			ps.append("<Tln>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.ichinait.gbpassenger:id/user_info_text_level").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.ichinait.gbpassenger:id/user_info_times_distance").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.ichinait.gbpassenger:id/user_info_edit_name").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.ichinait.gbpassenger:id/user_info_tv_sex").getText())+"</lv>"+"\n");

			driver.swipe(x/2, y*4/5, x/2, y/5, 1000);
			Thread.sleep(5000);
			//Screenshot(driver, dataName);

			ps.append("<lv>"+replace(driver.findElementById("com.ichinait.gbpassenger:id/user_info_phone").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.ichinait.gbpassenger:id/user_info_birthday").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.ichinait.gbpassenger:id/user_info_id").getText())+"</lv>"+"\n");
			ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();

			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(25, 0, "个人资料信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(12, 1, "提取个人资料信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}


	}
	/**
	 * 钱包
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void saveWalletData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(26, 0, "正在提取钱包信息...", writeProgress);
			driver.findElementById("com.ichinait.gbpassenger:id/tv_mywallet").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/SHOUYUE/WALLETINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/首汽约车/钱包</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/ShouYue/Wallet Information</Text>"+"\n");
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
			ps.append("<Prop vType=\"string\" TextKey=\"TRAVELCARDINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">出行卡</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Travel Card Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			//Screenshot(driver, dataName);

			ps.append("<Tln>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.ichinait.gbpassenger:id/wallet_amount_num").getText())+"</lv>"+"\n");

			driver.findElementByXPath("//*[@resource-id='com.ichinait.gbpassenger:id/wallet_item_title_iv' and @text='打车金']").click();
			Thread.sleep(5000);


			ps.append("<lv>"+replace(driver.findElementByXPath("//*[@class='android.view.View' and @resource-id='myRedPacket']/android.view.View[@index='1']").getText())+"</lv>"+"\n");
			driver.navigate().back();
			Thread.sleep(5000);

//    	while (isElementExits("xpath", "//*[@resource-id='com.ichinait.gbpassenger:id/wallet_item_title_iv' and @text='出行卡']", driver)) {
			driver.swipe(x/2, y*4/5, x/2, y/5, 1000);
			Thread.sleep(5000);
//		}

			driver.findElementByXPath("//*[@resource-id='com.ichinait.gbpassenger:id/wallet_item_title_iv' and @text='出行卡']").click();
			Thread.sleep(5000);

			ps.append("<lv>"+replace(driver.findElementByXPath("//*[@resource-id='travelCard' and @class='android.view.View']/android.view.View[@index='0']/android.view.View[@index='2']").getText())+"</lv>"+"\n");
			driver.navigate().back();
			Thread.sleep(5000);

			ps.append("</Tln>"+"\n");

			ps.append("</InfoTable>");
			ps.flush();
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(50, 0, "钱包信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(38, 1, "提取钱包信息出错！", writeProgress);
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
	public void saveContactData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(51, 0, "正在提取紧急联系人信息...", writeProgress);
			driver.findElementById("com.ichinait.gbpassenger:id/security_center_tv").click();
			Thread.sleep(5000);

			driver.findElementByXPath("//*[@resource-id='com.ichinait.gbpassenger:id/tv_content' and @text='紧急联系人']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/SHOUYUE/CONTACTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/首汽约车/紧急联系人</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/ShouYue/Contact Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">姓名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"PHONEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">电话</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Phone Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);

				List<MobileElement> contactList=driver.findElementsByClassName("android.view.ViewGroup");

				if (contactList.size()>0) {
					for (MobileElement contactElement : contactList) {
						ps.append("<Tln>"+"\n");
						ps.append("<lv>"+replace(contactElement.findElementById("com.ichinait.gbpassenger:id/tv_name").getText())+"</lv>"+"\n");
						ps.append("<lv>"+replace(contactElement.findElementById("com.ichinait.gbpassenger:id/tv_phone").getText())+"</lv>"+"\n");
						ps.append("</Tln>"+"\n");
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
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(75, 0, "紧急联系人信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(63, 1, "提取紧急联系人信息出错！", writeProgress);
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
			Util.writeProgress(76, 0,"正在提取常用地址信息...",writeProgress);
			driver.findElementById("com.ichinait.gbpassenger:id/setting_ll_push_tv").click();
			Thread.sleep(5000);

			driver.findElementById("com.ichinait.gbpassenger:id/setting_ll_common_address").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/SHOUYUE/ADDRESSINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/首汽约车/地址</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/ShouYue/Address Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">地址名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DETIALINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">详细地址</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Detial Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			ps.append("<Tln>"+"\n");
			ps.append("<lv>"+"家"+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementByXPath("//*[@resource-id='com.ichinait.gbpassenger:id/sl_common_address' and @index='0']/android.widget.RelativeLayout/android.widget.LinearLayout[@index='1']/android.widget.TextView[@resource-id='com.ichinait.gbpassenger:id/tv_common_address_no_set']").getText())+"</lv>"+"\n");
			ps.append("</Tln>"+"\n");

			ps.append("<Tln>"+"\n");
			ps.append("<lv>"+"公司"+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementByXPath("//*[@resource-id='com.ichinait.gbpassenger:id/sl_common_address' and @index='1']/android.widget.RelativeLayout/android.widget.LinearLayout[@index='1']/android.widget.TextView[@resource-id='com.ichinait.gbpassenger:id/tv_common_address_no_set']").getText())+"</lv>"+"\n");
			ps.append("</Tln>"+"\n");


			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);

				List<MobileElement> addressCollectionList=driver.findElementsById("com.ichinait.gbpassenger:id/collection_swipe");
				if (addressCollectionList.size()>0) {
					for (MobileElement addressCollectionElement : addressCollectionList) {
						if (isElementExits("id", "com.ichinait.gbpassenger:id/favorite_addr_item_addr_name", addressCollectionElement)&&
								isElementExits("id", "com.ichinait.gbpassenger:id/favorite_addr_item_addr", addressCollectionElement)) {
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(addressCollectionElement.findElementById("com.ichinait.gbpassenger:id/favorite_addr_item_addr_name").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(addressCollectionElement.findElementById("com.ichinait.gbpassenger:id/favorite_addr_item_addr").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						}
					}
				}

				driver.swipe(x/2, y*8/10, x/2, y*3/10, 1000);
				Thread.sleep(5000);
				nowPageSource=driver.getPageSource();

			} while (!lastPageSource.equals(nowPageSource));

			ps.append("</InfoTable>");
			ps.flush();

			driver.navigate().back();
			Thread.sleep(5000);

			driver.navigate().back();
			Thread.sleep(5000);

			Util.writeProgress(99, 0, "常用地址信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(89, 1, "提取常用地址信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}


	}
	
}
