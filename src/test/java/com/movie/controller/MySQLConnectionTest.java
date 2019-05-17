package com.movie.controller;
//package com.springbook.biz;
//
//import java.sql.DriverManager;
//import org.junit.Test;
//import com.mysql.jdbc.Connection;
//
//public class MySQLConnectionTest {
//	
//	private static final String DRIVER = "com.mysql.jdbc.Driver";
//	private static final String URL = "jdbc:mysql://localhost:3306/test_db";
//	private static final String USER = "javaDB";
//	private static final String PW = "javaDB";
//	
//	@Test
//	public void testConnection() throws Exception{
//		Class.forName(DRIVER);
//		try(Connection con = DriverManager.getConnection(URL, USER, PW)){
//			System.out.println(con);
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//	}
//}
