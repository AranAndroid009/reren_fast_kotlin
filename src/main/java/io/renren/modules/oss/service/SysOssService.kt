/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.modules.oss.service

import com.baomidou.mybatisplus.extension.service.IService
import io.renren.common.utils.PageUtils
import io.renren.modules.oss.entity.SysOssEntity

/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
interface SysOssService : IService<SysOssEntity?> {
    fun queryPage(params: Map<String?, Any?>?): PageUtils?
}