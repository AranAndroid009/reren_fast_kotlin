/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.modules.oss.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data
import java.io.Serializable
import java.util.*

/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("sys_oss")
class SysOssEntity : Serializable {
    @TableId
    private val id: Long? = null

    //URL地址
    internal var url: String? = null

    //创建时间
    var createDate: Date? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}