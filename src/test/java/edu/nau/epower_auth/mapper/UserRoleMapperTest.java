package edu.nau.epower_auth.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.nau.epower_auth.dao.UserRole;

/**
 * 用户-角色映射单元测试
 * 
 * @ClassName: UserRoleMapperTest
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-07 11:45:10
 */
@SpringBootTest
public class UserRoleMapperTest {

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Test
	public void testInsertUserRole() {

		int userId = 0;
		int roleId = 0;

//		userId = 1; // aaa
//		userId = 2; // bbb
//		userId = 3; // ccc
		userId = 6; // ddd

//		roleId = 1; // admin
//		roleId = 2; // root
//		roleId = 3; // editor
//		roleId = 4; // tester 1
		roleId = 5; // tester 2

		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);

		int insert = userRoleMapper.insertUserRole(userRole);
		System.out.println("testInsertUserRole::insert=" + insert);

		assertEquals(1, insert);
	}

	@Test
	public void testDeleteUserRole() {

		int userId = 0;
		int roleId = 0;

//		userId = 1; // aaa
//		userId = 2; // bbb
//		userId = 3; // ccc
		userId = 6; // ddd

//		roleId = 1; // admin
//		roleId = 2; // root
//		roleId = 3; // editor
//		roleId = 4; // tester 1
		roleId = 5; // tester 2

		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);

		int delete = userRoleMapper.deleteUserRole(userRole);
		System.out.println("testDeleteUserRole::delete=" + delete);

		assertEquals(1, delete);
	}

	@Test
	public void testInsertUserRoleBatch() {

		int userId = 0;
//		userId = 1; //aaa
//		userId = 2; //bbb
//		userId = 3; //ccc
		userId = 6; // ddd

		List<UserRole> userRoleList = new ArrayList<UserRole>();

		for (int i = 1; i <= 5; i++) {
			UserRole ur = new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(i);
			userRoleList.add(ur);
		}

		int insertBatch = userRoleMapper.insertUserRoleBatch(userRoleList);
		System.out.println("testInsertUserRoleBatch::insertBatch=" + insertBatch);
	}

	@Test
	public void testDeleteUserRoleBatch() {

		int userId = 0;
//		userId = 1; //aaa
//		userId = 2; //bbb
//		userId = 3; //ccc
		userId = 6; // ddd

		List<UserRole> userRoleList = new ArrayList<UserRole>();

		for (int i = 1; i <= 5; i++) {
			UserRole userRole = new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(i);
			userRoleList.add(userRole);
		}

		int deleteBatch = userRoleMapper.deleteUserRoleBatch(userRoleList);
		System.out.println("testDeleteUserRoleBatch::deleteBatch=" + deleteBatch);
	}

}
