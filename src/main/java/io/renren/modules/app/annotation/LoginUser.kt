/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.modules.app.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * 登录用户信息
 *
 * @author Mark sunlightcs@gmail.com
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
annotation class LoginUser 