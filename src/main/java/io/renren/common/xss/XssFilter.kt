/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.common.xss

import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

/**
 * XSS过滤
 *
 * @author Mark sunlightcs@gmail.com
 */
class XssFilter : Filter {
    @Throws(ServletException::class)
    override fun init(config: FilterConfig) {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val xssRequest = XssHttpServletRequestWrapper(
                request as HttpServletRequest)
        chain.doFilter(xssRequest, response)
    }

    override fun destroy() {}
}