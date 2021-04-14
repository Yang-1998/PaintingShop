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
		 * ���������ҵ���ͳһ�������Ӷ��󣬱�֤���DAO��ʹ��ͬһ�����ӣ�
		 * 	* 1.��������֮�󣬽����Ӷ��󴫵ݸ�DAO���˴�ʹ�ã�
		 *  * 2.����һ�����Ӷ��󣬽����Ӱ󶨵���ǰ�߳���.(ThreadLocal)
		 */
		Connection conn = null;
		try{
			conn = JDBCUtils.getConnection();
			// ��������:
			conn.setAutoCommit(false);
			// Ҫ��ɾ������֮ǰ���Ƚ������÷������Ʒ�ȴ���һ��.
			ProductDao productDao = new ProductDaoImpl();
			List<Product> list = productDao.findByCid(cid);
			for (Product product : list) {
				product.getCategory().setCid(null);
				productDao.update(conn,product);
			}
			
//			int d = 1 / 0;
			
			// ɾ������:
			CategoryDao categoryDao = new CategoryDaoImpl();
			categoryDao.delete(conn,cid);
			// �ύ����:
			conn.commit();
		}catch(Exception e){
			
			try {
				// �ع�����:
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			if(conn != null){
				try {
					//�ͷ�����
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
