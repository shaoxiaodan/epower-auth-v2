package edu.nau.epower_auth.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.thymeleaf.util.ListUtils;

import edu.nau.epower_auth.common.ConstantUtils;
import edu.nau.epower_auth.common.HtmlUtils;
import edu.nau.epower_auth.common.SessionUtils;
import edu.nau.epower_auth.dao.Menu;
import edu.nau.epower_auth.dao.Role;
import edu.nau.epower_auth.dao.Url;
import edu.nau.epower_auth.dao.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 资源访问的鉴权拦截器
 * 
 * @ClassName: AuthInterceptor
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-08 05:06:45
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("preHandle...鉴权开始");

		String msgStr = "";
		String urlStr = "";

		// 1，读取用户登录信息 & 检查用户是否登录
		User loginUser = (User) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER);

		if (loginUser == null) {
			msgStr = "登录超时，请重新登录。";
			urlStr = "/login";
//			writerPrint(response, msgStr, urlStr);
			HtmlUtils.getWebPageAlert(response, msgStr, urlStr);
			return false;
		}

		// 2，放行index
		String currUri = "";
		currUri = request.getRequestURI();
		System.out.println("preHandle...当前请求=" + currUri);
		urlStr = "/index";
		if (currUri.equals(urlStr)) {
			System.out.println("index 放行...");
			return true;
		}

		// 3，读取用户可以访问的URL & 是否有URL访问权限
		boolean isPermitted = false;
		String reqUri = ""; // 请求路径
		Role defRole = null; // 用户的当前角色

		reqUri = request.getRequestURI().toString();
		defRole = (Role) SessionUtils.retrieveSession(request, ConstantUtils.SESSION_LOGIN_USER_DEF_ROLE);

		// 用户的当前角色，是否存在
		if (defRole != null) {
			// 检查用户的当前角色，是否有可以访问的URL权限
			if (!ListUtils.isEmpty(defRole.getMenuList())) {
				for (Menu menu : defRole.getMenuList()) {
					if (!ListUtils.isEmpty(menu.getUrlList())) {
						for (Url url : menu.getUrlList()) {
							// 如果找到URL权限
							if (reqUri.equals(url.getPath())) {
								isPermitted = true; // 设置权限
								break;
							}
						}
					}
				}
			}

			// 4，判断用户操作权限
			if (!isPermitted) {
				msgStr = "当前用户角色无[" + reqUri + "]的访问或执行权限。";
//				writerPrint(response, msgStr, null);
				HtmlUtils.getWebPageAlert(response, msgStr, null);
				return false;
			}
		} else {
			msgStr = "当前用户无分配任何角色，请与管理员确认。";
//			writerPrint(response, msgStr, null);
			HtmlUtils.getWebPageAlert(response, msgStr, null);
			return false;
		}

		System.out.println("preHandle...鉴权结束");
		return true; // 默认放行
	}

}
