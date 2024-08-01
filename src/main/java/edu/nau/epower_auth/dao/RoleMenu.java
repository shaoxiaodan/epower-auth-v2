package edu.nau.epower_auth.dao;

import java.io.Serializable;

/**
 * 角色-菜单关联类
 * 
 * @ClassName: RoleMenu
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-06 10:21:45
 */
public class RoleMenu implements Serializable {

	private int id;

	private int roleId;

	private int menuId;

	public RoleMenu() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

}
