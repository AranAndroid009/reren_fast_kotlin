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
import io.renren.common.utils.Constant.ScheduleStatus
import io.renren.common.utils.PageUtils
import io.renren.common.utils.Query
import io.renren.modules.job.dao.ScheduleJobDao
import io.renren.modules.job.entity.ScheduleJobEntity
import io.renren.modules.job.service.ScheduleJobService
import io.renren.modules.job.utils.ScheduleUtils
import org.apache.commons.lang.StringUtils
import org.quartz.Scheduler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.annotation.PostConstruct

@Service("scheduleJobService")
class ScheduleJobServiceImpl : ServiceImpl<ScheduleJobDao?, ScheduleJobEntity?>(), ScheduleJobService {
    @Autowired
    private val scheduler: Scheduler? = null

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    fun init() {
        val scheduleJobList = this.list()
        for (scheduleJob in scheduleJobList!!) {
            val cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob!!.jobId)
            //如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob)
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob)
            }
        }
    }

    override fun queryPage(params: Map<String?, Any?>?): PageUtils? {
        val beanName = params!!["beanName"] as String?
        val page = this.page(
                Query<ScheduleJobEntity?>().getPage(params as MutableMap<String?, Any?>),
                QueryWrapper<ScheduleJobEntity?>().like(StringUtils.isNotBlank(beanName), "bean_name", beanName)
        )
        return PageUtils(page)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun saveJob(scheduleJob: ScheduleJobEntity?) {
        scheduleJob!!.createTime = (Date())
        scheduleJob.status = (ScheduleStatus.NORMAL.value)
        save(scheduleJob)
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun update(scheduleJob: ScheduleJobEntity?) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob)
        updateById(scheduleJob)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteBatch(jobIds: Array<Long?>?) {
        for (jobId in jobIds!!) {
            ScheduleUtils.deleteScheduleJob(scheduler, jobId)
        }

        //删除数据
        removeByIds(Arrays.asList(*jobIds))
    }

    override fun updateBatch(jobIds: Array<Long?>?, status: Int): Int {
        val map: MutableMap<String?, Any?> = HashMap(2)
        map["list"] = jobIds
        map["status"] = status
        return baseMapper!!.updateBatch(map)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun run(jobIds: Array<Long?>?) {
        for (jobId in jobIds!!) {
            ScheduleUtils.run(scheduler, getById(jobId))
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun pause(jobIds: Array<Long?>?) {
        for (jobId in jobIds!!) {
            ScheduleUtils.pauseJob(scheduler, jobId)
        }
        updateBatch(jobIds, ScheduleStatus.PAUSE.value)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun resume(jobIds: Array<Long?>?) {
        for (jobId in jobIds!!) {
            ScheduleUtils.resumeJob(scheduler, jobId)
        }
        updateBatch(jobIds, ScheduleStatus.NORMAL.value)
    }
}