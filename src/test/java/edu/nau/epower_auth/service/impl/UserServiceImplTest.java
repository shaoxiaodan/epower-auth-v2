package edu.nau.epower_auth.service.impl;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.nau.epower_auth.dao.User;
import edu.nau.epower_auth.service.UserService;

/**
 * 用户服务接口实现单元测试
 * 
 * @ClassName: UserServiceImplTest
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-07 11:29:49
 */
@SpringBootTest
public class UserServiceImplTest {

	@Autowired
	private UserService userService;

	@Test
	public void testListUser() {
		userService.listUser();
	}

	@Test
	public void testAddUser() {

		User user = new User();
		user.setUsername("Mybatis");
		user.setPassword("123456");
		user.setCreateTime(new Date());

		try {
			/**
			 * 异常操作 测试事务控制是否生效
			 */
			int errorCode = 12 / 0;

			int insert = userService.addUser(user);
			int userId = user.getId();
			System.out.println("UserServiceImplTest::insert=" + insert + "\tuserId=" + userId);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testUpdateUser() {
		// TODO
//		userService.updateUser(null);
	}

	@Test
	public void testRemoveUser() {
		// TODO
//		userService.removeUser(0);
	}

	@Test
	public void testAddUserRoleAuth() {
		// TODO
//		userService.addUserRoleAuth(null);
	}

	@Test
	public void testRemoveUserRoleAuth() {
		// TODO
//		userService.removeUserRoleAuth(null);
	}

}
