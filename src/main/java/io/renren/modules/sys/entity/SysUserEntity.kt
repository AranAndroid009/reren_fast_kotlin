/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.modules.sys.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import io.renren.common.validator.group.AddGroup
import io.renren.common.validator.group.UpdateGroup
import lombok.Data
import java.io.Serializable
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("sys_user")
class SysUserEntity : Serializable {
    /**
     * 用户ID
     */
    @TableId
    val userId: Long? = null

    /**
     * 用户名
     */
    val username: @NotBlank(message = "用户名不能为空", groups = [AddGroup::class, UpdateGroup::class]) String? = null

    /**
     * 密码
     */
    var password: @NotBlank(message = "密码不能为空", groups = [AddGroup::class]) String? = null

    /**
     * 盐
     */
    var salt: String? = null

    /**
     * 邮箱
     */
    var email: @NotBlank(message = "邮箱不能为空", groups = [AddGroup::class, UpdateGroup::class]) @Email(message = "邮箱格式不正确", groups = [AddGroup::class, UpdateGroup::class]) String? = null

    /**
     * 手机号
     */
    val mobile: String? = null

    /**
     * 状态  0：禁用   1：正常
     */
    val status: Int? = null

    /**
     * 角色ID列表
     */
    @TableField(exist = false)
    var roleIdList: List<Long?>? = null

    /**
     * 创建者ID
     */
    var createUserId: Long? = null

    /**
     * 创建时间
     */
    var createTime: Date? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}