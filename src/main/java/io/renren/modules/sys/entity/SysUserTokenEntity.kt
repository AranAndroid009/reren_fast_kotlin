/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.modules.sys.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data
import java.io.Serializable
import java.util.*

/**
 * 系统用户Token
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("sys_user_token")
class SysUserTokenEntity : Serializable {
    //用户ID
    @TableId(type = IdType.INPUT)
    var userId: Long? = null

    //token
    var token: String? = null

    //过期时间
    var expireTime: Date? = null

    //更新时间
    var updateTime: Date? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}