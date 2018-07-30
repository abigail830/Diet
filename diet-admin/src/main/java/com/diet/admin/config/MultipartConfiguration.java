package com.diet.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * 上传文件大小限制
 * @author LiuYu
 * @date 2017/12/18
 */
@Configuration
public class MultipartConfiguration {

    @Value("${spring.servlet.multipart.maxFileSize}")
    private String maxFileSize;

    @Value("${spring.servlet.multipart.maxRequestSize}")
    private String maxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(maxFileSize);
        factory.setMaxRequestSize(maxRequestSize);
        return factory.createMultipartConfig();
    }

}
