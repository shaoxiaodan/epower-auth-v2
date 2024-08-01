package edu.nau.epower_auth.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.nau.epower_auth.dao.RoleMenu;

/**
 * 角色-菜单映射单元测试
 * 
 * @ClassName: RoleMenuMapperTest
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-07 11:43:58
 */
@SpringBootTest
public class RoleMenuMapperTest {

	@Autowired
	private RoleMenuMapper roleMenuMapper;

	@Test
	public void testInsertRoleMenu() {

		int roleId = 0;
		int menuId = 0;

//		roleId = 1; // admin
//		roleId = 2; // root
//		roleId = 3; // editor
//		roleId = 4; // tester 1
		roleId = 5; // tester 2

//		menuId = 1; // 用户管理
//		menuId = 2; // 角色管理
//		menuId = 3; // 菜单管理
//		menuId = 4; // URL管理
//		menuId = 5; // 用户授权
//		menuId = 6; // 角色授权
		menuId = 8; // 测试菜单1

		RoleMenu roleMenu = new RoleMenu();
		roleMenu.setRoleId(roleId);
		roleMenu.setMenuId(menuId);

		int insert = roleMenuMapper.insertRoleMenu(roleMenu);
		System.out.println("testInsertRoleMenu::insert=" + insert);
		assertEquals(1, insert);
	}

}
