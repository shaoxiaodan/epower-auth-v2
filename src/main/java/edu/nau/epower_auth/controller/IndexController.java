package edu.nau.epower_auth.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.ListUtils;

import edu.nau.epower_auth.common.ConstantUtils;
import edu.nau.epower_auth.common.SessionUtils;
import edu.nau.epower_auth.dao.Menu;
import edu.nau.epower_auth.dao.Role;
import edu.nau.epower_auth.service.MenuService;
import edu.nau.epower_auth.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 主页控制器
 * 
 * @ClassName: IndexController
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-13 03:03:23
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private UrlService urlService;

	// logback日志
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	/*
	 * 首页page
	 */
	@GetMapping("index")
	public String indexPage(HttpServletRequest req) {

		Role defRole = null;
		List<Role> roleList = null;

		// 读取session中的默认角色 & role list
		defRole = (Role) SessionUtils.retrieveSession(req, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);
		roleList = (List<Role>) SessionUtils.retrieveSession(req, ConstantUtils.SESSION_LOGIN_USER_ROLES);

		// 当前用户没有默认角色
		if (defRole == null) {
			// 拿role list中的第一个，作为用户的默认role
			defRole = roleList.get(0);
			defRole = verifyDefRolet4Root(defRole);

			// 保存默认role
			SessionUtils.updateSession(req, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE, defRole);
		}

		// 渲染首页
		return "system/index";
	}

	/*
	 * 切换用户角色
	 */
//	@PostMapping("rolechange")  // 原始的用户角色切换
	@GetMapping("rolechange")
	public String userRoleChg(HttpServletRequest req, @RequestParam(name = "def", defaultValue = "0") int roleId) {

		Role defRole = null; // 用户的默认角色
		List<Role> roleList = null; // 用户的role list

		// 1，读取session中的role list
		roleList = (List<Role>) SessionUtils.retrieveSession(req, ConstantUtils.SESSION_LOGIN_USER_ROLES);

		// 2，检查角色的roleId
		if (roleId > 0) {
			for (Role role : roleList) {
				// 3，找到对应的角色
				if (role.getId() == roleId) {
					defRole = role;
					break; // 跳出当前循环
				}
			}
		} else {
			// 4，设置一个默认的角色
			defRole = roleList.get(0);
		}

		// 5，通过人工非法输入def，试图返回不存在的role
		if (defRole == null) {
			// 设置一个非法数值，并传递到前端做处理
			defRole = new Role();
			defRole.setId(-999);
		}

		// 6，当前角色是否为ROOT超级管理员
		defRole = verifyDefRolet4Root(defRole);

		// 7，更新defrole的session
		SessionUtils.updateSession(req, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE, defRole);

		return "redirect:index";
	}

	/*
	 * 重新处理当前默认defrole的菜单配置， 如果当前角色为超级管理员Root，重新配置菜单； 否则，不做任何原有菜单的改变
	 */
	private Role verifyDefRolet4Root(Role defRole) {

		// 如果当前默认role为Root超级管理员
		if (defRole.isRoot()) {
			// 获取所有的菜单list
			List<Menu> menuList = menuService.listMenu();
			if (!ListUtils.isEmpty(menuList)) {
				for (Menu menu : menuList) {
					// 重新为Root超级管理员装配菜单
					menu.setUrlList(urlService.findUrlByMenuId(menu.getId()));
				}
			}
			// 为ROOT角色，重新装配所有menu
			defRole.setMenuList(menuList);
		}

		return defRole;
	}

}
