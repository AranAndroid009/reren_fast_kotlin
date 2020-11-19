/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.modules.oss.cloud

import io.renren.common.utils.ConfigConstant
import io.renren.common.utils.Constant.CloudService
import io.renren.common.utils.SpringContextUtils
import io.renren.modules.sys.service.SysConfigService

/**
 * 文件上传Factory
 *
 * @author Mark sunlightcs@gmail.com
 */
object OSSFactory {
    private var sysConfigService: SysConfigService? = null
    fun build(): CloudStorageService? {
        //获取云存储配置信息
        val config = sysConfigService!!.getConfigObject<CloudStorageConfig>(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig::class.java)
        if (config!!.type == CloudService.QINIU.value) {
            return QiniuCloudStorageService(config)
        } else if (config.type == CloudService.ALIYUN.value) {
            return AliyunCloudStorageService(config)
        } else if (config.type == CloudService.QCLOUD.value) {
            return QcloudCloudStorageService(config)
        }
        return null
    }

    init {
        sysConfigService = SpringContextUtils.Companion.getBean("sysConfigService") as SysConfigService
    }
}