package com.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBC的工具类
 *
 */
public class JDBCUtils {
	public static final ComboPooledDataSource dataSource = new ComboPooledDataSource();
	
	/**
	 * 获得连接
	 * @throws Exception 
	 */
	public static Connection getConnection() throws Exception{
		dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
		//报错Unknown initial character set index '255' received from server.Initial client character,
		//是因为mysql-connector-java的版本与服务器数据库版本不符合，所以导致连接出错。
		//因此此处的驱动是com.mysql.cj.jdbc.Driver，不可缺少cj
		dataSource.setJdbcUrl("jdbc:mysql:///shop?useUnicode=true&characterEncoding=utf8");
		return dataSource.getConnection();
	}
	
	/**
	 * 获得连接池
	 */
	public static DataSource getDataSource(){
		return dataSource;
	}
	
	/**
	 * 释放资源
	 */
	public static void release(ResultSet rs,PreparedStatement stmt,Connection conn){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
	
	public static void release(Statement stmt,Connection conn){
		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
}
