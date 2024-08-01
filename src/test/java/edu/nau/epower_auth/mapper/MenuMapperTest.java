package edu.nau.epower_auth.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.nau.epower_auth.dao.Menu;

/**
 * 菜单映射单元测试
 * 
 * @ClassName: MenuMapperTest
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-06 09:28:04
 */
@SpringBootTest
public class MenuMapperTest {

	@Autowired
	private MenuMapper menuMapper;

	@Test
	public void testFindMenuByRoleId() {

		int roleId = 0;
		roleId = 1; // role=admin
//		roleId = 2; //role=root
//		roleId = 3; //editor

		List<Menu> menuList = menuMapper.findMenuByRoleId(roleId);
		if (menuList != null && menuList.size() > 0) {
			for (int i = 0; i < menuList.size(); i++) {
				System.out.println("testFindMenuByRoleId::menuList=" + menuList.get(i));
			}
		}

		assertNotNull(menuList);
	}

}
