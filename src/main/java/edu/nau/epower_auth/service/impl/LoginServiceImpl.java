package edu.nau.epower_auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nau.epower_auth.dao.User;
import edu.nau.epower_auth.mapper.UserMapper;
import edu.nau.epower_auth.service.LoginService;

/**
 * 登录服务实现
 * 
 * @ClassName: LoginServiceImpl
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-07 12:25:35
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserMapper userMapper;

	/*
	 * 根据用户名字查找用户
	 */
	@Override
	public User findUserByUserName(String userName) {
		return userMapper.findUserByUserName(userName);
	}

	/*
	 * 根据用户名字&密码查找用户
	 */
	@Override
	public User findUserByUserNameAndPwd(String userName, String passWord) {
		return userMapper.findUserByUserNameAndPwd(userName, passWord);
	}

}
