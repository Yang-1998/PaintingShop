package com.dao;

import java.sql.Connection;
import java.util.List;

import com.domain.Category;

public interface CategoryDao {

	List<Category> findAll();

	void save(Category newCategory);

	Category edit(Integer cid);

	void update(Category category);

	void delete(Integer cid);

	void delete(Connection conn, Integer cid);


}
