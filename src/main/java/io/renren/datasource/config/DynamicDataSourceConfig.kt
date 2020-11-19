/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */
package io.renren.datasource.config

import io.renren.datasource.properties.DataSourceProperties
import io.renren.datasource.properties.DynamicDataSourceProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import kotlin.collections.HashMap

/**
 * 配置多数据源
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties::class)
class DynamicDataSourceConfig {
    @Autowired
    private val properties: DynamicDataSourceProperties? = null

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    fun dataSourceProperties(): DataSourceProperties? {
        return DataSourceProperties()
    }

    @Bean
    fun dynamicDataSource(dataSourceProperties: DataSourceProperties?): DynamicDataSource? {
        val dynamicDataSource = DynamicDataSource()
        dynamicDataSource.setTargetDataSources(HashMap())

        //默认数据源
        val defaultDataSource = DynamicDataSourceFactory.buildDruidDataSource(dataSourceProperties)
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource!!)
        return dynamicDataSource
    }

    private val dynamicDataSource: Map<Any?, Any?>?
        private get() {
            val dataSourcePropertiesMap = properties!!.datasource
            val targetDataSources: MutableMap<Any?, Any?> = HashMap(dataSourcePropertiesMap!!.size)
            dataSourcePropertiesMap!!.forEach { (k: String?, v: DataSourceProperties?) ->
                val druidDataSource = DynamicDataSourceFactory.buildDruidDataSource(v)
                targetDataSources[k] = druidDataSource
            }
            return targetDataSources
        }
}