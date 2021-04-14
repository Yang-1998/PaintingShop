package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.domain.Category;
import com.dao.CategoryDao;
import com.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() {
		System.out.println("daoimpl");
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Category> categoryList=null;
		try {
			//创建连接
			conn = JDBCUtils.getConnection();
			//SQL语句
			String sql = "select * from category";
			//预编译
			pstmt = conn.prepareStatement(sql);
			//执行SQL
			rs = pstmt.executeQuery();
			//返回结果
			categoryList = new ArrayList <Category>();
			while(rs.next()) {
				Category category = new Category();
				category.setCid(rs.getInt("cid"));
				category.setCdesc(rs.getString("cdes"));
				category.setCname(rs.getString("cname"));
				categoryList.add(category);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//释放
			JDBCUtils.release(rs, pstmt, conn);
		}
		
		
		return categoryList;
	}

	@Override
	public void save(Category newCategory) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			
			//获得链接
			conn = JDBCUtils.getConnection();
			//SQL语句
			String sql = "insert into category values(null,?,?) ";
			//预编译
			pstmt = conn.prepareStatement(sql);
			//设置参数
			pstmt.setString(1, newCategory.getCname());
			pstmt.setString(2, newCategory.getCdesc());
			//执行SQL
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//释放
			JDBCUtils.release(pstmt, conn);
		}
		
		
		
	}

	@Override
	public Category edit(Integer cid) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Category resultCat = null;
		try {
			//创建链接
			conn = JDBCUtils.getConnection();
			//SQL语句
			String sql = "select * from category where cid = ?";
			//预编译
			pstmt = conn.prepareStatement(sql);
			//设置参数
			pstmt.setInt(1, cid);
			//执行语句
			rs = pstmt.executeQuery();
			//返回结果
			if(rs.next()) {
				resultCat = new Category();
				resultCat.setCid(cid);
				resultCat.setCname(rs.getString("cname"));
				resultCat.setCdesc(rs.getString(3));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			//释放
			JDBCUtils.release(rs, pstmt, conn);
		}
		return resultCat;
	}

	@Override
	public void update(Category category) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//建立连接
			conn = JDBCUtils.getConnection();
			//SQL语句
			String sql = "update category set cname = ?,cdes = ? where cid = ?";
			//预编译
			pstmt = conn.prepareStatement(sql);
			//设置参数
			pstmt.setString(1, category.getCname());
			pstmt.setString(2, category.getCdesc());
			pstmt.setInt(3, category.getCid());
			//执行
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//释放
			JDBCUtils.release(pstmt, conn);
		}
	}

	@Override
	public void delete(Integer cid) {
		// TODO Auto-generated method stub
				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					//建立连接
					conn = JDBCUtils.getConnection();
					//SQL语句
					String sql = "delete from category where cid = ?";
					//预编译
					pstmt = conn.prepareStatement(sql);
					//设置参数
					pstmt.setInt(1, cid);
					//执行
					pstmt.executeUpdate();
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					//释放
					JDBCUtils.release(pstmt, conn);
				}
	}

	@Override
	public void delete(Connection conn, Integer cid) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		try {
			//建立连接
			conn = JDBCUtils.getConnection();
			//SQL语句
			String sql = "delete from category where cid = ?";
			//预编译
			pstmt = conn.prepareStatement(sql);
			//设置参数
			pstmt.setInt(1, cid);
			//执行
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//释放
			JDBCUtils.release(pstmt, conn);
		}
		
	}

	

}
