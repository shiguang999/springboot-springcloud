package com.spring.providertwo.service.impl;

import java.util.List;
import com.spring.providertwo.dao.UserDao;
import com.spring.providertwo.entity.User;
import com.spring.providertwo.service.IUserService;
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
