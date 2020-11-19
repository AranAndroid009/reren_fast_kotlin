/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.modules.job.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.renren.common.utils.PageUtils
import io.renren.common.utils.Query
import io.renren.modules.job.dao.ScheduleJobLogDao
import io.renren.modules.job.entity.ScheduleJobLogEntity
import io.renren.modules.job.service.ScheduleJobLogService
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service

@Service("scheduleJobLogService")
class ScheduleJobLogServiceImpl : ServiceImpl<ScheduleJobLogDao?, ScheduleJobLogEntity?>(), ScheduleJobLogService {
    override fun queryPage(params: Map<String?, Any?>?): PageUtils? {
        val jobId = params!!["jobId"] as String?
        val page = this.page(
                Query<ScheduleJobLogEntity?>().getPage(params as MutableMap<String?, Any?>),
                QueryWrapper<ScheduleJobLogEntity?>().like(StringUtils.isNotBlank(jobId), "job_id", jobId)
        )
        return PageUtils(page)
    }
}