/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.common.utils

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.metadata.OrderItem
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import io.renren.common.xss.SQLFilter
import org.apache.commons.lang.StringUtils

/**
 * 查询参数
 *
 * @author Mark sunlightcs@gmail.com
 */
class Query<T> {
    fun getPage(params: MutableMap<String?, Any?>): IPage<T> {
        return this.getPage(params, null, false)
    }

    fun getPage(params: MutableMap<String?, Any?>?, defaultOrderField: String?, isAsc: Boolean): IPage<T> {
        //分页参数
        var curPage: Long = 1
        var limit: Long = 10
        if (params!![Constant.PAGE] != null) {
            curPage = (params[Constant.PAGE] as String)?.toLong()
        }
        if (params[Constant.LIMIT] != null) {
            limit = (params[Constant.LIMIT] as String)?. toLong ()
        }

        //分页对象
        val page = Page<T>(curPage, limit)

        //分页参数
        params[Constant.PAGE] = page

        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        val orderField = SQLFilter.sqlInject(params[Constant.ORDER_FIELD] as String?)
        val order = params[Constant.ORDER] as String?


        //前端字段排序
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            return if (Constant.ASC.equals(order, ignoreCase = true)) {
                page.addOrder(OrderItem.asc(orderField))
            } else {
                page.addOrder(OrderItem.desc(orderField))
            }
        }

        //没有排序字段，则不排序
        if (StringUtils.isBlank(defaultOrderField)) {
            return page
        }

        //默认排序
        if (isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField))
        } else {
            page.addOrder(OrderItem.desc(defaultOrderField))
        }
        return page
    }
}







