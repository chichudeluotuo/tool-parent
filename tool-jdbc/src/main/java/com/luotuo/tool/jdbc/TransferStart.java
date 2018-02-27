package com.luotuo.tool.jdbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransferStart {

	private static String dbPath = new String(System.getProperty("user.dir"));
	/**
	 * 启动数据移行任务
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("百信额度中心数据移行动作开始");
		
		showEnvConfig();
		
		//获得配置文件路径
        String configPath = new String(dbPath + "/db.properties");

        Configuration configuration = null;
        try {
        	configuration = new Configuration(configPath);
		} catch (IOException e) {
			System.out.println("配置文件读取失败");
		}
		
        //执行sql
        new DBOperation(configuration).execute(configuration.sql);
        
		System.out.println("百信额度中心数据移行动作结束");
	}
	
	
	/**
	 * 获取系统信息
	 */
	public static void showEnvConfig(){
		// 获取系统信息
		
        System.out.println("---------- 运行系统环境变量显示 ----------");
        System.out.println("操作系统:" + System.getProperty("os.name"));
        System.out.println("操作系统版本:" + System.getProperty("os.version"));
        System.out.println("JAVA版本:" + System.getProperty("java.version"));
        System.out.println("JAVA_HOME:" + System.getProperty("java.home"));
        System.out.println("当前用户:" + System.getProperty("user.name"));
        System.out.println("用户主目录:" + System.getProperty("user.home"));
        System.out.println("当前工作目录:" + System.getProperty("user.dir"));
	}
	
	/**
	 * 读取其他系统提供额度数据
	 * 
	 */
	public static List<CyclingAccountLimit> readFile(){
		
        String fileNamePath = new String(dbPath + "/tempData.txt");

		BufferedReader reader = null;
		
		List<CyclingAccountLimit> cyclingAccountLimitList = new ArrayList<CyclingAccountLimit>();

		try {

			System.out.println("以行为单位读取文件内容，一次读一整行：");

			reader = new BufferedReader(new FileReader(new File(fileNamePath)));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {

				// 显示行号

				// System.out.println("line " + line + ": " + tempString);
				System.out.println(tempString);
				String[] split = tempString.split("\\|");
				CyclingAccountLimit cyclingAccountLimit = new CyclingAccountLimit();
				
				//TODO将文件中数据转化到实体中
				
				System.out.println(cyclingAccountLimit);

				line++;

			}

			reader.close();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			if (reader != null) {

				try {

					reader.close();

				} catch (IOException e1) {

				}

			}

		}
		
		return cyclingAccountLimitList;

	}
}
