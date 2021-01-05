import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class ShenZhou_function extends Function{
	
	public ShenZhou_function(String dir, int x, int y, OutputStream writeProgress) {
		// TODO Auto-generated constructor stub
		super(dir, x, y,writeProgress);
	}
	/**
	 * 个人资料信息
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void savePersonalData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(2, 0, "正在提取个人资料信息...", writeProgress);
			driver.findElementById("com.szzc.ucar.pilot:id/mine").click();
			Thread.sleep(10000);
			driver.findElementById("com.szzc.ucar.pilot:id/username").click();
			Thread.sleep(10000);
			driver.findElementById("com.szzc.ucar.pilot:id/title_edit").click();
			Thread.sleep(10000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/SHENZHOU/PERSONALINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/神州专车/个人资料</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/ShenZhou/Personal Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NICKNAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">昵称</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Nickname Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SEXINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">性别</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Sex Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"BIRTHDAYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">生日</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Birthday Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"MOBILEPHONEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">手机号</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Mobile Phone Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"EMAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">电子邮箱</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">E-mail Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			//Screenshot(driver, dataName);

			ps.append("<Tln>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.szzc.ucar.pilot:id/name").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.szzc.ucar.pilot:id/sex_tv_edit_userinfo").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.szzc.ucar.pilot:id/birth").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.szzc.ucar.pilot:id/phone").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.szzc.ucar.pilot:id/email").getText())+"</lv>"+"\n");
			ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();

			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(20, 0, "个人资料信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(10, 1, "提取个人资料信息出错！",writeProgress);
			throw e;

		} finally {
			close(ps);
		}

	}
	/**
	 * 实名认证
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveVerifiedData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(21, 0, "正在提取实名认证信息...",writeProgress);
			driver.findElementById("com.szzc.ucar.pilot:id/tv_certification_status").click();
			Thread.sleep(10000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/SHENZHOU/VERIFIEDINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/神州专车/实名认证</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/ShenZhou/Verified Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CERTIFICATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">证件类型</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Certificate Information</Text>"+"\n");
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

			if (isElementExits("id", "com.szzc.ucar.pilot:id/et_user_identify_type", driver)
					&&isElementExits("id", "com.szzc.ucar.pilot:id/et_user_name", driver)
					&&isElementExits("id", "com.szzc.ucar.pilot:id/et_user_idcard_num", driver)){
				ps.append("<Tln>"+"\n");
				ps.append("<lv>"+replace(driver.findElementById("com.szzc.ucar.pilot:id/et_user_identify_type").getText())+"</lv>"+"\n");
				ps.append("<lv>"+replace(driver.findElementById("com.szzc.ucar.pilot:id/et_user_name").getText())+"</lv>"+"\n");
				ps.append("<lv>"+replace(driver.findElementById("com.szzc.ucar.pilot:id/et_user_idcard_num").getText())+"</lv>"+"\n");
				ps.append("</Tln>"+"\n");
			}

			ps.append("</InfoTable>");
			ps.flush();

			driver.navigate().back();
			Thread.sleep(5000);
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(40, 0, "实名认证信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(30, 1, "提取实名认证信息出错！", writeProgress);
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
	@SuppressWarnings("rawtypes")
	public void saveWalletData(AndroidDriver driver,String dataName) throws Exception {
		

		PrintStream ps=null;

		try {
			Util.writeProgress(41, 0, "正在提取钱包信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id='com.szzc.ucar.pilot:id/func_name' and @text='钱包']").click();
			Thread.sleep(10000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/SHENZHOU/WALLETINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/神州专车/钱包</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/ShenZhou/Wallet Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"BALANCEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">余额</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Balance Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			//Screenshot(driver, dataName);

			ps.append("<Tln>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.szzc.ucar.pilot:id/wallet_balance_value").getText())+"</lv>"+"\n");
			ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();

			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(60, 0, "钱包信息提取完成!",writeProgress);
		} catch (Exception e) {
			Util.writeProgress(50, 1, "提取钱包信息出错！", writeProgress);
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
	public void saveAddressData(AndroidDriver driver,String dataName) throws Exception {
		
		PrintStream ps=null;

		try {
			Util.writeProgress(61, 0, "正在提取常用地址信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id='com.szzc.ucar.pilot:id/func_name' and @text='设置']").click();
			Thread.sleep(10000);
			driver.findElementById("com.szzc.ucar.pilot:id/common_address").click();
			Thread.sleep(10000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/SHENZHOU/ADDRESSINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/神州专车/地址</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/ShenZhou/Address Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">地址名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DETIALINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">详细信息</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Detial Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			//Screenshot(driver, dataName);
			ps.append("<Tln>"+"\n");
			ps.append("<lv>"+"家"+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.szzc.ucar.pilot:id/tv_home_address_name").getText())+"</lv>"+"\n");
			ps.append("</Tln>"+"\n");
			ps.append("<Tln>"+"\n");
			ps.append("<lv>"+"公司"+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.szzc.ucar.pilot:id/tv_work_address_name").getText())+"</lv>"+"\n");
			ps.append("</Tln>"+"\n");

			List<MobileElement> addressList=driver.findElementsByXPath("//*[@resource-id='com.szzc.ucar.pilot:id/common_list']/android.widget.LinearLayout");
			if (addressList.size()>0) {
				for (MobileElement addressElement : addressList) {
					if (isElementExits("id", "com.szzc.ucar.pilot:id/address_name", addressElement)&&isElementExits("id", "com.szzc.ucar.pilot:id/address_name_detail", addressElement)) {
						ps.append("<Tln>"+"\n");
						ps.append("<lv>"+replace(addressElement.findElementById("com.szzc.ucar.pilot:id/address_name").getText())+"</lv>"+"\n");
						ps.append("<lv>"+replace(addressElement.findElementById("com.szzc.ucar.pilot:id/address_name_detail").getText())+"</lv>"+"\n");
						ps.append("</Tln>"+"\n");
					}
				}
			}

			ps.append("</InfoTable>");
			ps.flush();

			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(80, 0, "常用地址信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(70, 1, "提取常用地址信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 亲密联络人
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveContactData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(81, 0, "正在提取亲密联络人信息...",writeProgress);
			driver.findElementById("com.szzc.ucar.pilot:id/close_contact").click();
			Thread.sleep(10000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/SHENZHOU/CONTACTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/神州专车/亲密联络人</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/ShenZhou/Contact Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">姓名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"PHONEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">手机号</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Phone Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			//Screenshot(driver, dataName);

			ps.append("<Tln>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.szzc.ucar.pilot:id/close_contact_name").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.szzc.ucar.pilot:id/close_contact_number").getText())+"</lv>"+"\n");
			ps.append("</Tln>"+"\n");

			ps.append("</InfoTable>");
			ps.flush();

			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(99, 0, "亲密联络人信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(90, 1, "提取亲密联络人信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}


	}
	
	
	

}
