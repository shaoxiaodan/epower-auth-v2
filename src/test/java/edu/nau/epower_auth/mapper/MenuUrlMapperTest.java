package edu.nau.epower_auth.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.nau.epower_auth.dao.MenuUrl;

/**
 * 菜单-资源路径单元测试
 * 
 * @ClassName: MenuUrlMapperTest
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-07 07:30:26
 */
@SpringBootTest
public class MenuUrlMapperTest {

	@Autowired
	private MenuUrlMapper menuUrlMapper;

	@Test
	public void testInsertMenuUrl() {

		int menuId = 0;
		int urlId = 0;

//		menuId = 1; // 用户管理
//		menuId = 2; // 角色管理
//		menuId = 3; // 菜单管理
//		menuId = 4; // URL管理
//		menuId = 5; // 用户授权
//		menuId = 6; // 角色授权
		menuId = 8; // 测试菜单1

//		urlId = 1; // url=/sys/user/list
//		urlId = 2; // url=/sys/user/add
//		urlId = 3; // url=/sys/user/update
//		urlId = 4; // url=/sys/user/delete
//		urlId = 13; // url=/sys/url/list
//		urlId = 14; // url=/sys/url/add
//		urlId = 15; // url=/sys/url/update
//		urlId = 16; // url=/sys/url/delete
//		urlId = 19; // url=/test1
//		urlId = 20; // url=/test2
//		urlId = 21; // url=/test3
		urlId = 22; // url=/test4

		MenuUrl menuUrl = new MenuUrl();
		menuUrl.setMenuId(menuId);
		menuUrl.setUrlId(urlId);

		int insert = menuUrlMapper.insertMenuUrl(menuUrl);
		System.out.println("testInsertMenuUrl::insert=" + insert);
		assertEquals(1, insert);
	}

	@Test
	public void testDeleteMenuUrl() {

		int menuId = 0;
		int urlId = 0;

//		menuId = 1; // 用户管理
//		menuId = 2; // 角色管理
//		menuId = 3; // 菜单管理
//		menuId = 4; // URL管理
//		menuId = 5; // 用户授权
//		menuId = 6; // 角色授权
		menuId = 8; // 测试菜单1

//		urlId = 1; // url=/sys/user/list
//		urlId = 2; // url=/sys/user/add
//		urlId = 3; // url=/sys/user/update
//		urlId = 4; // url=/sys/user/delete
//		urlId = 13; // url=/sys/url/list
//		urlId = 14; // url=/sys/url/add
//		urlId = 15; // url=/sys/url/update
//		urlId = 16; // url=/sys/url/delete
//		urlId = 19; // url=/test1
//		urlId = 20; // url=/test2
//		urlId = 21; // url=/test3
		urlId = 22; // url=/test4

		MenuUrl menuUrl = new MenuUrl();
		menuUrl.setMenuId(menuId);
		menuUrl.setUrlId(urlId);

		int delete = menuUrlMapper.deleteMenuUrl(menuUrl);
		System.out.println("testDeleteMenuUrl::delete=" + delete);
		assertEquals(1, delete);
	}

	@Test
	public void testInserMenuUrlBatch() {

		int menuId = 0;
		int urlId = 0;

//		menuId = 9; // menu-100
//		menuId = 10; // menu-200

//		urlId = 1; // url=/sys/user/list
//		urlId = 2; // url=/sys/user/add
//		urlId = 3; // url=/sys/user/update
//		urlId = 4; // url=/sys/user/delete
//		urlId = 13; // url=/sys/url/list
//		urlId = 14; // url=/sys/url/add
//		urlId = 15; // url=/sys/url/update
//		urlId = 16; // url=/sys/url/delete

		MenuUrl menUrl1 = new MenuUrl();
		menUrl1.setMenuId(9);
		menUrl1.setUrlId(1);

		MenuUrl menUrl2 = new MenuUrl();
		menUrl2.setMenuId(9);
		menUrl2.setUrlId(2);

		List<MenuUrl> muList = new ArrayList<MenuUrl>();
		muList.add(menUrl1);
		muList.add(menUrl2);

		int insert = menuUrlMapper.insertMenuUrlBatch(muList);
		System.out.println("testInserMenuUrlBatch::insert=" + insert);
		assertEquals(1, insert);

	}

	@Test
	public void testDeleteMenuUrlBatch() {

		int menuId = 0;
		int urlId = 0;

//		menuId = 9; // menu-100
		menuId = 10; // menu-200

		urlId = 1; // url=/sys/user/list
//		urlId = 2; // url=/sys/user/add
//		urlId = 3; // url=/sys/user/update
//		urlId = 4; // url=/sys/user/delete
//		urlId = 13; // url=/sys/url/list
//		urlId = 14; // url=/sys/url/add
//		urlId = 15; // url=/sys/url/update
//		urlId = 16; // url=/sys/url/delete

		MenuUrl menUrl = new MenuUrl();
		menUrl.setMenuId(menuId);
		menUrl.setUrlId(urlId);

		List<MenuUrl> muList = new ArrayList<MenuUrl>();
		muList.add(menUrl);

		int delete = menuUrlMapper.deleteMenuUrlBatch(muList);
		System.out.println("deleteMenuUrlBatch::delete=" + delete);
		assertEquals(1, delete);

	}
}
