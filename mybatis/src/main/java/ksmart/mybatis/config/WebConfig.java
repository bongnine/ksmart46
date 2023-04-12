package ksmart.mybatis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ksmart.mybatis.interceptor.CommonInterceptor;
import ksmart.mybatis.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	private final CommonInterceptor commonInterceptor;
	private final LoginInterceptor loginInterceptor;
	public WebConfig(CommonInterceptor commonInterceptor, LoginInterceptor loginInterceptor) {
		this.commonInterceptor =commonInterceptor;
		this.loginInterceptor = loginInterceptor;
	}
@Override
public void addInterceptors(InterceptorRegistry registry) {
	//로깅 인터셉터 등록
	registry.addInterceptor(commonInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/css/**")
			.excludePathPatterns("/js/**")
			.excludePathPatterns("/favicon.ico");
	
	// 로그인 인터셉터 등록
	registry.addInterceptor(loginInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/css/**")
			.excludePathPatterns("/js/**")
			.excludePathPatterns("/favicon.ico")
			.excludePathPatterns("/")
			.excludePathPatterns("/member/addMember")
			.excludePathPatterns("/member/idCheck")
			.excludePathPatterns("/member/login")
			.excludePathPatterns("/member/logout");
	WebMvcConfigurer.super.addInterceptors(registry);
}
}
