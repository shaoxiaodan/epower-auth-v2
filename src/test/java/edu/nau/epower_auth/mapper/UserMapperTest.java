package edu.nau.epower_auth.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.nau.epower_auth.dao.User;

/**
 * 用户映射单元测试
 * 
 * @ClassName: UserMapperTest
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-06 09:51:01
 */
@SpringBootTest
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void testFindUserByUserId() {

		int userId = 0;
		userId = 1; // user = aaa
//		userId = 2; //user = bbb
//		userId = 3; //user = ccc

		User user = userMapper.findUserByUserId(userId);
		System.out.println("testFindByUserId:: user=" + user);
		assertNotNull(user);
	}

	@Test
	public void testFindUserByUserName() {

		String userName = "";
		userName = "aaa";
//		userName = "bbb";
//		userName = "ccc";

		User user = userMapper.findUserByUserName(userName);
		System.out.println("testFindByUserName::user=" + user);
		assertNotNull(user);
	}

	@Test
	public void testFindUserByUserNameAndPwd() {

		String userName = "";
		String pwd = "";

		userName = "aaa";
		pwd = "123";

		User user = userMapper.findUserByUserNameAndPwd(userName, pwd);
		assertNotNull(user);
	}

	@Test
	public void testListUser() {
		List<User> userList = userMapper.listUser();
		System.out.println("testListUser::userList=" + userList);
	}

	@Test
	public void testInsertUser() {

		User user = new User();
		user.setUsername("Java");
		user.setPassword("123456");
		user.setCreateTime(new Date());

		int insert = userMapper.insertUser(user);
		System.out.println("testInserUser::insert=" + insert + "\tid=" + user.getId());
	}

	@Test
	public void testUpdateUser() {

		User user = new User();
		user.setId(7);
		user.setUsername("Xiaodan Shao");
		user.setPassword("987654321");

		int update = 0;

		/*
		 * 测试异常操作
		 */
		try {
//			System.out.println(12 / 0);
			update = userMapper.updateUser(user);
//			System.out.println(12 / 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("testUpdateUser::update=" + update);
	}

	@Test
	public void testDeleteUser() {

		int userId = 0;
		userId = 7;

		int remove = userMapper.deleteUser(userId);
		System.out.println("testRemoveUser::remove=" + remove);
	}

}
