package com.diet.admin.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author LiuYu
 * @date 2018/5/11
 */
@Component
@PropertySource(value = {"classpath:config/api-wx-${spring.profiles.active}.properties"}, encoding = "utf-8")
public class PropertiesConfiguration {
}
