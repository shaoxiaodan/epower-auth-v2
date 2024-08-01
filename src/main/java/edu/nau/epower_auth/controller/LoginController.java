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
import org.thymeleaf.util.ListUtils;

import edu.nau.epower_auth.common.ConstantUtils;
import edu.nau.epower_auth.common.EncryptUtils;
import edu.nau.epower_auth.common.SessionUtils;
import edu.nau.epower_auth.dao.Role;
import edu.nau.epower_auth.dao.User;
import edu.nau.epower_auth.service.LoginService;
import edu.nau.epower_auth.service.RoleService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * 登录控制器
 * 
 * @ClassName: LoginController
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-12 01:49:15
 */
@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private RoleService roleService;

	// logback日志
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	/**
	 * 登录页面
	 * 
	 * @param modelMap
	 * @return
	 */
	@GetMapping("login")
	public String loginPage(ModelMap modelMap) {

		// 创建登录用户绑定对象
		modelMap.put("user", new User());
		modelMap.addAttribute("msg", "请开始登录。");
		System.out.println(">>> LoginController::loginPage...");
		return "login";
	}

	/*
	 * 登录验证
	 */
	@PostMapping("login")
	public String loginCheck(HttpServletRequest req, User user, ModelMap modelMap) {

		User loginUser = null;
		List<Role> userRoleList = null;
		String msgStr = "";
		String urlStr = "login"; // 登录页面

		// 检查用户输入
		if (user != null) {
			// 用户输入都不为空
			if (StringUtils.isNotEmpty(user.getUsername()) && StringUtils.isNotEmpty(user.getPassword())) {

				// MD5加密转换
				String md5Pwd = EncryptUtils.getMD5(user.getPassword());

				// 查询登录用户
//				loginUser = loginService.findUserByUserNameAndPwd(user.getUsername(), user.getPassword());
				loginUser = loginService.findUserByUserNameAndPwd(user.getUsername(), md5Pwd); // MD5加密

				// 用户已登录
				if (loginUser != null) {
					// 获取用户所有的角色
					userRoleList = roleService.findRoleByUserId(loginUser.getId());

					if (!ListUtils.isEmpty(userRoleList)) {

						// 保存登录用户信息session
						SessionUtils.updateSession(req, ConstantUtils.SESSION_LOGIN_USER, loginUser); // 保存用户的登录信息
						SessionUtils.updateSession(req, ConstantUtils.SESSION_LOGIN_USER_ROLES, userRoleList); // 保存用户所有的角色

						urlStr = "redirect:index"; // 重定向到index
					} else {
						user.setUsername("");
						user.setPassword("");
						msgStr = "当前用户无角色分配，请与管理员联系。";
					}

				} else {
					user.setUsername("");
					user.setPassword("");
					msgStr = "请输入正确的用户名或密码。";
				}

			} else {
				user.setUsername("");
				user.setPassword("");
				msgStr = "请输入用户名或密码。";
			}

		} else {
			user.setUsername("");
			user.setPassword("");
			msgStr = "请输入登录信息。";
		}

		modelMap.put("msg", msgStr);
		return urlStr;
	}

	/*
	 * 注销登录
	 */
	@GetMapping("logout")
	public String logOut(HttpServletRequest req) {

		HttpSession session = req.getSession();
		session.invalidate();
		System.out.println("退出登录");
		return "redirect:login";
	}

}
