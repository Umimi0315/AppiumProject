import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//import java.io.IOException;

public class QQmail_function_old extends Function{
	public static ArrayList<String> mail=new ArrayList<String>();
//	public static int mailnumber=0;
	public QQmail_function_old(String dir, int x, int y, OutputStream writeProgress) {
		super(dir, x, y,writeProgress);
	}
	/**
	 * 收件箱,全部遍历
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveInboxData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(2, 0, "正在提取收件箱信息...", writeProgress);
			driver.findElementByXPath("//*[@class='android.widget.TextView' and @text= '收件箱​']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QQMAIL/INBOXINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/QQ邮箱/收件箱</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/QQmail/Inbox Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"MAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件信息与主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Mail Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsByXPath("//android.widget.ListView[contains(@resource-id, 'com.tencent.androidqqmail:id')]/android.widget.RelativeLayout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("xpath", "//*[@class= 'android.view.View' and @index= '0']", mobileElement)) {
							if (mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").contains("带附件，")) {
//								System.out.println(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc"));
								int index=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").indexOf("发来:");
								int lastindex=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").lastIndexOf("。");
								attachments.add(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").substring(index+3,lastindex));
							}
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc"))+"</lv>"+"\n");
	//						ps.append("</Tln>"+"\n");
							mobileElement.click();
							Thread.sleep(3000);
							String content="";
							String contentLastPageSource=null;
							String contentNowPageSource=driver.getPageSource();
							do {
								if (isElementExits("xpath", "//*[@class='android.widget.TextView' and @text='翻译邮件']", driver)){
									driver.findElementByXPath("//*[@class='android.widget.TextView' and @text='翻译邮件']").click();
									Thread.sleep(1000);
								}
								if (isElementExits("id", "com.tencent.androidqqmail:id/alw", driver)){
									driver.findElementById("com.tencent.androidqqmail:id/alw").click();
									Thread.sleep(2000);
								}
								contentLastPageSource=contentNowPageSource;

								List<MobileElement> contentList=driver.findElementsByXPath("//android.view.View");
								if (contentList.size()>0) {
									for (MobileElement contentElement : contentList) {

										try {
											if (!contentElement.getAttribute("content-desc").equals("")||mobileElement.getAttribute("content-desc")!=null) {
											content=content+replace(contentElement.getAttribute("content-desc"));
											}
										} catch (Exception e) {
											continue;
										}

									}

								}
								driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
								Thread.sleep(1000);
								contentNowPageSource=driver.getPageSource();
							}while(!(contentLastPageSource.equals(contentNowPageSource))==true);
							if (content.equals("")) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+content+"</lv>"+"\n");
							}

							driver.navigate().back();
							ps.append("</Tln>"+"\n");
						}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
			}while(!(lastPageSource.equals(nowPageSource))==true);

			ps.append("</InfoTable>");
			ps.flush();
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(9, 0, "收件箱信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(5, 1, "提取收件箱信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}


	}
	/**
	 * 星标邮件,全部遍历
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveStarMailData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(28, 0, "正在提取星标邮件信息...", writeProgress);
			driver.findElementByXPath("//*[@class='android.widget.TextView' and @text= '星标邮件​']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QQMAIL/STARMAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/QQ邮箱/星标邮件</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/QQmail/Star Mail Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"MAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件信息与主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Mail Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsByXPath("//android.widget.ListView[contains(@resource-id, 'com.tencent.androidqqmail:id')]/android.widget.RelativeLayout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("xpath", "//*[@class= 'android.view.View' and @index= '0']", mobileElement)) {
							if (mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").contains("带附件，")) {
								int index=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").indexOf("发来:");
								int lastindex=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").lastIndexOf("。");
								attachments.add(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").substring(index+3,lastindex));
							}
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc"))+"</lv>"+"\n");
	//						ps.append("</Tln>"+"\n");
							mobileElement.click();
							Thread.sleep(3000);
							String content="";
							String contentLastPageSource=null;
							String contentNowPageSource=driver.getPageSource();
							do {
								contentLastPageSource=contentNowPageSource;

								List<MobileElement> contentList=driver.findElementsByXPath("//android.view.View");
								if (contentList.size()>0) {
									for (MobileElement contentElement : contentList) {
										try {
											if (!contentElement.getAttribute("content-desc").equals("")||mobileElement.getAttribute("content-desc")!=null) {
											content=content+replace(contentElement.getAttribute("content-desc"));
											}
										} catch (Exception e) {
											continue;
										}

									}

								}
								driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
								Thread.sleep(1000);
								contentNowPageSource=driver.getPageSource();
							}while(!(contentLastPageSource.equals(contentNowPageSource))==true);
							if (content.equals("")) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+content+"</lv>"+"\n");
							}

							driver.navigate().back();
							ps.append("</Tln>"+"\n");
						}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
			}while(!(lastPageSource.equals(nowPageSource))==true);

			ps.append("</InfoTable>");
			ps.flush();
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(36, 0, "星标邮件信息提取完成！",writeProgress);
		} catch (Exception e) {
			Util.writeProgress(32, 1, "提取星标邮件信息出错！",writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 通讯录,全部遍历
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveAddressBookData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(37, 0, "正在提取通讯录信息...", writeProgress);
			driver.findElementByXPath("//*[@class='android.widget.TextView' and @text= '通讯录​']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QQMAIL/ADDRESSBOOKINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/QQ邮箱/通讯录</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/QQmail/Address Book Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NICKNAME\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">昵称</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Nickname</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"MAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮箱</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Mail Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsByXPath("//*[@class='android.widget.ListView']/android.widget.LinearLayout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("xpath", "//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[@index='0']", mobileElement)&&
								isElementExits("xpath", "//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[@index='1']", mobileElement)) {
								ps.append("<Tln>"+"\n");
								ps.append("<lv>"+replace(mobileElement.findElementByXPath("//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[@index='0']").getText())+"</lv>"+"\n");
								ps.append("<lv>"+replace(mobileElement.findElementByXPath("//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[@index='1']").getText())+"</lv>"+"\n");
								ps.append("</Tln>"+"\n");
							}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
			}while(!(lastPageSource.equals(nowPageSource))==true);
			ps.append("</InfoTable>");
			ps.flush();
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(45, 0, "通讯录信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(41, 1, "提取通讯录信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 群邮件，全部遍历
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveGroupMailData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(55, 0, "正在提取群邮件信息...", writeProgress);
			driver.findElementByXPath("//*[@class='android.widget.TextView' and @text= '群邮件​']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QQMAIL/GROUPMAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/QQ邮箱/群邮件</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/QQmail/Group Mail Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"MAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件信息与主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Mail Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName+"邮件内容");
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsByXPath("//android.widget.ListView[contains(@resource-id, 'com.tencent.androidqqmail:id')]/android.widget.RelativeLayout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("xpath", "//*[@class= 'android.view.View' and @index= '0']", mobileElement)) {
							if (mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").contains("带附件，")) {
								int index=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").indexOf("发来:");
								int lastindex=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").lastIndexOf("。");
								attachments.add(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").substring(index+3,lastindex));
							}
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc"))+"</lv>"+"\n");
	//						ps.append("</Tln>"+"\n");
							mobileElement.click();
							Thread.sleep(3000);
							String content="";
							String contentLastPageSource=null;
							String contentNowPageSource=driver.getPageSource();
							do {
								contentLastPageSource=contentNowPageSource;
								List<MobileElement> contentList=driver.findElementsByXPath("//android.view.View");
								if (contentList.size()>0) {
									for (MobileElement contentElement : contentList) {
										try {
											if (!contentElement.getAttribute("content-desc").equals("")||mobileElement.getAttribute("content-desc")!=null) {
											content=content+replace(contentElement.getAttribute("content-desc"));
											}
										} catch (Exception e) {
											continue;
										}
									}
								}
								driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
								Thread.sleep(1000);
								contentNowPageSource=driver.getPageSource();
							}while(!(contentLastPageSource.equals(contentNowPageSource))==true);
							if (content.equals("")) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+content+"</lv>"+"\n");
							}

							driver.navigate().back();
							ps.append("</Tln>"+"\n");
						}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
			}while(!(lastPageSource.equals(nowPageSource))==true);
//    	ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(63, 0, "群邮件信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(59, 1, "提取群邮件信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 草稿箱，全部遍历
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveDraftBoxData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(64, 0, "正在提取草稿箱信息...",writeProgress);
			driver.findElementByXPath("//*[@class='android.widget.TextView' and @text= '草稿箱​']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QQMAIL/DRAFTBOXINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/QQ邮箱/草稿箱</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/QQmail/Draft Box Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"MAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件信息与主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Mail Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();

			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsByXPath("//android.widget.ListView[contains(@resource-id, 'com.tencent.androidqqmail:id')]/android.widget.RelativeLayout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("xpath", "//*[@class= 'android.view.View' and @index= '0']", mobileElement)) {
							if (mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").contains("带附件，")) {
								int index=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").indexOf("发来:");
								int lastindex=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").lastIndexOf("。");
								attachments.add(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").substring(index+3,lastindex));
							}
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc"))+"</lv>"+"\n");
	//						ps.append("</Tln>"+"\n");
							mobileElement.click();
							Thread.sleep(3000);
							String content="";
							String contentLastPageSource=null;
							String contentNowPageSource=driver.getPageSource();
							do {
								contentLastPageSource=contentNowPageSource;
								//Screenshot(driver, dataName+"邮件内容");
								List<MobileElement> contentList=driver.findElementsByXPath("//android.view.View");
								if (contentList.size()>0) {
									for (MobileElement contentElement : contentList) {
										try {
											if (!contentElement.getAttribute("content-desc").equals("")||mobileElement.getAttribute("content-desc")!=null) {
											content=content+replace(contentElement.getAttribute("content-desc"));
											}
										} catch (Exception e) {
											continue;
										}

									}

								}
								driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
								Thread.sleep(1000);
								contentNowPageSource=driver.getPageSource();
							}while(!(contentLastPageSource.equals(contentNowPageSource))==true);
							if (content.equals("")) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+content+"</lv>"+"\n");
							}

							driver.navigate().back();
							ps.append("</Tln>"+"\n");
						}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
			}while(!(lastPageSource.equals(nowPageSource))==true);

//    	ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(72, 0, "草稿箱信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(68, 1, "提取草稿箱信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}
	}
	/**
	 * 已发送
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveSentData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(73, 0, "正在提取已发送信息...", writeProgress);
			driver.findElementByXPath("//*[@class='android.widget.TextView' and @text= '已发送​']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QQMAIL/SENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/QQ邮箱/已发送</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/QQmail/Sent Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"MAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件信息与主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Mail Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsByXPath("//android.widget.ListView[contains(@resource-id, 'com.tencent.androidqqmail:id')]/android.widget.RelativeLayout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("xpath", "//*[@class= 'android.view.View' and @index= '0']", mobileElement)) {
							if (mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").contains("带附件，")) {
								int index=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").indexOf("发来:");
								int lastindex=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").lastIndexOf("。");
								attachments.add(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").substring(index+3,lastindex));
							}
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc"))+"</lv>"+"\n");
	//						ps.append("</Tln>"+"\n");
							mobileElement.click();
							Thread.sleep(3000);
							String content="";
							String contentLastPageSource=null;
							String contentNowPageSource=driver.getPageSource();
							do {
								contentLastPageSource=contentNowPageSource;
								//Screenshot(driver, dataName+"邮件内容");
								List<MobileElement> contentList=driver.findElementsByXPath("//android.view.View");
								if (contentList.size()>0) {
									for (MobileElement contentElement : contentList) {
										try {
											if (!contentElement.getAttribute("content-desc").equals("")||mobileElement.getAttribute("content-desc")!=null) {
											content=content+replace(contentElement.getAttribute("content-desc"));
											}
										} catch (Exception e) {
											continue;
										}

									}

								}
								driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
								Thread.sleep(1000);
								contentNowPageSource=driver.getPageSource();
							}while(!(contentLastPageSource.equals(contentNowPageSource))==true);
							if (content.equals("")) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+content+"</lv>"+"\n");
							}
							driver.navigate().back();
							ps.append("</Tln>"+"\n");
						}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
			}while(!(lastPageSource.equals(nowPageSource))==true);

//    	ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(81, 0, "已发送信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(77, 1, "提取已发送信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}
	}
	/**
	 * 已删除
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveDeletedData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(82, 0, "正在提取已删除信息...",writeProgress);
			//******************************出错**************************
			// An element could not be located on the page using the given search parameters.
			driver.findElementByXPath("//*[@class='android.widget.TextView' and @text= '已删除​']").click();
			//**************************************************************
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QQMAIL/DELETEDINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/QQ邮箱/已删除</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/QQmail/Deleted Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"MAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件信息与主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Mail Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsByXPath("//android.widget.ListView[contains(@resource-id, 'com.tencent.androidqqmail:id')]/android.widget.RelativeLayout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("xpath", "//*[@class= 'android.view.View' and @index= '0']", mobileElement)) {
							if (mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").contains("带附件，")) {
								int index=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").indexOf("发来:");
								int lastindex=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").lastIndexOf("。");
								attachments.add(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").substring(index+3,lastindex));
							}
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc"))+"</lv>"+"\n");
	//						ps.append("</Tln>"+"\n");
							mobileElement.click();
							Thread.sleep(3000);
							String content="";
							String contentLastPageSource=null;
							String contentNowPageSource=driver.getPageSource();
							do {
								contentLastPageSource=contentNowPageSource;
								//Screenshot(driver, dataName+"邮件内容");
								List<MobileElement> contentList=driver.findElementsByXPath("//android.view.View");
								if (contentList.size()>0) {
									for (MobileElement contentElement : contentList) {

										try {
											if (!contentElement.getAttribute("content-desc").equals("")||mobileElement.getAttribute("content-desc")!=null) {
											content=content+replace(contentElement.getAttribute("content-desc"));
											}
										} catch (Exception e) {
											continue;
										}

									}

								}
								driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
								Thread.sleep(1000);
								contentNowPageSource=driver.getPageSource();
							}while(!(contentLastPageSource.equals(contentNowPageSource))==true);
							if (content.equals("")) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+content+"</lv>"+"\n");
							}
							driver.navigate().back();
							ps.append("</Tln>"+"\n");
						}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
			}while(!(lastPageSource.equals(nowPageSource))==true);

			ps.append("</InfoTable>");
			ps.flush();
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(90, 0, "已删除信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(86, 1, "提取已删除信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}
	}
	/**
	 * 垃圾箱
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveTrashCanData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(91, 0, "正在提取垃圾箱信息...", writeProgress);
			driver.findElementByXPath("//*[@class='android.widget.TextView' and @text= '垃圾箱​']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QQMAIL/TRASHCANINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/QQ邮箱/垃圾箱</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/QQmail/Trash Can Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"MAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件信息与主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Mail Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsByXPath("//android.widget.ListView[contains(@resource-id, 'com.tencent.androidqqmail:id')]/android.widget.RelativeLayout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("xpath", "//*[@class= 'android.view.View' and @index= '0']", mobileElement)) {
							if (mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").contains("带附件，")) {
								int index=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").indexOf("发来:");
								int lastindex=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").lastIndexOf("。");
								attachments.add(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").substring(index+3,lastindex));
							}
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc"))+"</lv>"+"\n");
	//						ps.append("</Tln>"+"\n");
							mobileElement.click();
							Thread.sleep(3000);
							String content="";
							String contentLastPageSource=null;
							String contentNowPageSource=driver.getPageSource();
							do {
								contentLastPageSource=contentNowPageSource;
								//Screenshot(driver, dataName+"邮件内容");
								List<MobileElement> contentList=driver.findElementsByXPath("//android.view.View");
								if (contentList.size()>0) {
									for (MobileElement contentElement : contentList) {
										try {
											if (!contentElement.getAttribute("content-desc").equals("")||mobileElement.getAttribute("content-desc")!=null) {
											content=content+replace(contentElement.getAttribute("content-desc"));
											}
										} catch (Exception e) {
											continue;
										}

									}

								}
								driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
								Thread.sleep(1000);
								contentNowPageSource=driver.getPageSource();
							}while(!(contentLastPageSource.equals(contentNowPageSource))==true);

							if (content.equals("")) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+content+"</lv>"+"\n");
							}
							driver.navigate().back();
							ps.append("</Tln>"+"\n");
						}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
			}while(!(lastPageSource.equals(nowPageSource))==true);

//    	ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(99, 0, "垃圾箱信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(95, 1, "提取垃圾箱信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	
	/**
	 * 重要联系人邮件
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveImportantContactMailData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(10, 0, "正在提取重要联系人邮件信息...", writeProgress);
			driver.findElementByXPath("//*[contains(@content-desc,'重要联系人')]").click();
//		driver.findElementByAccessibilityId("重要联系人发来的邮件").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QQMAIL/IMPORTANTCONTACTMAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/QQ邮箱/重要联系人邮箱</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/QQmail/Important Contact Mail Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"MAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件信息与主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Mail Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮件内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();

			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsByXPath("//android.widget.ListView[contains(@resource-id, 'com.tencent.androidqqmail:id')]/android.widget.RelativeLayout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("xpath", "//*[@class= 'android.view.View' and @index= '0']", mobileElement)) {
							if (mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").contains("带附件，")) {
								int index=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").indexOf("发来:");
								int lastindex=mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").lastIndexOf("。");
								attachments.add(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc").substring(index+3,lastindex));
							}
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementByXPath("//*[@class= 'android.view.View' and @index= '0']").getAttribute("content-desc"))+"</lv>"+"\n");
	//						ps.append("</Tln>"+"\n");
							mobileElement.click();
							Thread.sleep(3000);
							String content="";
							String contentLastPageSource=null;
							String contentNowPageSource=driver.getPageSource();
							do {
								contentLastPageSource=contentNowPageSource;
								//Screenshot(driver, dataName+"邮件内容");
								List<MobileElement> contentList=driver.findElementsByXPath("//android.view.View");
								if (contentList.size()>0) {
									for (MobileElement contentElement : contentList) {
										try {
											if (!contentElement.getAttribute("content-desc").equals("")||mobileElement.getAttribute("content-desc")!=null) {
											content=content+replace(contentElement.getAttribute("content-desc"));
											}
										} catch (Exception e) {
											continue;
										}

									}

								}
								driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
								Thread.sleep(1000);
								contentNowPageSource=driver.getPageSource();
							}while(!(contentLastPageSource.equals(contentNowPageSource))==true);

							if (content.equals("")) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+content+"</lv>"+"\n");
							}
							driver.navigate().back();
							ps.append("</Tln>"+"\n");
						}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
			}while(!(lastPageSource.equals(nowPageSource))==true);
//    	ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			Thread.sleep(5000);
			Util.writeProgress(18, 0, "重要联系人邮件信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(14, 1, "提取重要联系人邮件信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 重要联系人
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveImportantContactData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(19, 0, "正在提取重要联系人信息", writeProgress);
			driver.findElementByXPath("//*[@class='android.widget.ImageButton' and @index='2']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QQMAIL/IMPORTANTCONTACTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/QQ邮箱/重要联系人</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/QQmail/Important Contact Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NICKNAME\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">昵称</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Nickname</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"MAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">邮箱</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Mail Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsByXPath("//*[@class='android.widget.ListView']/android.widget.RelativeLayout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("xpath", "//android.widget.LinearLayout/android.widget.TextView[@index='0']", mobileElement)&&
								isElementExits("xpath", "//android.widget.LinearLayout/android.widget.TextView[@index='1']", mobileElement)) {
								ps.append("<Tln>"+"\n");
								ps.append("<lv>"+replace(mobileElement.findElementByXPath("//android.widget.LinearLayout/android.widget.TextView[@index='0']").getText())+"</lv>"+"\n");
								ps.append("<lv>"+replace(mobileElement.findElementByXPath("//android.widget.LinearLayout/android.widget.TextView[@index='1']").getText())+"</lv>"+"\n");
								ps.append("</Tln>"+"\n");
							}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
			}while(!(lastPageSource.equals(nowPageSource))==true);


//    	ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			driver.navigate().back();
			Thread.sleep(5000);
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(27, 0, "重要联系人信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(23, 1, "提取重要联系人信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}


	}
	/**
	 * 记事本
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveNotebookData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(46, 0, "正在提取记事本信息...", writeProgress);
			driver.findElementByXPath("//*[@class='android.widget.TextView' and @text='记事本​']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QQMAIL/NOTEBOOKINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/QQ邮箱/记事本</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/QQmail/Notebook Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"THEMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Theme Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TIMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">时间</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Time Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsByXPath("//androidx.recyclerview.widget.RecyclerView[contains(@resource-id, 'com.tencent.androidqqmail:id')]/android.widget.RelativeLayout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("xpath", "//android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='0']", mobileElement)&&
								isElementExits("xpath", "//android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='1']", mobileElement)&&
								isElementExits("xpath", "//android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='2']", mobileElement)) {
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementByXPath("//android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='0']").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementByXPath("//android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='2']").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementByXPath("//android.widget.RelativeLayout[@index='0']/android.widget.TextView[@index='1']").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");

						}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
			}while(!(lastPageSource.equals(nowPageSource))==true);

//    	ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			driver.navigate().back();
			Thread.sleep(5000);
			Util.writeProgress(54, 0, "记事本信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(50, 1, "提取记事本信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}

	/**
	 * 下载文件
	 * @param driver
	 * @throws Exception
	 */
	public void downloadFile(AndroidDriver driver, InputStream readProgress, OutputStream writeProgress) throws Exception {

		while (true){
			byte[] frameHeader=new byte[1];
			readProgress.read(frameHeader);
			byte[] frameLengeh=new byte[4];
			readProgress.read(frameLengeh);
			int length=Util.bytesToInt(frameLengeh);
			byte[] frameCommand=new byte[4];
			readProgress.read(frameCommand);
			int fileNameLength=length-4;
			if (fileNameLength>0){
				byte[] fileNameBytes=new byte[fileNameLength];
				readProgress.read(fileNameBytes);
				String fileName=new String(fileNameBytes, "UTF-8");
				downloadFileMethod(driver, fileName, writeProgress);
			}
			byte[] frameEnd=new byte[1];
			readProgress.read(frameEnd);
			if (frameCommand[0]==(byte)0x00&&frameCommand[1]==(byte)0x00&&frameCommand[2]==(byte)0x00&&frameCommand[3]==(byte)0x03){
				break;
			}
		}
	}

	/**
	 * 下载文件方法
	 * @param driver
	 * @param fileName
	 * @param writeProgress
	 * @throws Exception
	 */
	public void downloadFileMethod(AndroidDriver driver,String fileName,OutputStream writeProgress) throws Exception {

		try {
			Util.writeProgress(0, 0, "开始下载邮件附件", writeProgress);
			while (!isElementExits("xpath", "//*[@class='android.widget.TextView' and @text= '通讯录​']", driver)){
				driver.navigate().back();
				Thread.sleep(2000);
			}
			if (!isElementExits("xpath", "//*[@class='android.widget.TextView' and @text='搜索']", driver)) {
				driver.swipe(x/2, y/5, x/2, y*4/5, 1000);
				Thread.sleep(5000);
			}
			driver.findElementByXPath("//*[@class='android.widget.TextView' and @text='搜索']").click();
			Thread.sleep(5000);

			int flag=0;

			Util.writeProgress(50, 0, "正在搜索邮件", writeProgress);

			fileName=fileName.trim();

			driver.findElementByXPath("//*[@class='android.widget.EditText' and @index='1']").sendKeys(fileName);
			Thread.sleep(2000);

			driver.pressKeyCode(67);
			Thread.sleep(5000);
/*			//切换输入法输入删除
			String switchSougouIME="adb -s 127.0.0.1:7555 shell ime set com.sohu.inputmethod.sogou/.SogouIME";
			String switchMuMuIME="adb -s 127.0.0.1:7555 shell ime set com.netease.nemu_vinput.nemu/com.android.inputmethodcommon.SoftKeyboard";
			String switchAppiumIME="adb -s 127.0.0.1:7555 shell ime set io.appium.android.ime/.UnicodeIME";

			excuteAdbShell(switchSougouIME);
			Thread.sleep(2000);
			driver.pressKeyCode(112);
			Thread.sleep(2000);
			excuteAdbShell(switchAppiumIME);
			Thread.sleep(2000);*/


			if (isElementExits("xpath", "//*[@class='android.widget.TextView' and @text='在服务器上搜索更多邮件']", driver)) {
				flag=1;
				driver.findElementByXPath("//*[@class='android.widget.TextView' and @text='在服务器上搜索更多邮件']").click();
				Thread.sleep(3000);
				String lastPageSource=null;
				String nowPageSource=driver.getPageSource();
				do {
					 lastPageSource=nowPageSource;
					 List<MobileElement> mailList=driver.findElementsByXPath("//*[@class='androidx.recyclerview.widget.RecyclerView' and @index='2']/android.widget.LinearLayout");
					 for (MobileElement mobileElement : mailList) {
						if (isElementExits("xpath", "//*[@class='android.view.ViewGroup' and @index='1']", mobileElement)) {
							mobileElement.click();
							Thread.sleep(2000);

							String contentLastPageSource=null;
							String contentNowPageSource=driver.getPageSource();
							do {
								contentLastPageSource=contentNowPageSource;
								driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
								Thread.sleep(1000);
								contentNowPageSource=driver.getPageSource();
							}while(!(contentLastPageSource.equals(contentNowPageSource)));
							//810 1440  732 1251
							driver.tap(1,x*732/810,y*1251/1440,10);
							Thread.sleep(2000);
							if (isElementExits("xpath", "//*[@class='android.widget.TextView' and contains(@text,'保存')]", driver)){
								driver.findElementByXPath("//*[@class='android.widget.TextView' and contains(@text,'保存')]").click();
								Thread.sleep(2000);
								driver.findElementByXPath("//*[@class='android.widget.Button' and @text='保存​']").click();
								Thread.sleep(8000);
							}
							driver.navigate().back();

						}
					 }
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
				}while(!(lastPageSource.equals(nowPageSource))==true);

				driver.navigate().back();
				Thread.sleep(2000);

			}else {
				String lastPageSource=null;
				String nowPageSource=driver.getPageSource();
				do {

					lastPageSource=nowPageSource;
					List<MobileElement> mailList=driver.findElementsByXPath("//*[@class='androidx.recyclerview.widget.RecyclerView']/android.widget.LinearLayout");
					for (MobileElement mobileElement : mailList) {
						if (isElementExits("xpath", "//*[@class='android.view.ViewGroup' and @index='1']", mobileElement)) {
							mobileElement.click();
							Thread.sleep(2000);
							String contentLastPageSource=null;
							String contentNowPageSource=driver.getPageSource();

							do {
								contentLastPageSource=contentNowPageSource;
								driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
								contentNowPageSource=driver.getPageSource();

							} while (!(contentLastPageSource.equals(contentNowPageSource))==true);
							int x1=732/810;///810
							int y1=1251/1440;///1440
							driver.tap(1,x*732/810,y*1251/1440,10);
							Thread.sleep(2000);
							if (isElementExits("xpath", "//*[@class='android.widget.TextView' and contains(@text,'保存')]", driver)){
								driver.findElementByXPath("//*[@class='android.widget.TextView' and contains(@text,'保存')]").click();
								Thread.sleep(2000);
								driver.findElementByXPath("//*[@class='android.widget.Button' and @text='保存​']").click();
								Thread.sleep(10000);
							}

							driver.navigate().back();
							Thread.sleep(2000);
						}

					  }
					driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
					nowPageSource=driver.getPageSource();
				}while(!(lastPageSource.equals(nowPageSource)));
			}
			Util.writeProgress(100, 0, "附件下载完成", writeProgress);
		} catch (Exception e) {
			try {
				Util.writeProgress(50, 1, "下载附件出错", writeProgress);
			}catch (Exception e2){

			}
		} finally {
			try {
				Util.sendDownloadFileEndMessage(writeProgress);
			}catch (Exception e3){

			}
		}

	}

	/**
	 * 执行cmd命令
	 * @param s
	 * @throws Exception
	 */
	private void excuteAdbShell(String s) throws Exception {
		Runtime runtime = Runtime.getRuntime();
		runtime.exec(s);
	}
}
