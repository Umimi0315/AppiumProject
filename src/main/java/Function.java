import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
//import java.io.PrintStream;
import java.lang.management.ManagementFactory;
//import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//import java.util.List;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Function {
	public String dir;
	public int x;
	public int y;
	public String pidName = ManagementFactory.getRuntimeMXBean().getName();
	public String pid = pidName.split("@")[0];
	public OutputStream writeProgress;
	Map<String,List<Object>>  pathMap;
//	new PrintStream(new FileOutputStream(pipe,true),true,"UTF-8");
	public ArrayList<String> attachments=new ArrayList<String>();
	public Function(String dir,int x,int y,OutputStream writeProgress) {
		this.dir=dir;
		this.x=x;
		this.y=y;
		try {
			this.writeProgress=writeProgress;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Function() {
		super();
	}

//	//截图
//    public void Screenshot(AndroidDriver driver,String pictureName) throws IOException {
//    	SimpleDateFormat timeFormat=new SimpleDateFormat("yyy-MM-dd-HH-mm-ss");
//    	String dateString=timeFormat.format(new Date());
//    	String dir_name=dir+"\\picture";
//    	if(!(new File(dir_name).isDirectory())) {
//    		new File(dir_name).mkdir();
//    	}
//    	File screen=driver.getScreenshotAs(OutputType.FILE);
//    	FileUtils.copyFile(screen, new File(dir_name+"\\"+dateString+pictureName+".jpg"));
//		
//	}
////    //保存页面布局
////    public void saveData(AndroidDriver driver,String dataName) throws Exception {
////    	SimpleDateFormat timeFormat=new SimpleDateFormat("yyy-MM-dd-HH-mm-ss");
////    	String dateString=timeFormat.format(new Date());
////    	String dir_name=dir+"\\data";
////    	if(!(new File(dir_name).isDirectory())) {
////    		new File(dir_name).mkdir();
////    	}
////    	File text=new File(dir_name+"\\"+dateString+dataName+".txt");
////    	if(!text.exists()) {
////    		text.createNewFile();
////    	}
////    	PrintStream ps=new PrintStream(new FileOutputStream(text));
////    	ps.println(driver.getPageSource());
////		
////	}
//    //保存页面文本信息
//    public void saveData(AndroidDriver driver,String dataName) throws Exception {
//    	SimpleDateFormat timeFormat=new SimpleDateFormat("yyy-MM-dd-HH-mm-ss");
//    	String dateString=timeFormat.format(new Date());
//    	String dir_name=dir+"\\data";
//    	if(!(new File(dir_name).isDirectory())) {
//    		new File(dir_name).mkdir();
//    	}
//    	File text=new File(dir_name+"\\"+dateString+dataName+".txt");
//    	if(!text.exists()) {
//    		text.createNewFile();
//    	}
//    	PrintStream ps=new PrintStream(new FileOutputStream(text));
//    	List<MobileElement> textlist=driver.findElementsByClassName("android.widget.TextView");
//    	if(textlist.size()>0)
//    	{
//    	for (MobileElement mobileElement : textlist) {
//    		if (mobileElement.getText()!=null&&mobileElement.getText().length()>0) {
//    			ps.append(mobileElement.getText()+"\n");
//    		}
//			
//		}
//    	}
//    	List<MobileElement> viewlist=driver.findElementsByClassName("android.view.View");
//    	if (viewlist.size()>0) {
//			for (MobileElement mobileElement : viewlist) {
//				if (mobileElement.getAttribute("content-desc")!=null&&mobileElement.getAttribute("content-desc").length()>0) {
//					ps.append(mobileElement.getAttribute("content-desc")+"\n");
//				}
//			}
//		}
//		
//	}
//    //通过resource-id定位控件
//    public void findById(AndroidDriver driver,String id,String name,int frequency) throws Exception {
//    	driver.findElementById(id).click();
//        Thread.sleep(3000);
//        for (int i = 0; i < frequency; i++) {
//        	Screenshot(driver, name);
//        	saveData(driver, name);
//			driver.swipe(x/2, y*9/10, x/2, y/10, 1000);
//			}
//	}
//    //通过xpath定位控件
//    public void findByXpath(AndroidDriver driver,String xpath,String name,int frequency) throws Exception {
//    	driver.findElementByXPath(xpath).click();
//        Thread.sleep(3000);
//        for (int i = 0; i < frequency; i++) {
//        	Screenshot(driver, name);
//        	saveData(driver, name);
//			driver.swipe(x/2, y*4/5, x/2, y*3/10, 1500);
//			}
//	}
    /**
	 * 截图,已废弃
	 * @param driver
	 * @param pictureName
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public void Screenshot(AndroidDriver driver,String pictureName) throws IOException {
    	SimpleDateFormat timeFormat=new SimpleDateFormat("yyy-MM-dd-HH-mm-ss");
    	String dateString=timeFormat.format(new Date());
    	String dir_name=dir+"\\picture";
    	if(!(new File(dir_name).isDirectory())) {
    		new File(dir_name).mkdir();
    	}
    	File screen=driver.getScreenshotAs(OutputType.FILE);
    	FileUtils.copyFile(screen, new File(dir_name+"\\"+dateString+pictureName+".jpg"));
		
	}
	/**
	 * 通过mobileElement判断元素是否存在
	 * @param by
	 * @param path
	 * @param mobileElement
	 * @return
	 */
	public boolean isElementExits(String by,String path,MobileElement mobileElement) {
		try {
			mobileElement.findElement(by, path);
			return true;
		} catch (Exception e) {
			return false;
			// TODO: handle exception
		}
	}

	/**
	 * 通过AndroidDriver判断元素是否存在
	 * @param by
	 * @param path
	 * @param driver
	 * @return
	 */
	public boolean isElementExits(String by,String path,AndroidDriver driver) {
		try {
			driver.findElement(by, path);
			return true;
		} catch (Exception e) {
			return false;
			// TODO: handle exception
		}
	}
	/**
	 * 替换xml元素中预定义实体
	 * @param string
	 * @return
	 */
	public String replace(String string) {
		String s=string.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("'", "&apos;").replaceAll("\"", "&quot;");
		return s;
	}
	
//	public void writeProgress(int nowProgress,int status_Code,String status_Text) {
//		
//		writeProgress.append("{"+"\n");
//		writeProgress.append("\"TASK_ID\":\""+pid+"\","+"\n");//+"\n"
//		writeProgress.append("\"SUB_TASK_ID\":"+"\"\","+"\n");
//		writeProgress.append("\"PROGRESS_CUR\":"+nowProgress+","+"\n");
//		writeProgress.append("\"PROGRESS_TOTAL\":"+"100"+","+"\n");
//		writeProgress.append("\"STATUS_CODE\":"+"\""+status_Code+"\""+","+"\n");
//		writeProgress.append("\"STATUS_TEXT\":"+"\""+status_Text+"\""+"\n");
//		writeProgress.append("}"+"\n");
//		writeProgress.flush();
//		
//		if (status_Code==1) {
//			writeProgress.close();
//		}
//	
//	}

//	/**
//	 * 发送任务执行进度
//	 * @param nowProgress 当前进度百分比数字
//	 * @param status_Code 当前进度状态代码，0为正常执行，1为执行出错
//	 * @param status_Text 当前进度状态描述信息
//	 * @throws Exception
//	 */
//	public void writeProgress(int nowProgress,int status_Code,String status_Text) throws Exception {
//
////		writeProgress.append("{"+"\n");
////		writeProgress.append("\"TASK_ID\":\""+pid+"\","+"\n");//+"\n"
////		writeProgress.append("\"SUB_TASK_ID\":"+"\"\","+"\n");
////		writeProgress.append("\"PROGRESS_CUR\":"+nowProgress+","+"\n");
////		writeProgress.append("\"PROGRESS_TOTAL\":"+"100"+","+"\n");
////		writeProgress.append("\"STATUS_CODE\":"+"\""+status_Code+"\""+","+"\n");
////		writeProgress.append("\"STATUS_TEXT\":"+"\""+status_Text+"\""+"\n");
////		writeProgress.append("}"+"\n");
//
//		String progressStr="{"
//							+"\"TASK_ID\":\""+pid+"\","
//							+"\"SUB_TASK_ID\":"+"\"\","
//							+"\"PROGRESS_CUR\":"+nowProgress+","
//							+"\"PROGRESS_TOTAL\":"+"100"+","
//							+"\"STATUS_CODE\":"+"\""+status_Code+"\""+","
//							+"\"STATUS_TEXT\":"+"\""+status_Text+"\""
//							+"}";
//		//内容
//		byte[] progressArr=progressStr.getBytes("UTF-8");
//
//		int lengthInt=progressArr.length+4;
//		//帧头
//		byte[] frameHeader=new byte[]{(byte)0x00};
//		//帧长
//		byte[] frameLength=Util.intToBytes(lengthInt);
//		//命令
//		byte[] cmd=new byte[] {(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01};
//		//帧尾
//		byte[] frameEnd=new byte[] {(byte)0xff};
//
////		byte[] frame1=Util.arrayJoin(frameHeader, frameLength);
////		byte[] frame2=Util.arrayJoin(frame1, cmd);
////		byte[] frame3=Util.arrayJoin(frame2, progressArr);
////		Util.arrayJoin(frame3, frameEnd);
//		byte[] frame = Util.segmentSplice(frameHeader,frameLength,cmd,progressArr,frameEnd);
//
//		writeProgress.write(frame, 0, frame.length);
//
//		writeProgress.flush();
//
////		if (status_Code==1) {
////			writeProgress.close();
////		}
//
//	}

	/**
	 * 在设定目录下创建保存提取数据的名为dataName的xml文件
	 * @param dataName 待创建的xml文件名
	 * @return 返回已创建的xml文件
	 * @throws Exception
	 */
	public File createDataSaveFile(String dataName) throws Exception {
		//准备
		SimpleDateFormat timeFormat=new SimpleDateFormat("yyy-MM-dd-HH-mm-ss");
		String dateString=timeFormat.format(new Date());
		String dir_name=dir+"\\data";
		if(!(new File(dir_name).isDirectory())) {
			new File(dir_name).mkdir();
		}
		File xmlFile=new File(dir_name+"\\"+dateString+dataName+".xml");
		if(!xmlFile.exists()) {
			xmlFile.createNewFile();
		}

		return xmlFile;
	}

	/**
	 * 关闭资源
	 * @param ps
	 */
	public void close(PrintStream ps){
		if (ps!=null){
			ps.flush();
			ps.close();
		}
	}

	public ArrayList<String> getAttachments() {
		return attachments;
	}
}
