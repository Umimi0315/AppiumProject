import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Point;

import java.io.*;
import java.util.*;

public class BaiduNetdisk_function extends Function{

	public BaiduNetdisk_function(String dir, int x, int y, OutputStream writeProgress) {
		// TODO Auto-generated constructor stub
		super(dir, x, y,writeProgress);
		pathMap=new HashMap<String, List<Object>>();
	}
	/**
	 * 视频
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveVideoData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.baidu.netdisk:id/file_list_category']");
		basePath.add("//*[@resource-id='com.baidu.netdisk:id/item1']");
		String baseName="百度网盘-视频-";

		PrintStream ps=null;

		try {
			Util.writeProgress(2, 0, "正在提取视频信息...", writeProgress);
			driver.findElementById("com.baidu.netdisk:id/file_list_category").click();
			Thread.sleep(2000);

			driver.findElementById("com.baidu.netdisk:id/item1").click();
			Thread.sleep(2000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/BAIDUNETDISK/VIDEOINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/百度网盘/视频</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/BaiduNetdisk/Video Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILESIZEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Size Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.baidu.netdisk:id/checkable_layout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.baidu.netdisk:id/text1", mobileElement)&&
							isElementExits("id", "com.baidu.netdisk:id/server_mtime", mobileElement)&&
							isElementExits("id", "com.baidu.netdisk:id/filesize", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.baidu.netdisk:id/text1").getText()+" "+mobileElement.findElementById("com.baidu.netdisk:id/server_mtime").getText()+" "+mobileElement.findElementById("com.baidu.netdisk:id/filesize").getText();
							//map中的key
							String fileName=baseName+itemName;
//							fileName.intern();
							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}

							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
//							filePath.add("//*[@resource-id='com.baidu.netdisk:id/text1' and @text='"+itemName+"']/../android.widget.ImageButton[@resource-id='android:id/button1']");
							filePath.add(mobileElement.getCenter());
							//以文件名为key，文件路径为value保存到map中

							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/text1").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/server_mtime").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/filesize").getText())+"</lv>"+"\n");
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
			driver.findElementById("com.baidu.netdisk:id/file_list_title_back").click();
			Thread.sleep(5000);
			Util.writeProgress(16, 0, "视频信息提取完成！", writeProgress);

		} catch (Exception e) {
			Util.writeProgress(8, 1, "提取视频信息出错!", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 文档，不可设置滑动次数
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveDocumentationData(AndroidDriver driver,String dataName) throws Exception {


		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.baidu.netdisk:id/file_list_category']");
		basePath.add("//*[@resource-id='com.baidu.netdisk:id/item2']");
		String baseName="百度网盘-文档-";

		PrintStream ps=null;

		try {
			Util.writeProgress(17, 0, "正在提取文档信息...", writeProgress);
			driver.findElementById("com.baidu.netdisk:id/file_list_category").click();
			Thread.sleep(5000);
			driver.findElementById("com.baidu.netdisk:id/item2").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/BAIDUNETDISK/DOCUMENTATIONINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/百度网盘/文档</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/BaiduNetdisk/Documentation Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILESIZEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Size Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.baidu.netdisk:id/checkable_layout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.baidu.netdisk:id/text1", mobileElement)&&
							isElementExits("id", "com.baidu.netdisk:id/server_mtime", mobileElement)&&
							isElementExits("id", "com.baidu.netdisk:id/filesize", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.baidu.netdisk:id/text1").getText()+" "+mobileElement.findElementById("com.baidu.netdisk:id/server_mtime").getText()+" "+mobileElement.findElementById("com.baidu.netdisk:id/filesize").getText();
							//map中的key
							String fileName=baseName+itemName;
							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}
							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
//							filePath.add("//*[@resource-id='com.baidu.netdisk:id/text1' and @text='"+itemName+"']/../android.widget.ImageButton[@resource-id='android:id/button1']");
							filePath.add(mobileElement.getCenter());
							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);


							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/text1").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/server_mtime").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/filesize").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();

				basePath.add("swipe");

			}while(!(lastPageSource.equals(nowPageSource))==true);

//    	ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			driver.findElementById("com.baidu.netdisk:id/file_list_title_back").click();
			Thread.sleep(5000);
			Util.writeProgress(32, 0, "文档信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(24, 1, "提取文档信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 音频
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveAudioData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.baidu.netdisk:id/file_list_category']");
		basePath.add("//*[@resource-id='com.baidu.netdisk:id/item3']");
		String baseName="百度网盘-音频-";


		PrintStream ps=null;

		try {
			Util.writeProgress(33, 0, "正在提取音频信息...", writeProgress);
			driver.findElementById("com.baidu.netdisk:id/file_list_category").click();
			Thread.sleep(5000);
			driver.findElementById("com.baidu.netdisk:id/item3").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/BAIDUNETDISK/AUDIOINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/百度网盘/音频</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/BaiduNetdisk/Audio Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILESIZEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Size Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.baidu.netdisk:id/checkable_layout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.baidu.netdisk:id/text1", mobileElement)&&
							isElementExits("id", "com.baidu.netdisk:id/server_mtime", mobileElement)&&
							isElementExits("id", "com.baidu.netdisk:id/filesize", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.baidu.netdisk:id/text1").getText()+" "+mobileElement.findElementById("com.baidu.netdisk:id/server_mtime").getText()+" "+mobileElement.findElementById("com.baidu.netdisk:id/filesize").getText();
							//map中的key
							String fileName=baseName+itemName;
							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}
							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
//							filePath.add("//*[@resource-id='com.baidu.netdisk:id/text1' and @text='"+itemName+"']/../android.widget.ImageButton[@resource-id='android:id/button1']");
							filePath.add(mobileElement.getCenter());
							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/text1").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/server_mtime").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/filesize").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();

				basePath.add("swipe");

			}while((!lastPageSource.equals(nowPageSource))==true);

//    	ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			driver.findElementById("com.baidu.netdisk:id/file_list_title_back").click();
			Thread.sleep(5000);
			Util.writeProgress(48, 0, "音频信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(40, 1, "提取音频信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 应用，不能设置滑动次数
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveApplicationData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.baidu.netdisk:id/file_list_category']");
		basePath.add("//*[@resource-id='com.baidu.netdisk:id/item4']");
		String baseName="百度网盘-应用-";


		PrintStream ps=null;

		try {
			Util.writeProgress(49, 0, "正在提取应用信息...", writeProgress);
			driver.findElementById("com.baidu.netdisk:id/file_list_category").click();
			Thread.sleep(5000);
			driver.findElementById("com.baidu.netdisk:id/item4").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/BAIDUNETDISK/APPLICATIONINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/百度网盘/应用</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/BaiduNetdisk/Application Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILESIZEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Size Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do{
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.baidu.netdisk:id/checkable_layout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.baidu.netdisk:id/text1", mobileElement)&&
							isElementExits("id", "com.baidu.netdisk:id/server_mtime", mobileElement)&&
							isElementExits("id", "com.baidu.netdisk:id/filesize", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.baidu.netdisk:id/text1").getText()+" "+mobileElement.findElementById("com.baidu.netdisk:id/server_mtime").getText()+" "+mobileElement.findElementById("com.baidu.netdisk:id/filesize").getText();
							//map中的key
							String fileName=baseName+itemName;
							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}
							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
//							filePath.add("//*[@resource-id='com.baidu.netdisk:id/text1' and @text='"+itemName+"']/../android.widget.ImageButton[@resource-id='android:id/button1']");
							filePath.add(mobileElement.getCenter());
							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/text1").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/server_mtime").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/filesize").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();

				basePath.add("swipe");

				}while((!lastPageSource.equals(nowPageSource))==true);
//    	ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			ps.close();
			driver.findElementById("com.baidu.netdisk:id/file_list_title_back").click();
			Thread.sleep(5000);
			Util.writeProgress(64,0,"应用信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(56, 1, "提取应用信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * BT种子
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveBTSeedData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.baidu.netdisk:id/file_list_category']");
		basePath.add("//*[@resource-id='com.baidu.netdisk:id/item5']");
		String baseName="百度网盘-BT种子-";


		PrintStream ps=null;

		try {
			Util.writeProgress(65, 0, "正在提取BT种子信息...", writeProgress);
			driver.findElementById("com.baidu.netdisk:id/file_list_category").click();
			Thread.sleep(5000);
			driver.findElementById("com.baidu.netdisk:id/item5").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/BAIDUNETDISK/BTSEEDINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/百度网盘/BT种子</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/BaiduNetdisk/BT Seed Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILESIZEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Size Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.baidu.netdisk:id/checkable_layout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.baidu.netdisk:id/text1", mobileElement)&&
							isElementExits("id", "com.baidu.netdisk:id/server_mtime", mobileElement)&&
							isElementExits("id", "com.baidu.netdisk:id/filesize", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.baidu.netdisk:id/text1").getText()+" "+mobileElement.findElementById("com.baidu.netdisk:id/server_mtime").getText()+" "+mobileElement.findElementById("com.baidu.netdisk:id/filesize").getText();
							//map中的key
							String fileName=baseName+itemName;
							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}
							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
//							filePath.add("//*[@resource-id='com.baidu.netdisk:id/text1' and @text='"+itemName+"']/../android.widget.ImageButton[@resource-id='android:id/button1']");
							filePath.add(mobileElement.getCenter());
							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/text1").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/server_mtime").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/filesize").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();

				basePath.add("swipe");

				}while((!lastPageSource.equals(nowPageSource))==true);
//    	ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			driver.findElementById("com.baidu.netdisk:id/file_list_title_back").click();
			Thread.sleep(5000);
			Util.writeProgress(80, 0, "BT种子信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(72, 1, "提取BT种子信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 其他，不可设置滑动次数
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveOtherData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.baidu.netdisk:id/file_list_category']");
		basePath.add("//*[@resource-id='com.baidu.netdisk:id/item6']");
		String baseName="百度网盘-其他-";

		PrintStream ps=null;

		try {
			Util.writeProgress(81, 0, "正在提取其他信息...", writeProgress);
			driver.findElementById("com.baidu.netdisk:id/file_list_category").click();
			Thread.sleep(5000);
			driver.findElementById("com.baidu.netdisk:id/item6").click();
			Thread.sleep(5000);

			File xmlFile= createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/BAIDUNETDISK/OTHERINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/百度网盘/其他</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/BaiduNetdisk/Other Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"DATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILESIZEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Size Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.baidu.netdisk:id/checkable_layout");
			if (list.size()>0) {
				for (MobileElement mobileElement : list) {
					if (isElementExits("id", "com.baidu.netdisk:id/text1", mobileElement)&&
							isElementExits("id", "com.baidu.netdisk:id/server_mtime", mobileElement)&&
							isElementExits("id", "com.baidu.netdisk:id/filesize", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.baidu.netdisk:id/text1").getText()+" "+mobileElement.findElementById("com.baidu.netdisk:id/server_mtime").getText()+" "+mobileElement.findElementById("com.baidu.netdisk:id/filesize").getText();
							//map中的key
							String fileName=baseName+itemName;
							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}
							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
//							filePath.add("//*[@resource-id='com.baidu.netdisk:id/text1' and @text='"+itemName+"']/../android.widget.ImageButton[@resource-id='android:id/button1']");
							filePath.add(mobileElement.getCenter());
							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/text1").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/server_mtime").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.baidu.netdisk:id/filesize").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						 }
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();

				basePath.add("swipe");

				}while((!lastPageSource.equals(nowPageSource))==true);

			ps.append("</InfoTable>");
			ps.flush();
			ps.close();
			driver.findElementById("com.baidu.netdisk:id/file_list_title_back").click();
			Thread.sleep(5000);

			Util.writeProgress(99, 0, "其他信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(91, 1, "提取其他信息出错！", writeProgress);
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
//				fileName.intern();
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
			Util.writeProgress(0, 0, "正在下载文件...", writeProgress);
			while (isElementExits("id", "com.baidu.netdisk:id/file_list_category", driver)==false) {
				driver.navigate().back();
				Thread.sleep(5000);
			}

			Util.writeProgress(50, 0, "正在搜索文件...", writeProgress);
			List<Object> filePath = pathMap.get(fileName);
			for (int i=0;i<filePath.size()-1;i++) {
				String path=(String) filePath.get(i);
				if ("swipe".equals(path)){
					driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
					Thread.sleep(3000);
				}else {
					driver.findElementByXPath(path).click();
					Thread.sleep(5000);
				}
			}

			Point lastPath= (Point) filePath.get(filePath.size()-1);

			driver.tap(1, lastPath.getX(), lastPath.getY(), 3000);

			driver.findElementById("com.baidu.netdisk:id/btn_to_download").click();
			Thread.sleep(5000);

			if (isElementExits("id", "com.baidu.netdisk:id/dialog_button_ok", driver)){
				driver.findElementById("com.baidu.netdisk:id/dialog_button_ok").click();
				Thread.sleep(5000);
			}

			Util.writeProgress(100, 0, "已下载文件", writeProgress);

		}catch (Exception e){
			try {
				Util.writeProgress(50, 1, "下载文件出错", writeProgress);

			}catch (Exception e2){

			}
		}finally {
			try {
				Util.sendDownloadFileEndMessage(writeProgress);
			}catch (Exception e3){

			}
		}
	}
	

}
