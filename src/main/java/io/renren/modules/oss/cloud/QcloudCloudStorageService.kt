/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.modules.oss.cloud

import com.alibaba.fastjson.JSONObject
import com.qcloud.cos.COSClient
import com.qcloud.cos.ClientConfig
import com.qcloud.cos.request.UploadFileRequest
import com.qcloud.cos.sign.Credentials
import io.renren.common.exception.RRException
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream

/**
 * 腾讯云存储
 *
 * @author Mark sunlightcs@gmail.com
 */
class QcloudCloudStorageService(config: CloudStorageConfig?) : CloudStorageService() {
    private var client: COSClient? = null
    private fun init() {
        val credentials = Credentials(config!!.qcloudAppId!!, config!!.qcloudSecretId,
                config!!.qcloudSecretKey)

        //初始化客户端配置
        val clientConfig = ClientConfig()
        //设置bucket所在的区域，华南：gz 华北：tj 华东：sh
        clientConfig.setRegion(config!!.qcloudRegion)
        client = COSClient(clientConfig, credentials)
    }

    override fun upload(data: ByteArray?, path: String?): String? {
        //腾讯云必需要以"/"开头
        var path = path
        if (!path!!.startsWith("/")) {
            path = "/$path"
        }

        //上传到腾讯云
        val request = UploadFileRequest(config!!.qcloudBucketName, path, data)
        val response = client!!.uploadFile(request)
        val jsonObject = JSONObject.parseObject(response)
        if (jsonObject!!.getInteger("code") != 0) {
            throw RRException("文件上传失败，" + jsonObject.getString("message"))
        }
        return config!!.qcloudDomain + path
    }

    override fun upload(inputStream: InputStream?, path: String?): String? {
        return try {
            val data = IOUtils.toByteArray(inputStream)
            this.upload(data, path)
        } catch (e: IOException) {
            throw RRException("上传文件失败", e)
        }
    }

    override fun uploadSuffix(data: ByteArray?, suffix: String?): String? {
        return upload(data, getPath(config!!.qcloudPrefix, suffix))
    }

    override fun uploadSuffix(inputStream: InputStream?, suffix: String?): String? {
        return upload(inputStream, getPath(config!!.qcloudPrefix, suffix))
    }

    init {
        this.config = config

        //初始化
        init()
    }
}