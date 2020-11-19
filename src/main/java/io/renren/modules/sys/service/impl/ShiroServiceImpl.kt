/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.modules.sys.service.impl

import io.renren.common.utils.Constant
import io.renren.modules.sys.dao.SysMenuDao
import io.renren.modules.sys.dao.SysUserDao
import io.renren.modules.sys.dao.SysUserTokenDao
import io.renren.modules.sys.entity.SysUserEntity
import io.renren.modules.sys.entity.SysUserTokenEntity
import io.renren.modules.sys.service.ShiroService
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ShiroServiceImpl : ShiroService {
    @Autowired
    private val sysMenuDao: SysMenuDao? = null

    @Autowired
    private val sysUserDao: SysUserDao? = null

    @Autowired
    private val sysUserTokenDao: SysUserTokenDao? = null
    override fun getUserPermissions(userId: Long): Set<String?>? {
        val permsList: MutableList<String?>?

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN.toLong()) {
            val menuList = sysMenuDao!!.selectList(null)
            permsList = ArrayList(menuList!!.size)
            for (menu in menuList) {
                permsList.add(menu!!.perms)
            }
        } else {
            permsList = sysUserDao!!.queryAllPerms(userId)
        }
        //用户权限列表
        val permsSet: HashSet<String?> = HashSet()
        for (perms in permsList!!) {
            if (StringUtils.isBlank(perms)) {
                continue
            }
            permsSet.addAll(Arrays.asList(*perms!!.trim { it <= ' ' }.split(",").toTypedArray()))
        }
        return permsSet
    }

    override fun queryByToken(token: String?): SysUserTokenEntity? {
        return sysUserTokenDao!!.queryByToken(token)
    }

    override fun queryUser(userId: Long?): SysUserEntity? {
        return sysUserDao!!.selectById(userId)
    }
}