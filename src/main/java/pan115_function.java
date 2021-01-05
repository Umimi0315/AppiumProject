import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Point;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import java.io.IOException;
//import org.apache.commons.io.FileUtils;
//import org.openqa.selenium.OutputType;

public class pan115_function extends Function{
	public pan115_function(String dir, int x, int y, OutputStream writeProgress) {
		super(dir,x,y,writeProgress);
		pathMap=new HashMap<String, List<Object>>();
	}
	/**
	 * 接收
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveReceiveData(AndroidDriver driver,String dataName) throws Exception {


		PrintStream ps=null;

		try {
			Util.writeProgress(2, 0, "正在提取接收信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id= 'com.ylmf.androidclient:id/title' and @text= '接收']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/115PAN/RECEIVEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/115网盘/接收</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/115Pan/Receive Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SIZEANDDATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小与日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Size And Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do{
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.ylmf.androidclient:id/item_layout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.ylmf.androidclient:id/file_name", mobileElement)&&
							isElementExits("id", "com.ylmf.androidclient:id/size", mobileElement)) {
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/file_name").getText())+"</lv>"+"\n");
							//attachments.add(mobileElement.findElementById("com.ylmf.androidclient:id/file_name").getText());
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/size").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();
			}while(!(lastPageSource.equals(nowPageSource))==true);

//    	ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			Util.writeProgress(9, 0, "接收信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(5, 1, "提取接收信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 转存
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveSaveToData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(10, 0, "正在提取转存信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id= 'com.ylmf.androidclient:id/title' and @text= '转存']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/115PAN/SAVETOINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/115网盘/转存</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/115Pan/Save To Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SIZEANDDATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小与日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Size And Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.ylmf.androidclient:id/item_layout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.ylmf.androidclient:id/file_name", mobileElement)&&
							isElementExits("id", "com.ylmf.androidclient:id/size", mobileElement)) {
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/file_name").getText())+"</lv>"+"\n");
							//attachments.add(mobileElement.findElementById("com.ylmf.androidclient:id/file_name").getText());
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/size").getText())+"</lv>"+"\n");
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
			Util.writeProgress(18, 0, "转存信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(14, 1, "转存信息提取出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}


	}
	/**
	 * 链接任务
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveLinkTaskData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(19, 0, "正在提取链接任务信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id= 'com.ylmf.androidclient:id/title' and @text= '链接任务']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/115PAN/LINKTASKINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/115网盘/链接任务</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/115Pan/Link Task Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SIZEANDDATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小与日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Size And Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.ylmf.androidclient:id/item_layout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.ylmf.androidclient:id/file_name", mobileElement)&&
							isElementExits("id", "com.ylmf.androidclient:id/size", mobileElement)) {
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/file_name").getText())+"</lv>"+"\n");
							//attachments.add(mobileElement.findElementById("com.ylmf.androidclient:id/file_name").getText());
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/size").getText())+"</lv>"+"\n");
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
			Util.writeProgress(27, 0, "链接任务信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(23, 1, "提取链接任务信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 上传，全部遍历
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveUploadData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(28, 0, "正在提取上传信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id= 'com.ylmf.androidclient:id/title' and @text= '上传']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/115PAN/UPLOADINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/115网盘/上传</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/115Pan/Upload Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SIZEANDDATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小与日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Size And Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.ylmf.androidclient:id/item_layout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.ylmf.androidclient:id/file_name", mobileElement)&&
							isElementExits("id", "com.ylmf.androidclient:id/size", mobileElement)) {
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/file_name").getText())+"</lv>"+"\n");
							//attachments.add(mobileElement.findElementById("com.ylmf.androidclient:id/file_name").getText());
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/size").getText())+"</lv>"+"\n");
							ps.append("</Tln>"+"\n");
						}
					}
				}
				driver.swipe(x/2, y*8/10, x/2, y*2/10, 1000);
				Thread.sleep(3000);
				nowPageSource=driver.getPageSource();
			}while(!(lastPageSource.equals(nowPageSource))==true);


//    	ps.append("</Tln>"+"\n");
			ps.append("</InfoTable>");
			ps.flush();
			Util.writeProgress(36, 0, "上传信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(32, 1, "提取上传信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 下载
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveDownloadData(AndroidDriver driver,String dataName) throws Exception {

		PrintStream ps=null;

		try {
			Util.writeProgress(37, 0, "正在提取下载信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id= 'com.ylmf.androidclient:id/title' and @text= '下载']").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/115PAN/DOWNLOADINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/115网盘/下载</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/115Pan/Download Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SIZEANDDATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小与日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Size And Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.ylmf.androidclient:id/item_layout");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.ylmf.androidclient:id/file_name", mobileElement)&&
							isElementExits("id", "com.ylmf.androidclient:id/size", mobileElement)) {
							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/file_name").getText())+"</lv>"+"\n");
							//attachments.add(mobileElement.findElementById("com.ylmf.androidclient:id/file_name").getText());
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/size").getText())+"</lv>"+"\n");
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
			Util.writeProgress(45, 0, "下载信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(41, 1, "提取下载信息出错！", writeProgress);
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
	public void saveVideoData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.ylmf.androidclient:id/tv_file']");
		basePath.add("//*[@resource-id= 'com.ylmf.androidclient:id/action_open_folder' and @index= '2']");
		basePath.add("//*[@resource-id= 'com.ylmf.androidclient:id/file_cate_video']");
		String baseName="115网盘-视频-";

		PrintStream ps=null;

		try {
			Util.writeProgress(46, 0, "正在提取视频信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id= 'com.ylmf.androidclient:id/action_open_folder' and @index= '2']").click();
			Thread.sleep(5000);
			driver.findElementById("com.ylmf.androidclient:id/file_cate_video").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/115PAN/VIDEOINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/115网盘/视频</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/115Pan/Video Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SIZEANDDATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小与日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Size And Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");

			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.ylmf.androidclient:id/file_attr");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.ylmf.androidclient:id/filename", mobileElement)&&
							isElementExits("id", "com.ylmf.androidclient:id/file_date", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.ylmf.androidclient:id/filename").getText()+" "+mobileElement.findElementById("com.ylmf.androidclient:id/file_date").getText();
							//map中的key
							String fileName=baseName+itemName;

							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}

							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
//							filePath.add("//*[@resource-id='com.ylmf.androidclient:id/filename' and @text='"+itemName+"']");
							filePath.add(mobileElement.getCenter());
							//以文件名为key，文件路径为value保存到map中

							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/filename").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/file_date").getText())+"</lv>"+"\n");
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
			Util.writeProgress(54, 0, "视频信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(50, 1, "提取视频信息出错！", writeProgress);
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
	public void saveMusicData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.ylmf.androidclient:id/tv_file']");
		basePath.add("//*[@resource-id= 'com.ylmf.androidclient:id/action_open_folder' and @index= '2']");
		basePath.add("//*[@resource-id= 'com.ylmf.androidclient:id/file_cate_music']");
		String baseName="115网盘-音乐-";

		PrintStream ps=null;

		try {
			Util.writeProgress(55, 0, "正在提取音乐信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id= 'com.ylmf.androidclient:id/action_open_folder' and @index= '2']").click();
			Thread.sleep(5000);
			driver.findElementById("com.ylmf.androidclient:id/file_cate_music").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/115PAN/MUSICINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/115网盘/音乐</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/115Pan/Music Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SIZEANDDATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小与日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Size And Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.ylmf.androidclient:id/file_attr");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.ylmf.androidclient:id/filename", mobileElement)&&
							isElementExits("id", "com.ylmf.androidclient:id/file_date", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.ylmf.androidclient:id/filename").getText()+" "+mobileElement.findElementById("com.ylmf.androidclient:id/file_date").getText();
							//map中的key
							String fileName=baseName+itemName;

							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}

							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
//							filePath.add("//*[@resource-id='com.ylmf.androidclient:id/filename' and @text='"+itemName+"']");
							filePath.add(mobileElement.getCenter());

							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/filename").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/file_date").getText())+"</lv>"+"\n");
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
			Util.writeProgress(63, 0, "音乐信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(59, 1, "提取音乐信息出错！", writeProgress);
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
		basePath.add("//*[@resource-id='com.ylmf.androidclient:id/tv_file']");
		basePath.add("//*[@resource-id= 'com.ylmf.androidclient:id/action_open_folder' and @index= '2']");
		basePath.add("//*[@resource-id= 'com.ylmf.androidclient:id/file_cate_pic']");
		String baseName="115网盘-图片-";

		PrintStream ps=null;

		try {
			Util.writeProgress(64, 0, "正在提取图片信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id= 'com.ylmf.androidclient:id/action_open_folder' and @index= '2']").click();
			Thread.sleep(5000);
			driver.findElementById("com.ylmf.androidclient:id/file_cate_pic").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/115PAN/PICTUREINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/115网盘/图片</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/115Pan/Picture Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SIZEANDDATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小与日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Size And Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.ylmf.androidclient:id/file_attr");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.ylmf.androidclient:id/filename", mobileElement)&&
							isElementExits("id", "com.ylmf.androidclient:id/file_date", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.ylmf.androidclient:id/filename").getText()+" "+mobileElement.findElementById("com.ylmf.androidclient:id/file_date").getText();
							//map中的key
							String fileName=baseName+itemName;

							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}

							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
//							filePath.add("//*[@resource-id='com.ylmf.androidclient:id/filename' and @text='"+itemName+"']");
							filePath.add(mobileElement.getCenter());

							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/filename").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/file_date").getText())+"</lv>"+"\n");
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
			Util.writeProgress(72, 0, "正在提取图片信息...", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(68, 1, "提取图片信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}
	}
	/**
	 * 文档
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveDocData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.ylmf.androidclient:id/tv_file']");
		basePath.add("//*[@resource-id= 'com.ylmf.androidclient:id/action_open_folder' and @index= '2']");
		basePath.add("//*[@resource-id= 'com.ylmf.androidclient:id/file_cate_doc']");
		String baseName="115网盘-文档-";

		PrintStream ps=null;

		try {
			Util.writeProgress(73, 0, "正在提取文档信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id= 'com.ylmf.androidclient:id/action_open_folder' and @index= '2']").click();
			Thread.sleep(5000);
			driver.findElementById("com.ylmf.androidclient:id/file_cate_doc").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/115PAN/DOCINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/115网盘/文档</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/115Pan/Doc Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SIZEANDDATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小与日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Size And Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.ylmf.androidclient:id/file_attr");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.ylmf.androidclient:id/filename", mobileElement)&&
							isElementExits("id", "com.ylmf.androidclient:id/file_date", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.ylmf.androidclient:id/filename").getText()+" "+mobileElement.findElementById("com.ylmf.androidclient:id/file_date").getText();
							//map中的key
							String fileName=baseName+itemName;

							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}

							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
//							filePath.add("//*[@resource-id='com.ylmf.androidclient:id/filename' and @text='"+itemName+"']");
							filePath.add(mobileElement.getCenter());

							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/filename").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/file_date").getText())+"</lv>"+"\n");
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
			Util.writeProgress(81, 0, "文档信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(77, 1, "提取文档信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}

	}
	/**
	 * 应用，全部遍历
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveAppData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.ylmf.androidclient:id/tv_file']");
		basePath.add("//*[@resource-id= 'com.ylmf.androidclient:id/action_open_folder' and @index= '2']");
		basePath.add("//*[@resource-id= 'com.ylmf.androidclient:id/file_cate_app']");
		String baseName="115网盘-应用-";

		PrintStream ps=null;

		try {
			Util.writeProgress(82, 0, "正在提取应用信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id= 'com.ylmf.androidclient:id/action_open_folder' and @index= '2']").click();
			Thread.sleep(5000);
			driver.findElementById("com.ylmf.androidclient:id/file_cate_app").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/115PAN/APPINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/115网盘/应用</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/115Pan/App Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SIZEANDDATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小与日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Size And Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.ylmf.androidclient:id/file_attr");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.ylmf.androidclient:id/filename", mobileElement)&&
							isElementExits("id", "com.ylmf.androidclient:id/file_date", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.ylmf.androidclient:id/filename").getText()+" "+mobileElement.findElementById("com.ylmf.androidclient:id/file_date").getText();
							//map中的key
							String fileName=baseName+itemName;

							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}

							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
//							filePath.add("//*[@resource-id='com.ylmf.androidclient:id/filename' and @text='"+itemName+"']");
							filePath.add(mobileElement.getCenter());

							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/filename").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/file_date").getText())+"</lv>"+"\n");
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
			Util.writeProgress(90, 0, "应用信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(86, 1, "提取应用信息出错！", writeProgress);
			throw e;
		} finally {
			close(ps);
		}


	}
	/**
	 * 压缩包，全部遍历
	 * @param driver
	 * @param dataName
	 * @throws Exception
	 */
	public void saveArchiveData(AndroidDriver driver,String dataName) throws Exception {

		List<String> basePath=new ArrayList<String>();
		basePath.add("//*[@resource-id='com.ylmf.androidclient:id/tv_file']");
		basePath.add("//*[@resource-id= 'com.ylmf.androidclient:id/action_open_folder' and @index= '2']");
		basePath.add("//*[@resource-id= 'com.ylmf.androidclient:id/file_cate_zip']");
		String baseName="115网盘-压缩包-";

		PrintStream ps=null;

		try {
			Util.writeProgress(91, 0, "正在提取压缩包信息...", writeProgress);
			driver.findElementByXPath("//*[@resource-id= 'com.ylmf.androidclient:id/action_open_folder' and @index= '2']").click();
			Thread.sleep(5000);
			driver.findElementById("com.ylmf.androidclient:id/file_cate_zip").click();
			Thread.sleep(5000);

			File xmlFile=createDataSaveFile(dataName);

			ps=new PrintStream(new FileOutputStream(xmlFile),true,"UTF-8");
			ps.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			ps.append("<InfoTable>"+"\n");
			ps.append("<Node vType=\"string\" TextKey=\"/115PAN/ARCHIVEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">/115网盘/压缩包</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">/115Pan/Archive Information</Text>"+"\n");
			ps.append("</Node>"+"\n");
			ps.append("<TCol>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"FILENAMEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件名</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">File Name Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("<Prop vType=\"string\" TextKey=\"SIZEANDDATEINFORMATION\">"+"\n");
			ps.append("<Text Lang=\"zh_cn\">文件大小与日期</Text>"+"\n");
			ps.append("<Text Lang=\"en_us\">Size And Date Information</Text>"+"\n");
			ps.append("</Prop>"+"\n");
			ps.append("</TCol>"+"\n");
//    	ps.append("<Tln>"+"\n");
			String lastPageSource=null;
			String nowPageSource=driver.getPageSource();
			do {
				lastPageSource=nowPageSource;
				//Screenshot(driver, dataName);
				List<MobileElement> list=driver.findElementsById("com.ylmf.androidclient:id/file_attr");
				if (list.size()>0) {
					for (MobileElement mobileElement : list) {
						if (isElementExits("id", "com.ylmf.androidclient:id/filename", mobileElement)&&
							isElementExits("id", "com.ylmf.androidclient:id/file_date", mobileElement)) {

							//记录每一个文件的下载路径
							String itemName=mobileElement.findElementById("com.ylmf.androidclient:id/filename").getText()+" "+mobileElement.findElementById("com.ylmf.androidclient:id/file_date").getText();
							//map中的key
							String fileName=baseName+itemName;

							//map中包含该key则跳过
							if (pathMap.containsKey(fileName)){
								continue;
							}

							List<Object> filePath=new ArrayList<Object>(basePath);
							//基础路径+文件xpath=文件总路径
//							filePath.add("//*[@resource-id='com.ylmf.androidclient:id/filename' and @text='"+itemName+"']");
							filePath.add(mobileElement.getCenter());

							//以文件名为key，文件路径为value保存到map中
							pathMap.put(fileName, filePath);

							//将key值作为可下载文件名返回给C#桌面
							attachments.add(fileName);

							ps.append("<Tln>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/filename").getText())+"</lv>"+"\n");
							ps.append("<lv>"+replace(mobileElement.findElementById("com.ylmf.androidclient:id/file_date").getText())+"</lv>"+"\n");
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
			Util.writeProgress(99, 0, "压缩包信息提取完成！", writeProgress);
		} catch (Exception e) {
			Util.writeProgress(95, 1, "提取压缩包信息出错！", writeProgress);
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
			while (!isElementExits("id", "com.ylmf.androidclient:id/tv_file", driver)) {
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

			Point lastPath=(Point) filePath.get(filePath.size()-1);

			driver.tap(1, lastPath.getX(), lastPath.getY(), 3000);

			driver.findElementById("com.ylmf.androidclient:id/bottom_opt_download").click();
			Thread.sleep(5000);

			Util.writeProgress(100, 0, "已下载文件", writeProgress);

		}catch (Exception e){
			try {
				Util.writeProgress(50, 1, "下载文件出错！", writeProgress);
			}catch (Exception e2){

			}
		}finally {
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
