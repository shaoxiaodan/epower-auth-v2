package edu.nau.epower_auth.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 资源访问的鉴权拦截器配置
 * 
 * @ClassName: AuthInterceptorConfig
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-08 05:06:15
 */
@Configuration
public class AuthInterceptorConfig implements WebMvcConfigurer {

	@Autowired
	AuthInterceptor authInterceptor;

	/**
	 * 重写拦截器方法，定义拦截路径
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// 注册鉴权过滤器
//		registry.addInterceptor(authInterceptor).addPathPatterns("/**") // 拦截所有请求
		registry.addInterceptor(authInterceptor)
			.addPathPatterns("/index")
			.addPathPatterns("/user/**")
			.addPathPatterns("/role/**")
			.addPathPatterns("/menu/**")
			.addPathPatterns("/url/**")
				.excludePathPatterns("/login", "/rolechange", "/logout", "/error") // 放行login, logout
//				.excludePathPatterns("/index.html", "/login.html") // 放行index.html, login.html
				.excludePathPatterns("/css/**", "/js/**", "/pic/**"); // 放行static下的css, js, pic等静态资源文件
	}

	/**
	 * 重写资源拦截器方法，添加静态文件路径
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/", "classpath:/templates/");
	}

}
