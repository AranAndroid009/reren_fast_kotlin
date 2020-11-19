/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.modules.sys.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.renren.common.exception.RRException
import io.renren.common.utils.Constant
import io.renren.common.utils.PageUtils
import io.renren.common.utils.Query
import io.renren.modules.sys.dao.SysUserDao
import io.renren.modules.sys.entity.SysUserEntity
import io.renren.modules.sys.service.SysRoleService
import io.renren.modules.sys.service.SysUserRoleService
import io.renren.modules.sys.service.SysUserService
import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.StringUtils
import org.apache.shiro.crypto.hash.Sha256Hash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysUserService")
class SysUserServiceImpl : ServiceImpl<SysUserDao?, SysUserEntity?>(), SysUserService {
    @Autowired
    private val sysUserRoleService: SysUserRoleService? = null

    @Autowired
    private val sysRoleService: SysRoleService? = null
    override fun queryPage(params: Map<String?, Any?>?): PageUtils? {
        val username = params!!["username"] as String?
        val createUserId = params["createUserId"] as Long?
        val page = this.page(
                Query<SysUserEntity?>().getPage(params as MutableMap<String?, Any?>),
                QueryWrapper<SysUserEntity?>()
                        .like(StringUtils.isNotBlank(username), "username", username)
                        .eq(createUserId != null, "create_user_id", createUserId)
        )
        return PageUtils(page)
    }

    override fun queryAllPerms(userId: Long?): List<String?>? {
        return baseMapper!!.queryAllPerms(userId)
    }

    override fun queryAllMenuId(userId: Long?): List<Long?>? {
        return baseMapper!!.queryAllMenuId(userId)
    }

    override fun queryByUserName(username: String?): SysUserEntity? {
        return baseMapper!!.queryByUserName(username)
    }

    @Transactional
    override fun saveUser(user: SysUserEntity?) {
        user!!.createTime = (Date())
        //sha256加密
        val salt = RandomStringUtils.randomAlphanumeric(20)
        user.password = (Sha256Hash(user.password, salt).toHex())
        user.salt = (salt)
        save(user)

        //检查角色是否越权
        checkRole(user)

        //保存用户与角色关系
        sysUserRoleService!!.saveOrUpdate(user.userId, user.roleIdList)
    }

    @Transactional
    override fun update(user: SysUserEntity?) {
        if (StringUtils.isBlank(user!!.password)) {
            user.password = (null)
        } else {
            user.password = (Sha256Hash(user.password, user.salt).toHex())
        }
        updateById(user)

        //检查角色是否越权
        checkRole(user)

        //保存用户与角色关系
        sysUserRoleService!!.saveOrUpdate(user!!.userId, user.roleIdList)
    }

    override fun deleteBatch(userId: Array<Long?>?) {
        removeByIds(Arrays.asList(userId))
    }

    override fun updatePassword(userId: Long?, password: String?, newPassword: String?): Boolean {
        val userEntity = SysUserEntity()
        userEntity.password = newPassword
        return this.update(userEntity,
                QueryWrapper<SysUserEntity?>().eq("user_id", userId).eq("password", password))
    }

    /**
     * 检查角色是否越权
     */
    private fun checkRole(user: SysUserEntity?) {
        if (user!!.roleIdList == null || user.roleIdList!!.size == 0) {
            return
        }
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (user.createUserId == Constant.SUPER_ADMIN.toLong()) {
            return
        }

        //查询用户创建的角色列表
        val roleIdList = sysRoleService!!.queryRoleIdList(user.createUserId)

        //判断是否越权
        if (!roleIdList!!.containsAll(user.roleIdList!!)) {
            throw RRException("新增用户所选角色，不是本人创建")
        }
    }
}