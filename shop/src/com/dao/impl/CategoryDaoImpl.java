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
			//��������
			conn = JDBCUtils.getConnection();
			//SQL���
			String sql = "select * from category";
			//Ԥ����
			pstmt = conn.prepareStatement(sql);
			//ִ��SQL
			rs = pstmt.executeQuery();
			//���ؽ��
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
			//�ͷ�
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
			
			//�������
			conn = JDBCUtils.getConnection();
			//SQL���
			String sql = "insert into category values(null,?,?) ";
			//Ԥ����
			pstmt = conn.prepareStatement(sql);
			//���ò���
			pstmt.setString(1, newCategory.getCname());
			pstmt.setString(2, newCategory.getCdesc());
			//ִ��SQL
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//�ͷ�
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
			//��������
			conn = JDBCUtils.getConnection();
			//SQL���
			String sql = "select * from category where cid = ?";
			//Ԥ����
			pstmt = conn.prepareStatement(sql);
			//���ò���
			pstmt.setInt(1, cid);
			//ִ�����
			rs = pstmt.executeQuery();
			//���ؽ��
			if(rs.next()) {
				resultCat = new Category();
				resultCat.setCid(cid);
				resultCat.setCname(rs.getString("cname"));
				resultCat.setCdesc(rs.getString(3));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			//�ͷ�
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
			//��������
			conn = JDBCUtils.getConnection();
			//SQL���
			String sql = "update category set cname = ?,cdes = ? where cid = ?";
			//Ԥ����
			pstmt = conn.prepareStatement(sql);
			//���ò���
			pstmt.setString(1, category.getCname());
			pstmt.setString(2, category.getCdesc());
			pstmt.setInt(3, category.getCid());
			//ִ��
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//�ͷ�
			JDBCUtils.release(pstmt, conn);
		}
	}

	@Override
	public void delete(Integer cid) {
		// TODO Auto-generated method stub
				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					//��������
					conn = JDBCUtils.getConnection();
					//SQL���
					String sql = "delete from category where cid = ?";
					//Ԥ����
					pstmt = conn.prepareStatement(sql);
					//���ò���
					pstmt.setInt(1, cid);
					//ִ��
					pstmt.executeUpdate();
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					//�ͷ�
					JDBCUtils.release(pstmt, conn);
				}
	}

	@Override
	public void delete(Connection conn, Integer cid) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		try {
			//��������
			conn = JDBCUtils.getConnection();
			//SQL���
			String sql = "delete from category where cid = ?";
			//Ԥ����
			pstmt = conn.prepareStatement(sql);
			//���ò���
			pstmt.setInt(1, cid);
			//ִ��
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//�ͷ�
			JDBCUtils.release(pstmt, conn);
		}
		
	}

	

}
