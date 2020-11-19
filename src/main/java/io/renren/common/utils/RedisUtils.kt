/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.common.utils

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.*
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

/**
 * Redis工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
class RedisUtils {
    @Autowired
    private val redisTemplate: RedisTemplate<String?, Any?>? = null

    @Autowired
    private val valueOperations: ValueOperations<String?, String?>? = null

    @Autowired
    private val hashOperations: HashOperations<String?, String?, Any?>? = null

    @Autowired
    private val listOperations: ListOperations<String?, Any?>? = null

    @Autowired
    private val setOperations: SetOperations<String?, Any?>? = null

    @Autowired
    private val zSetOperations: ZSetOperations<String?, Any?>? = null

    @JvmOverloads
    fun set(key: String?, value: Any?, expire: Long = DEFAULT_EXPIRE) {
        valueOperations!![key!!] = toJson(value)!!
        if (expire != NOT_EXPIRE) {
            redisTemplate!!.expire(key, expire, TimeUnit.SECONDS)
        }
    }

    operator fun <T> get(key: String?, clazz: Class<T>, expire: Long): T? {
        val value = valueOperations!![key!!]
        if (expire != NOT_EXPIRE) {
            redisTemplate!!.expire(key, expire, TimeUnit.SECONDS)
        }
        return value?.let { fromJson(it, clazz) }
    }

    operator fun <T> get(key: String?, clazz: Class<T>): T? {
        return get(key, clazz, NOT_EXPIRE)
    }

    @JvmOverloads
    operator fun get(key: String?, expire: Long = NOT_EXPIRE): String? {
        val value = valueOperations!![key!!]
        if (expire != NOT_EXPIRE) {
            redisTemplate!!.expire(key, expire, TimeUnit.SECONDS)
        }
        return value
    }

    fun delete(key: String?) {
        redisTemplate!!.delete(key!!)
    }

    /**
     * Object转成JSON数据
     */
    private fun toJson(`object`: Any?): String? {
        return if (`object` is Int || `object` is Long || `object` is Float ||
                `object` is Double || `object` is Boolean || `object` is String) {
            `object`.toString()
        } else gson!!.toJson(`object`)
    }

    /**
     * JSON数据，转成Object
     */
    private fun <T> fromJson(json: String?, clazz: Class<T>): T? {
        return gson!!.fromJson(json, clazz)
    }



    companion object {
        /**  默认过期时长，单位：秒  */
        const val DEFAULT_EXPIRE = 60 * 60 * 24.toLong()

        /**  不设置过期时长  */
        const val NOT_EXPIRE: Long = -1
        private val gson: Gson? = Gson()
    }
}