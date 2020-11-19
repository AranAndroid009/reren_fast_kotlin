/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.modules.oss.controller

import com.google.gson.Gson
import io.renren.common.exception.RRException
import io.renren.common.utils.ConfigConstant
import io.renren.common.utils.Constant.CloudService
import io.renren.common.utils.R
import io.renren.common.validator.ValidatorUtils
import io.renren.common.validator.group.AliyunGroup
import io.renren.common.validator.group.QcloudGroup
import io.renren.common.validator.group.QiniuGroup
import io.renren.modules.oss.cloud.CloudStorageConfig
import io.renren.modules.oss.cloud.OSSFactory
import io.renren.modules.oss.entity.SysOssEntity
import io.renren.modules.oss.service.SysOssService
import io.renren.modules.sys.service.SysConfigService
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("sys/oss")
class SysOssController {
    @Autowired
    private val sysOssService: SysOssService? = null

    @Autowired
    private val sysConfigService: SysConfigService? = null

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:oss:all")
    fun list(@RequestParam params: Map<String?, Any?>?): R? {
        val page = sysOssService!!.queryPage(params)
        return R.Companion.ok().put("page", page)
    }

    /**
     * 云存储配置信息
     */
    @GetMapping("/config")
    @RequiresPermissions("sys:oss:all")
    fun config(): R? {
        val config = sysConfigService!!.getConfigObject<CloudStorageConfig>(KEY, CloudStorageConfig::class.java)
        return R.Companion.ok().put("config", config)
    }

    /**
     * 保存云存储配置信息
     */
    @PostMapping("/saveConfig")
    @RequiresPermissions("sys:oss:all")
    fun saveConfig(@RequestBody config: CloudStorageConfig?): R? {
        //校验类型
        ValidatorUtils.validateEntity(config)
        if (config!!.type == CloudService.QINIU.value) {
            //校验七牛数据
            ValidatorUtils.validateEntity(config, QiniuGroup::class.java)
        } else if (config.type == CloudService.ALIYUN.value) {
            //校验阿里云数据
            ValidatorUtils.validateEntity(config, AliyunGroup::class.java)
        } else if (config.type == CloudService.QCLOUD.value) {
            //校验腾讯云数据
            ValidatorUtils.validateEntity(config, QcloudGroup::class.java)
        }
        sysConfigService!!.updateValueByKey(KEY, Gson().toJson(config))
        return R.Companion.ok()
    }

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    @RequiresPermissions("sys:oss:all")
    @Throws(Exception::class)
    fun upload(@RequestParam("file") file: MultipartFile?): R? {
        if (file!!.isEmpty) {
            throw RRException("上传文件不能为空")
        }

        //上传文件
        val suffix = file.originalFilename!!.substring(file.originalFilename!!.lastIndexOf("."))
        val url = OSSFactory.build()!!.uploadSuffix(file.bytes, suffix)

        //保存文件信息
        val ossEntity = SysOssEntity()
        ossEntity.url = url
        ossEntity.createDate = Date()
        sysOssService!!.save(ossEntity)
        return R.Companion.ok().put("url", url)
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:oss:all")
    fun delete(@RequestBody ids: Array<Long?>?): R? {
        sysOssService!!.removeByIds(Arrays.asList(ids))
        return R.Companion.ok()
    }

    companion object {
        private val KEY: String? = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY
    }
}