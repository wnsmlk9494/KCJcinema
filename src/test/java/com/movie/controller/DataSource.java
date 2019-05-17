package com.movie.controller;
//package com.springbook.biz;
//
//import javax.inject.Inject;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.mysql.jdbc.Connection;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
//public class DataSource {
//	
//	@Inject
//	private DataSource ds;
//	
//	@Test
//	public void testConnection()throws Exception{
//		try(Connection con = ds.getConnection()){
//			System.out.println(con);
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
