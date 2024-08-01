package edu.nau.epower_auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.nau.epower_auth.dao.Role;
import edu.nau.epower_auth.dao.RoleMenu;
import edu.nau.epower_auth.mapper.RoleMapper;
import edu.nau.epower_auth.mapper.RoleMenuMapper;
import edu.nau.epower_auth.service.RoleService;

/**
 * 角色服务接口实现
 * 
 * @ClassName: RoleServiceImpl
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-07 12:31:34
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RoleMenuMapper roleMenuMapper;

	/*
	 * 角色列表
	 */
	@Override
	public List<Role> listRole() {
		return roleMapper.listRole();
	}

	/*
	 * 角色列表 (非Root角色，只能显示自己当前和以下级别的role数据)
	 */
	public List<Role> listRoleNotRoot(Role defRole) {
		return roleMapper.listRoleNotRoot(defRole);
	}

	/*
	 * 获取角色
	 */
	@Override
	public Role getRole(int roleId) {
		return roleMapper.findRole(roleId);
	}

	/*
	 * 获取角色by user id
	 */
	@Override
	public List<Role> findRoleByUserId(int userId) {
		return roleMapper.findRoleByUserId(userId);
	}

	/*
	 * 添加角色
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int addRole(Role role) {
		return roleMapper.addRole(role);
	}

	/*
	 * 更新角色
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateRole(Role role) {
		return roleMapper.updateRole(role);
	}

	/*
	 * 删除角色
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int removeRole(int roleId) {
		return roleMapper.removeRole(roleId);
	}

	/*
	 * 检查角色菜单是否存在
	 */
	@Override
	public RoleMenu getRoleMenu(RoleMenu roleMenu) {
		return roleMenuMapper.getRoleMenu(roleMenu);
	}

	/*
	 * 添加角色-菜单授权
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int addRoleMenuAuth(RoleMenu roleMenu) {
		return roleMenuMapper.insertRoleMenu(roleMenu);
	}

	/*
	 * 删除角色-菜单授权
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int removeRoleMenuAuth(RoleMenu roleMenu) {
		return roleMenuMapper.deleteRoleMenu(roleMenu);
	}

}
