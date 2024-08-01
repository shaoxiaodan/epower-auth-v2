package edu.nau.epower_auth.dao;

import java.io.Serializable;

/**
 * 用户-角色关联类
 * 
 * @ClassName: UserRole
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-06 10:19:55
 */
public class UserRole implements Serializable {

	private int id;

	private int userId;

	private int roleId;

	public UserRole() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

}
