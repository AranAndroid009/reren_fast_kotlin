/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.modules.job.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Data
import java.io.Serializable
import java.util.*
import javax.validation.constraints.NotBlank

/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("schedule_job")
class ScheduleJobEntity : Serializable {
    /**
     * 任务id
     */
    @TableId
    val jobId: Long? = null

    /**
     * spring bean名称
     */
    val beanName: @NotBlank(message = "bean名称不能为空") String? = null

    /**
     * 参数
     */
    val params: String? = null

    /**
     * cron表达式
     */
    val cronExpression: @NotBlank(message = "cron表达式不能为空") String? = null

    /**
     * 任务状态
     */
    var status: Int? = null

    /**
     * 备注
     */
    val remark: String? = null

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var createTime: Date? = null

    companion object {
        private const val serialVersionUID = 1L

        /**
         * 任务调度参数key
         */
        val JOB_PARAM_KEY: String? = "JOB_PARAM_KEY"
    }
}