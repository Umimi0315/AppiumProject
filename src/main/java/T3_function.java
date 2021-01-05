import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
//import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class T3_function extends Function{
	 
	
	public T3_function(String dir,int x,int y,OutputStream writeProgress) {
		super(dir, x, y,writeProgress);
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
			driver.findElementById("com.t3go.passenger:id/top_back_layout").click();
			Thread.sleep(2000);
			
			driver.findElementById("com.t3go.passenger:id/tv_menu_trip").click();
			Thread.sleep(2000);

			File xmlFile=createDataSaveFile(dataName);
	    	
	    	ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
	    	ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
	    	ps.append("<InfoTable>"+"\n");
	    	ps.append("<Node vType=\"string\" TextKey=\"/T3GO/STROKEINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">/T3出行/行程信息</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">/T3go/Stroke Information</Text>"+"\n");
	    	ps.append("</Node>"+"\n");
	    	ps.append("<TCol>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"VEHICLETYPEINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">车辆类型</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">Vehicle Type Information</Text>"+"\n");
	    	ps.append("</Prop>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"STATUSINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">订单状态</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">Status Information</Text>"+"\n");
	    	ps.append("</Prop>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"TIMEINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">订单时间</Text>"+"\n");
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
	    	
	    	String lastPageSource=null;
	    	String nowPageSource=driver.getPageSource();
	    	do {
	    		lastPageSource=nowPageSource;
	    		//Screenshot(driver, dataName);
	    		List<MobileElement> orderList=driver.findElementsByXPath("//*[@resource-id='com.t3go.passenger:id/x_recycler_view']/android.widget.RelativeLayout");
	    		if (orderList.size()>0) {
					for (MobileElement orderElement : orderList) {
						if (isElementExits("id", "com.t3go.passenger:id/tv_car_name", orderElement)&&isElementExits("id", "com.t3go.passenger:id/tv_status", orderElement)&&
								isElementExits("id", "com.t3go.passenger:id/tv_time", orderElement)&&isElementExits("id", "com.t3go.passenger:id/tv_address_or", orderElement)&&
								isElementExits("id", "com.t3go.passenger:id/tv_address_des", orderElement)) {
							ps.append("<Tln>"+"\n");
					    	ps.append("<lv>"+replace(orderElement.findElementById("com.t3go.passenger:id/tv_car_name").getText())+"</lv>"+"\n");
					    	ps.append("<lv>"+replace(orderElement.findElementById("com.t3go.passenger:id/tv_status").getText())+"</lv>"+"\n");
					    	ps.append("<lv>"+replace(orderElement.findElementById("com.t3go.passenger:id/tv_time").getText())+"</lv>"+"\n");
					    	ps.append("<lv>"+replace(orderElement.findElementById("com.t3go.passenger:id/tv_address_or").getText())+"</lv>"+"\n");
					    	ps.append("<lv>"+replace(orderElement.findElementById("com.t3go.passenger:id/tv_address_des").getText())+"</lv>"+"\n");
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
	    	driver.navigate().back();
	    	Thread.sleep(2000);
			Util.writeProgress(16, 0, "行程信息提取完成！",writeProgress);
		} catch (Exception e) {
			Util.writeProgress(10, 1, "提取行程信息出错！",writeProgress);
	        throw e;
		}finally {
			close(ps);
		}

	}
	
	
	
	/**
	 * 钱包
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveWalletData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;
		try {
			Util.writeProgress(17, 0, "正在提取钱包信息...",writeProgress);
			driver.findElementById("com.t3go.passenger:id/top_back_layout").click();
			Thread.sleep(2000);
			while (!isElementExits("id", "com.t3go.passenger:id/tv_menu_wallet", driver)){
				driver.findElementById("com.t3go.passenger:id/top_back_layout").click();
				Thread.sleep(2000);
			}
			driver.findElementById("com.t3go.passenger:id/tv_menu_wallet").click();
			Thread.sleep(2000);

			File xmlFile=createDataSaveFile(dataName);
	    	
	    	ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
	    	ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
	    	ps.append("<InfoTable>"+"\n");
	    	ps.append("<Node vType=\"string\" TextKey=\"/T3GO/WALLETINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">/T3出行/钱包</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">/T3go/Wallet Information</Text>"+"\n");
	    	ps.append("</Node>"+"\n");
	    	ps.append("<TCol>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"BALANCEINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">余额</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">Balance Information</Text>"+"\n");
	    	ps.append("</Prop>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"CASHINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">现金</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">Cash Information</Text>"+"\n");
	    	ps.append("</Prop>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"GIFTMONEYINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">赠送币</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">Gift Money Information</Text>"+"\n");
	    	ps.append("</Prop>"+"\n");
	    	ps.append("</TCol>"+"\n");
	    	//Screenshot(driver, dataName);
	    	ps.append("<Tln>"+"\n");
	    	ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_balance").getText())+"</lv>"+"\n");
	    	ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_cash").getText())+"</lv>"+"\n");
	    	ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_gift_money").getText())+"</lv>"+"\n");
	    	ps.append("</Tln>"+"\n");
	    	ps.append("</InfoTable>");
	    	ps.flush();
	    	ps.close();
	    	driver.navigate().back();
	    	Thread.sleep(2000);
			Util.writeProgress(33, 0, "钱包信息提取完成！",writeProgress);
		} catch (Exception e) {
			Util.writeProgress(26, 1, "提取钱包信息出错！",writeProgress);
	        throw e;
		}finally {
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
			Util.writeProgress(34, 0, "正在提取个人资料信息...",writeProgress);
			driver.findElementById("com.t3go.passenger:id/top_back_layout").click();
			Thread.sleep(2000);
			driver.findElementById("com.t3go.passenger:id/rl_menu_head").click();
			Thread.sleep(2000);
			driver.findElementById("com.t3go.passenger:id/id_top_menu").click();
			Thread.sleep(2000);
			
			File xmlFile=createDataSaveFile(dataName);
	    	
	    	ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
	    	ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
	    	ps.append("<InfoTable>"+"\n");
	    	ps.append("<Node vType=\"string\" TextKey=\"/T3GO/PERSONALINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">/T3出行/个人资料</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">/T3go/Personal Information</Text>"+"\n");
	    	ps.append("</Node>"+"\n");
	    	ps.append("<TCol>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"NICKNAMEINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">昵称</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">Nickname Information</Text>"+"\n");
	    	ps.append("</Prop>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"EMAILINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">邮箱</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">Email Information</Text>"+"\n");
	    	ps.append("</Prop>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"SEXINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">性别</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">Sex Information</Text>"+"\n");
	    	ps.append("</Prop>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"AGEINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">年龄</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">Age Information</Text>"+"\n");
	    	ps.append("</Prop>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"WORKINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">行业</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">Work Information</Text>"+"\n");
	    	ps.append("</Prop>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"COMPANYINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">公司</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">Company Information</Text>"+"\n");
	    	ps.append("</Prop>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"JOBINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">职业</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">Job Information</Text>"+"\n");
	    	ps.append("</Prop>"+"\n");
	    	ps.append("</TCol>"+"\n");
	    	//Screenshot(driver, dataName);
	    	ps.append("<Tln>"+"\n");
	    	ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/et_name").getText())+"</lv>"+"\n");
	    	ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/et_email").getText())+"</lv>"+"\n");
	    	ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_sex").getText())+"</lv>"+"\n");
	    	driver.swipe(x/2, y*4/5, x/2, y/5, 1000);
	    	Thread.sleep(2000);
	    	//Screenshot(driver, dataName);
	    	ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_age").getText())+"</lv>"+"\n");
	    	ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_work").getText())+"</lv>"+"\n");
	    	ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_company").getText())+"</lv>"+"\n");
	    	ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_job").getText())+"</lv>"+"\n");
	    	ps.append("</Tln>"+"\n");
	    	ps.append("</InfoTable>");
	    	ps.flush();
	    	ps.close();
	    	driver.navigate().back();
	    	Thread.sleep(2000);
	    	driver.navigate().back();
	    	Thread.sleep(2000);
			Util.writeProgress(50, 0, "个人资料信息提取完成！",writeProgress);
		} catch (Exception e) {
			Util.writeProgress(44, 1, "提取个人资料信息出错！",writeProgress);
	        throw e;
		}finally {
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
			Util.writeProgress(51, 0, "正在提取紧急联系人信息...",writeProgress);
			driver.findElementById("com.t3go.passenger:id/top_back_layout").click();
			Thread.sleep(2000);
			driver.findElementById("com.t3go.passenger:id/tv_menu_setting").click();
			Thread.sleep(2000);
			driver.findElementById("com.t3go.passenger:id/rl_contacts").click();
			Thread.sleep(2000);
			
			File xmlFile=createDataSaveFile(dataName);
	    	
	    	ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
	    	ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
	    	ps.append("<InfoTable>"+"\n");
	    	ps.append("<Node vType=\"string\" TextKey=\"/T3GO/CONTACTINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">/T3出行/紧急联系人</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">/T3go/Contact Information</Text>"+"\n");
	    	ps.append("</Node>"+"\n");
	    	ps.append("<TCol>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"DETAILINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">详细信息</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">Detail Information</Text>"+"\n");
	    	ps.append("</Prop>"+"\n");
	    	ps.append("</TCol>"+"\n");
	    	//Screenshot(driver, dataName);
	    	if(isElementExits("id", "com.t3go.passenger:id/tv_contact_1", driver)) {
	    		ps.append("<Tln>"+"\n");
	    		ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_contact_1").getText())+"</lv>"+"\n");
	    		ps.append("</Tln>"+"\n");
	    	}
	    	if(isElementExits("id", "com.t3go.passenger:id/tv_contact_2", driver)) {
	    		ps.append("<Tln>"+"\n");
	    		ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_contact_2").getText())+"</lv>"+"\n");
	    		ps.append("</Tln>"+"\n");
	    	}
	    	if(isElementExits("id", "com.t3go.passenger:id/tv_contact_3", driver)) {
	    		ps.append("<Tln>"+"\n");
	    		ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_contact_3").getText())+"</lv>"+"\n");
	    		ps.append("</Tln>"+"\n");
	    	}
	    	ps.append("</InfoTable>");
	    	ps.flush();
	    	ps.close();
	    	driver.navigate().back();
	    	Thread.sleep(2000);
	    	driver.navigate().back();
	    	Thread.sleep(2000);
			Util.writeProgress(67, 0, "紧急联系人信息提取完成！",writeProgress);
		} catch (Exception e) {

			Util.writeProgress(60, 1, "提取紧急联系人信息出错！",writeProgress);
			
	        throw e;
		}finally {
			close(ps);
		}

	}
	/**
	 * 问题验证
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveProblemData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;
		try {
			Util.writeProgress(68, 0, "正在提取问题验证信息...",writeProgress);
			driver.findElementById("com.t3go.passenger:id/top_back_layout").click();
			Thread.sleep(2000);
			driver.findElementById("com.t3go.passenger:id/tv_menu_setting").click();
			Thread.sleep(2000);
			driver.findElementById("com.t3go.passenger:id/rl_problem_validation").click();
			Thread.sleep(2000);
			
			File xmlFile=createDataSaveFile(dataName);
			
	    	ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
	    	ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
	    	ps.append("<InfoTable>"+"\n");
	    	ps.append("<Node vType=\"string\" TextKey=\"/T3GO/VERIFICATIONINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">/T3出行/问题验证</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">/T3go/Verification Information</Text>"+"\n");
	    	ps.append("</Node>"+"\n");
	    	ps.append("<TCol>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"PROBLEMINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">问题</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">Problem Information</Text>"+"\n");
	    	ps.append("</Prop>"+"\n");
	    	ps.append("<Prop vType=\"string\" TextKey=\"ANSWERINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">答案</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">Answer Information</Text>"+"\n");
	    	ps.append("</Prop>"+"\n");
	    	ps.append("</TCol>"+"\n");
	    	//Screenshot(driver, dataName);
	    	if (isElementExits("id", "com.t3go.passenger:id/tv_problem_one", driver)&&
	    			isElementExits("id", "com.t3go.passenger:id/et_problem_one_answer", driver)) {
	    		ps.append("<Tln>"+"\n");
	    		ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_problem_one").getText())+"</lv>"+"\n");
	    		ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/et_problem_one_answer").getText())+"</lv>"+"\n");
	    		ps.append("</Tln>"+"\n");
				
			}
	    	if (isElementExits("id", "com.t3go.passenger:id/tv_problem_two", driver)&&
	    			isElementExits("id", "com.t3go.passenger:id/et_problem_two_answer", driver)) {
	    		ps.append("<Tln>"+"\n");
	    		ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_problem_two").getText())+"</lv>"+"\n");
	    		ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/et_problem_two_answer").getText())+"</lv>"+"\n");
	    		ps.append("</Tln>"+"\n");
				
			}
	    	driver.swipe(x/2, y*4/5, x/2, y/5, 1000);
	    	//Screenshot(driver, dataName);
	    	if (isElementExits("id", "com.t3go.passenger:id/tv_problem_three", driver)&&
	    			isElementExits("id", "com.t3go.passenger:id/et_problem_three_answer", driver)) {
	    		ps.append("<Tln>"+"\n");
	    		ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_problem_three").getText())+"</lv>"+"\n");
	    		ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/et_problem_three_answer").getText())+"</lv>"+"\n");
	    		ps.append("</Tln>"+"\n");
				
			}
	    	ps.append("</InfoTable>");
	    	ps.flush();
	    	ps.close();
	    	driver.navigate().back();
	    	Thread.sleep(2000);
	    	driver.navigate().back();
	    	Thread.sleep(2000);
			Util.writeProgress(85, 0, "问题验证信息提取完成！",writeProgress);
		} catch (Exception e) {
			Util.writeProgress(76, 1, "提取问题验证信息出错！",writeProgress);
			throw e;
		}finally {
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
			Util.writeProgress(86, 0, "正在提取常用地址信息...",writeProgress);
			driver.findElementById("com.t3go.passenger:id/top_back_layout").click();
			Thread.sleep(2000);
			driver.findElementById("com.t3go.passenger:id/tv_menu_setting").click();
			Thread.sleep(2000);
			driver.findElementById("com.t3go.passenger:id/rl_address").click();
			Thread.sleep(2000);
			
			File xmlFile=createDataSaveFile(dataName);
	    	
	    	ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
	    	ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
	    	ps.append("<InfoTable>"+"\n");
	    	ps.append("<Node vType=\"string\" TextKey=\"/T3GO/ADDRESSINFORMATION\">"+"\n");
	    	ps.append("<Text Lang=\"zh_cn\">/T3出行/地址</Text>"+"\n");
	    	ps.append("<Text Lang=\"en_us\">/T3go/Address Information</Text>"+"\n");
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
			ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_home").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("com.t3go.passenger:id/tv_company").getText())+"</lv>"+"\n");
			ps.append("</Tln>"+"\n");
			
			ps.append("</InfoTable>");
	    	ps.flush();
	    	ps.close();
	    	driver.navigate().back();
	    	Thread.sleep(2000);
	    	driver.navigate().back();
	    	Thread.sleep(2000);
			Util.writeProgress(99, 0, "常用地址信息提取完成！",writeProgress);
		} catch (Exception e) {
			Util.writeProgress(94, 1, "提取问题验证信息出错！",writeProgress);
			throw e;
		}finally {
			close(ps);
		}
				
	}	

}
