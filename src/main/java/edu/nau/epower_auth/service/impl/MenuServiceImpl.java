package edu.nau.epower_auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.nau.epower_auth.dao.Menu;
import edu.nau.epower_auth.dao.MenuUrl;
import edu.nau.epower_auth.mapper.MenuMapper;
import edu.nau.epower_auth.mapper.MenuUrlMapper;
import edu.nau.epower_auth.service.MenuService;

/**
 * 菜单服务接口实现
 * 
 * @ClassName: MenuServiceImpl
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-07 11:42:29
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private MenuUrlMapper menuUrlMapper;

	/*
	 * 菜单列表
	 */
	@Override
	public List<Menu> listMenu() {
		return menuMapper.listMenu();
	}

	/*
	 * 获取菜单
	 */
	@Override
	public Menu getMenu(int menuId) {
		return menuMapper.findMenu(menuId);
	}

	/*
	 * 根据role id获取所有菜单
	 */
	@Override
	public List<Menu> findMenuByRoleId(int roleId) {
		return menuMapper.findMenuByRoleId(roleId);
	}

	/*
	 * 添加菜单
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int addMenu(Menu menu) {
		return menuMapper.insertMenu(menu);
	}

	/*
	 * 更新菜单
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateMenu(Menu menu) {
		return menuMapper.updateMenu(menu);
	}

	/*
	 * 删除菜单
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int removeMenu(int menuId) {
		return menuMapper.deleteMenu(menuId);
	}

	/*
	 * 检查菜单-URL是否存在
	 */
	@Override
	public List<MenuUrl> getMenuUrl(int menuId) {
		return menuUrlMapper.getMenuUrl(menuId);
	}

	/*
	 * 添加菜单-URL授权
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int addMenuUrlAuth(MenuUrl menuUrl) {
		return menuUrlMapper.insertMenuUrl(menuUrl);
	}

	/*
	 * 删除菜单-资源路径授权
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int removeMenuUrlAuth(MenuUrl menuUrl) {
		return menuUrlMapper.deleteMenuUrl(menuUrl);
	}

	/*
	 * （批量）添加菜单-URL授权
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int addMenuUrlAuthBatch(List<MenuUrl> menuUrlList) {
		return menuUrlMapper.insertMenuUrlBatch(menuUrlList);
	}

	/*
	 * （批量）删除菜单-资源路径授权
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int removeMenuUrlAuthBatch(List<MenuUrl> menuUrlList) {
		return menuUrlMapper.deleteMenuUrlBatch(menuUrlList);
	}

}
