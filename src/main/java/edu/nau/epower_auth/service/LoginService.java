package edu.nau.epower_auth.service;

import edu.nau.epower_auth.dao.User;

/**
 * 登录服务接口
 * 
 * @ClassName: LoginService
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-13 04:25:06
 */
public interface LoginService {

	// 根据user name查找用户
	public User findUserByUserName(String userName);

	// 根据username & password查找用户
	public User findUserByUserNameAndPwd(String userName, String passWord);
}
