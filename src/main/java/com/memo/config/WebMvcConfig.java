package com.memo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.Interceptor.Permissionlnterceptor;
import com.memo.common.FileManagerService;

@Configuration // 설정을 위한 Spring Bean
public class WebMvcConfig implements WebMvcConfigurer { // 무조건 implements를 추가를 시켜줘야함.

	@Autowired
	private Permissionlnterceptor interceptor;
	
	// 인터셉터 설정
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
		.addInterceptor(interceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/error", "/css/**", "/img/**", "/user/sign-out"); // 모든 주소일때 이 주소를 사용할것이고, 안보고 싶을때는 error를 출력하게 만들것이다.
	}
	
	// 이미지 path와 서버에서 업로드 된 실제 이미지와 매핑 설정, 주소의 이미지하고..
	@Override
	public void addResourceHandlers(
			ResourceHandlerRegistry registry) { // 접근해주겠다라는 객체이다.
		
		// bulider처럼 정렬, **: 자식 패스들 자손 패스들도 모두 포함한다는 의미
		registry 
		.addResourceHandler("/images/**") // web의 path를 의미, http://localhost/images/aaaa_1721209676372/bird-7943041_640.jpg 이런식으로 들어오게 되는것이다. 
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); // 실제 파일이 있는 위치, 실제 이미지 파일 위치, 알아서 매핑 될 것이다. 파일의 위치를 의미할때 file를 사용한다.
		// window : 3개 맥 : 2개
		// FileManagerService.FILE_UPLOAD_PATH을 바로 사용할수있는 이유는 static으로 정의를 해놓았기 때문.
		
	}
	
}
