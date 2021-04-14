package com.service.impl;

import java.util.List;

import com.dao.ProductDao;
import com.dao.impl.ProductDaoImpl;
import com.domain.PageBean;
import com.domain.Product;
import com.service.ProductService;

public class ProductServiceImpl implements ProductService {

	@Override
	public List<Product> findAll() {
		System.out.println("ProductService��findAll����ִ����...");
		ProductDao productDao = new ProductDaoImpl();
		return productDao.findAll();
	}

	@Override
	public void save(Product product) {
		ProductDao productDao = new ProductDaoImpl();
		productDao.save(product);
	}
	
	@Override
	public Product findOne(Integer pid) {
		ProductDao productDao = new ProductDaoImpl();
		return productDao.findOne(pid);
	}
	
	@Override
	public void update(Product product) {
		ProductDao productDao = new ProductDaoImpl();
		productDao.update(product);
		System.out.println("ServiceUpdateִ��");
	}

	@Override
	public void delete(Integer pid) {
		// TODO Auto-generated method stub
		ProductDao productDao = new ProductDaoImpl();
		productDao.delete(pid);
	}

	@Override
	public PageBean<Product> findByPage(int page) {
		// TODO Auto-generated method stub
		PageBean pageBean = new PageBean<Product>();
		//��װ��ǰҳ��
		pageBean.setPage(page);
		//��װÿҳ��ʾ��¼��
		int limit = 3;
		pageBean.setLimit(limit);
		//��װ�ܼ�¼��
		ProductDao pd = new ProductDaoImpl();
		int totalCount = pd.findCount();
		pageBean.setTotalCount(totalCount);
		//��װ��ҳ�������ݼ�¼����limit����õ���
		int totalPage;
		if(totalCount%limit==0) {
			totalPage = totalCount/limit;
		}else {
			totalPage = totalCount/limit+1;
		}
		pageBean.setTotalPage(totalPage);
		//��װ��ѯ����ҳ�ļ�¼����
		int begin = (page-1)*limit;
		List<Product> productList = pd.findByPage(begin, limit);
		pageBean.setList(productList);
		return pageBean;
	}
}
