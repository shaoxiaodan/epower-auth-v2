package edu.nau.epower_auth.common;

/**
 * 常量工具类
 * 
 * @ClassName: ConstantUtils
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-13 03:59:32
 */
public class ConstantUtils {

	// session中的登录用户信息
	public static final String SESSION_LOGIN_USER = "loginuser";

	// session中的登录用户所有角色
	public static final String SESSION_LOGIN_USER_ROLES = "userroles";

	// session中的登录用户默认角色
	public static final String SESSION_LOGIN_USER_DEF_ROLE = "defrole";

	// session中的登录用户可访问URL
	public static final String SESSION_LOGIN_USER_URLS = "userurls";

	// 每页的显示数据量
	public static final int PAGE_SIZE = 5;

	// 分页获取的名称
	public static final String PAGE_INFO = "pageinfo";

	// 用于页面校验请求的key
	public static final String PAGE_VERIFY_REQ = "verifyreq";

	// 用于页面校验URL的key
	public static final String PAGE_VERIFY_URLS = "verifyurls";

}
