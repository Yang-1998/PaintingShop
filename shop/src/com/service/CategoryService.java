package com.service;

import java.util.List;

import com.domain.Category;

public interface CategoryService {

	List<Category> findAll();

	void save(Category newCategory);

	Category edit(Integer cid);

	void update(Category category);

	void delete(Integer cid);




}
