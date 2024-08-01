package edu.nau.epower_auth.common;

import java.util.List;

import edu.nau.epower_auth.dao.Role;
import edu.nau.epower_auth.dao.User;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Session工具类
 * 
 * @ClassName: SessionUtils
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-13 03:38:17
 */
public class SessionUtils {

	/*
	 * 获取session中的用户登录信息
	 */
	public static User getLoginUser(HttpServletRequest req) {
		return (User) req.getSession().getAttribute(ConstantUtils.SESSION_LOGIN_USER);
	}

	/*
	 * 获取session中的用户所有角色
	 */
	public static List<Role> getLoginUserRoleList(HttpServletRequest req) {
		return (List<Role>) req.getSession().getAttribute(ConstantUtils.SESSION_LOGIN_USER_ROLES);
	}

	/*
	 * 更新session中用户的默认角色
	 */
	public static void setDefRoleSession(HttpServletRequest req, Role defRole) {
		req.getSession().setAttribute(ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE, defRole);
	}

	public static void updateSession(HttpServletRequest req, String sessionId, Object object) {
		req.getSession().setAttribute(sessionId, object);
	}

	public static Object retrieveSession(HttpServletRequest req, String sessionId) {
		return req.getSession().getAttribute(sessionId);
	}

}
