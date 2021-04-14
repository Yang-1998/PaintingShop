package com.service.impl;

import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.domain.User;
import com.service.UserService;

public class UserServiceImpl implements UserService {

	public User login(User user) {
		//调用DAO的方法查询用户是否存在
		UserDao userDao = new UserDaoImpl();
		User existUser = userDao.login(user);
		return existUser;
	}

}
