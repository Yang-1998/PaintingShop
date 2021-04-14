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
			
			//1.�������ݿ�
			conn = JDBCUtils.getConnection();
			//2.��дsql���
			String sql = "select * from user where username=? and password =?";
			//3.Ԥ����
			pstmt = conn.prepareStatement(sql);
			//4.���ò���
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			//5.ִ��Ԥ����
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
			//6.�ͷ�
			JDBCUtils.release(rs,pstmt,conn);
		}
		
		
		

		return null;
	}

}
