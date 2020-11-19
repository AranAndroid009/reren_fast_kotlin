/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.common.utils

import java.util.*

/**
 * Map工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
class MapUtils : HashMap<String?, Any?>() {
    override fun put(key: String?, value: Any?): MapUtils? {
        super.put(key, value)
        return this
    }
}