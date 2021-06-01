package com.example.firstcode

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication

/**
 *
 * -----------------------------------------------------------------
 * Copyright (C) 2021, by Sumpay, All rights reserved.
 * -----------------------------------------------------------------
 * desc: AppController
 * Author: wangjp
 * Email: wangjp1@fosun.com
 * Version: Vx.x.x
 * Create: 2021/5/31 6:04 下午
 *
 *
 */
class AppController : MultiDexApplication() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        const val TOKEN = "ghbkeChNeRd8bvF4"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}