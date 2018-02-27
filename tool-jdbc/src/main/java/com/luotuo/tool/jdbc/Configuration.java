package com.luotuo.tool.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class Configuration {

	//DB配置
	public String user;
	public String password;
	public String url;
	public String sql;
	
	
	public Configuration(String path) throws IOException{
		
		Properties dbProperties = new Properties();
		dbProperties.load(new FileInputStream(path));
		
		//读取配置文件中数据
		Class<? extends Configuration> clazz = this.getClass();
		for (Field filed : clazz.getFields()) {
			try {
				filed.set(this, dbProperties.getProperty(filed.getName()));
			} catch (Exception e) {
				System.out.println("反射读取配置文件初始化失败");
			}
		}
		
	}
}