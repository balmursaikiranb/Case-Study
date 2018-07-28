package com.cg.paymentwallet.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.cg.paymentwallet.exception.PaymentWalletException;




public class DBUtil {
	static Connection con;
	static Properties prop;
	
	static
	{
		
		try {
			prop= new Properties();
			File file = new File("jdbc.properties");
			FileInputStream fin;
			fin = new FileInputStream(file);
			prop.load(fin);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
System.out.println("error in getting file");
		}
		
	}
	
	public static Connection getConnect() throws Exception
	{
		String driver = prop.getProperty("driver");
		String user = prop.getProperty("username");
		String pass = prop.getProperty("password");
		String url = prop.getProperty("url");
		Class.forName(driver);
		con = DriverManager.getConnection(url,user,pass);
		System.out.println("connection established ");
		return con;
	}
	
}