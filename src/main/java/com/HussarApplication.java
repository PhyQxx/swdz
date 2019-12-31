/*
 * 金现代轻骑兵V8开发平台
 * HussarApplication.java
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SpringBoot方式启动类
 *
 * @author WangBinBin
 * @Date 2018/2/7 12:06
 */

@ServletComponentScan
@SpringBootApplication(scanBasePackages = {"com"}, exclude = {SecurityAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class})
@MapperScan(basePackages = {"com.jxdinfo.hussar.**.dao"})
public class HussarApplication extends SpringBootServletInitializer implements WebMvcConfigurer {

    protected static final Logger logger = LoggerFactory.getLogger(HussarApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(HussarApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(HussarApplication.class, args);
        logger.info("com.HussarApplication is success!");
    }




}
