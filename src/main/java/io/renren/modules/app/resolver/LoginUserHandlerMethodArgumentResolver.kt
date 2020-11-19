/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.modules.app.resolver

import io.renren.modules.app.annotation.LoginUser
import io.renren.modules.app.entity.UserEntity
import io.renren.modules.app.interceptor.AuthorizationInterceptor
import io.renren.modules.app.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
class LoginUserHandlerMethodArgumentResolver : HandlerMethodArgumentResolver {
    @Autowired
    private val userService: UserService? = null
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter!!.parameterType.isAssignableFrom(UserEntity::class.java) && parameter.hasParameterAnnotation(LoginUser::class.java)
    }

    @Throws(Exception::class)
    override fun resolveArgument(parameter: MethodParameter, @Nullable container: ModelAndViewContainer?, webRequest: NativeWebRequest, @Nullable factory: WebDataBinderFactory?): Any? {
        //获取用户ID
        val `object` = webRequest!!.getAttribute(AuthorizationInterceptor.Companion.USER_KEY!!, RequestAttributes.SCOPE_REQUEST)
                ?: return null

        //获取用户信息
        return userService!!.getById(`object` as Long)
    }
}