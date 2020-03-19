package com.spring.providerone.service.impl;

import java.util.List;


import com.spring.providerone.dao.UserDao;
import com.spring.providerone.entity.User;
import com.spring.providerone.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Component
public class UserServiceImpl implements IUserService {
	@Autowired
	public UserDao userDao;
	@Override
	public void createUser(User user) {
		userDao.createUser(user);
	}
	@Override
	public List<User> findAllUser() {
		return userDao.findAllUser();
	}
	@Override
	public void delUser(String id) { userDao.delUser(id);}
	@Override
	public void updateUser(User user) { userDao.updateUser(user);}
}
