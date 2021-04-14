package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.dao.UserDao;
import com.domain.User;
import com.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	public User login(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			
			//1.连接数据库
			conn = JDBCUtils.getConnection();
			//2.编写sql语句
			String sql = "select * from user where username=? and password =?";
			//3.预编译
			pstmt = conn.prepareStatement(sql);
			//4.设置参数
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			//5.执行预编译
			rs = pstmt.executeQuery();
			if(rs.next()) {
				User existUser = new User();
				existUser.setUid(rs.getInt("uid"));
				existUser.setPassword(rs.getString("password"));
				existUser.setUsername(rs.getString("username"));
				return existUser;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//6.释放
			JDBCUtils.release(rs,pstmt,conn);
		}
		
		
		

		return null;
	}

}
