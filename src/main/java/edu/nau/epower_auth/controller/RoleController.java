package edu.nau.epower_auth.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import edu.nau.epower_auth.common.ConstantUtils;
import edu.nau.epower_auth.common.HtmlUtils;
import edu.nau.epower_auth.common.SessionUtils;
import edu.nau.epower_auth.dao.Menu;
import edu.nau.epower_auth.dao.Role;
import edu.nau.epower_auth.dao.RoleMenu;
import edu.nau.epower_auth.service.MenuService;
import edu.nau.epower_auth.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 角色控制器
 * 
 * @ClassName: RoleController
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-13 03:15:54
 */
@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;

	// logback日志
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	/*
	 * 角色列表page
	 */
	@GetMapping("list")
	public String listRole(HttpServletRequest request, @RequestParam(defaultValue = "1") int pageNum,
			ModelMap modelMap) {

		// 列表结果集
		List<Role> roles = null;

		// 获取当前登录用户的默认角色
		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 添加前端操作按钮的控制对象
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_REQ, "/role");
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_URLS, HtmlUtils.getUrlListByDefRole(defRole));

		// 列表 + 分页
		PageHelper.startPage(pageNum, ConstantUtils.PAGE_SIZE);

		if (defRole.isRoot()) {
			// 如果是root，显示所有角色
			roles = roleService.listRole();
		} else {
			// 如果非root，只能看到相同级别和以下的角色
			roles = roleService.listRoleNotRoot(defRole);
		}
		PageInfo<Role> pageInfo = new PageInfo<Role>(roles);

		modelMap.addAttribute("roles", roles);
		modelMap.addAttribute(ConstantUtils.PAGE_INFO, pageInfo);

		return "system/role/list";
	}

	/*
	 * 角色添加page
	 */
	@GetMapping("add")
	public String addPage(HttpServletRequest request, ModelMap modelMap) {

		// 获取当前登录用户的默认角色
		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 添加前端操作按钮的控制对象
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_REQ, "/role");
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_URLS, HtmlUtils.getUrlListByDefRole(defRole));

		// 返回表单绑定对象
		modelMap.addAttribute("addrole", new Role());

		return "system/role/add";
	}

	/*
	 * 角色添加
	 */
	@PostMapping("add")
	public String addRole(HttpServletRequest request, Role role) {

		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);
		if (defRole != null) {
			role.setLevel(defRole.getLevel() + 1); // 角色级别 +1
			int add = roleService.addRole(role);
		}

		return "redirect:list";
	}

	/*
	 * 角色更新page
	 */
	@GetMapping("update")
	public String updatePage(HttpServletRequest request, @RequestParam("rid") int roleId, ModelMap modelMap) {

		// 获取当前登录用户的默认角色
		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 添加前端操作按钮的控制对象
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_REQ, "/role");
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_URLS, HtmlUtils.getUrlListByDefRole(defRole));

		// 表单绑定对象
		Role role = roleService.getRole(roleId);
		modelMap.addAttribute("updaterole", role);

		return "system/role/update";
	}

	/*
	 * 角色更新
	 */
	@PostMapping("update")
	public String updateRole(Role role) {
		int update = roleService.updateRole(role);
		return "redirect:list";
	}

	/*
	 * 角色删除
	 */
	@GetMapping("remove")
	public String removeRole(@RequestParam("rid") int roleId) {

		int remove = roleService.removeRole(roleId);
		return "redirect:list";
	}

	/*
	 * 角色授权page
	 */
	@GetMapping("auth")
	public String authPage(HttpServletRequest request, @RequestParam("rid") int roleId, ModelMap modelMap) {

		// 获取当前登录用户的默认角色
		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 添加前端操作按钮的控制对象
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_REQ, "/role");
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_URLS, HtmlUtils.getUrlListByDefRole(defRole));

		// 根据role id获取角色
		Role role = roleService.getRole(roleId);
		modelMap.addAttribute("role", role);

		// 获取菜单列表
		List<Menu> menus = menuService.listMenu();
		modelMap.addAttribute("menus", menus);

		// 根据role id获取角色所有菜单
		List<Menu> roleMenus = menuService.findMenuByRoleId(roleId);
		modelMap.addAttribute("rolemenus", roleMenus);

		// 用role id创建menu映射对象，返回前端并绑定表单
		RoleMenu roleMenu = new RoleMenu();
		roleMenu.setRoleId(roleId);
		modelMap.addAttribute("addmenu", roleMenu);

		return "system/role/auth";
	}

	/*
	 * 角色授权添加
	 */
	@PostMapping("auth")
	public String addAuth(RoleMenu roleMenu) {

		// 1，先检查角色菜单是否存在
		RoleMenu rm = roleService.getRoleMenu(roleMenu);
		if (rm != null) {
			// 2，已存在角色菜单，先删除该角色菜单
			int remove = roleService.removeRoleMenuAuth(roleMenu);
		}

		// 3，再添加新的角色菜单
		int auth = roleService.addRoleMenuAuth(roleMenu);

		// 4，重定向返回auth列表
		return "redirect:auth?rid=" + roleMenu.getRoleId();
	}

	/*
	 * 角色授权删除
	 */
	@GetMapping("removeauth")
	public String removeAuth(@RequestParam("rid") int roleId, @RequestParam("mid") int menuId) {

		RoleMenu roleMenu = new RoleMenu();
		roleMenu.setRoleId(roleId);
		roleMenu.setMenuId(menuId);
		int remove = roleService.removeRoleMenuAuth(roleMenu);
		return "redirect:auth?rid=" + roleId;
	}

}
