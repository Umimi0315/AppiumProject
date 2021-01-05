import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Point;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Qcloud_function extends Function{
	public Qcloud_function(String dir, int x, int y, OutputStream writeProgress)
	{
		super(dir, x, y,writeProgress);
		pathMap=new HashMap<String, List<Object>>();
	}
	/**
	 * 文档
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveDocumentationData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.qq.qcloud:id/search_entrance_fl']");
		basePath.add("//*[@resource-id='com.qq.qcloud:id/search_lable_text' and @text='文档']");
		String baseName="腾讯微云-文档-";

		PrintStream ps=null;

		try {
			Util.writeProgress(2, 0, "正在提取文档信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id='com.qq.qcloud:id/search_lable_text' and @text='文档']").click();
			Thread.sleep(2000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QCLOUD/DOCUMENTATIONINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/腾讯微云/文档</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Qcloud/Documentation Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEANDFILESIZEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期与文件大小</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date And File Size Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.qq.qcloud:id/listview_item_background");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.qq.qcloud:id/file_name", mobileElement)&&
							isElementExits("id", "com.qq.qcloud:id/file_modify_time", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.qq.qcloud:id/file_name").getText()+" "+mobileElement.findElementById("com.qq.qcloud:id/file_modify_time").getText();
							//map中的key
							String fileName=baseName+itemName;

							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}

							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
							filePath.add(mobileElement.getCenter());

							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.qq.qcloud:id/file_name").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.qq.qcloud:id/file_modify_time").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();

				basePath.add("swipe");

			}while(!(lastPageSource.equals(nowPageSource))==true);
			ps.append("</InfoTable>");
			ps.flush();
			driver.findElementById("com.qq.qcloud:id/back_to_lable_feak").click();
			Thread.sleep(5000);
			Util.writeProgress(16, 0, "文档信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(8, 1, "提取文档信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}
	}
	/**
	 * 图片
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void savePictureData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.qq.qcloud:id/search_entrance_fl']");
		basePath.add("//*[@resource-id='com.qq.qcloud:id/search_lable_text' and @text='图片']");
		String baseName="腾讯微云-图片-";

		PrintStream ps=null;

		try {
			Util.writeProgress(17, 0, "正在提取图片信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id='com.qq.qcloud:id/search_lable_text' and @text='图片']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QCLOUD/PICTUREINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/腾讯微云/图片</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Qcloud/Picture Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEANDFILESIZEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期与文件大小</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date And File Size Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.qq.qcloud:id/listview_item_background");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.qq.qcloud:id/file_name", mobileElement)&&
							isElementExits("id", "com.qq.qcloud:id/file_modify_time", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.qq.qcloud:id/file_name").getText()+" "+mobileElement.findElementById("com.qq.qcloud:id/file_modify_time").getText();
							//map中的key
							String fileName=baseName+itemName;

							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}

							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
							filePath.add(mobileElement.getCenter());

							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.qq.qcloud:id/file_name").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.qq.qcloud:id/file_modify_time").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();

				basePath.add("swipe");

			}while(!(lastPageSource.equals(nowPageSource))==true);
			ps.append("</InfoTable>");
			ps.flush();
			driver.findElementById("com.qq.qcloud:id/back_to_lable_feak").click();
			Thread.sleep(5000);
			Util.writeProgress(32, 0, "图片信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(24, 1, "提取图片信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}


	}
	/**
	 * 视频
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveVideoData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.qq.qcloud:id/search_entrance_fl']");
		basePath.add("//*[@resource-id='com.qq.qcloud:id/search_lable_text' and @text='视频']");
		String baseName="腾讯微云-视频-";

		PrintStream ps=null;

		try {
			Util.writeProgress(33, 0, "正在提取视频信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id='com.qq.qcloud:id/search_lable_text' and @text='视频']").click();
			Thread.sleep(2000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QCLOUD/VIDEOINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/腾讯微云/视频</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Qcloud/Video Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEANDFILESIZEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期与文件大小</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date And File Size Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.qq.qcloud:id/listview_item_background");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.qq.qcloud:id/file_name", mobileElement)&&
							isElementExits("id", "com.qq.qcloud:id/file_modify_time", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.qq.qcloud:id/file_name").getText()+" "+mobileElement.findElementById("com.qq.qcloud:id/file_modify_time").getText();
							//map中的key
							String fileName=baseName+itemName;

							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}

							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
							filePath.add(mobileElement.getCenter());

							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.qq.qcloud:id/file_name").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.qq.qcloud:id/file_modify_time").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();

				basePath.add("swipe");

			}while(!(lastPageSource.equals(nowPageSource))==true);
			ps.append("</InfoTable>");
			ps.flush();
			driver.findElementById("com.qq.qcloud:id/back_to_lable_feak").click();
			Thread.sleep(2000);
			Util.writeProgress(48, 0, "视频信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(40, 1, "提取视频信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 笔记
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveNotesData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.qq.qcloud:id/search_entrance_fl']");
		basePath.add("//*[@resource-id='com.qq.qcloud:id/search_lable_text' and @text='笔记']");
		String baseName="腾讯微云-笔记-";

		PrintStream ps=null;

		try {
			Util.writeProgress(49, 0, "正在提取笔记信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id='com.qq.qcloud:id/search_lable_text' and @text='笔记']").click();
			Thread.sleep(2000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QCLOUD/NOTESINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/腾讯微云/笔记</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Qcloud/Notes Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEANDFILESIZEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期与文件大小</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date And File Size Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.qq.qcloud:id/listview_item_background");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.qq.qcloud:id/file_name", mobileElement)&&
							isElementExits("id", "com.qq.qcloud:id/file_modify_time", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.qq.qcloud:id/file_name").getText()+" "+mobileElement.findElementById("com.qq.qcloud:id/file_modify_time").getText();
							//map中的key
							String fileName=baseName+itemName;

							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}

							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
							filePath.add(mobileElement.getCenter());

							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.qq.qcloud:id/file_name").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.qq.qcloud:id/file_modify_time").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();

				basePath.add("swipe");

			}while(!(lastPageSource.equals(nowPageSource))==true);
			ps.append("</InfoTable>");
			ps.flush();
			driver.findElementById("com.qq.qcloud:id/back_to_lable_feak").click();
			Thread.sleep(5000);
			Util.writeProgress(64, 0, "笔记信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(56, 1, "提取笔记信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 音乐
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveMusicData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.qq.qcloud:id/search_entrance_fl']");
		basePath.add("//*[@resource-id='com.qq.qcloud:id/search_lable_text' and @text='音乐']");
		String baseName="腾讯微云-音乐-";

		PrintStream ps=null;

		try {
			Util.writeProgress(65, 0, "正在提取音乐信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id='com.qq.qcloud:id/search_lable_text' and @text='音乐']").click();
			Thread.sleep(2000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QCLOUD/MUSICINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/腾讯微云音乐</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Qcloud/Music Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEANDFILESIZEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期与文件大小</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date And File Size Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.qq.qcloud:id/listview_item_background");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.qq.qcloud:id/file_name", mobileElement)&&
							isElementExits("id", "com.qq.qcloud:id/file_modify_time", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.qq.qcloud:id/file_name").getText()+" "+mobileElement.findElementById("com.qq.qcloud:id/file_modify_time").getText();
							//map中的key
							String fileName=baseName+itemName;

							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}

							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
							filePath.add(mobileElement.getCenter());

							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.qq.qcloud:id/file_name").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.qq.qcloud:id/file_modify_time").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();

				basePath.add("swipe");

			}while(!(lastPageSource.equals(nowPageSource))==true);
			ps.append("</InfoTable>");
			ps.flush();
			driver.findElementById("com.qq.qcloud:id/back_to_lable_feak").click();
			Thread.sleep(5000);
			Util.writeProgress(80, 0, "音乐信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(72, 1, "提取音乐信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 其他
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveOtherData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.qq.qcloud:id/search_entrance_fl']");
		basePath.add("//*[@resource-id='com.qq.qcloud:id/search_lable_text' and @text='音乐']");
		String baseName="腾讯微云-音乐-";

		PrintStream ps=null;

		try {
			Util.writeProgress(81, 0, "正在提取其他信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id='com.qq.qcloud:id/search_lable_text' and @text='其他']").click();
			Thread.sleep(2000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/QCLOUD/OTHERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/腾讯微云/其他</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/Qcloud/Other Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEANDFILESIZEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期与文件大小</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date And File Size Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.qq.qcloud:id/listview_item_background");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.qq.qcloud:id/file_name", mobileElement)&&
							isElementExits("id", "com.qq.qcloud:id/file_modify_time", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.qq.qcloud:id/file_name").getText()+" "+mobileElement.findElementById("com.qq.qcloud:id/file_modify_time").getText();
							//map中的key
							String fileName=baseName+itemName;

							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}

							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
							filePath.add(mobileElement.getCenter());

							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.qq.qcloud:id/file_name").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.qq.qcloud:id/file_modify_time").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();

				basePath.add("swipe");

			}while(!(lastPageSource.equals(nowPageSource))==true);
			ps.append("</InfoTable>");
			ps.flush();
			driver.findElementById("com.qq.qcloud:id/back_to_lable_feak").click();
			Thread.sleep(5000);
			Util.writeProgress(99,0,"其他信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(91, 1, "其他信息提取出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 下载
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
	 */
	public void downloadFileMethod(AndroidDriver driver,String fileName,OutputStream writeProgress){

		try {
			Util.writeProgress(0, 0, "正在下载文件", writeProgress);

			while (!isElementExits("id", "com.qq.qcloud:id/search_entrance_fl", driver)){
				driver.navigate().back();
				Thread.sleep(5000);
			}

			Util.writeProgress(50, 0, "正在搜索文件", writeProgress);

			List<Object> filePath = pathMap.get(fileName);
			for (int i=0;i<filePath.size()-1;i++){
				String path=(String) filePath.get(i);
				if ("swipe".equals(path)){
					driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
					Thread.sleep(3000);
				}else {
					driver.findElementByXPath(path).click();
					Thread.sleep(5000);
				}
			}

			Point lastPath=(Point) filePath.get(filePath.size()-1);

			driver.tap(1, lastPath.getX(), lastPath.getY(), 3000);

			driver.findElementByXPath("//*[@resource-id='com.qq.qcloud:id/layout_operation']/android.widget.ImageView[@index='4']").click();
			Thread.sleep(5000);

			driver.findElementByXPath("//*[@resource-id='com.qq.qcloud:id/save_file_title']/../android.widget.LinearLayout[@index='2']").click();
			Thread.sleep(5000);

			Util.writeProgress(100, 0, "文件下载完成", writeProgress);
		} catch (Exception e) {
			try {
				Util.writeProgress(50, 1, "下载文件出错", writeProgress);
			} catch (Exception exception) {

			}
		} finally {
			try {
				Util.sendDownloadFileEndMessage(writeProgress);
			} catch (Exception e) {

			}
		}

	}

}
