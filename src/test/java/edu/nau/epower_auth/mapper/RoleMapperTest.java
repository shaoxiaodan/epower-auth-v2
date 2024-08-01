package edu.nau.epower_auth.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.nau.epower_auth.dao.Menu;
import edu.nau.epower_auth.dao.Role;
import edu.nau.epower_auth.dao.Url;

/**
 * 角色映射单元测试
 * 
 * @ClassName: RoleMapperTest
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-06 09:57:19
 */
@SpringBootTest
public class RoleMapperTest {

	@Autowired
	private RoleMapper roleMapper;

	@Test
	public void testFindRoleByUserId() {

		int userId = 0;
//		userId = 1; // aaa = editor
		userId = 2; //bbb = admin
//		userId = 3; // ccc = root

		List<Role> roleList = roleMapper.findRoleByUserId(userId);
		System.out.println("testFindRoleByUserId::roleList=" + roleList);

		if (roleList != null && roleList.size() > 0) {

			Role role = null;
			List<Menu> menuList = null;
			List<Url> urlList = null;

			for (int i = 0; i < roleList.size(); i++) {

				role = roleList.get(i);
				menuList = role.getMenuList();

				System.out.println("testFindRoleByUserId::role=" + role.getId() + "\t" + role.getName());

				if (menuList != null && menuList.size() > 0) {

					Menu menu = null;

					for (int j = 0; j < menuList.size(); j++) {
						menu = menuList.get(j);
						urlList = menu.getUrlList();
						System.out.println("testFindRoleByUserId::menu=" + menu.getId() + "\t" + menu.getName() + "\t"
								+ menu.getDescription() + "\turl=" + menu.getUrlList());
					}
				}
			}
		}

		assertNotNull(roleList);
	}

	@Test
	public void testAddRole() {
		// TODO
//		roleMapper.addRole(null);
	}

}
