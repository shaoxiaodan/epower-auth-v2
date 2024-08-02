package edu.nau.epower_auth.interceptor;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import edu.nau.epower_auth.common.JwtUtils;
import edu.nau.epower_auth.common.ThreadLocalUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * token验证拦截器
 * 
 * @ClassName: AuthTokenInterceptor
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-08-01 07:57:06
 */
@Component
public class AuthTokenInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 获取令牌token
		String token = request.getHeader("Authorization");

		// 验证令牌token
		try {
			Map<String, Object> claims = JwtUtils.parseToken(token);

			// 将业务数据存储到ThreadLoca中
			ThreadLocalUtils.set(claims);

			// 放行
			return true;
		} catch (Exception e) {
			// 设置状态码401
			response.setStatus(401);

			// 不放行
			return false;
		}

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 清空ThreadLocal中的数据，防止内存泄露
		ThreadLocalUtils.remove();
	}

}
