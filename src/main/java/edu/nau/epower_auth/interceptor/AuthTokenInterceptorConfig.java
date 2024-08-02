package edu.nau.epower_auth.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册token拦截器配置
 * 
 * @ClassName: AuthTokenInterceptorConfig
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-08-01 07:57:29
 */
@Configuration
public class AuthTokenInterceptorConfig implements WebMvcConfigurer {

	@Autowired
	AuthTokenInterceptor authTokenInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(authTokenInterceptor).addPathPatterns("/index").addPathPatterns("/user/**")
				.addPathPatterns("/role/**").addPathPatterns("/menu/**").addPathPatterns("/url/**")
				.excludePathPatterns("/login", "/rolechange", "/logout", "/error") // 放行login, logout
//			.excludePathPatterns("/index.html", "/login.html") // 放行index.html, login.html
				.excludePathPatterns("/css/**", "/js/**", "/pic/**"); // 放行static下的css, js, pic等静态资源文件

	}

}
