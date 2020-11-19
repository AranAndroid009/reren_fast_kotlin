/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.config

import io.renren.common.xss.XssFilter
import org.apache.shiro.web.servlet.ShiroFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.DelegatingFilterProxy
import javax.servlet.DispatcherType
import javax.servlet.Filter

/**
 * Filter配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
class FilterConfig {
    @Bean
    fun shiroFilterRegistration(): FilterRegistrationBean<*>? {
        val registration: FilterRegistrationBean<*> = FilterRegistrationBean<Filter?>().apply {
            setFilter(DelegatingFilterProxy("shiroFilter"))
            //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
            addInitParameter("targetFilterLifecycle", "true")
        }
        registration.isEnabled = true
        registration.order = Int.MAX_VALUE - 1
        registration.addUrlPatterns("/*")
        return registration
    }

    @Bean
    fun xssFilterRegistration(): FilterRegistrationBean<*>? {
        val registration: FilterRegistrationBean<Filter> = FilterRegistrationBean()
        registration.setDispatcherTypes(DispatcherType.REQUEST)
        registration.setFilter(XssFilter())
        registration.addUrlPatterns("/*")
        registration.setName("xssFilter")
        registration.order = Int.MAX_VALUE
        return registration
    }
}