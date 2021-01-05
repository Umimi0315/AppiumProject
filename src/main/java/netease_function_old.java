import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//import java.io.IOException;
//import org.apache.commons.io.FileUtils;
//import org.openqa.selenium.OutputType;

public class netease_function_old extends Function{
	public static ArrayList<String> mail=new ArrayList<String>();

	public netease_function_old(String dir, int x, int y, OutputStream writeProgress) {
		super(dir, x, y,writeProgress);
	}

/**
 * 收件箱，全部遍历
 * @param driver
 * @param dataName
 * @throws Exception
 */
	public void saveInboxData(AndroidDriver driver,String dataName) throws Exception {



		PrintStream ps=null;

		try {
			Util.writeProgress(2, 0, "正在提取收件箱信息...", writeProgress);
			if (isElementExits("id", "com.netease.mobimail:id/iv_mail_list_folder", driver)){
				driver.findElementById("com.netease.mobimail:id/iv_mail_list_folder").click();
				Thread.sleep(5000);
			}

			try {
				driver.findElementByXPath("//*[@resource-id='com.netease.mobimail:id/mail_folder_list_item_name' and @text= '收件箱']").click();
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println("找不到收件箱");
				return;
			}

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/NETEASE/INBOXINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/网易邮箱/收件箱</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Netease/Inbox Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TOORFROMINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">发送或接收方</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">To Or From Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUBJECTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Subject Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUMMARYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">概要</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Summary Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do{
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
				if (list.size()>0) {
					int count=0;
					for (MobileElement mobileElement : list) {
						count++;
						if (count==list.size()) {
							break;
						}
						if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement)) {
							String time="";
							if (mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
								continue;
							}else {
								time=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
								ps.append("<Tln>"+"\n");
								ps.append("<lv>"+replace(time)+"</lv>"+"\n");
							}
							String from=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
							ps.append("<lv>"+replace(from)+"</lv>"+"\n");
							String subject="";
							if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement)) {
								if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement)) {
									subject=mobileElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
								}else {
									subject="无";
								}
							}else {
								subject=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
							}
							ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement)) {
								ps.append("<lv>"+replace(mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}

							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement)) {
								//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
								attachments.add(subject);
							}
							mobileElement.click();
							Thread.sleep(5000);

							if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
								ps.append("</Tln>"+"\n");
								String lastPageSource2=null;
								String nowPageSource2=driver.getPageSource();
								do {
									lastPageSource2=nowPageSource2;
									//Screenshot(driver, dataName);
									List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");

									if (list2.size()>0) {
										for (MobileElement mobileElement2 : list2) {
											if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
													isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
												String time2="";
												if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
													continue;
												}else {
													time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
													ps.append("<Tln>"+"\n");
													ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
												}
												String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
												ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
												String subject2="";
												if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
													if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
														subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
													}else {
														subject2="无";
													}
												}else {
													subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
												}
												ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
													ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
												}else {
													ps.append("<lv>"+"无"+"</lv>"+"\n");
												}

												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
													//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
													attachments.add(subject);
												}
												mobileElement2.click();
												Thread.sleep(5000);
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
													Thread.sleep(5000);
													ps.append("</Tln>"+"\n");
											}
										}
									}
									driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
									Thread.sleep(1000);
									nowPageSource2=driver.getPageSource();
								} while (!(lastPageSource2.equals(nowPageSource2)));
								driver.navigate().back();
								Thread.sleep(5000);

							}else {
								String content="";
								String contentLastPageSource=null;
								String contentNowPageSource=driver.getPageSource();
								do {
									contentLastPageSource=contentNowPageSource;
									//Screenshot(driver, dataName+"邮件内容");

									List<MobileElement> contentList=driver.findElementsByXPath("//android.view.View");
									if (contentList.size()>0) {
										for (MobileElement contentElement : contentList) {
	//
											try {
												if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
								Thread.sleep(5000);
								ps.append("</Tln>"+"\n");
								}


						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
				}while(!(lastPageSource.equals(nowPageSource))==true);
			/////////////////////////////////////////////////////////////////
			List<MobileElement> lastList=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
			if (lastList.size()>0){
				MobileElement lastElement=lastList.get(lastList.size()-1);
				if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", lastElement)) {
					String time="";

					time=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
					ps.append("<Tln>"+"\n");
					ps.append("<lv>"+replace(time)+"</lv>"+"\n");

					String from=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
					ps.append("<lv>"+replace(from)+"</lv>"+"\n");
					String subject="";
					if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", lastElement)) {
						if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", lastElement)) {
							subject=lastElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
						}else {
							subject="无";
						}
					}else {
						subject=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
					}
					ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", lastElement)) {
						ps.append("<lv>"+replace(lastElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
					}else {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
					}

					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", lastElement)) {
						//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
						attachments.add(subject);
					}
					lastElement.click();
					Thread.sleep(5000);

					if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
						ps.append("</Tln>"+"\n");
						String lastPageSource2=null;
						String nowPageSource2=driver.getPageSource();
						do {
							lastPageSource2=nowPageSource2;
							//Screenshot(driver, dataName);
							List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");

							if (list2.size()>0) {
								for (MobileElement mobileElement2 : list2) {
									if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
											isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
										String time2="";
										if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
											continue;
										}else {
											time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
											ps.append("<Tln>"+"\n");
											ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
										}
										String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
										ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
										String subject2="";
										if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
											if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
												subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
											}else {
												subject2="无";
											}
										}else {
											subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
										}
										ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
											ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
										}else {
											ps.append("<lv>"+"无"+"</lv>"+"\n");
										}

										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
											//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
											attachments.add(subject);
										}
										mobileElement2.click();
										Thread.sleep(5000);
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
														if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
										Thread.sleep(5000);
										ps.append("</Tln>"+"\n");
									}
								}
							}
							driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
							Thread.sleep(1000);
							nowPageSource2=driver.getPageSource();
						} while (!(lastPageSource2.equals(nowPageSource2)));
						driver.navigate().back();
						Thread.sleep(5000);

					}else {
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
										if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
						Thread.sleep(5000);
						ps.append("</Tln>"+"\n");
					}

				}
			}

			///////////////////////////////////////////////////////
			ps.append("</InfoTable>");
			ps.flush();
			Util.writeProgress(11, 0, "收件箱信息提取完毕!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(6, 1, "提取收件箱信息出错！", writeProgress);
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
			Util.writeProgress(23, 0, "正在提取草稿箱信息...", writeProgress);
			if (isElementExits("id", "com.netease.mobimail:id/iv_mail_list_folder", driver)){
				driver.findElementById("com.netease.mobimail:id/iv_mail_list_folder").click();
				Thread.sleep(5000);
			}
			try {
				driver.findElementByXPath("//*[@resource-id='com.netease.mobimail:id/mail_folder_list_item_name' and @text= '草稿箱']").click();
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println("找不到草稿箱");
				return;
			}

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/NETEASE/DRAFTBOXINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/网易邮箱/草稿箱</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Netease/Draft Box Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TOORFROMINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">发送或接收方</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">To Or From Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUBJECTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Subject Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUMMARYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">概要</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Summary Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do{
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
				if (list.size()>0) {
					int count=0;
					for (MobileElement mobileElement : list) {
						count++;
						if (count==list.size()) {
							break;
						}
						if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement)) {
							String time="";
							if (mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
								continue;
							}else {
								time=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
								ps.append("<Tln>"+"\n");
								ps.append("<lv>"+replace(time)+"</lv>"+"\n");
							}
							String from=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
							ps.append("<lv>"+replace(from)+"</lv>"+"\n");
							String subject="";
							if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement)) {
								if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement)) {
									subject=mobileElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
								}else {
									subject="无";
								}
							}else {
								subject=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
							}
							ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement)) {
								ps.append("<lv>"+replace(mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}

							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement)) {
								//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
								attachments.add(subject);
							}
							mobileElement.click();
							Thread.sleep(5000);

							if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
								ps.append("</Tln>"+"\n");
								String lastPageSource2=null;
								String nowPageSource2=driver.getPageSource();
								do {
									lastPageSource2=nowPageSource2;
									//Screenshot(driver, dataName);
									List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
									if (list2.size()>0) {
										for (MobileElement mobileElement2 : list2) {
											if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
													isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
												String time2="";
												if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
													continue;
												}else {
													time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
													ps.append("<Tln>"+"\n");
													ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
												}
												String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
												ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
												String subject2="";
												if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
													if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
														subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
													}else {
														subject2="无";
													}
												}else {
													subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
												}
												ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
													ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
												}else {
													ps.append("<lv>"+"无"+"</lv>"+"\n");
												}

												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
													//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
													attachments.add(subject);
												}
												mobileElement2.click();
												Thread.sleep(5000);
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
													Thread.sleep(5000);
													ps.append("</Tln>"+"\n");
											}
										}
									}
									driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
									Thread.sleep(1000);
									nowPageSource2=driver.getPageSource();
								} while (!(lastPageSource2.equals(nowPageSource2)));
								driver.navigate().back();
								Thread.sleep(5000);
							}else {
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
												if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
								Thread.sleep(5000);
								ps.append("</Tln>"+"\n");
								}
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
				}while(!(lastPageSource.equals(nowPageSource))==true);
			///////////////////////////////////////////////////
			List<MobileElement> lastList=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
			if (lastList.size()>0){
				MobileElement lastElement=lastList.get(lastList.size()-1);
				if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", lastElement)) {
					String time="";

					time=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
					ps.append("<Tln>"+"\n");
					ps.append("<lv>"+replace(time)+"</lv>"+"\n");

					String from=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
					ps.append("<lv>"+replace(from)+"</lv>"+"\n");
					String subject="";
					if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", lastElement)) {
						if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", lastElement)) {
							subject=lastElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
						}else {
							subject="无";
						}
					}else {
						subject=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
					}
					ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", lastElement)) {
						ps.append("<lv>"+replace(lastElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
					}else {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
					}

					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", lastElement)) {
						//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
						attachments.add(subject);
					}
					lastElement.click();
					Thread.sleep(5000);

					if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
						ps.append("</Tln>"+"\n");
						String lastPageSource2=null;
						String nowPageSource2=driver.getPageSource();
						do {
							lastPageSource2=nowPageSource2;
							//Screenshot(driver, dataName);
							List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
							if (list2.size()>0) {
								for (MobileElement mobileElement2 : list2) {
									if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
											isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
										String time2="";
										if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
											continue;
										}else {
											time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
											ps.append("<Tln>"+"\n");
											ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
										}
										String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
										ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
										String subject2="";
										if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
											if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
												subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
											}else {
												subject2="无";
											}
										}else {
											subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
										}
										ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
											ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
										}else {
											ps.append("<lv>"+"无"+"</lv>"+"\n");
										}

										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
											//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
											attachments.add(subject);
										}
										mobileElement2.click();
										Thread.sleep(5000);
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
														if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
										Thread.sleep(5000);
										ps.append("</Tln>"+"\n");
									}
								}
							}
							driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
							Thread.sleep(1000);
							nowPageSource2=driver.getPageSource();
						} while (!(lastPageSource2.equals(nowPageSource2)));
						driver.navigate().back();
						Thread.sleep(5000);
					}else {
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
										if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
						Thread.sleep(5000);
						ps.append("</Tln>"+"\n");
					}
				}
			}

			/////////////////////////////////////////////////
			ps.append("</InfoTable>");
			ps.flush();
			Util.writeProgress(33, 0, "草稿箱信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(28, 1, "提取草稿箱信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}
	}
	/**
	 * 发件箱，全部遍历
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveOutboxData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(34, 0, "正在提取发件箱信息...", writeProgress);
			if (isElementExits("id", "com.netease.mobimail:id/iv_mail_list_folder", driver)){
				driver.findElementById("com.netease.mobimail:id/iv_mail_list_folder").click();
				Thread.sleep(5000);
			}
			try {
				driver.findElementByXPath("//*[@resource-id='com.netease.mobimail:id/mail_folder_list_item_name' and @text= '已发送']").click();
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println("找不到发件箱");
				return;
			}


			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/NETEASE/OUTBOXINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/网易邮箱/发件箱</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Netease/Outbox Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TOORFROMINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">发送或接收方</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">To Or From Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUBJECTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Subject Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUMMARYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">概要</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Summary Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do{
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
				if (list.size()>0) {
					int count=0;
					for (MobileElement mobileElement : list) {
						count++;
						if (count==list.size()) {
							break;
						}
						if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement)) {
							String time="";
							if (mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
								continue;
							}else {
								time=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
								ps.append("<Tln>"+"\n");
								ps.append("<lv>"+replace(time)+"</lv>"+"\n");
							}
							String from=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
							ps.append("<lv>"+replace(from)+"</lv>"+"\n");
							String subject="";
							if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement)) {
								if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement)) {
									subject=mobileElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
								}else {
									subject="无";
								}
							}else {
								subject=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
							}
							ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement)) {
								ps.append("<lv>"+replace(mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}

							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement)) {
								//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
								attachments.add(subject);
							}
							mobileElement.click();
							Thread.sleep(5000);

							if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
								ps.append("</Tln>"+"\n");
								String lastPageSource2=null;
								String nowPageSource2=driver.getPageSource();
								do {
									lastPageSource2=nowPageSource2;
									//Screenshot(driver, dataName);
									List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
									if (list2.size()>0) {
										for (MobileElement mobileElement2 : list2) {
											if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
													isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
												String time2="";
												if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
													continue;
												}else {
													time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
													ps.append("<Tln>"+"\n");
													ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
												}
												String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
												ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
												String subject2="";
												if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
													if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
														subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
													}else {
														subject2="无";
													}
												}else {
													subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
												}
												ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
													ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
												}else {
													ps.append("<lv>"+"无"+"</lv>"+"\n");
												}

												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
													//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
													attachments.add(subject);
												}
												mobileElement2.click();
												Thread.sleep(5000);
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
													Thread.sleep(5000);
													ps.append("</Tln>"+"\n");
											}
										}
									}
									driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
									Thread.sleep(1000);
									nowPageSource2=driver.getPageSource();
								} while (!(lastPageSource2.equals(nowPageSource2)));
								driver.navigate().back();
								Thread.sleep(5000);

							}else {
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
												if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
								Thread.sleep(5000);
								ps.append("</Tln>"+"\n");
								}
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
				}while(!(lastPageSource.equals(nowPageSource))==true);
			////////////////////////////////////////
			List<MobileElement> lastList=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
			if (lastList.size()>0){
				MobileElement lastElement=lastList.get(lastList.size()-1);
				if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", lastElement)) {
					String time="";
					time=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
					ps.append("<Tln>"+"\n");
					ps.append("<lv>"+replace(time)+"</lv>"+"\n");

					String from=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
					ps.append("<lv>"+replace(from)+"</lv>"+"\n");
					String subject="";
					if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", lastElement)) {
						if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", lastElement)) {
							subject=lastElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
						}else {
							subject="无";
						}
					}else {
						subject=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
					}
					ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", lastElement)) {
						ps.append("<lv>"+replace(lastElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
					}else {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
					}

					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", lastElement)) {
						//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
						attachments.add(subject);
					}
					lastElement.click();
					Thread.sleep(5000);

					if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
						ps.append("</Tln>"+"\n");
						String lastPageSource2=null;
						String nowPageSource2=driver.getPageSource();
						do {
							lastPageSource2=nowPageSource2;
							//Screenshot(driver, dataName);
							List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");

							if (list2.size()>0) {
								for (MobileElement mobileElement2 : list2) {
									if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
											isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
										String time2="";
										if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
											continue;
										}else {
											time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
											ps.append("<Tln>"+"\n");
											ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
										}
										String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
										ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
										String subject2="";
										if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
											if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
												subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
											}else {
												subject2="无";
											}
										}else {
											subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
										}
										ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
											ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
										}else {
											ps.append("<lv>"+"无"+"</lv>"+"\n");
										}

										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
											//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
											attachments.add(subject);
										}
										mobileElement2.click();
										Thread.sleep(5000);
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
														if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
										Thread.sleep(5000);
										ps.append("</Tln>"+"\n");
									}
								}
							}
							driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
							Thread.sleep(1000);
							nowPageSource2=driver.getPageSource();
						} while (!(lastPageSource2.equals(nowPageSource2)));
						driver.navigate().back();
						Thread.sleep(5000);
					}else {
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
										if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
						Thread.sleep(5000);
						ps.append("</Tln>"+"\n");
					}
				}
			}

			///////////////////////////////////////
			ps.append("</InfoTable>");
			ps.flush();
			Util.writeProgress(44, 0,"发件箱信息提取完成!",writeProgress);
		} catch (Exception e) {
			Util.writeProgress(39, 1, "提取发件箱信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}
	}
	/**
	 * 红旗邮件
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveRedFlagMailData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(12, 0, "正在提取红旗邮件信息...", writeProgress);
			if (isElementExits("id", "com.netease.mobimail:id/iv_mail_list_folder", driver)){
				driver.findElementById("com.netease.mobimail:id/iv_mail_list_folder").click();
				Thread.sleep(5000);
			}
			try {
				driver.findElementByXPath("//*[@resource-id='com.netease.mobimail:id/mail_folder_list_item_name' and @text= '红旗邮件']").click();
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println("找不到红旗邮件");
				return;
			}

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/NETEASE/REDFLAGMAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/网易邮箱/红旗邮件</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Netease/Red Flag Mail Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TOORFROMINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">发送或接收方</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">To Or From Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUBJECTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Subject Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUMMARYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">概要</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Summary Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do{
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
				if (list.size()>0) {
					int count=0;
					for (MobileElement mobileElement : list) {
						count++;
						if (count==list.size()) {
							break;
						}

						if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement)) {
							String time="";
							if (mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
								continue;
							}else {
								time=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
								ps.append("<Tln>"+"\n");
								ps.append("<lv>"+replace(time)+"</lv>"+"\n");
							}
							String from=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
							ps.append("<lv>"+replace(from)+"</lv>"+"\n");
							String subject="";
							if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement)) {
								if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement)) {
									subject=mobileElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
								}else {
									subject="无";
								}
							}else {
								subject=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
							}
							ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement)) {
								ps.append("<lv>"+replace(mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}

							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement)) {
								//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
								attachments.add(subject);
							}
							mobileElement.click();
							Thread.sleep(5000);

							if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
								ps.append("</Tln>"+"\n");
								String lastPageSource2=null;
								String nowPageSource2=driver.getPageSource();
								do {
									lastPageSource2=nowPageSource2;
									//Screenshot(driver, dataName);
									List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
									if (list2.size()>0) {
										for (MobileElement mobileElement2 : list2) {
											if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
													isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
												String time2="";
												if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
													continue;
												}else {
													time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
													ps.append("<Tln>"+"\n");
													ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
												}
												String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
												ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
												String subject2="";
												if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
													if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
														subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
													}else {
														subject2="无";
													}
												}else {
													subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
												}
												ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
													ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
												}else {
													ps.append("<lv>"+"无"+"</lv>"+"\n");
												}

												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
													//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
													attachments.add(subject);
												}
												mobileElement2.click();
												Thread.sleep(5000);
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
													Thread.sleep(5000);
													ps.append("</Tln>"+"\n");
											}
										}
									}
									driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
									Thread.sleep(1000);
									nowPageSource2=driver.getPageSource();
								} while (!(lastPageSource2.equals(nowPageSource2)));
								driver.navigate().back();
								Thread.sleep(5000);

							}else {
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
												if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
								Thread.sleep(5000);
								ps.append("</Tln>"+"\n");
								}
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
				}while(!(lastPageSource.equals(nowPageSource))==true);
			////////////////////////////////////////////
			List<MobileElement> lastList=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
			if (lastList.size()>0){
				MobileElement lastElement=lastList.get(lastList.size()-1);
				if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", lastElement)) {
					String time="";

					time=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
					ps.append("<Tln>"+"\n");
					ps.append("<lv>"+replace(time)+"</lv>"+"\n");

					String from=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
					ps.append("<lv>"+replace(from)+"</lv>"+"\n");
					String subject="";
					if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", lastElement)) {
						if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", lastElement)) {
							subject=lastElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
						}else {
							subject="无";
						}
					}else {
						subject=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
					}
					ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", lastElement)) {
						ps.append("<lv>"+replace(lastElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
					}else {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
					}

					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", lastElement)) {
						//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
						attachments.add(subject);
					}
					lastElement.click();
					Thread.sleep(5000);

					if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
						ps.append("</Tln>"+"\n");
						String lastPageSource2=null;
						String nowPageSource2=driver.getPageSource();
						do {
							lastPageSource2=nowPageSource2;
							//Screenshot(driver, dataName);
							List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");

							if (list2.size()>0) {
								for (MobileElement mobileElement2 : list2) {
									if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
											isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
										String time2="";
										if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
											continue;
										}else {
											time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
											ps.append("<Tln>"+"\n");
											ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
										}
										String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
										ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
										String subject2="";
										if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
											if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
												subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
											}else {
												subject2="无";
											}
										}else {
											subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
										}
										ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
											ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
										}else {
											ps.append("<lv>"+"无"+"</lv>"+"\n");
										}

										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
											//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
											attachments.add(subject);
										}
										mobileElement2.click();
										Thread.sleep(5000);
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
														if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
										Thread.sleep(5000);
										ps.append("</Tln>"+"\n");
									}
								}
							}
							driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
							Thread.sleep(1000);
							nowPageSource2=driver.getPageSource();
						} while (!(lastPageSource2.equals(nowPageSource2)));
						driver.navigate().back();
						Thread.sleep(5000);
					}else {
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
										if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
						Thread.sleep(5000);
						ps.append("</Tln>"+"\n");
					}
				}
			}

			////////////////////////////////////////////
			ps.append("</InfoTable>");
			ps.flush();
			Util.writeProgress(22, 0, "红旗邮件信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(17, 1, "提取红旗邮件出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}
	}
	/**
	 * 订阅邮件
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveSubscribeMailData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(45, 0, "正在提取订阅邮件信息...", writeProgress);
			if (isElementExits("id", "com.netease.mobimail:id/iv_mail_list_folder", driver)){
				driver.findElementById("com.netease.mobimail:id/iv_mail_list_folder").click();
				Thread.sleep(5000);
			}
			driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
			try {
				driver.findElementByXPath("//*[@resource-id='com.netease.mobimail:id/mail_folder_list_item_name' and @text= '订阅邮件']").click();
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println("找不到订阅邮件");
				return;
			}

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/NETEASE/SUBSCRIBEMAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/网易邮箱/订阅邮件</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Netease/Subscribe Mail Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TOORFROMINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">发送或接收方</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">To Or From Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUBJECTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Subject Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUMMARYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">概要</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Summary Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do{
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
				if (list.size()>0) {
					int count=0;
					for (MobileElement mobileElement : list) {
						count++;
						if (count==list.size()) {
							break;
						}

						if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement)) {
							String time="";
							if (mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
								continue;
							}else {
								time=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
								ps.append("<Tln>"+"\n");
								ps.append("<lv>"+replace(time)+"</lv>"+"\n");
							}
							String from=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
							ps.append("<lv>"+replace(from)+"</lv>"+"\n");
							String subject="";
							if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement)) {
								if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement)) {
									subject=mobileElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
								}else {
									subject="无";
								}
							}else {
								subject=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
							}
							ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement)) {
								ps.append("<lv>"+replace(mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}

							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement)) {
								//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
								attachments.add(subject);
							}
							mobileElement.click();
							Thread.sleep(5000);

							if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
								ps.append("</Tln>"+"\n");
								String lastPageSource2=null;
								String nowPageSource2=driver.getPageSource();
								do {
									lastPageSource2=nowPageSource2;
									//Screenshot(driver, dataName);
									List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
									if (list2.size()>0) {
										for (MobileElement mobileElement2 : list2) {
											if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
													isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
												String time2="";
												if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
													continue;
												}else {
													time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
													ps.append("<Tln>"+"\n");
													ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
												}
												String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
												ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
												String subject2="";
												if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
													if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
														subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
													}else {
														subject2="无";
													}
												}else {
													subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
												}
												ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
													ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
												}else {
													ps.append("<lv>"+"无"+"</lv>"+"\n");
												}

												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
													//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
													attachments.add(subject);
												}
												mobileElement2.click();
												Thread.sleep(5000);
												String content="";
												String contentLastPageSource=null;
												String contentNowPageSource=driver.getPageSource();
												do {
													contentLastPageSource=contentNowPageSource;
													//Screenshot(driver, dataName+"邮件内容");
													List<MobileElement> contentList=driver.findElementsByXPath("//android.view.View");
													if (contentList.size()>0) {
														for (MobileElement contentElement : contentList){
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
													Thread.sleep(5000);
													ps.append("</Tln>"+"\n");
											}
										}
									}
									driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
									Thread.sleep(1000);
									nowPageSource2=driver.getPageSource();
								} while (!(lastPageSource2.equals(nowPageSource2)));
								driver.navigate().back();
								Thread.sleep(5000);
							}else {
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
												if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
								Thread.sleep(5000);
								ps.append("</Tln>"+"\n");
								}
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
				}while(!(lastPageSource.equals(nowPageSource))==true);
			///////////////////////////////////////////////
			List<MobileElement> lastList=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
			if (lastList.size()>0){
				MobileElement lastElement=lastList.get(lastList.size()-1);
				if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", lastElement)) {
					String time="";

					time=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
					ps.append("<Tln>"+"\n");
					ps.append("<lv>"+replace(time)+"</lv>"+"\n");

					String from=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
					ps.append("<lv>"+replace(from)+"</lv>"+"\n");
					String subject="";
					if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", lastElement)) {
						if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", lastElement)) {
							subject=lastElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
						}else {
							subject="无";
						}
					}else {
						subject=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
					}
					ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", lastElement)) {
						ps.append("<lv>"+replace(lastElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
					}else {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
					}

					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", lastElement)) {
						//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
						attachments.add(subject);
					}
					lastElement.click();
					Thread.sleep(5000);

					if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
						ps.append("</Tln>"+"\n");
						String lastPageSource2=null;
						String nowPageSource2=driver.getPageSource();
						do {
							lastPageSource2=nowPageSource2;
							//Screenshot(driver, dataName);
							List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");

							if (list2.size()>0) {
								for (MobileElement mobileElement2 : list2) {
									if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
											isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
										String time2="";
										if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
											continue;
										}else {
											time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
											ps.append("<Tln>"+"\n");
											ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
										}
										String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
										ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
										String subject2="";
										if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
											if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
												subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
											}else {
												subject2="无";
											}
										}else {
											subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
										}
										ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
											ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
										}else {
											ps.append("<lv>"+"无"+"</lv>"+"\n");
										}

										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
											//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
											attachments.add(subject);
										}
										mobileElement2.click();
										Thread.sleep(5000);
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
														if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
										Thread.sleep(5000);
										ps.append("</Tln>"+"\n");
									}
								}
							}
							driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
							Thread.sleep(1000);
							nowPageSource2=driver.getPageSource();
						} while (!(lastPageSource2.equals(nowPageSource2)));
						driver.navigate().back();
						Thread.sleep(5000);
					}else {
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
										if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
						Thread.sleep(5000);
						ps.append("</Tln>"+"\n");
					}
				}
			}

			/////////////////////////////////////////////
			ps.append("</InfoTable>");
			ps.flush();
			Util.writeProgress(55, 0, "订阅邮件信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(50, 1, "提取订阅邮件信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}
	}
	/**
	 * 广告邮件
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveAdvertisementMailData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(56, 0, "正在提取广告邮件信息...", writeProgress);
			if (isElementExits("id", "com.netease.mobimail:id/iv_mail_list_folder", driver)){
				driver.findElementById("com.netease.mobimail:id/iv_mail_list_folder").click();
				Thread.sleep(5000);
			}
			try {
				driver.findElementByXPath("//*[@resource-id='com.netease.mobimail:id/mail_folder_list_item_name' and @text= '广告邮件']").click();
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println("找不到广告邮件");
				return;
			}

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/NETEASE/ADVERTISEMENTMAILINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/网易邮箱/广告邮件</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Netease/Advertisement Mail Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TOORFROMINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">发送或接收方</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">To Or From Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUBJECTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Subject Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUMMARYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">概要</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Summary Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();

			do{
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
				if (list.size()>0) {
					int count=0;
					for (MobileElement mobileElement : list) {
						count++;
						if (count==list.size()) {
							break;
						}
						if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement)) {
							String time="";
							if (mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
								continue;
							}else {
								time=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
								ps.append("<Tln>"+"\n");
								ps.append("<lv>"+replace(time)+"</lv>"+"\n");
							}
							String from=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
							ps.append("<lv>"+replace(from)+"</lv>"+"\n");
							String subject="";
							if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement)) {
								if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement)) {
									subject=mobileElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
								}else {
									subject="无";
								}
							}else {
								subject=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
							}
							ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement)) {
								ps.append("<lv>"+replace(mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}

							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement)) {
								//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
								attachments.add(subject);
							}
							mobileElement.click();
							Thread.sleep(5000);

							if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
								ps.append("</Tln>"+"\n");
								String lastPageSource2=null;
								String nowPageSource2=driver.getPageSource();
								do {
									lastPageSource2=nowPageSource2;
									//Screenshot(driver, dataName);
									List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
									if (list2.size()>0) {
										for (MobileElement mobileElement2 : list2) {
											if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
													isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
												String time2="";
												if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
													continue;
												}else {
													time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
													ps.append("<Tln>"+"\n");
													ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
												}
												String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
												ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
												String subject2="";
												if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
													if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
														subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
													}else {
														subject2="无";
													}
												}else {
													subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
												}
												ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
													ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
												}else {
													ps.append("<lv>"+"无"+"</lv>"+"\n");
												}

												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
													//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
													attachments.add(subject);
												}
												mobileElement2.click();
												Thread.sleep(5000);
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
													Thread.sleep(5000);
													ps.append("</Tln>"+"\n");
											}
										}
									}
									driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
									Thread.sleep(1000);
									nowPageSource2=driver.getPageSource();
								} while (!(lastPageSource2.equals(nowPageSource2)));
								driver.navigate().back();
								Thread.sleep(5000);
							}else {
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
												if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
								Thread.sleep(5000);
								ps.append("</Tln>"+"\n");
								}
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
				}while(!(lastPageSource.equals(nowPageSource))==true);
			/////////////////////////////////////////
			List<MobileElement> lastList=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
			if (lastList.size()>0){
				MobileElement lastElement=lastList.get(lastList.size()-1);
				if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", lastElement)) {
					String time="";
					time=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
					ps.append("<Tln>"+"\n");
					ps.append("<lv>"+replace(time)+"</lv>"+"\n");

					String from=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
					ps.append("<lv>"+replace(from)+"</lv>"+"\n");
					String subject="";
					if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", lastElement)) {
						if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", lastElement)) {
							subject=lastElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
						}else {
							subject="无";
						}
					}else {
						subject=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
					}
					ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", lastElement)) {
						ps.append("<lv>"+replace(lastElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
					}else {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
					}

					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", lastElement)) {
						//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
						attachments.add(subject);
					}
					lastElement.click();
					Thread.sleep(5000);

					if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
						ps.append("</Tln>"+"\n");
						String lastPageSource2=null;
						String nowPageSource2=driver.getPageSource();
						do {
							lastPageSource2=nowPageSource2;
							//Screenshot(driver, dataName);
							List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
							if (list2.size()>0) {
								for (MobileElement mobileElement2 : list2) {
									if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
											isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
										String time2="";
										if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
											continue;
										}else {
											time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
											ps.append("<Tln>"+"\n");
											ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
										}
										String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
										ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
										String subject2="";
										if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
											if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
												subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
											}else {
												subject2="无";
											}
										}else {
											subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
										}
										ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
											ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
										}else {
											ps.append("<lv>"+"无"+"</lv>"+"\n");
										}

										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
											//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
											attachments.add(subject);
										}
										mobileElement2.click();
										Thread.sleep(5000);
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
														if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
										Thread.sleep(5000);
										ps.append("</Tln>"+"\n");
									}
								}
							}
							driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
							Thread.sleep(1000);
							nowPageSource2=driver.getPageSource();
						} while (!(lastPageSource2.equals(nowPageSource2)));
						driver.navigate().back();
						Thread.sleep(5000);
					}else {
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
										if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
						Thread.sleep(5000);
						ps.append("</Tln>"+"\n");
					}
				}
			}

			////////////////////////////
			ps.append("</InfoTable>");
			ps.flush();
			Util.writeProgress(66, 0, "广告邮件信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(61, 1, "提取广告邮件信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}
	}
	/**
	 * 垃圾邮件
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveSpamData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(67, 0, "正在提取垃圾邮件信息...", writeProgress);
			if (isElementExits("id", "com.netease.mobimail:id/iv_mail_list_folder", driver)){
				driver.findElementById("com.netease.mobimail:id/iv_mail_list_folder").click();
				Thread.sleep(5000);
			}
			try {
				driver.findElementByXPath("//*[@resource-id='com.netease.mobimail:id/mail_folder_list_item_name' and @text= '垃圾邮件']").click();
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println("找不到垃圾邮件");
				return;
			}

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/NETEASE/SPAMINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/网易邮箱/垃圾邮件</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Netease/Spam Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TOORFROMINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">发送或接收方</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">To Or From Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUBJECTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Subject Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUMMARYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">概要</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Summary Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do{
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
				if (list.size()>0) {
					int count=0;
					for (MobileElement mobileElement : list) {
						count++;
						if (count==list.size()) {
							break;
						}
						if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement)) {
							String time="";
							if (mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
								continue;
							}else {
								time=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
								ps.append("<Tln>"+"\n");
								ps.append("<lv>"+replace(time)+"</lv>"+"\n");
							}
							String from=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
							ps.append("<lv>"+replace(from)+"</lv>"+"\n");
							String subject="";
							if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement)) {
								if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement)) {
									subject=mobileElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
								}else {
									subject="无";
								}
							}else {
								subject=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
							}
							ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement)) {
								ps.append("<lv>"+replace(mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}

							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement)) {
								//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
								attachments.add(subject);
							}
							mobileElement.click();
							Thread.sleep(5000);

							if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
								ps.append("</Tln>"+"\n");
								String lastPageSource2=null;
								String nowPageSource2=driver.getPageSource();
								do {
									lastPageSource2=nowPageSource2;
									//Screenshot(driver, dataName);
									List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
									if (list2.size()>0) {
										for (MobileElement mobileElement2 : list2) {
											if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
													isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
												String time2="";
												if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
													continue;
												}else {
													time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
													ps.append("<Tln>"+"\n");
													ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
												}
												String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
												ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
												String subject2="";
												if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
													if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
														subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
													}else {
														subject2="无";
													}
												}else {
													subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
												}
												ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
													ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
												}else {
													ps.append("<lv>"+"无"+"</lv>"+"\n");
												}
												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
													//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
													attachments.add(subject);
												}
												mobileElement2.click();
												Thread.sleep(5000);
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
													Thread.sleep(5000);
													ps.append("</Tln>"+"\n");
											}
										}
									}
									driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
									Thread.sleep(1000);
									nowPageSource2=driver.getPageSource();
								} while (!(lastPageSource2.equals(nowPageSource2)));
								driver.navigate().back();
								Thread.sleep(5000);
							}else {
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
												if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
								Thread.sleep(5000);
								ps.append("</Tln>"+"\n");
								}
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
				}while(!(lastPageSource.equals(nowPageSource))==true);
			////////////////////////////////////////////
			List<MobileElement> lastList=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
			if (lastList.size()>0){
				MobileElement lastElement=lastList.get(lastList.size()-1);
				if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", lastElement)) {
					String time="";
					time=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
					ps.append("<Tln>"+"\n");
					ps.append("<lv>"+replace(time)+"</lv>"+"\n");

					String from=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
					ps.append("<lv>"+replace(from)+"</lv>"+"\n");
					String subject="";
					if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", lastElement)) {
						if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", lastElement)) {
							subject=lastElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
						}else {
							subject="无";
						}
					}else {
						subject=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
					}
					ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", lastElement)) {
						ps.append("<lv>"+replace(lastElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
					}else {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
					}

					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", lastElement)) {
						//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
						attachments.add(subject);
					}
					lastElement.click();
					Thread.sleep(5000);

					if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
						ps.append("</Tln>"+"\n");
						String lastPageSource2=null;
						String nowPageSource2=driver.getPageSource();
						do {
							lastPageSource2=nowPageSource2;
							//Screenshot(driver, dataName);
							List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
							if (list2.size()>0) {
								for (MobileElement mobileElement2 : list2) {
									if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
											isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
										String time2="";
										if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
											continue;
										}else {
											time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
											ps.append("<Tln>"+"\n");
											ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
										}
										String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
										ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
										String subject2="";
										if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
											if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
												subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
											}else {
												subject2="无";
											}
										}else {
											subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
										}
										ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
											ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
										}else {
											ps.append("<lv>"+"无"+"</lv>"+"\n");
										}

										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
											//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
											attachments.add(subject);
										}
										mobileElement2.click();
										Thread.sleep(5000);
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
														if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
										Thread.sleep(5000);
										ps.append("</Tln>"+"\n");
									}
								}
							}
							driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
							Thread.sleep(1000);
							nowPageSource2=driver.getPageSource();
						} while (!(lastPageSource2.equals(nowPageSource2)));
						driver.navigate().back();
						Thread.sleep(5000);
					}else {
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
										if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
						Thread.sleep(5000);
						ps.append("</Tln>"+"\n");
					}
				}
			}

			////////////////////////////////////////////////////////
			ps.append("</InfoTable>");
			ps.flush();
			Util.writeProgress(77, 0, "垃圾邮件信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(72, 1, "提取垃圾邮件信息出错！", writeProgress);
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
			Util.writeProgress(78, 0, "正在提取已删除信息...", writeProgress);
			if (isElementExits("id", "com.netease.mobimail:id/iv_mail_list_folder", driver)){
				driver.findElementById("com.netease.mobimail:id/iv_mail_list_folder").click();
				Thread.sleep(5000);
			}
			try {
				driver.findElementByXPath("//*[@resource-id='com.netease.mobimail:id/mail_folder_list_item_name' and @text= '已删除']").click();
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println("找不到已删除");
				return;
			}

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/NETEASE/DELETEDINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/网易邮箱/已删除</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Netease/Deleted Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"TOORFROMINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">发送或接收方</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">To Or From Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUBJECTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">主题</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Subject Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SUMMARYINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">概要</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Summary Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"CONTENTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">内容</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Content Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do{
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
				if (list.size()>0) {
					int count=0;
					for (MobileElement mobileElement : list) {
						count++;
						if (count==list.size()) {
							break;
						}
						if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement)) {
							String time="";
							if (mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
								continue;
							}else {
								time=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
								ps.append("<Tln>"+"\n");
								ps.append("<lv>"+replace(time)+"</lv>"+"\n");
							}
							String from=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
							ps.append("<lv>"+replace(from)+"</lv>"+"\n");
							String subject="";
							if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement)) {
								if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement)) {
									subject=mobileElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
								}else {
									subject="无";
								}
							}else {
								subject=mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
							}
							ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement)) {
								ps.append("<lv>"+replace(mobileElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
							}else {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
							}

							if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement)) {
								//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
								attachments.add(subject);
							}
							mobileElement.click();
							Thread.sleep(5000);

							if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
								ps.append("<lv>"+"无"+"</lv>"+"\n");
								ps.append("</Tln>"+"\n");
								String lastPageSource2=null;
								String nowPageSource2=driver.getPageSource();
								do {
									lastPageSource2=nowPageSource2;
									//Screenshot(driver, dataName);
									List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
									if (list2.size()>0) {
										for (MobileElement mobileElement2 : list2) {
											if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
													isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
												String time2="";
												if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
													continue;
												}else {
													time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
													ps.append("<Tln>"+"\n");
													ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
												}
												String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
												ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
												String subject2="";
												if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
													if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
														subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
													}else {
														subject2="无";
													}
												}else {
													subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
												}
												ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
													ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
												}else {
													ps.append("<lv>"+"无"+"</lv>"+"\n");
												}

												if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
													//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
													attachments.add(subject);
												}
												mobileElement2.click();
												Thread.sleep(5000);
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
													Thread.sleep(5000);
													ps.append("</Tln>"+"\n");
											}
										}
									}
									driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
									Thread.sleep(1000);
									nowPageSource2=driver.getPageSource();
								} while (!(lastPageSource2.equals(nowPageSource2)));
								driver.navigate().back();
								Thread.sleep(5000);
							}else {
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
												if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
								Thread.sleep(5000);
								ps.append("</Tln>"+"\n");
								}
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
				}while(!(lastPageSource.equals(nowPageSource))==true);
			//////////////////////////////////////////
			List<MobileElement> lastList=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");
			if (lastList.size()>0){
				MobileElement lastElement=lastList.get(lastList.size()-1);
				if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", lastElement)) {
					String time="";
					time=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
					ps.append("<Tln>"+"\n");
					ps.append("<lv>"+replace(time)+"</lv>"+"\n");

					String from=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
					ps.append("<lv>"+replace(from)+"</lv>"+"\n");
					String subject="";
					if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", lastElement)) {
						if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", lastElement)) {
							subject=lastElement.findElementById("com.netease.mobimail:id/subject_and_session").getText();
						}else {
							subject="无";
						}
					}else {
						subject=lastElement.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
					}
					ps.append("<lv>"+replace(subject)+"</lv>"+"\n");
					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", lastElement)) {
						ps.append("<lv>"+replace(lastElement.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
					}else {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
					}
					if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", lastElement)) {
						//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
						attachments.add(subject);
					}
					lastElement.click();
					Thread.sleep(5000);

					if (isElementExits("id", "com.netease.mobimail:id/lv_mail_list", driver)) {
						ps.append("<lv>"+"无"+"</lv>"+"\n");
						ps.append("</Tln>"+"\n");
						String lastPageSource2=null;
						String nowPageSource2=driver.getPageSource();
						do {
							lastPageSource2=nowPageSource2;
							//Screenshot(driver, dataName);
							List<MobileElement> list2=driver.findElementsById("com.netease.mobimail:id/mail_list_item_content");

							if (list2.size()>0) {
								for (MobileElement mobileElement2 : list2) {
									if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_time", mobileElement2)&&
											isElementExits("id", "com.netease.mobimail:id/mail_list_item_from", mobileElement2)) {
										String time2="";
										if (mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText().equals("广告")) {
											continue;
										}else {
											time2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_time").getText();
											ps.append("<Tln>"+"\n");
											ps.append("<lv>"+replace(time2)+"</lv>"+"\n");
										}
										String from2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_from").getText();
										ps.append("<lv>"+replace(from2)+"</lv>"+"\n");
										String subject2="";
										if (!isElementExits("id", "com.netease.mobimail:id/mail_list_item_subject", mobileElement2)) {
											if (isElementExits("id", "com.netease.mobimail:id/subject_and_session", mobileElement2)) {
												subject2=mobileElement2.findElementById("com.netease.mobimail:id/subject_and_session").getText();
											}else {
												subject2="无";
											}
										}else {
											subject2=mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_subject").getText();
										}
										ps.append("<lv>"+replace(subject2)+"</lv>"+"\n");
										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_summary", mobileElement2)) {
											ps.append("<lv>"+replace(mobileElement2.findElementById("com.netease.mobimail:id/mail_list_item_summary").getText())+"</lv>"+"\n");
										}else {
											ps.append("<lv>"+"无"+"</lv>"+"\n");
										}

										if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mobileElement2)) {
											//mail.add("时间为:"+time+",通信对象为:"+from+"主题为:"+"\""+subject+" \"");
											attachments.add(subject);
										}
										mobileElement2.click();
										Thread.sleep(5000);
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
														if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
										Thread.sleep(5000);
										ps.append("</Tln>"+"\n");
									}
								}
							}
							driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
							Thread.sleep(1000);
							nowPageSource2=driver.getPageSource();
						} while (!(lastPageSource2.equals(nowPageSource2)));
						driver.navigate().back();
						Thread.sleep(5000);
					}else {
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
										if (!contentElement.getAttribute("content-desc").equals("")||contentElement.getAttribute("content-desc")!=null) {
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
						Thread.sleep(5000);
						ps.append("</Tln>"+"\n");
					}
				}
			}

			/////////////////////////////////////////
			ps.append("</InfoTable>");
			ps.flush();
			Util.writeProgress(88, 0, "已删除信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(83, 1, "提取已删除信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}
	}
	/**
	 * 通讯录
	 * @param driver
	 * @param dataName
	 * @throws Exception 
	 */
	public void saveContactData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(89, 0, "正在提取通讯录信息...", writeProgress);
			try {
				driver.findElementById("com.netease.mobimail:id/tab_contacts").click();
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println("找不到通讯录");
				return;
			}

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/NETEASE/CONTACTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/网易邮箱/通讯录</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Netease/Contact Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"NICKNAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">昵称</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Nickname Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"ACCOUNTINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">账号</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Account Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			if (isElementExits("xpath", "//*[@resource-id='com.netease.mobimail:id/contact_header_title' and @text='合并联系人']", driver)) {
				driver.findElementByXPath("//*[@resource-id='com.netease.mobimail:id/contact_header_title' and @text='合并联系人']").click();
				String lastMergePageSource=null;
				String nowMergePageSource=driver.getPageSource();
				do {
					lastMergePageSource=nowMergePageSource;
					//Screenshot(driver, dataName);
					List<MobileElement> mergeList=driver.findElementsByXPath("//*[@resource-id='com.netease.mobimail:id/before_merge_container']/android.widget.LinearLayout");
					if (mergeList.size()>0) {
						for (MobileElement mobileElement : mergeList) {
							if (isElementExits("id", "com.netease.mobimail:id/title_tv", mobileElement)&&
									isElementExits("id", "com.netease.mobimail:id/summary_tv", mobileElement)) {
								ps.append("<Tln>"+"\n");
								ps.append("<lv>"+replace(mobileElement.findElementById("com.netease.mobimail:id/title_tv").getText())+"</lv>"+"\n");
								ps.append("<lv>"+replace(mobileElement.findElementById("com.netease.mobimail:id/summary_tv").getText())+"</lv>"+"\n");
								ps.append("</Tln>"+"\n");
							}
						}
					}
					driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
					Thread.sleep(1000);
					nowMergePageSource=driver.getPageSource();
				} while (!lastMergePageSource.equals(nowMergePageSource));
				driver.navigate().back();
				Thread.sleep(5000);
			}
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.netease.mobimail:id/contact_list_item_detail");
				if (list.size()>0) {
					int count=0;
					for (MobileElement mobileElement : list) {
						count++;
						if(count>=(list.size()-1)) {
							break;
						}
						mobileElement.click();
						Thread.sleep(5000);
						//Screenshot(driver, dataName);
						if (isElementExits("id", "com.netease.mobimail:id/tv_contact_name", driver)&&
								isElementExits("id", "com.netease.mobimail:id/content_tv", driver)) {
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(driver.findElementById("com.netease.mobimail:id/tv_contact_name").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(driver.findElementById("com.netease.mobimail:id/content_tv").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						}
						driver.navigate().back();
						Thread.sleep(5000);
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();

			} while (!lastPageSource.equals(nowPageSource));
			/////////////////////////////////////
			List<MobileElement> lastList=driver.findElementsById("com.netease.mobimail:id/contact_list_item_detail");
			if (lastList.size()>0){
				if (lastList.size()>1){
					MobileElement lastSecondElement=lastList.get(lastList.size()-2);
					lastSecondElement.click();
					Thread.sleep(5000);
					//Screenshot(driver, dataName);
					if (isElementExits("id", "com.netease.mobimail:id/tv_contact_name", driver)&&
							isElementExits("id", "com.netease.mobimail:id/content_tv", driver)) {
						ps.append("<Tln>" + "\n");
						ps.append("<lv>" + replace(driver.findElementById("com.netease.mobimail:id/tv_contact_name").getText()) + "</lv>" + "\n");
						ps.append("<lv>" + replace(driver.findElementById("com.netease.mobimail:id/content_tv").getText()) + "</lv>" + "\n");
						ps.append("</Tln>" + "\n");
					}
					driver.navigate().back();
					Thread.sleep(5000);
				}

				List<MobileElement> lastList2=driver.findElementsById("com.netease.mobimail:id/contact_list_item_detail");
				MobileElement lastElement=lastList2.get(lastList2.size()-1);
				lastElement.click();
				Thread.sleep(5000);
				//Screenshot(driver, dataName);
				if (isElementExits("id", "com.netease.mobimail:id/tv_contact_name", driver)&&
						isElementExits("id", "com.netease.mobimail:id/content_tv", driver)) {
					ps.append("<Tln>"+"\n");
					ps.append("<lv>"+replace(driver.findElementById("com.netease.mobimail:id/tv_contact_name").getText())+"</lv>"+"\n");
					ps.append("<lv>"+replace(driver.findElementById("com.netease.mobimail:id/content_tv").getText())+"</lv>"+"\n");
					ps.append("</Tln>"+"\n");
				}
				driver.navigate().back();
				Thread.sleep(5000);
			}

			///////////////////////////////////////
			Util.writeProgress(99, 0, "通讯录信息提取完成!", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(94, 1, "提取通讯录信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}
	}

	/**
	 * 下载邮件
	 * @param driver
	 * @param readProgress
	 * @param writeProgress
	 * @throws Exception
	 */
	public void downloadFile(AndroidDriver driver, InputStream readProgress,OutputStream writeProgress) throws Exception {

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
	 * 下载邮件方法
	 * @param driver
	 * @param fileName
	 * @param writeProgress
	 * @throws Exception
	 */
	public void downloadFileMethod(AndroidDriver driver,String fileName,OutputStream writeProgress) {

		try {
			Util.writeProgress(0, 0, "开始下载附件...", writeProgress);
			while (!isElementExits("id", "com.netease.mobimail:id/tab_mail", driver)) {
				driver.navigate().back();
				Thread.sleep(2000);
			}

			driver.findElementById("com.netease.mobimail:id/tab_mail").click();
			Thread.sleep(2000);

			Util.writeProgress(25, 0, "正在搜索邮件", writeProgress);
			driver.findElementById("com.netease.mobimail:id/iv_mail_list_search").click();
			Thread.sleep(2000);

			driver.findElementById("com.netease.mobimail:id/et_mail_search").sendKeys(fileName);
			Thread.sleep(3000);
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			Util.writeProgress(50,0,"正在下载邮件附件", writeProgress);
			do {
				lastPageSource=nowPageSource;
				List<MobileElement> mailList=driver.findElementsByXPath("//*[@resource-id='com.netease.mobimail:id/lv_mail_list']/android.widget.FrameLayout");
				if (mailList.size()>0) {
					for (MobileElement mailElement : mailList) {
						if (isElementExits("id", "com.netease.mobimail:id/mail_list_item_attach", mailElement)) {
							mailElement.click();
							Thread.sleep(2000);
							String lastContentPageSource=null;
							String nowContentPageSource=driver.getPageSource();
							do {
								lastContentPageSource=nowContentPageSource;
								List<MobileElement> attachList=driver.findElementsById("com.netease.mobimail:id/attachment_detail");
								if (attachList.size()>0) {
									for (MobileElement attachElement : attachList) {
										if ("已保存".equals(attachElement.findElementById("com.netease.mobimail:id/attachment_filesize").getText()))
										{
											continue;
										}
										attachElement.findElementById("com.netease.mobimail:id/attachment_more").click();
										Thread.sleep(2000);
										driver.findElementById("com.netease.mobimail:id/file_operate_save").click();
										Thread.sleep(2000);
										driver.findElementById("com.netease.mobimail:id/tv_done").click();
										Thread.sleep(10000);
									}
								}
								driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
								Thread.sleep(1000);
								nowContentPageSource=driver.getPageSource();
							} while (!lastContentPageSource.equals(nowContentPageSource));
							driver.navigate().back();
							Thread.sleep(2000);
						}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(1000);
				nowPageSource=driver.getPageSource();
			} while (!lastPageSource.equals(nowPageSource));

			Util.writeProgress(100, 0, "邮件附件下载完成", writeProgress);

		} catch (Exception e) {
			try {
				Util.writeProgress(50, 1, "下载邮件附件出错", writeProgress);
			} catch (Exception exception) {

			}
		} finally {
			try {
				Util.sendDownloadFileEndMessage(writeProgress);
			} catch (Exception exception) {

			}
		}
	}

}
