/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.config

import io.renren.modules.sys.oauth2.OAuth2Filter
import io.renren.modules.sys.oauth2.OAuth2Realm
import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import javax.servlet.Filter

/**
 * Shiro配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
class ShiroConfig {
    @Bean("securityManager")
    fun securityManager(oAuth2Realm: OAuth2Realm?): SecurityManager? {
        val securityManager = DefaultWebSecurityManager()
        securityManager.setRealm(oAuth2Realm)
        securityManager.rememberMeManager = null
        return securityManager
    }

    @Bean("shiroFilter")
    fun shiroFilter(securityManager: SecurityManager?): ShiroFilterFactoryBean? {
        val shiroFilter = ShiroFilterFactoryBean()
        shiroFilter.securityManager = securityManager

        //oauth过滤
        val filters: MutableMap<String?, Filter?> = HashMap()
        filters["oauth2"] = OAuth2Filter()
        shiroFilter.filters = filters
        val filterMap: MutableMap<String?, String?> = LinkedHashMap()
        filterMap["/webjars/**"] = "anon"
        filterMap["/druid/**"] = "anon"
        filterMap["/app/**"] = "anon"
        filterMap["/sys/login"] = "anon"
        filterMap["/swagger/**"] = "anon"
        filterMap["/v2/api-docs"] = "anon"
        filterMap["/swagger-ui.html"] = "anon"
        filterMap["/swagger-resources/**"] = "anon"
        filterMap["/captcha.jpg"] = "anon"
        filterMap["/aaa.txt"] = "anon"
        filterMap["/**"] = "oauth2"
        shiroFilter.filterChainDefinitionMap = filterMap
        return shiroFilter
    }

    @Bean("lifecycleBeanPostProcessor")
    fun lifecycleBeanPostProcessor(): LifecycleBeanPostProcessor? {
        return LifecycleBeanPostProcessor()
    }

    @Bean
    fun authorizationAttributeSourceAdvisor(securityManager: SecurityManager?): AuthorizationAttributeSourceAdvisor? {
        val advisor = AuthorizationAttributeSourceAdvisor()
        advisor.securityManager = securityManager
        return advisor
    }
}