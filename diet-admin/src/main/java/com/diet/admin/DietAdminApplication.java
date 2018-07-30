package com.diet.admin;

import com.diet.admin.core.BaseMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * EnableTransactionManagement 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
 *
 * @author LiuYu
 */
@SpringBootApplication
@ServletComponentScan
@EnableCaching
@EnableTransactionManagement
@MapperScan(basePackages = "com.diet.admin.dao", markerInterface = BaseMapper.class)
public class DietAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DietAdminApplication.class, args);
    }
}
