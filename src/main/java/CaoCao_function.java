import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class CaoCao_function extends Function{
	public CaoCao_function(String dir, int x, int y, OutputStream writeProgress) {
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
	public void savePersonalData(AndroidDriver driver,String dataName) throws Exception{
		
		PrintStream ps=null;
		try {
			Util.writeProgress(2, 0, "正在提取个人资料信息...",writeProgress);
			driver.findElementById("cn.caocaokeji.user:id/platform_home_menu_view").click();
			Thread.sleep(2000);
			driver.findElementById("cn.caocaokeji.user:id/menu_main_tv_person_name").click();
			Thread.sleep(2000);
			
			File xmlFile=createDataSaveFile(dataName);
			
			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/CAOCAO/PERSONALINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/曹操出行/个人资料</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/CaoCao/Personal Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"MEMBERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">会员等级</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Member Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NICKNAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">昵称</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Nickname Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"GENDERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">性别</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Gender Information</Text>"+"\n");
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
			ps.append("<Prop vType=\"string\" TextKey=\"INDUSTRYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">行业</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Industry Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"PREFERENCEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">乘车偏好</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Preference Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			//Screenshot(driver, dataName);
			ps.append("<Tln>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("cn.caocaokeji.user:id/menu_person_tv_member").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("cn.caocaokeji.user:id/menu_person_tv_name").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("cn.caocaokeji.user:id/menu_person_tv_gender").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("cn.caocaokeji.user:id/menu_person_tv_birthday").getText())+"</lv>"+"\n");
			driver.swipe(x/2, y*4/5, x/2, y/5, 1000);
			//Screenshot(driver, dataName);
			ps.append("<lv>"+replace(driver.findElementById("cn.caocaokeji.user:id/menu_person_tv_mobile").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("cn.caocaokeji.user:id/menu_person_tv_email").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("cn.caocaokeji.user:id/menu_person_tv_business").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("cn.caocaokeji.user:id/menu_person_tv_preference").getText())+"</lv>"+"\n");
			ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			driver.navigate().back();
			Thread.sleep(2000);
			Util.writeProgress(19, 0, "个人资料信息提取完成！",writeProgress);
		} catch (Exception e) {

			Util.writeProgress(10, 1, "个人资料信息提取出错！",writeProgress);
			throw e;
		}finally {
			close(ps);
		}

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
			Util.writeProgress(20, 0, "正在提取行程信息...",writeProgress);
			driver.findElementByXPath("//*[@resource-id='cn.caocaokeji.user:id/menu_main_item_menu_name' and @text='行程']").click();
			Thread.sleep(2000);
			
			File xmlFile=createDataSaveFile(dataName);
			
			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/CAOCAO/STROKEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/曹操出行/行程信息</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/CaoCao/Stroke Information</Text>"+"\n");
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
				List<MobileElement> orderList=driver.findElementsByXPath("//*[@resource-id='cn.caocaokeji.user:id/menu_trip_rv_content']/android.widget.LinearLayout");
				if (orderList.size()>0) {
					for (MobileElement orderElement : orderList) {
						if (isElementExits("id", "cn.caocaokeji.user:id/menu_trip_tv_biz", orderElement)&&isElementExits("id", "cn.caocaokeji.user:id/menu_trip_tv_order_status", orderElement)&&
								isElementExits("id", "cn.caocaokeji.user:id/menu_trip_tv_time", orderElement)&&isElementExits("id", "cn.caocaokeji.user:id/menu_trip_tv_start_point", orderElement)&&
								isElementExits("id", "cn.caocaokeji.user:id/menu_trip_tv_end_point", orderElement)) {
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(orderElement.findElementById("cn.caocaokeji.user:id/menu_trip_tv_biz").getText())+"</lv>"+"\n");
					    	ps.append("<lv>"+replace(orderElement.findElementById("cn.caocaokeji.user:id/menu_trip_tv_order_status").getText())+"</lv>"+"\n");
					    	ps.append("<lv>"+replace(orderElement.findElementById("cn.caocaokeji.user:id/menu_trip_tv_time").getText())+"</lv>"+"\n");
					    	ps.append("<lv>"+replace(orderElement.findElementById("cn.caocaokeji.user:id/menu_trip_tv_start_point").getText())+"</lv>"+"\n");
					    	ps.append("<lv>"+replace(orderElement.findElementById("cn.caocaokeji.user:id/menu_trip_tv_end_point").getText())+"</lv>"+"\n");
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
			Util.writeProgress(39, 0, "行程信息提取完成！",writeProgress);
		} catch (Exception e) {
			
			Util.writeProgress(30, 1, "行程信息提取出错！",writeProgress);
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
			Util.writeProgress(40, 0, "正在提取钱包信息...",writeProgress);
			driver.findElementByXPath("//*[@resource-id='cn.caocaokeji.user:id/menu_main_item_menu_name' and @text='钱包']").click();
			Thread.sleep(10000);

			File xmlFile=createDataSaveFile(dataName);
			
			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/CAOCAO/WALLETINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/曹操出行/钱包</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/CaoCao/Wallet Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TOTALAMOUNTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">总金额</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Total Amount Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FROZENAMOUNTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">冻结金额</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Frozen Amount Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"BONUSINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">赠额</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Bouns Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"PRINCIPALINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">本金</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Principal Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TAILWINDINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">顺风车钱包余额</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Tailwind Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			
			//Screenshot(driver, dataName);
//			driver.findElementByXPath("//*[@class='android.view.View' and @content-desc='账单']").click();
//			Thread.sleep(2000);
//			driver.navigate().back();
//			Thread.sleep(2000 );
			ps.append("<Tln>"+"\n");
			if(isElementExits("xpath", "//*[@class='android.webkit.WebView']/android.view.View[@index='2']/android.view.View[@index='3']", driver)){
				ps.append("<lv>"+replace(driver.findElementByXPath("//*[@class='android.webkit.WebView']/android.view.View[@index='2']/android.view.View[@index='3']").getAttribute("content-desc"))+"</lv>"+"\n");
			}else {
				ps.append("<lv>"+"0"+"</lv>"+"\n");
			}
			if(isElementExits("xpath", "//*[@class='android.webkit.WebView']/android.view.View[@index='2']/android.view.View[@index='4']", driver)){
				ps.append("<lv>"+replace(driver.findElementByXPath("//*[@class='android.webkit.WebView']/android.view.View[@index='2']/android.view.View[@index='4']").getAttribute("content-desc"))+"</lv>"+"\n");
			}else {
				ps.append("<lv>"+"0"+"</lv>"+"\n");
			}
			if(isElementExits("xpath", "//*[@class='android.webkit.WebView']/android.view.View[@index='2']/android.view.View[@index='6']", driver)){
				ps.append("<lv>"+replace(driver.findElementByXPath("//*[@class='android.webkit.WebView']/android.view.View[@index='2']/android.view.View[@index='6']").getAttribute("content-desc"))+"</lv>"+"\n");

			}else {
				ps.append("<lv>"+"0"+"</lv>"+"\n");
			}
			if(isElementExits("xpath","//*[@class='android.webkit.WebView']/android.view.View[@index='2']/android.view.View[@index='8']" , driver)){
				ps.append("<lv>"+replace(driver.findElementByXPath("//*[@class='android.webkit.WebView']/android.view.View[@index='2']/android.view.View[@index='8']").getAttribute("content-desc"))+"</lv>"+"\n");
			}else {
				ps.append("<lv>"+"0"+"</lv>"+"\n");
			}
			driver.findElementByXPath("//*[@class='android.view.View' and @text='顺风车账户']").click();
			Thread.sleep(3000);
			//Screenshot(driver, dataName);
			ps.append("<lv>"+replace(driver.findElementByXPath("//*[@class='android.view.View' and @resource-id='root']/android.view.View[@index='0']/android.view.View[@index='2']").getText())+"</lv>"+"\n");
			ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			
			driver.navigate().back();
			Thread.sleep(2000);
			
			driver.navigate().back();
			Thread.sleep(2000);
			Util.writeProgress(59, 0, "钱包信息提取完成！",writeProgress);
		} catch (Exception e) {

			Util.writeProgress(50, 1, "钱包信息提取出错！",writeProgress);
			throw e;
		}finally {
			close(ps);
		}

	}
	/**
	 * 紧急联系人
	 * @param driver
	 * @param dataName
	 */
	public void saveContactData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;
		try {
			Util.writeProgress(60, 0, "正在提取紧急联系人信息...",writeProgress);
			driver.findElementByXPath("//*[@resource-id='cn.caocaokeji.user:id/menu_main_item_menu_name' and @text='设置']").click();
			Thread.sleep(2000);
			driver.findElementByXPath("//*[@class='android.widget.TextView' and @text='设置紧急联系人']").click();
			Thread.sleep(2000);
			
			File xmlFile=createDataSaveFile(dataName);
			
			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/CAOCAO/CONTACTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/曹操出行/紧急联系人</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/CaoCao/Contact Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">姓名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"PHONENUMBERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">手机号</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Phone Number Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			
			//Screenshot(driver, dataName);
			List<MobileElement> contactList=driver.findElementsByClassName("android.view.View");
			int num=0;
			if (contactList.size()>0) {
				for (MobileElement contactElement : contactList) {
					if (contactElement.getText().equals("")||contactElement.getText()==null||
							contactElement.getText().equals("设置紧急联系人")||contactElement.getText().equals("自动分享行程")||
							contactElement.getText().equals("去开启")||contactElement.getText().equals("修改第一联系人")||
							contactElement.getText().equals("添加紧急联系人")||contactElement.getText().equals("最多添加3位紧急联系人")) {
						continue;				
					}
					num++;
					if (num==1) {
						ps.append("<Tln>"+"\n");
						ps.append("<lv>"+replace(contactElement.getText())+"</lv>"+"\n");
					}
					if (num==2) {
						ps.append("<lv>"+replace(contactElement.getText())+"</lv>"+"\n");
						ps.append("</Tln>"+"\n");
						num=0;
					}
				}
			}
			ps.append("</InfoTable>");
			ps.flush();
			
			driver.navigate().back();
			Thread.sleep(2000);
			Util.writeProgress(79, 0, "紧急联系人信息提取完成！",writeProgress);
		} catch (Exception e) {

			Util.writeProgress(70, 1, "紧急联系人信息提取出错！",writeProgress);
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
			Util.writeProgress(80, 0, "正在提取常用地址信息...",writeProgress);
			driver.findElementByXPath("//*[@class='android.widget.TextView' and @text='设置常用地址']").click();
			Thread.sleep(2000);
			
			File xmlFile=createDataSaveFile(dataName);
			
			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/CAOCAO/ADDRESSINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/曹操出行/常用地址</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/CaoCao/Address Information</Text>"+"\n");
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
			ps.append("<lv>"+replace(driver.findElementById("cn.caocaokeji.user:id/common_item_tv_set_address_1").getText())+"</lv>"+"\n");
			ps.append("<lv>"+replace(driver.findElementById("cn.caocaokeji.user:id/common_item_tv_set_address_2").getText())+"</lv>"+"\n");
			ps.append("</Tln>"+"\n");
			
			ps.append("</InfoTable>");
			ps.flush();
			
			driver.navigate().back();
			Thread.sleep(2000);
			Util.writeProgress(99, 0, "常用地址信息提取完成！",writeProgress);
		} catch (Exception e) {

			Util.writeProgress(90, 1, "常用地址信息提取出错！",writeProgress);
			throw e;
		}finally {
			close(ps);
		}

	}

}
