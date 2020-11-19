/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.modules.sys.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.renren.common.utils.PageUtils
import io.renren.common.utils.Query
import io.renren.modules.sys.dao.SysLogDao
import io.renren.modules.sys.entity.SysLogEntity
import io.renren.modules.sys.service.SysLogService
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service

@Service("sysLogService")
class SysLogServiceImpl : ServiceImpl<SysLogDao?, SysLogEntity?>(), SysLogService {
    override fun queryPage(params: Map<String?, Any?>?): PageUtils? {
        val key = params!!["key"] as String?
        val page = this.page(
                Query<SysLogEntity?>().getPage(params as MutableMap<String?, Any?>),
                QueryWrapper<SysLogEntity?>().like(StringUtils.isNotBlank(key), "username", key)
        )
        return PageUtils(page)
    }
}