/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.modules.oss.cloud

import io.renren.common.validator.group.AliyunGroup
import io.renren.common.validator.group.QcloudGroup
import io.renren.common.validator.group.QiniuGroup
import lombok.Data
import org.hibernate.validator.constraints.Range
import org.hibernate.validator.constraints.URL
import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * 云存储配置信息
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
class CloudStorageConfig : Serializable {
    //类型 1：七牛  2：阿里云  3：腾讯云
    val type: @Range(min = 1, max = 3, message = "类型错误") Int? = null

    //七牛绑定的域名
    val qiniuDomain: @NotBlank(message = "七牛绑定的域名不能为空", groups = [QiniuGroup::class]) @URL(message = "七牛绑定的域名格式不正确", groups = [QiniuGroup::class]) String? = null

    //七牛路径前缀
    val qiniuPrefix: String? = null

    //七牛ACCESS_KEY
    val qiniuAccessKey: @NotBlank(message = "七牛AccessKey不能为空", groups = [QiniuGroup::class]) String? = null

    //七牛SECRET_KEY
    val qiniuSecretKey: @NotBlank(message = "七牛SecretKey不能为空", groups = [QiniuGroup::class]) String? = null

    //七牛存储空间名
    val qiniuBucketName: @NotBlank(message = "七牛空间名不能为空", groups = [QiniuGroup::class]) String? = null

    //阿里云绑定的域名
    val aliyunDomain: @NotBlank(message = "阿里云绑定的域名不能为空", groups = [AliyunGroup::class]) @URL(message = "阿里云绑定的域名格式不正确", groups = [AliyunGroup::class]) String? = null

    //阿里云路径前缀
    val aliyunPrefix: String? = null

    //阿里云EndPoint
    val aliyunEndPoint: @NotBlank(message = "阿里云EndPoint不能为空", groups = [AliyunGroup::class]) String? = null

    //阿里云AccessKeyId
    val aliyunAccessKeyId: @NotBlank(message = "阿里云AccessKeyId不能为空", groups = [AliyunGroup::class]) String? = null

    //阿里云AccessKeySecret
    val aliyunAccessKeySecret: @NotBlank(message = "阿里云AccessKeySecret不能为空", groups = [AliyunGroup::class]) String? = null

    //阿里云BucketName
    val aliyunBucketName: @NotBlank(message = "阿里云BucketName不能为空", groups = [AliyunGroup::class]) String? = null

    //腾讯云绑定的域名
    val qcloudDomain: @NotBlank(message = "腾讯云绑定的域名不能为空", groups = [QcloudGroup::class]) @URL(message = "腾讯云绑定的域名格式不正确", groups = [QcloudGroup::class]) String? = null

    //腾讯云路径前缀
    val qcloudPrefix: String? = null

    //腾讯云AppId
    val qcloudAppId: @NotNull(message = "腾讯云AppId不能为空", groups = [QcloudGroup::class]) Long? = null

    //腾讯云SecretId
    val qcloudSecretId: @NotBlank(message = "腾讯云SecretId不能为空", groups = [QcloudGroup::class]) String? = null

    //腾讯云SecretKey
    val qcloudSecretKey: @NotBlank(message = "腾讯云SecretKey不能为空", groups = [QcloudGroup::class]) String? = null

    //腾讯云BucketName
    val qcloudBucketName: @NotBlank(message = "腾讯云BucketName不能为空", groups = [QcloudGroup::class]) String? = null

    //腾讯云COS所属地区
    val qcloudRegion: @NotBlank(message = "所属地区不能为空", groups = [QcloudGroup::class]) String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}