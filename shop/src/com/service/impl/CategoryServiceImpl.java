package com.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.dao.CategoryDao;
import com.dao.ProductDao;
import com.dao.impl.CategoryDaoImpl;
import com.dao.impl.ProductDaoImpl;
import com.domain.Category;
import com.domain.Product;
import com.service.CategoryService;
import com.utils.JDBCUtils;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		CategoryDao categoryDao =new CategoryDaoImpl();
		return categoryDao.findAll();
	}

	@Override
	public void save(Category newCategory) {
		// TODO Auto-generated method stub
		CategoryDao categoryDao =new CategoryDaoImpl();
		categoryDao.save(newCategory);
	}

	@Override
	public Category edit(Integer cid) {
		// TODO Auto-generated method stub
		CategoryDao categoryDao =new CategoryDaoImpl();
		return categoryDao.edit(cid);
	}


	@Override
	public void update(Category category) {
		// TODO Auto-generated method stub
		CategoryDao categoryDao =new CategoryDaoImpl();
		categoryDao.update(category);
	}

	@Override
	public void delete(Integer cid) {
		/**
		 * 事务管理：在业务层统一创建连接对象，保证多个DAO中使用同一个连接：
		 * 	* 1.创建连接之后，将连接对象传递给DAO（此处使用）
		 *  * 2.创建一个连接对象，将连接绑定到当前线程中.(ThreadLocal)
		 */
		Connection conn = null;
		try{
			conn = JDBCUtils.getConnection();
			// 开启事务:
			conn.setAutoCommit(false);
			// 要在删除分类之前，先将所属该分类的商品先处理一下.
			ProductDao productDao = new ProductDaoImpl();
			List<Product> list = productDao.findByCid(cid);
			for (Product product : list) {
				product.getCategory().setCid(null);
				productDao.update(conn,product);
			}
			
//			int d = 1 / 0;
			
			// 删除分类:
			CategoryDao categoryDao = new CategoryDaoImpl();
			categoryDao.delete(conn,cid);
			// 提交事务:
			conn.commit();
		}catch(Exception e){
			
			try {
				// 回滚事务:
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			if(conn != null){
				try {
					//释放连接
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
