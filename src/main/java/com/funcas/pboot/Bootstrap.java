package com.funcas.pboot;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.funcas.pboot.common.util.FastJsonUtil;
import com.funcas.pboot.module.upms.entity.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年10月30日
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@MapperScan("com.funcas.pboot.module.*.mapper")
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Bootstrap {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    public static void main(String[] args) {

        SpringApplication.run(Bootstrap.class, args);

    }
}
