/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.datasource.aspect

import io.renren.datasource.annotation.DataSource
import io.renren.datasource.config.DynamicContextHolder
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

/**
 * 多数据源，切面处理类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class DataSourceAspect {
    protected var logger = LoggerFactory.getLogger(javaClass)

    @Pointcut("@annotation(io.renren.datasource.annotation.DataSource) " +
            "|| @within(io.renren.datasource.annotation.DataSource)")
    fun dataSourcePointCut() {
    }

    @Around("dataSourcePointCut()")
    @Throws(Throwable::class)
    fun around(point: ProceedingJoinPoint?): Any? {
        val signature = point!!.signature as MethodSignature
        val targetClass: Class<*> = point.target.javaClass
        val method = signature.method
        val targetDataSource = targetClass.getAnnotation(DataSource::class.java) as DataSource
        val methodDataSource = method!!.getAnnotation(DataSource::class.java)
        if (targetDataSource != null || methodDataSource != null) {
            val value: String?
            if (methodDataSource != null) {
                value = methodDataSource.value
            } else {
                value = targetDataSource.value
            }
            DynamicContextHolder.push(value)
            logger!!.debug("set datasource is {}", value)
        }
        return try {
            point.proceed()
        } finally {
            DynamicContextHolder.poll()
            logger!!.debug("clean datasource")
        }
    }
}