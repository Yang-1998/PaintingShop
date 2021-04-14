package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.ProductDao;
import com.domain.Product;
import com.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findAll() {
		System.out.println("ProductDao��findAll����ִ����...");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> list = null;
		try{
			// �������:
			conn = JDBCUtils.getConnection();
			// ��дSQL:
			String sql = "SELECT * FROM product p,category c WHERE p.cid=c.cid ORDER BY p.pid DESC;";
			// Ԥ����SQL:
			pstmt = conn.prepareStatement(sql);
			// ִ��SQL:
			rs = pstmt.executeQuery();
			// ���������:
			list = new ArrayList<Product>();
			while(rs.next()){
				Product product = new Product();
				product.setPid(rs.getInt("pid"));
				product.setPname(rs.getString("pname"));
				product.setAuthor(rs.getString("author"));
				product.setPrice(rs.getDouble("price"));
				product.setDescription(rs.getString("description"));
				product.setFilename(rs.getString("filename"));
				product.setPath(rs.getString("path"));
				// ��װ��Ʒ�����ķ���:
				product.getCategory().setCid(rs.getInt("cid"));
				product.getCategory().setCname(rs.getString("cname"));
				product.getCategory().setCdesc(rs.getString("cdes"));
				
				list.add(product);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCUtils.release(rs, pstmt, conn);
		}
		return list;
	}

	@Override
	public void save(Product product) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			// �������:
			conn = JDBCUtils.getConnection();
			// ��дSQL:
			String sql = "insert into product values (null,?,?,?,?,?,?,?)";
			// Ԥ����SQL
			pstmt = conn.prepareStatement(sql);
			// ���ò���:
			pstmt.setString(1, product.getPname());
			pstmt.setString(2, product.getAuthor());
			pstmt.setDouble(3, product.getPrice());
			pstmt.setString(4, product.getDescription());
			pstmt.setString(5, product.getFilename());
			pstmt.setString(6, product.getPath());
			pstmt.setInt(7, product.getCategory().getCid());
			// ִ��SQL��
			pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCUtils.release(pstmt, conn);
		}
	}

	@Override
	public Product findOne(Integer pid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			// �������:
			conn = JDBCUtils.getConnection();
			// ��дSQL:
			String sql = "SELECT * FROM product p,category c WHERE p.cid=c.cid AND p.pid=?";
			// Ԥ����SQL:
			pstmt = conn.prepareStatement(sql);
			// ���ò���
			pstmt.setInt(1, pid);
			// ִ��SQL���
			rs = pstmt.executeQuery();
			if(rs.next()){
				// ��װ����:
				Product product = new Product();
				product.setPid(rs.getInt("pid"));
				product.setPname(rs.getString("pname"));
				product.setAuthor(rs.getString("author"));
				product.setPrice(rs.getDouble("price"));
				product.setDescription(rs.getString("description"));
				product.setFilename(rs.getString("filename"));
				product.setPath(rs.getString("path"));
				product.getCategory().setCid(rs.getInt("cid"));
				product.getCategory().setCname(rs.getString("cname"));
				product.getCategory().setCdesc(rs.getString(3));
				return product;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCUtils.release(pstmt, conn);
		}
		return null;
	}

	@Override
	public void update(Product product) {
		Connection conn = null;
		PreparedStatement pstmt= null;
		try{
			// �������:
			conn = JDBCUtils.getConnection();
			// ��дSQL:
			String sql = "update product set pname = ?,author=?,price=? ,description =?,filename=?,path=?,cid=? where pid=?";
			// Ԥ����SQL:
			pstmt = conn.prepareStatement(sql);
			// ���ò���:
			pstmt.setString(1, product.getPname());
			pstmt.setString(2, product.getAuthor());
			pstmt.setDouble(3, product.getPrice());
			pstmt.setString(4, product.getDescription());
			pstmt.setString(5, product.getFilename());
			pstmt.setString(6, product.getPath());
			pstmt.setObject(7, product.getCategory().getCid());//setInt����û�п�ֵ����˴˴�����ΪsetObject
			pstmt.setInt(8, product.getPid());
			// ִ��SQL:
			pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCUtils.release(pstmt, conn);
		}
	}

	@Override
	public void update(Connection conn,Product product) {
		PreparedStatement pstmt= null;
		try{
			// ��дSQL:
			String sql = "update product set pname = ?,author=?,price=? ,description =?,filename=?,path=?,cid=? where pid=?";
			// Ԥ����SQL:
			pstmt = conn.prepareStatement(sql);
			// ���ò���:
			pstmt.setString(1, product.getPname());
			pstmt.setString(2, product.getAuthor());
			pstmt.setDouble(3, product.getPrice());
			pstmt.setString(4, product.getDescription());
			pstmt.setString(5, product.getFilename());
			pstmt.setString(6, product.getPath());
			pstmt.setObject(7, product.getCategory().getCid());
			pstmt.setInt(8, product.getPid());
			// ִ��SQL:
			pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void delete(Integer pid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = JDBCUtils.getConnection();
			String sql = "delete from product where pid = ?";
			pstmt = conn.prepareStatement(sql);
			// ���ò���:
			pstmt.setInt(1, pid);
			// ִ��SQL:
			pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCUtils.release(pstmt, conn);
		}
	}

	@Override
	public List<Product> findByCid(Integer cid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> list = null;
		try{
			// �������:
			conn = JDBCUtils.getConnection();
			// ��дSQL:
			String sql = "SELECT * FROM product p,category c WHERE p.cid=c.cid and p.cid = ? ORDER BY p.pid DESC;";
			// Ԥ����SQL:
			pstmt = conn.prepareStatement(sql);
			// ���ò���:
			pstmt.setInt(1, cid);
			// ִ��SQL:
			rs = pstmt.executeQuery();
			// ���������:
			list = new ArrayList<Product>();
			while(rs.next()){
				Product product = new Product();
				product.setPid(rs.getInt("pid"));
				product.setPname(rs.getString("pname"));
				product.setAuthor(rs.getString("author"));
				product.setPrice(rs.getDouble("price"));
				product.setDescription(rs.getString("description"));
				product.setFilename(rs.getString("filename"));
				product.setPath(rs.getString("path"));
				// ��װ��Ʒ�����ķ���:
				product.getCategory().setCid(rs.getInt("cid"));
				product.getCategory().setCname(rs.getString("cname"));
				product.getCategory().setCdesc(rs.getString("cdesc"));
				
				list.add(product);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCUtils.release(rs, pstmt, conn);
		}
		return list;
	}

	@Override
	public int findCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long count = 0l;
		try{
			// �������:
			conn = JDBCUtils.getConnection();
			// ��дSQL:
			String sql = "select count(*) as count from product";//�˴��趨��ѯ�����ı���Ϊcount
			// Ԥ����SQL:
			pstmt = conn.prepareStatement(sql);
			// ִ��SQL:
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getLong("count");//�˴���ѯ����Ϊlong��
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCUtils.release(rs, pstmt, conn);
		}
		return count.intValue();
	}

	@Override
	public List<Product> findByPage(int begin, int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> list = null;
		try{
			// �������:
			conn = JDBCUtils.getConnection();
			// ��дSQL���:
			String sql = "select * from product limit ?,?";
			// Ԥ����SQL:
			pstmt = conn.prepareStatement(sql);
			// ���ò���:
			pstmt.setInt(1, begin);
			pstmt.setInt(2, limit);
			// ִ�в�ѯ:
			rs = pstmt.executeQuery();
			list = new ArrayList<Product>();
			while(rs.next()){
				Product product = new Product();
				product.setPid(rs.getInt("pid"));
				product.setPname(rs.getString("pname"));
				product.setAuthor(rs.getString("author"));
				product.setPrice(rs.getDouble("price"));
				product.setDescription(rs.getString("description"));
				product.setFilename(rs.getString("filename"));
				product.setPath(rs.getString("path"));
				list.add(product);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCUtils.release(rs, pstmt, conn);
		}
		return list;
	}


}

