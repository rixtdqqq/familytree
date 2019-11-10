package com.zhu.familytree

import android.app.Application
import android.content.Context

/**
 * @description 全局配置
 *
 * @author QQ657036139
 * @since 2019/11/9
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        private lateinit var context: Context
        fun getContext(): Context {
            return context
        }
    }
}