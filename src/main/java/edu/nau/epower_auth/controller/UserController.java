package edu.nau.epower_auth.controller;

import java.util.Date;
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
import edu.nau.epower_auth.common.EncryptUtils;
import edu.nau.epower_auth.common.HtmlUtils;
import edu.nau.epower_auth.common.SessionUtils;
import edu.nau.epower_auth.dao.Role;
import edu.nau.epower_auth.dao.User;
import edu.nau.epower_auth.dao.UserRole;
import edu.nau.epower_auth.service.RoleService;
import edu.nau.epower_auth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 用户控制器
 * 
 * @ClassName: UserController
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-13 03:21:01
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	// logback日志
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/*
	 * 用户列表page
	 */
	@GetMapping("list")
	public String listUser(HttpServletRequest request, @RequestParam(defaultValue = "1") int pageNum,
			ModelMap modelMap) {

		List<User> users = null;

		// 获取当前登录用户
		User loginUser = SessionUtils.getLoginUser(request);

		// 获取当前登录用户的默认角色
		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 添加前端操作按钮的控制对象
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_REQ, "/user");
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_URLS, HtmlUtils.getUrlListByDefRole(defRole));

		// 列表 + 分页，过滤当前登录用户的数据显示
		PageHelper.startPage(pageNum, ConstantUtils.PAGE_SIZE);
		if (defRole.isRoot()) {
			users = userService.listUserNotMe(loginUser.getId());
		} else {
			users = userService.listUserNotMeAndRoot(loginUser.getId(), defRole);
		}
		PageInfo<User> pageInfo = new PageInfo<User>(users);

		modelMap.addAttribute("users", users);
		modelMap.addAttribute(ConstantUtils.PAGE_INFO, pageInfo);

		return "system/user/list";
	}

	/*
	 * 用户添加page
	 */
	@GetMapping("add")
	public String addPage(HttpServletRequest request, ModelMap modelMap) {

		// 获取当前登录用户的默认角色
		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 添加前端操作按钮的控制对象
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_REQ, "/user");
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_URLS, HtmlUtils.getUrlListByDefRole(defRole));

		// 绑定新表单对象，返回前端page
		modelMap.addAttribute("adduser", new User());
		return "system/user/add";
	}

	/*
	 * 用户添加
	 */
	@PostMapping("add")
	public String addUser(User user) {
		if (user != null) {
			user.setPassword(getMd5UserPwd(user));
			user.setCreateTime(new Date());
			int add = userService.addUser(user);
		}
		return "redirect:list";
	}

	/*
	 * 用户更新page
	 */
	@GetMapping("update")
	public String updatePage(HttpServletRequest request, @RequestParam("uid") int userId, ModelMap modelMap) {

		// 获取当前登录用户的默认角色
		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 添加前端操作按钮的控制对象
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_REQ, "/user");
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_URLS, HtmlUtils.getUrlListByDefRole(defRole));

		User user = userService.getUser(userId);
		if (user != null) {
			user.setPassword(null); // 清空密码的显示
		}
		modelMap.addAttribute("updateuser", user);

		return "system/user/update";
	}

	/*
	 * 用户更新
	 */
	@PostMapping("update")
	public String updateUser(User user) {
		if (user != null) {
			user.setPassword(getMd5UserPwd(user)); // MD5加密
			int update = userService.updateUser(user);
		}
		return "redirect:list";
	}

	/*
	 * 用户删除
	 */
	@GetMapping("remove")
	public String removeUser(@RequestParam("uid") int userId) {
		int remove = userService.removeUser(userId);
		return "redirect:list";
	}

	/*
	 * 用户授权page
	 */
	@GetMapping("auth")
	public String authPage(HttpServletRequest request, @RequestParam("uid") int userId, ModelMap modelMap) {

		List<Role> roles = null;

		// 根据user id获取用户
		User user = userService.getUser(userId);
		modelMap.addAttribute("user", user);

		// 判断当前登录用户的默认角色，是否为root
		Role defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 添加前端操作按钮的控制对象
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_REQ, "/user");
		modelMap.addAttribute(ConstantUtils.PAGE_VERIFY_URLS, HtmlUtils.getUrlListByDefRole(defRole));

		// 判断当前登录用户的默认角色，是否为root
		if (defRole.isRoot()) {
			// 获取所有的角色列表
			roles = roleService.listRole();
		} else {
			roles = roleService.listRoleNotRoot(defRole);
		}

		// 返回页面前端的role list
		modelMap.addAttribute("roles", roles);

		// 根据user id获取用户所有角色
		List<Role> userRoles = roleService.findRoleByUserId(userId);
		modelMap.addAttribute("userroles", userRoles);

		// 用user id创建映射对象，返回前端并绑定表单
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		modelMap.addAttribute("addrole", userRole);

		return "system/user/auth";
	}

	/*
	 * 用户授权添加
	 */
	@PostMapping("auth")
	public String addAuth(UserRole userRole) {

		// 1，先检查用户角色是否存在
		UserRole ur = userService.getUserRole(userRole);
		if (ur != null) {
			// 2，已存在用户角色，先删除该用户角色
			int removeRole = userService.removeUserRoleAuth(userRole);
		}

		// 3，再添加新的角色
		int authRole = userService.addUserRoleAuth(userRole);

		// 4，重定向返回auth列表
		return "redirect:auth?uid=" + userRole.getUserId();
	}

	/*
	 * 用户授权删除
	 */
	@GetMapping("removeauth")
	public String removeAuth(@RequestParam("uid") int userId, @RequestParam("rid") int roleId) {
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);
		int remove = userService.removeUserRoleAuth(userRole);
		return "redirect:auth?uid=" + userId;
	}

	/*
	 * MD5加密用户密码
	 */
	private String getMd5UserPwd(User user) {
		return EncryptUtils.getMD5(user.getPassword());
	}

}
